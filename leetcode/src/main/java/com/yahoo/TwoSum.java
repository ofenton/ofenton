package com.yahoo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 3/29/15.
 */
public class TwoSum {

    TwoSum() {}

    /**
     * Return the first found answer
     * @param numbers
     * @param target
     * @return
     */
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

    /**
     * Return all the answers found & support duplicates
     * @param numbers
     * @param target
     * @return
     */
    public List<List<Integer>> twoSumAll(int[] numbers, int target) {
        List<List<Integer>> result = new ArrayList();

        if (numbers.length < 2) {
            // Populate & exit
            return result;
        }

        Map<Integer, Set<Integer>> targets = new HashMap();
        for (int i = 0; i < numbers.length - 1; i++) {
            int oneTarget = target - numbers[i];
            if (targets.containsKey(oneTarget)) {
                Set<Integer> values = targets.get(oneTarget);
                if (!values.contains(i)) {
                    values.add(i);
                }
            } else {
                Set<Integer> values = new HashSet();
                values.add(i);
                targets.put(oneTarget, values);
            }

            for (int j = i + 1; j < numbers.length; j++) {
                int targetWeCouldGetTo = numbers[j];
                if (targets.containsKey(targetWeCouldGetTo)) {
                    Set<Integer> allResults = targets.get(targetWeCouldGetTo);
                    for (Integer val : allResults) {
                        if (val == i) {
                            List<Integer> aResult = new ArrayList();
                            aResult.add(i);
                            aResult.add(j);
                            result.add(aResult);
                        }
                    }
                }
            }

        }

        return result;
    }

    public static void main(String args[]) {
        int[] numbers = {3,3,3,3,3,2,4};
        System.out.println(Arrays.toString(numbers));
        System.out.println(Arrays.toString(new TwoSum().twoSum(numbers, 6)));

        System.out.println(Arrays.toString(numbers));
        System.out.println(new TwoSum().twoSumAll(numbers, 6));
    }
}
