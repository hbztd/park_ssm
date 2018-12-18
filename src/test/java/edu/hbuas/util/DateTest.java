package edu.hbuas.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Map;

public class DateTest {

    public void dateTest() {
       LocalTime localTime = new LocalTime();
        System.out.println(localTime.toString("HH:mm:ss"));
    }
    @Test
    public void timeTest() {
       DateTime dateTime = new DateTime();
       long l = 1544785467001L;
       long l2 = 1544785227000L;
       System.out.println((l-l2)/(1000*60));
       String a = "F13245";
       long b = new Long(a.split("F")[1]);
       System.out.println(b);
    }
}
