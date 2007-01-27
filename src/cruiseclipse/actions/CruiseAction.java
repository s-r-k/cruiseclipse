package cruiseclipse.actions;

import org.cruiseclipse.CruiseclipsePlugin;
import org.cruiseclipse.plugin.CruiseMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import cruiseclipse.preferences.PreferenceConstants;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class CruiseAction {
	private IWorkbenchWindow window;
	private IPreferenceStore preferenceStore = CruiseclipsePlugin.getDefault().getPreferenceStore();
	private final CruiseMonitor cruiseMonitor = CruiseclipsePlugin.getDefault().getCruiseMonitor();;
	

	public CruiseAction() {
	}
	
	/**
	 * The constructor. - for tests
	 * @param monitor 
	 */
	public CruiseAction(CruiseMonitor monitor) {
		CruiseclipsePlugin.getDefault().setCruiseMonitor(monitor);
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		if(preferenceStore.getBoolean(PreferenceConstants.P_ENABLED))
			showBuildStatus();
		else
			showDefaultMessage();
			
	}

	private void showDefaultMessage() {
		MessageDialog.openInformation(
				window.getShell(),
				"Cruiseclipse Plug-in",
				"This Plugin is currently disabled.");
	}

	private void showBuildStatus() {
		MessageDialog.openInformation(
			window.getShell(),
			"Cruiseclipse Plug-in",
			"Build Status: " + getCurrentBuildStatus() + "\n" + cruiseMonitor.getLastUpdatedTime());
	}

	String getCurrentBuildStatus() {
		return cruiseMonitor.currentStatus();
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

}