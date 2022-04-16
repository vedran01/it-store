package com.itstore.core.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class Util {

    public static Date nowPlusDuration(Duration duration) {
       return Date.from(ZonedDateTime.now().plus(duration).toInstant());
    }

    public static boolean isDateAfterNow(Date date) {
        return LocalDateTime.now()
                .isAfter(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }
}
