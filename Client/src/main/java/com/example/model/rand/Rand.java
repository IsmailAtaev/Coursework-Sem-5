package com.example.model.rand;

/**
 * @author Ataeyv I.M.
 *  It is class generates random number.
 * */

public class Rand {
    public static int random(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}
