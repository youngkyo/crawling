package com.youngkyo.crawling.crawling.utils;

public class CrawlingStringUtils {
    private static final int lowerCaseAsciiIndex = 97;
    private static final int upperCaseAsciiIndex = 65;
    private static final int numericAsciiIndex = 48;

    public static String parse(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (isAlphabetOrNumber(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static boolean isAlphabetOrNumber(char c) {
        if(c >= 65 && c <= 90) {
            return true;
        }
        if (c >= 97 && c <= 122) {
            return true;
        }
        return c >= 48 && c <= 57;
    }

    public static String sort(String input) {
        int[] charArr = new int[128];
        for (char c : input.toCharArray()) {
            charArr[c] = 1;
        }

        StringBuilder sb = new StringBuilder();
        for (int count = 0; count < 26; count++) {
            if(charArr[upperCaseAsciiIndex +count] == 1) sb.append((char)(upperCaseAsciiIndex +count));
            if(charArr[lowerCaseAsciiIndex +count] == 1) sb.append((char)(lowerCaseAsciiIndex +count));
        }

        for (int count = 0; count < 10; count++) {
            if(charArr[numericAsciiIndex +count] == 1) sb.append((char)(numericAsciiIndex +count));
        }

        return sb.toString();
    }

    public static String cross(String input) {
        int[] lowerArr = new int[26];
        int[] upperArr = new int[26];
        int[] numericArr = new int[10];
        for (char c : input.toCharArray()) {
            if(c >= 65 && c <= 90) {
                upperArr[c-upperCaseAsciiIndex]=1;
            } else if (c >= 97 && c <= 122) {
                lowerArr[c-lowerCaseAsciiIndex] = 1;
            } else  {
                numericArr[c-numericAsciiIndex]=1;
            }
        }
        int numericIndex = 0;
        StringBuilder sb = new StringBuilder();
        for (int alphabetIndex = 0; alphabetIndex < 26; alphabetIndex++) {
            if (upperArr[alphabetIndex] == 1 || lowerArr[alphabetIndex]== 1) {
                if (upperArr[alphabetIndex] == 1) sb.append((char) (upperCaseAsciiIndex + alphabetIndex));
                if (lowerArr[alphabetIndex] == 1) sb.append((char) (lowerCaseAsciiIndex + alphabetIndex));
                while (numericIndex < 10) {
                    if (numericArr[numericIndex] == 1) {
                        sb.append((char)(numericAsciiIndex + (numericIndex++)));
                        break;
                    }
                    numericIndex++;
                }
            }
        }
        for (; numericIndex < 10; numericIndex++) {
            if (numericArr[numericIndex] == 1) {
                sb.append((char) (numericAsciiIndex + numericIndex));
            }
        }

        return sb.toString();
    }
}
