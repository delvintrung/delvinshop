package com.example.delvin.util;

import java.security.SecureRandom;

public class KeyGeneratorUtils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public static String formatKey(String prefix, String randomPart) {
        StringBuilder formatted = new StringBuilder();
        if (prefix != null && !prefix.isEmpty()) {
            formatted.append(prefix.toUpperCase()).append("-");
        }

        for (int i = 0; i < randomPart.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                formatted.append("-");
            }
            formatted.append(randomPart.charAt(i));
        }
        return formatted.toString();
    }
}
