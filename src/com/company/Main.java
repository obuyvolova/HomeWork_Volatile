package com.company;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger counter3 = new AtomicInteger();
    public static AtomicInteger counter4 = new AtomicInteger();
    public static AtomicInteger counter5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        new Thread(() -> {
            for (String text : texts) {
                if (isIncreasingChar(text)) {
                    counterUpdate(text);
                }
            }
        }).start();


        new Thread(() -> {
            for (String text : texts) {
                StringBuilder str = new StringBuilder();
                String newText = str.append(text).reverse().toString();
                if (text.equals(newText)) {
                    counterUpdate(text);
                }
            }
        }).start();


        new Thread(() -> {
            for (String text : texts) {
                if (isSameChars(text)) {
                    counterUpdate(text);
                }
            }
        }).start();


        Thread.sleep(1000);
        System.out.println("The number of words with a length of 3:  " + counter3.get());
        System.out.println("The number of words with a length of 4:  " + counter4.get());
        System.out.println("The number of words with a length of 5:  " + counter5.get());

    }

    private static void counterUpdate(String text) {
        if (text.length() == 3) {
            counter3.getAndIncrement();
        }
        if (text.length() == 4) {
            counter4.getAndIncrement();
        }
        if (text.length() == 5) {
            counter5.getAndIncrement();
        }
    }

    private static boolean isIncreasingChar(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSameChars(String text) {
        char s = text.charAt(0);
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != s) {
                return false;
            }
        }
        return true;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
