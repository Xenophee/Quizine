package com.dassonville.api.util;

import com.dassonville.api.constant.AppConstants;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class TestDataFactory {

    private static final int THRESHOLD = AppConstants.NEWNESS_THRESHOLD_DAYS;

    public static Stream<Arguments> provideCreatedAtsAndIsNew() {
        return Stream.of(
                Arguments.of(LocalDateTime.now().minusDays(0), true),
                Arguments.of(LocalDateTime.now().minusDays(THRESHOLD - 1), true),
                Arguments.of(LocalDateTime.now().minusDays(THRESHOLD), false),
                Arguments.of(LocalDateTime.now().minusDays(THRESHOLD + 5), false)
        );
    }
}

