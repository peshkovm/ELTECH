package com.eltech.common;

import java.util.Random;

public class Rand {
    static private int[] mas = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    static private Random rand = new Random(System.currentTimeMillis());

    public static int[] rand() {
        int len = mas.length - 1;

        for (int i = len; i > 0; i--) {
            int ind = rand.nextInt(i + 1);
            int k = mas[i];
            mas[i] = mas[ind];
            mas[ind] = k;
        }

        return mas;
    }
}