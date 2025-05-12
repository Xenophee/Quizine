package com.dassonville.api.util;

import java.time.LocalDateTime;

public class DateUtils {

    public static boolean isNew(LocalDateTime createdAt, int daysThreshold) {
        return createdAt.isAfter(LocalDateTime.now().minusDays(daysThreshold));
    }
}
