package com.bootcamp.bank.cuentas.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class Util {

    private static SecureRandom random = new SecureRandom ();
    public static int generateRandomNumber(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static String getCurrentDateAsString(String format) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return currentDate.format(formatter);
    }
    public static LocalDateTime getCurrentLocalDate() {
        return LocalDateTime.now();
    }




}
