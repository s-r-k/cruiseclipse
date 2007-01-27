package org.cruiseclipse.plugin;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class CruiseMonitor {

	private CruiseControlDataSource cruiseControlDataSource;
	private List<CruiseListener> listeners;

	public CruiseMonitor(String buildPageURL, int pollInterval) throws MalformedURLException {
		listeners = new ArrayList<CruiseListener>();
		cruiseControlDataSource = CCProxy.newInstance(new CruisePageParser(
				buildPageURL));

		Thread thread = new Thread(new BuildPageUpdater(pollInterval));
		thread.start();
	}

	public String currentStatus() {
		return cruiseControlDataSource.getCurrentStatus();
	}

	public String getLastUpdatedTime() {
		return cruiseControlDataSource.getLastUpdatedTime();
	}

	public List<String> getProjectNames() {
		return cruiseControlDataSource.getProjectNames();
	}

	public String getCurrentStatus(String projectName) {
		return cruiseControlDataSource.getCurrentStatus(projectName);
	}

	public List<CruiseProject> getProjects() {
		return cruiseControlDataSource.getProjects();
	}

	public void refresh() {
		cruiseControlDataSource.updateProjects();
	}

	public void addListener(CruiseListener listener) {
		listeners.add(listener);
	}

	public void removeListener(CruiseListener listener) {
		listeners.remove(listener);
	}
	
	class BuildPageUpdater implements Runnable {
		private int interval;
		
		public BuildPageUpdater(int interval) {
			this.interval = interval;
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(interval);
					for (CruiseListener listener: listeners) {
						listener.update();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
