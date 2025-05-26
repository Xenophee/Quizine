package com.dassonville.api.util;


import org.springframework.util.StringUtils;

import java.text.Normalizer;

public class TextUtils {

    public static String normalizeText(String input) {
        if (input == null) return null;

        return StringUtils.capitalize(
                input.trim().replaceAll("\\s+", " ")
        );
    }

    public static String normalizeAndRemoveAccents(String input) {
        if (input == null) return "";

        // 1. Supprime les accents et les diacritiques
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String withoutAccents = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // 2. Supprime ponctuations / caractères spéciaux (garde lettres, chiffres, espaces)
        String alphanumericOnly = withoutAccents.replaceAll("[^\\p{L}\\p{N}\\s]", "");

        // 3. Réduit les espaces multiples à un seul et met en minuscule
        return alphanumericOnly.replaceAll("\\s+", " ").trim().toLowerCase();
    }
}
