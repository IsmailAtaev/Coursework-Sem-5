package com.example.model.rand;

public class Rand {
    public static int random(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}
