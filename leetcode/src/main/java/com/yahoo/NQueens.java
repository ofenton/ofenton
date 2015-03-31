package com.yahoo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 3/29/15.
 */
public class NQueens {

    private final int[] x;
    private List<String[]> result;
    NQueens(int n, boolean printResults) {
        x = new int[n];
        result = new ArrayList();

        List<String[]> result = solveNQueens(n);
        if (printResults) {
            for (String[] s : result) {
                System.out.println("Results:");
                for (int i = 0; i < s.length; i++) {
                    System.out.println(s[i]);
                }
            }
        }
        System.out.println("Num Results for " + n + ": " + result.size());
    }

    public List<String[]> solveNQueens(int n) {
        placeNQueens(0, n);
        return result;
    }

    private boolean canPlaceQueen(int r, int c) {
        for (int i = 0; i < r; i++) {
            if (x[i] == c) return false;
            if ((i - r) == (x[i] - c) ||(i - r) == (c - x[i])) return false;
        }
        return true;
    }

    private String getRowString(int r, int n) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            if (r == i) sb.append("Q");
            else sb.append(".");
        }
        return sb.toString();
    }

    private void placeNQueens(int r, int n) {
        for (int c = 0; c < n; c++) {
            if (canPlaceQueen(r, c)) {
                x[r] = c;

                // END?
                if (r == n - 1) {
                    // int to String
                    String[] out = new String[n];
                    for (int i = 0; i < n; i++) {
                        out[i] = getRowString(x[i], n);
                    }
                    result.add(out);
                } else {
                    placeNQueens(r + 1, n);
                }

            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            new NQueens(i, false);
        }
    }
}
