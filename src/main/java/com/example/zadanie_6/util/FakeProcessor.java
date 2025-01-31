package com.example.zadanie_6.util;

import java.util.Random;


public class FakeProcessor {
    private static final long ONE_SECOND = 1000;
    private static final Random RANDOM = new Random();

    public static void process(long from, long to) {
        try {
            Thread.sleep(ONE_SECOND * RANDOM.nextLong(from, to));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private FakeProcessor() {}
}
