package com.nrs.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Util {

    private static final char BLANK_SPACE = ' ';
    private static final Log logger = LogFactory.getLog(Util.class);

    /**
     * Generate a unique identifier.
     */
    public static String uuid() {
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * Convert a String value into an unboxed int. Truncate doubles and return
     * the floor. Tolerate null strings, and invalid integer strings and return
     * 0.
     */
    static public int atoi(String a) {
        int i = 0;
        if (a != null && a.length() > 0) {
            try {
                int dotIndex = a.indexOf('.');
                if (dotIndex > 0)
                    a = a.substring(0, dotIndex);

                i = Integer.parseInt(a);
            } catch (NumberFormatException e) {
                // ignore, return 0
            }
        }
        return i;
    }

    /**
     * Convert a String value into a primitive integer value. If the string is
     * invalid, the value of the "def" argument is returned.
     */
    static public int atoi(String a, int def) {
        int i = atoi(a);

        return (i != 0) ? i : def;
    }

    /**
     * Convert a random object into an unboxed int.
     */
    static public int otoi(Object o) {
        int i = 0;
        if (o != null) {
            if (o instanceof Number)
                i = ((Number) o).intValue();
            else
                i = atoi(o.toString());
        }
        return i;
    }

    /**
     * Convert an unboxed int to a String.
     */
    static public String itoa(int i) {
        return new Integer(i).toString();
    }

    /**
     * Convert a long to String.
     */
    static public String ltoa(long l) {
        return new Long(l).toString();
    }

    /**
     * Convert a float to String.
     */
    static public String ftoa(float f) {
        return new Float(f).toString();
    }

    /**
     * Convert a double to String.
     */
    static public String dtoa(double d) {
        return new Double(d).toString();
    }

    /**
     * Convert a random object into an unboxed boolean.
     */
    static public boolean otob(Object o) {
        boolean b = false;
        if (o != null) {
            if (o instanceof Boolean)
                b = ((Boolean) o).booleanValue();
            else
                b = o.toString().equalsIgnoreCase("true")
                        || o.toString().equals("1");
        }
        return b;
    }

    /**
     * Convert an object to its string representation using {@link #toString()}
     * method.
     *
     * @param o
     *            The object to convert to a string
     *
     * @return The string representation of the given object, or null if the
     *         given object is null.
     */
    public static String otos(Object o) {
        return (!Objects.isNull(o)) ? o.toString() : null;
    }

    /**
     * Return the size of the given collection.
     *
     * @param c
     *            The possibly-null collection to get the size of.
     */
    public static int size(Collection<?> c) {
        return null != c ? c.size() : 0;
    }

    /**
     * This method will return true true is the collection is null or empty
     *
     * @param collection
     * @return boolean value according to the condition
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Return true if the specified Iterator is empty.
     *
     * @param i
     *            Iterator being checked
     * @return true of the given Iterator is empty
     */
    public static boolean isEmpty(Iterator<?> i) {
        return i == null || !i.hasNext();
    }

    /**
     * Return true if the specified array is empty.
     *
     * @param args
     *            array being checked
     * @return true of the given array is empty
     */
    public static boolean isEmpty(Object[] args) {
        return args == null || args.length == 0;
    }

    /**
     * Return true if the specified map is empty.
     *
     * @param map
     *            Map being checked
     * @return true of the given map is empty
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map != null) ? map.isEmpty() : true;
    }

    /**
     * Return true if the specified String is empty.
     *
     * @param src
     *            String being checked
     * @return true of the given map is empty
     */
    public static boolean isEmpty(String src) {
        return Objects.isNull(src) || src.trim().isEmpty();
    }

    //////////////////////////////////////////////////////////////////////
    //
    // Date utilities
    //
    //////////////////////////////////////////////////////////////////////

    /**
     * Format a Date value as a String, using the usual "American" format with
     * the current time zone.
     */
    public static String dateToString(Date src) {
        return dateToString(src, null);
    }

    public static String dateToString(Date src, String format) {
        return dateToString(src, format, TimeZone.getDefault());
    }

    public static String dateToString(Date src, String format, TimeZone tz) {
        DateFormat f = null;

        if (format == null)
            format = "M/d/y H:m:s a z";

        f = new SimpleDateFormat(format);

        f.setTimeZone(tz);
        return f.format(src);
    }

    /**
     * Compute the difference between two dates in {@code Duration}
     *
     * @param start
     *            the start date
     * @param end
     *            the end date
     * @return the total length of the duration
     * @throws Exception
     *             if {@code start} or {@code end} is null or if {@code end} is
     *             before the {@code start}
     */
    private static Duration computeDifferenceDuration(Date start, Date end)
            throws Exception {

        if ((null == start) || (null == end)) {
            throw new Exception("Both dates must be non null.");
        }

        if (end.before(start)) {
            throw new Exception("End date [" + end.toString()
                    + "] is before start date [" + start.toString() + "].");
        }

        return Duration.between(start.toInstant(), end.toInstant());
    }

    /**
     * This returns the difference between two dates in milliseconds
     */
    public static long computeDifferenceMilli(Date start, Date end)
            throws Exception {
        return computeDifferenceDuration(start, end).toMillis();
    }

    /**
     * This returns the difference between two dates in parts such as '11 h 58
     * min 27 s 8 ms'
     */
    public static String computeDifference(Date start, Date end)
            throws Exception {

        Duration diff = computeDifferenceDuration(start, end);

        final int MILLIS_PER_SECOND = 1000;
        final int SECONDS_PER_MINUTE = 60;
        final int MINUTES_PER_HOUR = 60;

        long hours = diff.toHours();
        long minutes = diff.toMinutes() % MINUTES_PER_HOUR;
        // Duration.toSeconds() is private
        long seconds = diff.toMillis() / MILLIS_PER_SECOND % SECONDS_PER_MINUTE;
        long millis = diff.toMillis() % MILLIS_PER_SECOND;

        StringBuilder sb = new StringBuilder();
        if (hours > 0) {
            sb.append(hours).append(" h");
        }
        if (minutes > 0) {
            if (sb.length() > 0)
                sb.append(BLANK_SPACE);
            sb.append(minutes).append(" min");
        }
        if (seconds > 0) {
            if (sb.length() > 0)
                sb.append(BLANK_SPACE);
            sb.append(seconds).append(" s");
        }
        if (millis > 0) {
            if (sb.length() > 0)
                sb.append(BLANK_SPACE);
            sb.append(millis).append(" ms");
        }

        return sb.toString();
    }
}
