package org.cruiseclipse.plugin;


public interface Project extends Comparable<Project> {

	String name();

	boolean isGreen();

	String currentStatus();

	String currentBuildLabel();

	String timeSinceLastBuild();

	boolean hasStatusChanged();

//	TODO::
	String getDetails();

	void update(Object element, String lastUpdatedTime);

	String link();

}
