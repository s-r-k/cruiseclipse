package org.cruiseclipse.plugin;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.joda.time.DateTime;

public class CruiseMonitorTest extends TestCase {

//	public void testCruiseMonitorPoll() throws Exception {
//		CruiseMonitor monitor =  new CruiseMonitor("file:///C:/DevTools/data/CruiseControl Status Page.htm");
//		monitor.status = monitor.cruiseControlDataSource.getCurrentStatus();
//		assertEquals("Build Passed", monitor.currentStatus());
//	}
	
	@SuppressWarnings("deprecation")
	public void xtestDate() throws Exception {
		Date date = new CruiseProject(null).getDate("14/10/2005 07:53:26");
		assertEquals(Calendar.OCTOBER, date.getMonth());
		assertEquals(7, date.getHours());
	}
	
	public void testname() throws Exception {
		CruiseProject cruiseProject = new CruiseProject(null);
		cruiseProject.update(null, "18:45");
		DateTime dateTime = cruiseProject.diff("14/10/2005 18:43:26");
		
		System.out.println(dateTime.getHourOfDay());
		System.out.println(dateTime.getMinuteOfHour());
		String lastUpDatedTime = cruiseProject.getLastUpDatedTime("listing generated at 14:19");
		System.out.println(lastUpDatedTime);
		
/*		DateTime dateTime =  new DateTime(cruiseProject.getDate("14/10/2005 18:39:26").getTime());
		DateTime dateTime2 =  new DateTime(cruiseProject.getDate("14/10/2005 07:55:26").getTime());
		DateTime dateTime3 = dateTime.minus(timeOfDay.toDateTimeToday().getMillis());
		System.out.println(dateTime3.toString());
*/
	}
}
