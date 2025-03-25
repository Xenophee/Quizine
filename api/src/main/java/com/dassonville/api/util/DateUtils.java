package com.dassonville.api.util;

import java.time.LocalDate;

public class DateUtils {

    public static boolean isNew(LocalDate createdAt, int daysThreshold) {
        return createdAt.isAfter(LocalDate.now().minusDays(daysThreshold));
    }
}
