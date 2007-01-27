package cruiseclipse.views;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.cruiseclipse.CruiseclipsePlugin;
import org.cruiseclipse.plugin.CruiseListener;
import org.cruiseclipse.plugin.CruiseMonitor;
import org.cruiseclipse.plugin.CruiseProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class CruiseView extends ViewPart {
	private TableViewer viewer;
	private Action refreshAction;
	private Action doubleClickAction;

	class CruiseViewContentProvider implements IStructuredContentProvider, CruiseListener {
		private CruiseMonitor cruiseMonitor = CruiseclipsePlugin.getDefault().getCruiseMonitor();

		public CruiseViewContentProvider() {
			cruiseMonitor.addListener(this);
		}

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {}

		public void dispose() {
			cruiseMonitor.removeListener(this);
		}

		public Object[] getElements(Object parent) {
			return cruiseMonitor.getProjects().toArray();
		}
		public void update() {
			getViewer().getTable().getDisplay().syncExec(new Runnable() {
				public void run() {
					getViewer().refresh();
				}
			});
		}
	}

	static Image createImage(String path) {
		URL url = CruiseclipsePlugin.getDefault().getBundle().getEntry("/");
		ImageDescriptor descriptor = null;
		try {
			descriptor = ImageDescriptor.createFromURL(new URL(url, path));
		} catch (MalformedURLException e) {
			descriptor = ImageDescriptor.getMissingImageDescriptor();
		}
		return descriptor.createImage();
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL);
		setupTable(viewer.getTable());
		viewer.setContentProvider(new CruiseViewContentProvider());
		viewer.setLabelProvider(new CruiseViewLabelProvider());
		viewer.setSorter(new CruiseViewerSorter());
		viewer.setInput(new ArrayList());
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void setupTable(Table table) {
		table.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL| GridData.GRAB_HORIZONTAL));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableColumn column = new TableColumn(table, SWT.CENTER, 0);
		column.setText("Project Name");
		column.setWidth(280);

		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText("CurrentStatus");
		column.setWidth(80);

		column = new TableColumn(table, SWT.LEFT, 2);
		column.setText("Build Label");
		column.setWidth(300);

		column = new TableColumn(table, SWT.LEFT, 3);
		column.setText("Time since last build");
		column.setWidth(200);
		
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("Menu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				CruiseView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		bars.getMenuManager().add(refreshAction);
		bars.getToolBarManager().add(refreshAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(refreshAction);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void makeActions() {
		refreshAction = new Action("Refresh View", ImageDescriptor
				.createFromImage(createImage("icons/refresh_nav.gif"))) {
			public void run() {
				CruiseclipsePlugin.getDefault().getCruiseMonitor().refresh();
				getViewer().refresh();
			}
		};
		refreshAction.setToolTipText("Refresh View");
		
		doubleClickAction = new Action() {
			public void run() {
				Object obj = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
				showMessage(CruiseclipsePlugin.getDefault().getCruiseMonitor()
						.getCurrentStatus(((CruiseProject) obj).getDetails()));
			}
		};		
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(),
				"Cruise Control View", message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public TableViewer getViewer() {
		return viewer;
	}
}