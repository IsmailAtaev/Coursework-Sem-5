package com.example.model.check;

/**
 * @author Ataev Ismayll
 * class Check intended for check String and Integer.
 * All methods static.
 * I thing, what is use thread then will bad ideas or call methods for static. :-)
 * */

public class Check {

    /**
     * Check value na zero, null and  number fort integer.
     */
    public static boolean isNumber(String number) {
        return !number.equals("") && number != null && number.matches("\\d+?");
    }

    public static boolean isFloat(String fltStr) {
        try {
            Float.parseFloat(fltStr);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Check value zero and null.
     * @param str
     */
    public static boolean isString(String str) {
        return !str.equals("") && str != null;
    }
}