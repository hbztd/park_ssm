package edu.hbuas.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.HashMap;
import java.util.Map;

public class TimeUtil {
    public static long getTimestamp() {
        return DateTime.now().getMillis();
    }

    public static String getLocalDate() {
        LocalDate localDate = new LocalDate();
        return localDate.toString();
    }

    public static String getLocalTime() {
        LocalTime localTime = new LocalTime();
        return localTime.toString("HH:mm:ss");
    }

    public static Map getDateTime() {
        DateTime dateTime = new DateTime();
        LocalDate localDate = dateTime.toLocalDate();
        LocalTime localTime = dateTime.toLocalTime();
        Map<String,Object> map = new HashMap<>();
        map.put("localDate", localDate.toString());
        map.put("localTime", localTime.toString("HH:mm:ss"));
        map.put("timestamp",dateTime.getMillis());
        return map;
    }
}