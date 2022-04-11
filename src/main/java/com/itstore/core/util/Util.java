package com.itstore.core.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Util {

    public static boolean isDateAfter(Date date) {
        return LocalDateTime.now()
                .isAfter(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }
}
