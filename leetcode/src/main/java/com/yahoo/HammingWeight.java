package com.yahoo;

/**
 * Created on 3/29/15.
 *
 * For counting the number of bits set in an int
 */
public class HammingWeight {

    private static Integer NUM_BITS_IN_AN_INT = 32;

    public static int hammingWeight(int n) {
        int count = 0;
        for (int i = 0; i < NUM_BITS_IN_AN_INT; i++) {
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
