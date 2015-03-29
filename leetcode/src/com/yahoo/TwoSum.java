package com.yahoo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 3/29/15.
 */
public class TwoSum {

    TwoSum() {}

    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> numMap = new HashMap();

//        for (int i = 0; i < numbers.length; i++) {
//            numMap.put(target - numbers[i], i);
//        }

        for (int i = 0; i < numbers.length; i++) {
            if (numMap.containsKey(numbers[i]) && i != numMap.get(numbers[i])) {
                int[] result = {i, numMap.get(numbers[i])};
                return result;
            }

            numMap.put(target - numbers[i], i);
        }

        // No solution
        int[] empty = new int[0];
        return empty;
    }

    public static void main(String args[]) {
        int[] numbers = {3,2,4};
        System.out.println(Arrays.toString(numbers));
        System.out.println(Arrays.toString(new TwoSum().twoSum(numbers, 6)));
    }
}
