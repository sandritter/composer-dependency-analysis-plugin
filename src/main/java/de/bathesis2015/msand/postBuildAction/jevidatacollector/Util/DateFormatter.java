package de.bathesis2015.msand.postBuildAction.jevidatacollector.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * DateFormatter.java
 * util object that formats timestamp to readable time
 *
 * @author Michael Sandritter
 *
 */
public class DateFormatter {
	
	/**
	 * is formating a timestamp to readble time  dd.MM.yyyy  -  HH:mm:ss
	 * @param timestamp
	 * @return formatted and readable time of the build-start
	 */
	public static String getFormattedTime(long timestamp){
		if(timestamp != -1){			
			Date date = new Date(timestamp); // *1000 is to convert seconds to milliseconds
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY - HH:mm:ss"); // the format of your date
			sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // give a timezone reference for formating (see comment at the bottom
			String formattedDate = sdf.format(date);
			return formattedDate;
		} 
		return "no date available";
	}
}
