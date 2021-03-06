package cruiseclipse.preferences;

import org.cruiseclipse.CruiseclipsePlugin;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class CruisePreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public CruisePreferencePage() {
		super(GRID);
		setPreferenceStore(CruiseclipsePlugin.getDefault().getPreferenceStore());
		setDescription("Cruise Control Preferences");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
//		addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH, 
//				"&Directory preference:", getFieldEditorParent()));
		addField(
			new BooleanFieldEditor(
				PreferenceConstants.P_ENABLED,
				"&Enable Plugin",
				getFieldEditorParent()));

//		addField(new RadioGroupFieldEditor(
//				PreferenceConstants.P_CHOICE,
//			"An example of a multiple-choice preference",
//			1,
//			new String[][] { { "&Choice 1", "choice1" }, {
//				"C&hoice 2", "choice2" }
//		}, getFieldEditorParent()));
		
		addField(
			new StringFieldEditor(PreferenceConstants.P_BUILD_PAGE_URL, "CruiseControl Build Page URL:", getFieldEditorParent()));
		
		addField(new IntegerFieldEditor(PreferenceConstants.P_POLL_INTERVAL,"Refresh once every(ms) ",getFieldEditorParent(),6));
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
	@Override
	public boolean performOk() {
		boolean status = super.performOk();
		CruiseclipsePlugin.getDefault().initCruiseMonitor();
		return status;
	}
	
}