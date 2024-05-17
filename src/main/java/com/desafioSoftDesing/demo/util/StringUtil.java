package com.desafioSoftDesing.demo.util;

public class StringUtil {

    public static String removeNonDigits(String input) {
        return input.replaceAll("\\D", "");
    }
}