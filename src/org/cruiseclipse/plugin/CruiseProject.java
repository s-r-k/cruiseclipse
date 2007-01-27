package org.cruiseclipse.plugin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.TimeOfDay;

import au.id.jericho.lib.html.Element;

public class CruiseProject implements Comparable<CruiseProject> {

	private Element element;
	private String previousStatus;
	private boolean statusChanged;
	private String lastUpdatedTime;

	public CruiseProject(Element element) {
		this.element = element;
	}

	public String getName() {
		Element projectNameCell = (Element) element.findAllElements("td").get(0);
		projectNameCell = (Element) projectNameCell.findAllElements("a").get(0);
		return projectNameCell.getContentText();
	}

	public String getCurrentStatus() {
		Element projectStatus = (Element) element.findAllElements("td").get(1);
		String currentStatus = projectStatus.getContentText();
		if(currentStatus.equals(previousStatus))
			statusChanged = false;
		else
			statusChanged = true;
		previousStatus = currentStatus;
		return currentStatus;
	}

	public String getCurrentBuildLabel() {
		Element labelElement = (Element) element.findAllElements("td").get(4);
		return labelElement.getContentText();
	}

	public void update(Element element, String lastUpdatedTime) {
		this.element = element;
		this.lastUpdatedTime = getLastUpDatedTime(lastUpdatedTime);
	}

	String getLastUpDatedTime(String lastUpdatedTime2) {
		return lastUpdatedTime2.substring(lastUpdatedTime2.length()-5, lastUpdatedTime2.length());
	}

	public boolean isPassed() {
		return getCurrentStatus().equals("passed");
	}

	public String getDetails() {
		return null;
	}

	public boolean hasStatusChanged() {
		return statusChanged;
	}

	public String getTimeSinceLastBuild() {
		StringBuffer message = new StringBuffer("Last Build was ");
		Element timeSinceLastBuild = (Element) element.findAllElements("td").get(2);
		String contentText = timeSinceLastBuild.getContentText();
		DateTime diff = diff(contentText);
		int minutes = 0;
		int hours = 0;
		if (diff != null) {
			if (diff.getHourOfDay() == 5) {
				minutes = diff.getMinuteOfHour() - 30;
				message.append(minutes + " minute(s) ago");
			}else {
				hours = diff.getHourOfDay() - 5;
				message.append(hours + " hour(s) ago");
			}
		}
		return message.toString();
	}

	DateTime diff(String contentText) {
		try {
			TimeOfDay listingGenerated = new TimeOfDay(new SimpleDateFormat("HH:mm").parse(lastUpdatedTime).getTime());
			TimeOfDay lastBuild = new TimeOfDay(getDate(contentText).getTime());
			return listingGenerated.toDateTimeToday().minus(lastBuild.toDateTimeToday().getMillis());
		} catch (ParseException e) {
		}
		return null;
	}

	Date getDate(String contentText) throws ParseException {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(contentText);
	}

	public int compareTo(CruiseProject arg) {
		return getName().compareTo(arg.getName());
	}
}
