package com.softdev.fmsb.auth.application;

import java.util.Random;

public class RandomCodeGenerator {
    public static String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}