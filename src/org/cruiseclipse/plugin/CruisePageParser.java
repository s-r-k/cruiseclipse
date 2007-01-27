package org.cruiseclipse.plugin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import au.id.jericho.lib.html.Element;
import au.id.jericho.lib.html.Source;
import au.id.jericho.lib.html.Util;

public class CruisePageParser implements CruiseControlDataSource {

	private Source buildPage;
	private URL buildPageURL;
	private List<CruiseProject> projects;
	
	public CruisePageParser(String buildPageURL) throws MalformedURLException  {
		this.buildPageURL = new URL(buildPageURL);
		getLatestBuildPage();
		loadProjects();
	}

	private void getLatestBuildPage() {
		try {
			buildPage = new Source(Util.getString(new InputStreamReader(buildPageURL.openStream())));
		} catch (IOException e) {
			System.out.println("Unknown Host");
//			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	void loadProjects() {
		projects = new ArrayList<CruiseProject>();
		Element body = (Element) buildPage.findAllElements("tbody").get(1);
		List<Element> projectElements = body.findAllElements("tr");
		for (Element element : projectElements) {
			projects.add(new CruiseProject(element));
		}
		System.out.println("Projects Loaded");
	}

	public @SuppressWarnings("unchecked")
	void updateProjects() {
		System.out.println("updating projects...");
		getLatestBuildPage();
		Element body = (Element) buildPage.findAllElements("tbody").get(1);
		List<Element> projectElements = body.findAllElements("tr");
		String lastUpdatedTime = getLastUpdatedTime();
		for (int i = 0; i < projectElements.size(); i++) {
			((CruiseProject) projects.get(i)).update(projectElements.get(i), lastUpdatedTime);
		}	
	}

	//TODO
	public String getCurrentStatus() {
		if(buildPage.toString().contains("failed")){
			return "One or more Builds have failed";
		}
		return "Build(s) Passed";
	}

	public List<String> getProjectNames() {
		List<String> names = new ArrayList<String>();
		for (CruiseProject project : projects) {
			names.add(project.getName());
		}
		return names;
	}

	public String getLastUpdatedTime() {
		Element foot = (Element) buildPage.findAllElements("tfoot").get(0);
		Element element = (Element) foot.findAllElements("td").get(0);
		return element.getContentText();
	}

	public String getCurrentStatus(String projectName) {
		return getProject(projectName).getCurrentStatus();
	}

	public String getCurrentBuildLabel(String projectName) {
		return getProject(projectName).getCurrentBuildLabel();
	}

	private CruiseProject getProject(String projectName) {
		for (CruiseProject project : projects) {
			if(projectName.equals(project.getName())){
				return project;
			}
		}
		return null;
	}

	public List<CruiseProject> getProjects() {
		return projects;
	}
}
