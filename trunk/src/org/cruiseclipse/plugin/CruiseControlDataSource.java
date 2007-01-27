package org.cruiseclipse.plugin;

import java.util.List;

public interface CruiseControlDataSource {
	
	public void updateProjects();

	public String getCurrentStatus();

	public List<String> getProjectNames();

	public String getLastUpdatedTime();

	public String getCurrentStatus(String projectName);

	public String getCurrentBuildLabel(String projectName);

	public List<CruiseProject> getProjects();

}