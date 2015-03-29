package com.yahoo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 3/29/15.
 */
public class ThreeSum {

    ThreeSum() {}

    public List<List<Integer>> threeSum(int[] num) {
        List<List<Integer>> result = new ArrayList();
        Arrays.sort(num);
        System.out.println(Arrays.toString(num));

        // a + b + c = 0
        // a + b = -c
        for (int i = 0; i < num.length; i++) {
            int target = -num[i];
            int a = i+1; // 1
            int c = num.length - 1; // 2
            while (a < c) {
                if (num[c] + num[a] > target) {
                    c--;
                } else if (num[c] + num[a] < target) {
                    a++;
                } else {
                    List<Integer> out = new ArrayList();
                    out.add(num[a]);
                    out.add(num[i]);
                    out.add(num[c]);
                    if (!result.contains(out)) {
                        result.add(out);
                    }
                    c--;
                    a++;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        //int[] arr = {-9,-14,-3,2,0,-11,-5,11,5,-5,4,-4,5,-15,14,-8,-11,10,-6,1,-14,-12,-13,-11,9,-7,-2,-13,2,2,-15,1,3,-3,-12,-12,1,-2,6,14,0,-4,-13,-10,-12,8,-2,-8,3,-1,8,4,-6,2,1,10,2,14,4,12,1,4,-2,11,9,-7,6,-13,7,-3,8,14,8,10,12,11,-4,-13,10,14,1,-4,-4,2,5,4,-11,-7,3,8,-10,11,-11,-5,7,13,3,-2,8,-13,2,1,9,-12,-11,6};
        int[] arr = {-9,14,-7,-8,9,1,-10,-8,13,12,6,9,3,-3,-15,-15,1,8,-7,-4,-6,8,2,-10,8,11,-15,3,0,-11,-1,-1,10,0,6,5,-14,3,12,-15,-7,-5,9,11,-1,1,3,-15,-5,11,-12,-4,-4,-2,-6,-10,-6,-6,0,2,-9,14,-14,-14,-9,-1,-2,-7,-12,-13,-15,-4,-3,1,14,3,-12,3,3,-10,-9,-1,-7,3,12,-6,0,13,4,-15,0,2,6,1,3,13,8,-13,13,11,11,13,14,-6};
        System.out.println(Arrays.toString(arr));
        System.out.println(new ThreeSum().threeSum(arr));
    }
}
