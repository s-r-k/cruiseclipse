package org.cruiseclipse;

import java.net.MalformedURLException;

import org.cruiseclipse.plugin.CruiseMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import cruiseclipse.preferences.PreferenceConstants;

/**
 * The main plugin class to be used in the desktop.
 */
public class CruiseclipsePlugin extends AbstractUIPlugin {

	//The shared instance.
	private static CruiseclipsePlugin plugin;
	public CruiseMonitor cruiseMonitor;
	
	/**
	 * The constructor.
	 */
	public CruiseclipsePlugin() {
		cruiseMonitor = new CruiseMonitor();
		plugin = this;
	}

	public CruiseMonitor getCruiseMonitor(){
		return cruiseMonitor;
	}

	public void setCruiseMonitor(CruiseMonitor cruiseMonitor) {
		this.cruiseMonitor = cruiseMonitor;
	}
	
	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		initCruiseMonitor();
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static CruiseclipsePlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("Cruiseclipse", path);
	}
	
	public void initCruiseMonitor() {
		String url = getPreferenceStore().getString(PreferenceConstants.P_BUILD_PAGE_URL);
		int interval = getPreferenceStore().getInt(PreferenceConstants.P_POLL_INTERVAL);
		try {
			cruiseMonitor.init(url, interval);
		} catch (MalformedURLException e) {
			System.out.println("Wrong URL");
			e.printStackTrace();
		}
	}
}