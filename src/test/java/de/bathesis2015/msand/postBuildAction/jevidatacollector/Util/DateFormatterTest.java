package de.bathesis2015.msand.postBuildAction.jevidatacollector.Util;

import static org.junit.Assert.*;
import org.junit.Test;

public class DateFormatterTest {

	@Test
	public void shouldGetFormattedTime()
	{
		long timestamp = 1460920662154L;
		String date = DateFormatter.getFormattedTime(timestamp);
		assertEquals("17.04.2016 - 19:17:42", date);
	}
	
	@Test
	public void shouldGetNoDateAvailableMessage()
	{
		long timestamp = -1;
		String date = DateFormatter.getFormattedTime(timestamp);
		assertEquals("no date available", date);
	}
}
