package com.yahoo;

/**
 * Created on 3/27/15.
 */
public class BubbleSort {

    BubbleSort(int[] A) {
        boolean done = false;
        while(!done) {
            done = true;
            for (int j = 0; j < A.length - 1; j++) {
                if (A[j] > A[j + 1]) {
                    swap(A, j, j + 1);
                    done = false;
                }
            }
        }
    }

    private void swap(int[] A, int i, int j) {
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    public static String arrToString(int[] A) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for (int i = 0; i < A.length; i++) {
            if (i != 0) {
                sb.append(",");
            }

            sb.append(A[i]);
        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String args[]) {
        int[] A = {9,8,7,6,5,4,3,2,1};
        System.out.println("INPUT :" + arrToString(A));
        new BubbleSort(A);
        System.out.println("OUTPUT:" + arrToString(A));
    }
}
