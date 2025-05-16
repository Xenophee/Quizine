package com.dassonville.api.util;


import org.springframework.util.StringUtils;

public class TextUtils {

    public static String normalizeText(String input) {
        if (input == null) return null;

        return StringUtils.capitalize(
                input.trim().replaceAll("\\s+", " ")
        );
    }
}
