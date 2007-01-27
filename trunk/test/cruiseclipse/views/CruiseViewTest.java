package cruiseclipse.views;

import junit.framework.TestCase;

import org.eclipse.swt.widgets.Table;
import org.eclipse.testfixture.TestProject;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class CruiseViewTest extends TestCase {

	private TestProject project;
	private CruiseView view;

	@Override
	protected void setUp() throws Exception {
		project = new TestProject();
	}
	
	public void testView() throws Exception {
		showView();
		Table table = (Table) view.getViewer().getControl();
		assertEquals(23, table.getItemCount());
	}
	
	@Override
	protected void tearDown() throws Exception {
		project.dispose();
	}

	private void showView() throws PartInitException {
		view = (CruiseView) getPage().showView("cruiseclipse.views.CruiseView");
	}

	private IWorkbenchPage getPage() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		return window.getActivePage();
	}
	
	

}
