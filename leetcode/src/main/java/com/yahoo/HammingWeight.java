package com.yahoo;

/**
 * Created on 3/29/15.
 */
public class HammingWeight {

    public static int hammingWeight(int n) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            if (n % 2 == 1) {
                count++;
            }
            n = n >> 1;
        }
        return count;
    }

    public static void main(String[] args) {
        for (int n = 0; n < 129; n++) {
            System.out.println(String.format("N=%d, Weight=%d", n, hammingWeight(n)));
        }
    }
}
