package org.cruiseclipse.plugin;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class CruiseMonitor {

	private CruiseControlDataSource cruiseControlDataSource;
	private List<CruiseListener> listeners;
	private volatile Thread updateThread;

	public CruiseMonitor() {
		listeners = new ArrayList<CruiseListener>();
	}

	public void init(CruiseControlDataSource source, int interval) {
		this.cruiseControlDataSource = source;
		updateThread = new Thread(new BuildPageUpdater(interval));
		updateThread.start();
		
	}
	
	@Deprecated
	public void init(String buildPageURL, int pollInterval) throws MalformedURLException {
		init(CCProxy.feedSource(buildPageURL), pollInterval);
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

	public List<Project> getProjects() {
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
			Thread thisThread = Thread.currentThread();
			while (updateThread == thisThread) {
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
