package redmine.utils;

import java.util.Random;

public class StringGenerators {
    public static final String ENGLISH_UPPER = "QWERTYUIOPASDFGHJKLZXCVBNM";
    public static final String ENGLISH_LOWER = ENGLISH_UPPER.toLowerCase();
    public static final String ENGLISH = ENGLISH_LOWER + ENGLISH_UPPER;

    public static String randomEmail() {
        return randomEnglishLowerString(8) + "@" + randomEnglishString(8) + "." + randomEnglishLowerString(3);
    }

    public static String randomEnglishLowerString(int length) {
        return randomString(length, ENGLISH_LOWER);
    }

    public static String randomEnglishString(int length) {
        return randomString(length, ENGLISH);
    }

    public static String randomString(int length, String pattern) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(pattern.charAt(new Random().nextInt(length)));
        }
        return sb.toString();
    }

}

