package cruiseclipse.preferences;

import org.cruiseclipse.CruiseclipsePlugin;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public static final String DEFAULT_URL = "http://home.raibledesigns.com:8280/rss";
//	"file:///C:/DevTools/data/CruiseControl Status Page.htm";
//	http://home.raibledesigns.com:8280/rss
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = CruiseclipsePlugin.getDefault()
				.getPreferenceStore();
		store.setDefault(PreferenceConstants.P_ENABLED, true);
		store.setDefault(PreferenceConstants.P_CHOICE, "choice2");
		store.setDefault(PreferenceConstants.P_BUILD_PAGE_URL,
				DEFAULT_URL);
		store.setDefault(PreferenceConstants.P_POLL_INTERVAL,
				60000);
	}

}
