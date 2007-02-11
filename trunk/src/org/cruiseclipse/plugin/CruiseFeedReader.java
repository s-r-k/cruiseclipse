package org.cruiseclipse.plugin;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class CruiseFeedReader implements CruiseControlDataSource {

	private SyndFeed feed;
	private URL feedURL;
	private List<Project> projects = new ArrayList<Project>();

	public CruiseFeedReader(String feedURL) {
		try {
			this.feedURL  = new URL(feedURL);
			refreshFeed();
			loadProjects();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void refreshFeed() {
		try {
			feed = new SyndFeedInput().build(new XmlReader(this.feedURL));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadProjects() {
		System.out.println("Loading project feeds...");
		List<SyndEntryImpl> entries = feed.getEntries();
		for (SyndEntryImpl entry : entries) {
			projects.add(new RSSProject(entry));
		}
	}

	public void updateProjects() {
		System.out.println("Updating project feeds...");
		refreshFeed();
		List<SyndEntryImpl> entries = feed.getEntries();
		for (SyndEntryImpl entry : entries) {
			Project project = getProjectFor(entry.getLink());
			project.update(entry, new Date().toString());
		}
	}	
	
	private Project getProjectFor(String link) {
		for (Project project : projects) {
			if(link.equals(project.link())){
				return project;
			}
		}
		return null;
	}

	public String getCurrentBuildLabel(String projectName) {
		// TODO Auto-generated method stub
		return "Build";
	}

	public String getCurrentStatus() {
		// TODO Auto-generated method stub
		return "Current Status";
	}

	public String getCurrentStatus(String projectName) {
		return getProject(projectName).currentStatus();
	}

	public String getLastUpdatedTime() {
		// TODO Auto-generated method stub
		return "Now";
	}

	public List<String> getProjectNames() {
		List<String> names = new ArrayList<String>();
		for (Project project : projects) {
			names.add(project.name());
		}
		return names;
	}

	public List<Project> getProjects() {
		return projects;
	}

	private Project getProject(String projectName) {
		for (Project project : projects) {
			if(projectName.equals(project.name())){
				return project;
			}
		}
		return null;
	}


}
