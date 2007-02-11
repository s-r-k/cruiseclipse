package org.cruiseclipse.plugin;

import com.sun.syndication.feed.synd.SyndEntryImpl;

public class RSSProject implements Project {

	private SyndEntryImpl entry;

	public RSSProject(SyndEntryImpl entry) {
		this.entry = entry;
	}

	public String name() {
		return entry.getTitle().substring(0, entry.getTitle().indexOf(" "));
	}

	public String currentBuildLabel() {
		return null;
	}

	public String currentStatus() {
		return entry.getDescription().getValue();
	}

	public String getDetails() {
		return null;
	}

	public boolean hasStatusChanged() {
		return false;
	}

	public boolean isGreen() {
		return currentStatus().contains("passed");
	}

	public String timeSinceLastBuild() {
		return "";
	}

	public int compareTo(Project o) {
		return o.name().compareTo(name());
	}

	public void update(Object element, String lastUpdatedTime) {
		this.entry = (SyndEntryImpl) element;
		
	}

	public String link() {
		return entry.getLink();
	}
	
	@Override
	public String toString() {
		return entry.toString();
	}

}
