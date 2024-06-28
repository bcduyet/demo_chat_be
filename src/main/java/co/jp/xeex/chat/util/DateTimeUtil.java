package co.jp.xeex.chat.util;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import co.jp.xeex.chat.common.AppConstant;

/**
 * The utility class for date and time
 * (add more if needed)
 * 
 * @author v_long
 */
public class DateTimeUtil {
    /**
     * Get the current system date
     * 
     * @return Date
     */
    public static Date getSystemDate() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    /**
     * Get the current system date as a string
     * 
     * @return String representation of the current system date
     *         For example: "Wed Dec 31 23:59:59 JST 2020"
     */
    public static String getSystemDateString() {
        return getSystemDate().toString();
    }

    /**
     * Get the current system date as a string
     * 
     * @return String representation of the current system date
     *         For example: "2020-12-31 23:59:59"
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Get the current system date as a string
     * 
     * @return String representation of the current system date
     *         For example: "2020-12-31 23:59:59"
     */
    public static String getCurrentTimestampString() {
        return getCurrentTimestamp().toString();
    }

    /**
     * Get Zone date time from timestamp
     * 
     * @param timeStamp
     * @return
     */
    public static String getZoneDateTimeString(Timestamp timeStamp) {
        ZonedDateTime zonedDateTime = timeStamp.toInstant().atZone(ZoneId.of(AppConstant.UTC));
        return zonedDateTime.format(DateTimeFormatter.ofPattern(AppConstant.DATE_TIME_ZONE_FORMAT));
    }
}
