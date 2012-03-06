package name.cphillipson.experimental.gwt.server.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.ISODateTimeFormat;


// Converts between Date, DateTime and various string representations with and without respect to the market timezone.

// Conventions:
//
// General conversion method pattern: [type1]To[Type2]([Type1] arg), eg. "dateToIso(Date date)"
//
// Type identifiers used:
//
// date - Java Date
// dateTime - Joda DateTime
// marketDateTime - Joda DateTime with the market timezone set
// iso - ISO 8601 formatted string representation of date, eg. "2010-06-30T05:00-04:00"
// isoDay - ISO 8601 formatted string representation of date, containing only the day, eg. "2010-06-30"
// isoTime - ISO 8601 formatted string representation of date, containing only the time, eg. "05:00-04:00"
// utc - Oracle-friendly string representation of date in UTC timezone, eg. "2010-04-23 13:00"
// day - YYYYMMDD string representation of market day

public class TimeUtil {

    private static String[] normalDayLabels = new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09",
        "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" };
    private static String[] shortDayLabels = new String[] { "01", "03", "04", "05", "06", "07", "08", "09", "10", "11",
        "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" };
    private static String[] longDayLabels = new String[] { "01", "02", "02*", "03", "04", "05", "06", "07", "08", "09",
        "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" };

    // TBD - inject these?
    private static String m_marketTimeZoneId = "America/Chicago";
    private static TimeZone m_marketTimeZone = TimeZone.getTimeZone(m_marketTimeZoneId);
    private static DateTimeZone m_marketTimeZoneJoda = DateTimeZone.forID(m_marketTimeZoneId);

    protected static DateTimeFormatter m_format_oracle = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm");
    private static DateTimeFormatter m_format_isoday = DateTimeFormat.forPattern("YYYY-MM-dd");
    private static DateTimeFormatter m_format_isotime = DateTimeFormat.forPattern("HH:mm:ssZZ");
    private static DateTimeFormatter m_format_day = DateTimeFormat.forPattern("YYYYMMdd");

    private static DateTimeFormatter isoLikeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ssZ");

    // a milli-second instant under the Long.MAX_VALUE, used to create END_OF_TIME
    private static final long MAX_VAL = 9000000000000000000L;

    // 0001.01.01 12:00:00 AM +0000
    public static final Date BEGINNING_OF_TIME;

    // new Date(Long.MAX_VALUE) in UTC time zone
    public static final Date END_OF_TIME;

    // default our time zone to the machine local one.
    private static final DateTimeZone LOCAL_TZ = DateTimeZone.getDefault();

    // equivalent to Oracle's DD-MON-YYYY hh24: mi format
    public static final DateTimeFormatter DAY_ABBREVIATEDMONTH_YEAR_HOUR_MIN_FORMATTER =
            new DateTimeFormatterBuilder()
    .appendDayOfMonth(2)
    .appendLiteral('-')
    .appendMonthOfYearShortText()
    .appendLiteral('-')
    .appendYear(4,4)
    .appendLiteral(' ')
    .appendHourOfDay(2)
    .appendLiteral(':')
    .appendMinuteOfHour(2)
    .toFormatter().withZone(LOCAL_TZ);

    // format of date (i.e., timestamp) is yyyy-MM-dd HH:mm
    public static final DateTimeFormatter YEAR_MONTH_DAY_HOUR_MINUTE_FORMATTER =
            new DateTimeFormatterBuilder()
    .appendYear(4,4)
    .appendLiteral('-')
    .appendMonthOfYear(2)
    .appendLiteral('-')
    .appendDayOfMonth(2)
    .appendLiteral(' ')
    .appendHourOfDay(2)
    .appendLiteral(':')
    .appendMinuteOfHour(2)
    .toFormatter().withZone(LOCAL_TZ);

    // format of date is yyyy-MM-dd
    public static final DateTimeFormatter YEAR_MONTH_DAY_FORMATTER =
            new DateTimeFormatterBuilder()
    .appendYear(4,4)
    .appendLiteral('-')
    .appendMonthOfYear(2)
    .appendLiteral('-')
    .appendDayOfMonth(2)
    .toFormatter().withZone(LOCAL_TZ);

    // format of date is MM/dd/yyyy
    public static final DateTimeFormatter SLASH_SEPARATED_MONTH_DAY_YEAR_FORMATTER =
            new DateTimeFormatterBuilder()
    .appendMonthOfYear(2)
    .appendLiteral('/')
    .appendDayOfMonth(2)
    .appendLiteral('/')
    .appendYear(4,4)
    .toFormatter().withZone(LOCAL_TZ);

    // format of date is just an hour
    public static final DateTimeFormatter HOUR_FORMATTER =
            new DateTimeFormatterBuilder()
    .appendHourOfDay(2)
    .toFormatter().withZone(LOCAL_TZ);

    // format of time is HH:mm
    public static final DateTimeFormatter HOUR_MINUTE_FORMATTER =
            new DateTimeFormatterBuilder()
    .appendHourOfDay(2)
    .appendLiteral(':')
    .appendMinuteOfHour(2)
    .toFormatter().withZone(LOCAL_TZ);

    public static final Map<String, DateTimeFormatter> SUPPORTED_DATETIME_FORMATS = new HashMap<String, DateTimeFormatter>();

    // see http://stackoverflow.com/questions/1743985/best-way-to-get-maximum-date-value-in-java
    static {
        final Calendar c = new GregorianCalendar(DateTimeZone.UTC.toTimeZone());
        c.set(1, 0, 1, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        BEGINNING_OF_TIME = c.getTime();
        c.setTime(new Date(MAX_VAL));
        END_OF_TIME = c.getTime();
        SUPPORTED_DATETIME_FORMATS.put("TIMESTAMP", YEAR_MONTH_DAY_HOUR_MINUTE_FORMATTER);
        SUPPORTED_DATETIME_FORMATS.put("DATE", YEAR_MONTH_DAY_FORMATTER);
        SUPPORTED_DATETIME_FORMATS.put("TIME", HOUR_MINUTE_FORMATTER);
    }

    // formatter renders ISO8601 Date Strings
    private static final DateTimeFormatter ISO_FORMATTER = ISODateTimeFormat.dateTime().withZone(DateTimeZone.getDefault());

    // returns last date known to JDK in String form
    public static final String BEGINNING_OF_TIME_AS_STRING = ISO_FORMATTER.print(BEGINNING_OF_TIME.getTime());

    // returns last date known to JDK in String form
    public static final String END_OF_TIME_AS_STRING = ISO_FORMATTER.print(END_OF_TIME.getTime());


    // Formats a java.util.Date in to iso like format, eg: 2012-01-01T03:00:00-05:00
    public static String convert_UtilDate_IsoDayTimeString(Date date) {
        if (date == null) {
            return null;
        }
        final DateTime dt = TimeUtil.dateToMarketDateTime(date);

        final String str = isoLikeFormat.print(dt);

        // Add a colon to the zone portion
        final String part1 = str.substring(0, 22);
        final String part2 = str.substring(22, 24);

        final String iso = part1.replace(' ', 'T') + ":" + part2;

        return iso;
    }

    // Converts a iso datetime string (eg: 2012-01-01T03:00:00-05:00) in to a Joda DateTime
    public static DateTime convert_IsoString_JodaDateTime(String iso) {
        return TimeUtil.isoToDateTime(iso);
    }

    // Converts a joda DateTime to iso date time String
    public static String convert_JodaDateTime_IsoString(DateTime dateTime) {
        return TimeUtil.dateTimeToIsoNoMillis(dateTime);
    }

    // Get the date part of DateTime object as a DateTime
    public static DateTime getDatePartAsDateTime(DateTime dateTime) {
        final LocalDate localDate = dateTime.toLocalDate();
        final Date date = localDate.toDate();
        return new DateTime(date);
    }

    public static Date getMarketStartDate() {
        return getMarketStartDateTime().toDate();
    }

    public static DateTime getMarketStartDateTime() {
        // Current market date based on now
        DateTime dt = new DateTime();
        dt = dt.withZone(m_marketTimeZoneJoda);
        dt = dt.withMillisOfDay(0); // Set to start of day
        return dt;
    }

    public static boolean isDst(Date date) {
        return isDst(new DateTime(date).withZone(m_marketTimeZoneJoda));
    }

    public static boolean isDst(DateTime dt) {
        return !m_marketTimeZoneJoda.isStandardOffset(dt.toDate().getTime());
    }

    public static int hoursInDay(Date date) {
        return hoursInDay(new DateTime(date).withZone(m_marketTimeZoneJoda));
    }

    public static int hoursInDay(DateTime dt) {
        final DateTime dt0 = dt.withMillisOfDay(0); // Set to start of day
        final DateTime dt1 = dt0.plusDays(1); // Set to end of day
        final Hours hours = Hours.hoursBetween(dt0, dt1);
        return hours.getHours();
    }

    public static int hourInDay(Date dayStart, Date dayTime) {
        return hourInDay(new DateTime(dayStart), new DateTime(dayTime));
    }

    public static int hourInDay(DateTime dayStart, DateTime dayTime) {
        final Hours hours = Hours.hoursBetween(dayStart, dayTime);
        return hours.getHours();
    }

    public static DateTime timeInDay(DateTime dayStart, String hourLabel) {
        final String[] labels = labelsForDay(dayStart);
        int hour = 0;
        for (final String label : labels) {
            if (label.equalsIgnoreCase(hourLabel)) {
                break;
            }
            hour++;
        }
        return dayStart.plusHours(hour);
    }

    public static DateTime timeInDay(DateTime dayStart, int hour) {
        return dayStart.plusHours(hour);
    }

    public static String[] labelsForDay(Date date) {
        return labelsForDay(new DateTime(date).withZone(m_marketTimeZoneJoda));
    }

    public static String[] labelsForDay(DateTime dt) {
        final int hoursInDay = hoursInDay(dt);

        if (hoursInDay == 24) {
            return normalDayLabels;
        } else if (hoursInDay == 23) {
            return shortDayLabels;
        } else if (hoursInDay == 25) {
            return longDayLabels;
        } else {
            return null;
        }
    }

    /* public static Date applyTimeInDay(Date day, Date time) {
        return applyTimeInDay(new DateTime(day), new DateTime(time)).toDate();
    }

    public static DateTime applyTimeInDay(Date day, DateTime time) {
        return applyTimeInDay(new DateTime(day), time);
    }*/

    // This is not working as expected after changing the timezone from America/Ney_York to
    // America/Chicago. Didn't try to fix the code since we may not use this function in SPP

    /*public static DateTime applyTimeInDay(DateTime day, DateTime time) {
        // If time is greater than day, then the time value did not originate from XML
        // and has probably been set to the correct absolute value. No processing is required.
        if (time.toDate().getTime() >= day.toDate().getTime()) {
            return time;
        }

        // Consider the "time" argument as representing an ISO8601 time value or an "xsd:time"
        // type. The ISO8601 time was parsed as if it was a time relative to the start of the
        // epoch (Jan 1, 1970). Since this was originally interpreted as an offset with
        // respect to Standard time, we need to apply an hour adjustment of 1 if the date
        // the time is being applied to is in the DST period (Mar-Nov).

        DateTime dtDay = day.withZone(m_marketTimeZoneJoda);

        long mktInstant = dayToDateTime("19700101").toDate().getTime();
        long gmtInstant = time.toDate().getTime();

        // System.out.println("mktinstant = " + mktInstant);
        // System.out.println("gmtinstant = " + gmtInstant);

        long standardMillisOffset = gmtInstant - mktInstant;
        long dstMillisOffset = standardMillisOffset + 3600000; // One hour

        // System.out.println("offset = " + standardMillisOffset);

        if (isDst(dtDay)) {
            return dtDay.plusMillis((int) dstMillisOffset);
        } else {
            return dtDay.plusMillis((int) standardMillisOffset);
        }
    }*/

    public static boolean isInMonth(Date reference, Date time) {
        // Determine if the two times fall within the same month.
        final DateTime referenceDt = new DateTime(reference).withZone(m_marketTimeZoneJoda);
        final DateTime monthStartDt = referenceDt.withDayOfMonth(1).withMillisOfDay(0);
        final DateTime monthEndDt = monthStartDt.plusMonths(1);

        final long startMillis = monthStartDt.toDate().getTime();
        final long endMillis = monthEndDt.toDate().getTime();
        final long timeMillis = time.getTime();

        return timeMillis >= startMillis && timeMillis < endMillis;
    }

    public static boolean isInMonth(Date reference, Date startTime, Date stopTime) {
        // Determine if the range of time between startTime/stopTime falls within the reference month.
        final DateTime referenceDt = new DateTime(reference).withZone(m_marketTimeZoneJoda);
        final DateTime monthStartDt = referenceDt.withDayOfMonth(1).withMillisOfDay(0);
        final DateTime monthEndDt = monthStartDt.plusMonths(1);

        final long startMonthMillis = monthStartDt.toDate().getTime();
        final long endMonthMillis = monthEndDt.toDate().getTime();

        final long startTimeMillis = startTime.getTime();
        final long stopTimeMillis = stopTime.getTime();

        return startTimeMillis < endMonthMillis && stopTimeMillis >= startMonthMillis;
    }

    // Conversions

    public static DateTime dateToDateTime(Date date) {
        return new DateTime(date).withZone(m_marketTimeZoneJoda);
    }
    public static DateTime dateToDateTime2(Date date) {
        date.getTimezoneOffset();
        return new DateTime(date).withZone(m_marketTimeZoneJoda);
    }

    public static DateTime utcToDateTime(String str) {
        // String is in canonical form of "YYYY-MM-dd HH:mm" representing UTC.
        // This is useful for results from an Oracle query.

        // First convert to ISO8601 format
        final String iso = str.replace(' ', 'T') + "Z";

        final DateTimeFormatter fmt = ISODateTimeFormat.dateTimeParser();
        final DateTime dt = fmt.parseDateTime(iso);
        return dt;
    }

    public static String dateTimeToUtc(DateTime dateTime) {
        // String is in canonical form of "YYYY-MM-DD HH:MI" representing UTC.
        // This is useful for results from an Oracle query.
        final DateTime dt = dateTime.withZone(DateTimeZone.forID("UTC"));
        final DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm");
        return fmt.print(dt);
    }

    public static String dateToIso(Date date) {
        DateTime dt = new DateTime(date);
        dt = dt.withZone(m_marketTimeZoneJoda);
        return dateTimeToIso(dt);
    }

    public static String dateToIsoNoMillies(Date date) {
        DateTime dt = new DateTime(date);
        dt = dt.withZone(m_marketTimeZoneJoda);
        return dateTimeToIsoNoMillis(dt);
    }

    public static String dateTimeToIso(DateTime dateTime) {
        // Assumes the caller has set the preferred time zone
        final DateTime dt = dateTime;
        final DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
        return fmt.print(dt);
    }

    public static String dateTimeToIsoNoMillis(DateTime dateTime) {
        // Assumes the caller has set the preferred time zone
        final DateTime dt = dateTime;
        final DateTimeFormatter fmt = ISODateTimeFormat.dateTimeNoMillis();
        return fmt.print(dt);
    }

    public static String isoToUtc(String iso) {
        final DateTime dt = isoToDateTime(iso);
        return dateTimeToUtc(dt);
    }

    public static DateTime isoToDateTime(String iso) {
        final DateTimeFormatter fmt = ISODateTimeFormat.dateTimeParser().withZone(m_marketTimeZoneJoda);
        return fmt.parseDateTime(iso);
    }

    public static DateTime isoTimeToDateTime(String isoTime) {
        // For time segments, obey the offset info if it exists, otherwise assume local (market) time.
        DateTimeFormatter fmt = null;

        final int plusPos = isoTime.indexOf("+");
        final int minusPos = isoTime.indexOf("-");

        // Some foolery to force retention of the offset in the parsed DateTime.
        // This is important because time values are almost meaningless without a day context.
        // We'll want to be doing arithmetic with these after the parse at which point we will
        // want to know the hour value originally specified and the intended offset from UTC.
        if (plusPos >= 0) {
            final String tzStr = "GMT" + isoTime.substring(plusPos);
            final TimeZone tz = TimeZone.getTimeZone(tzStr);
            fmt = ISODateTimeFormat.timeParser().withZone(DateTimeZone.forTimeZone(tz));
        } else if (minusPos >= 0) {
            final String tzStr = "GMT" + isoTime.substring(minusPos);
            final TimeZone tz = TimeZone.getTimeZone(tzStr);
            fmt = ISODateTimeFormat.timeParser().withZone(DateTimeZone.forTimeZone(tz));
        } else {
            fmt = ISODateTimeFormat.timeParser().withZone(m_marketTimeZoneJoda);
        }
        return fmt.parseDateTime(isoTime);
    }

    public static String dateToIsoDay(Date date) {
        // Formats only the ISO 8601 day portion, eg. "2010-06-10"
        final DateTime dt = dateToMarketDateTime(date);
        return m_format_isoday.print(dt);
    }

    public static String dateTimeToIsoDay(DateTime dateTime) {
        // Formats only the ISO 8601 day portion, eg. "2010-06-10"
        return m_format_isoday.print(dateTime);
    }

    public static String dateToIsoTime(Date date) {
        // Formats only the ISO 8601 time portion, eg. "04:00"
        final DateTime dt = dateToMarketDateTime(date);
        return m_format_isotime.print(dt);
    }

    public static String dateTimeToIsoTime(DateTime dateTime) {
        // Formats only the ISO 8601 time portion, eg. "04:00"
        return m_format_isotime.print(dateTime);
    }

    public static Date isoToDate(String iso) {
        // Parses any ISO 8601 string into a Java Date
        return isoToDateTime(iso).toDate();
    }

    public static Date isoTimeToDate(String isoTime) {
        // Parses any ISO 8601 string into a Java Date
        return isoTimeToDateTime(isoTime).toDate();
    }

    public static String dateToDay(Date date) {
        // Parses YYYYMMDD string into a Java Date
        final DateTime dt = dateToMarketDateTime(date);
        return m_format_day.print(dt);
    }

    public static DateTime dayToDateTime(String day) {
        final DateTimeFormatter fmt = m_format_day.withZone(m_marketTimeZoneJoda);
        return fmt.parseDateTime(day);
    }

    // Non-public convenience methods

    protected static DateTime dateToMarketDateTime(Date date) { // Converts a Java date to a Joda date with the market
        // timezone
        final DateTime dateTime = new DateTime(date);
        return dateTime.withZone(m_marketTimeZoneJoda);
    }
}
