package name.cphillipson.experimental.gwt.client.util;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

// Client-side time utility

public class TimeUtil {

    private static DateTimeFormat ymdFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
    private static DateTimeFormat isoLikeFormat = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
    private static DateTimeFormat isoLikeNoTzFormat = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static DateTimeFormat dayFormat = DateTimeFormat.getFormat("yyyyMMdd");
    private static DateTimeFormat hourFormat = DateTimeFormat.getFormat("HH");

    private static int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    public static String dateToIso(Date date) {
        if (date == null) {
            return null;
        }

        final String str = isoLikeFormat.format(date);

        // Add a colon to the zone portion
        final String part1 = str.substring(0, 26);
        final String part2 = str.substring(26, 28);

        final String iso = part1.replace(' ', 'T') + ":" + part2;

        return iso;
    }

    public static String dateToYMD(Date date) {
        return ymdFormat.format(date);
    }

    public static Date isoToDate(String iso) {
        if (iso == null) {
            return null;
        }

        // Remove colon from the zone portion and 'T' from the time portion
        final String part1 = iso.substring(0, 26);
        final String part2 = iso.substring(27, 29);

        final String str = part1.replace('T', ' ') + part2;

        final Date date = isoLikeFormat.parse(str);

        return date;
    }

    public static String dateToDay(Date date) {
        return dayFormat.format(date);
    }

    public static String dateToHour(Date date) {
        return hourFormat.format(date);
    }

    public static int hoursInDay(Date date) {
        // Determine whether 23,24, or 25 hour day.
        final int year = date.getYear() + 1900;
        final int month = date.getMonth(); // zero-based
        final int day = date.getDate();

        int numDays = daysInMonth[month];

        // Adjust for leap year
        if (month == 1) {
            if (year % 400 == 0 || year % 4 == 0 && year % 100 != 0) {
                numDays++;
            }
        }

        // Determine day/month/year values for following day.
        int nextYear = year;
        int nextMonth = month;
        int nextDay = day + 1;
        if (nextDay > numDays) {
            nextDay = 1;
            nextMonth++;
            if (nextMonth > 11) {
                nextMonth = 0;
                nextYear++;
            }
        }

        final Date startDate = new Date(year - 1900, month, day);
        final Date nextDate = new Date(nextYear - 1900, nextMonth, nextDay);

        final long millisDay = nextDate.getTime() - startDate.getTime();

        final long millis24hours = 1000 * 60 * 60 * 24;
        final long leapSecondFudge = 100;
        if (millisDay > millis24hours + leapSecondFudge) {
            return 25;
        } else if (millisDay < millis24hours - leapSecondFudge) {
            return 23;
        } else {
            return 24;
        }
    }

    // These methods will produce "incorrect" dates on the client-side,
    // which are a useful fudge when dealing with clients that are running in
    // a different timezone than the server. Should not be needed in production.
    // These are entirely due to GWTs limitation of no timezone support on the
    // client-side for DateFields. Use carefully!!!!

    public static String dateToIsoNoTz(Date date) {
        if (date == null) {
            return null;
        }

        final String str = isoLikeNoTzFormat.format(date);

        final String iso = str.replace(' ', 'T');

        return iso;
    }

    public static Date isoToDateNoTz(String iso) {
        if (iso == null) {
            return null;
        }

        // Remove 'T' from the time portion
        String str = iso.substring(0, 23);
        str = str.replace('T', ' ');

        final Date date = isoLikeNoTzFormat.parse(str);

        return date;
    }

    public static Date getMarketStartDate() {
        final long oneDay = (long) 1000.0 * 60 * 60 * 24;
        final long oneHour = oneDay / 24;
        final Date today = new Date();
        Date tomorrow = null;
        if (hoursInDay(today) == 24) {
            tomorrow = new Date(System.currentTimeMillis() + oneDay);
        } else if (hoursInDay(today) == 25) {
            tomorrow = new Date(System.currentTimeMillis() + oneDay + oneHour);
        } else if (hoursInDay(today) == 23) {
            tomorrow = new Date(System.currentTimeMillis() + oneDay - oneHour);
        }
        return tomorrow;
    }

}
