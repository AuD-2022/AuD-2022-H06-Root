package h06;

import java.util.Calendar;

public class MyDate
{
	private final int year;
	private final int month;
	private final int day;
	private final int hour;
	private final int minute;

	private final long coefficientYear = 4563766470487201L; 	// * 2021
	private final long coefficientMonth = 73232;	// * 12
	private final long coefficientDay = 4;		// * 31
	private final long coefficientHour = 1234;		// * 23
	private final long coefficientMinute = 99998;	// * 59
	private final long coefficientSum = 98924;		// * (2021 + 12 + 31 + 23 + 59)

	private final boolean randomBoolean;

    /**
     * Creates a new date consisting of a calendar and a boolean value deciding how to calculate the hash value.
     * @param date The date.
     * @param randomBoolean The boolean value used to determine the calculation of the hash value.
     */
	public MyDate(Calendar date, boolean randomBoolean)
	{
		year = date.get(Calendar.YEAR);
		month = date.get(Calendar.MONTH);
		day = date.get(Calendar.DAY_OF_MONTH);
		hour = date.get(Calendar.HOUR_OF_DAY);
		minute = date.get(Calendar.MINUTE);

		this.randomBoolean = randomBoolean;
	}

	/**
	 * Returns the year of the date stored in this object.
	 * @return The year of the date stored in this object.
	 */
	public int getYear()
	{
		return year;
	}

	/**
	 * Returns the month of the date stored in this object.
	 * @return The month of the date stored in this object.
	 */
	public int getMonth()
	{
		return month;
	}

	/**
	 * Returns the day of month of the date stored in this object.
	 * @return The day of month of the date stored in this object.
	 */
	public int getDay()
	{
		return day;
	}

	/**
	 * Returns the hour of the date stored in this object.
	 * @return The hour of the date stored in this object.
	 */
	public int getHour()
	{
		return hour;
	}

	/**
	 * Returns the minute of the date stored in this object.
	 * @return The minute of the date stored in this object.
	 */
	public int getMinute() { return minute; }

    /**
     * Return the boolean value set in the constructor.
     * @return The boolean value set in the constructor.
     */
    public boolean getBool() { return randomBoolean; }

	@Override
	public int hashCode()
	{
		if (randomBoolean) {
			return (int)
        Math.floorMod(
        	 Math.floorMod((coefficientYear * year), (long)Integer.MAX_VALUE)
			+ Math.floorMod((coefficientMonth * month), Integer.MAX_VALUE)
			+ Math.floorMod((coefficientDay * day), Integer.MAX_VALUE)
			+ Math.floorMod((coefficientHour * hour), Integer.MAX_VALUE)
			+ Math.floorMod((coefficientMinute * minute), Integer.MAX_VALUE)
			, Integer.MAX_VALUE);
		}

		return (int)Math.floorMod(((year + month + day + hour + minute) * coefficientSum), Integer.MAX_VALUE);
	}

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(MyDate.class)) {
            return obj.hashCode() == this.hashCode();
        }
        return false;
    }
}
