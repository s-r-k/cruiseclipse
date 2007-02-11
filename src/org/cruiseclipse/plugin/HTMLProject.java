package org.cruiseclipse.plugin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.TimeOfDay;

import au.id.jericho.lib.html.Element;

public class HTMLProject implements Project {

	private Element element;
	private String previousStatus;
	private boolean statusChanged;
	private String lastUpdatedTime;

	public HTMLProject(Element element) {
		this.element = element;
	}

	public void update(Object element, String lastUpdatedTime) {
		this.element = (Element) element;
		this.lastUpdatedTime = getLastUpDatedTime(lastUpdatedTime);
	}

	String getLastUpDatedTime(String lastUpdatedTime2) {
		return lastUpdatedTime2.substring(lastUpdatedTime2.length()-5, lastUpdatedTime2.length());
	}

	public String getDetails() {
		return "Coming soon!!!";
	}

	public boolean hasStatusChanged() {
		return statusChanged;
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

	public int compareTo(Project arg) {
		return name().compareTo(arg.name());
	}

	public String name() {
		Element projectNameCell = (Element) element.findAllElements("td").get(0);
		projectNameCell = (Element) projectNameCell.findAllElements("a").get(0);
		return projectNameCell.getContentText();
	}

	public String currentBuildLabel() {
		Element labelElement = (Element) element.findAllElements("td").get(4);
		return labelElement.getContentText();
	}

	public String currentStatus() {
		Element projectStatus = (Element) element.findAllElements("td").get(1);
		String currentStatus1 = projectStatus.getContentText();
		if(currentStatus1.equals(previousStatus))
			statusChanged = false;
		else
			statusChanged = true;
		previousStatus = currentStatus1;
		return currentStatus1;
	}

	public boolean isGreen() {
		return currentStatus().equals("passed");
	}

	public String timeSinceLastBuild() {
		StringBuffer message = new StringBuffer("");
		Element timeSinceLastBuild1 = (Element) element.findAllElements("td").get(2);
		String contentText = timeSinceLastBuild1.getContentText();
		DateTime diff = diff(contentText);
		int minutes = 0;
		int hours = 0;
		if (diff != null) {
			if (diff.getHourOfDay() == 5) {
				minutes = diff.getMinuteOfHour() - 30;
				message.append(minutes + " minute(s)");
			}else {
				hours = diff.getHourOfDay() - 5;
				message.append(hours + " hour(s)");
			}
		}
		return message.toString();
	}

	public String link() {
		return "TBD";
	}
}
