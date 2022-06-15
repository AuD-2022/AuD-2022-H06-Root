package h06;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import h06.*;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission("H06")
public class H05_Tests
{
	@Test
	public void H05_MyDateBasic()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, 40);
		calendar.set(Calendar.HOUR_OF_DAY, 10);
		calendar.set(Calendar.DAY_OF_MONTH, 19);
		calendar.set(Calendar.MONTH, Calendar.APRIL);
		calendar.set(Calendar.YEAR, 2000);
		MyDate date = new MyDate(calendar, true);

		assertEquals(40, date.getMinute());
		assertEquals(10, date.getHour());
		assertEquals(19, date.getDay());
		assertEquals(3, date.getMonth());
		assertEquals(2000, date.getYear());
	}

	@Test
	public void H05_MyDateHash()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, 40);
		calendar.set(Calendar.HOUR, 10);
		calendar.set(Calendar.DAY_OF_MONTH, 19);
		calendar.set(Calendar.MONTH, Calendar.APRIL);
		calendar.set(Calendar.YEAR, 2000);

		MyDate dateFalse = new MyDate(calendar, false);
		MyDate dateTrue = new MyDate(calendar, true);

		assertEquals(206157616, dateFalse.hashCode());
		assertEquals(470580413, dateTrue.hashCode());
	}
}
