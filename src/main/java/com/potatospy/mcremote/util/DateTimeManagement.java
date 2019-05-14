package com.potatospy.mcremote.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

public class DateTimeManagement {


    public static LocalDateTime toLocalDateTime(Calendar calendar){

        TimeZone timeZone = calendar.getTimeZone();
        ZoneId zoneId = timeZone == null ? ZoneId.systemDefault() : timeZone.toZoneId();


        return LocalDateTime.ofInstant(calendar.toInstant(), zoneId);
    }
}
