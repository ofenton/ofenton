package com.yahoo;

/**
 * Created on 3/25/15.
 */
public class Singleton {

    private static Singleton s;

    // Not allowed
    private Singleton() {}

    public static Singleton getInstance() {
        if (s == null) {
            s = new Singleton();
        }

        return s;
    }

}
