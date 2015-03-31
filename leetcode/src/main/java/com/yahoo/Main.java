package com.yahoo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

/**
 * Created on 3/27/15.
 */
public class Main {

    public static List<String> findRepeatedDnaSequences(String s) {
        HashSet<String> duplicates = new HashSet();

        if (s.length() < 10) { return new ArrayList<String>(); }

        HashSet<String> seen = new HashSet();
        for (int i = 0; i < s.length() - 9; i++) {
            String testString = s.substring(i, i+10);
            if (seen.contains(testString)) {
                duplicates.add(testString);
            } else {
                seen.add(testString);
            }
        }

        ArrayList out = new ArrayList<String>();
        out.addAll(duplicates);
        return out;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
        public String toString() {
            return String.format("%d,%s,%s", val, (left == null ? "#" : left), (right == null ? "#" : right));
        }
    }

    public static void flatten(TreeNode root) {
        // BASE CASE... the end!!
        if (root == null) {
            return;
        }

        // BASE CASE for LEFT
        if (root.left == null) {
            flatten(root.right);
        } else if (root.left.right == null) {
            root.left.left = root.right;
            root.right = root.left;
            root.left = null;
            flatten(root.right);
        } else {
            flatten(root.left);
        }
    }

    public static int reverse(int x) {
        Stack<Long> stack = new Stack();

        if (x == Integer.MIN_VALUE) { return 0; }

        long y = Math.abs(x);
        System.out.println("Y:" + y + ":" + x);
        while (y != 0) {
            long mod = y % 10;
            System.out.println("M:" + mod);
            stack.push(mod);

            y = Math.abs(y / 10);
        }

        long newInt = 0;
        int i = 0;
        while (!stack.empty()) {
            long n = (long) stack.pop() * (long) Math.pow(10, i);
            System.out.println("N:" + n + ":" + newInt);
            newInt += n;
            i++;
        }
        System.out.println("NEW:" + newInt);

        if (x > 0 && newInt > Integer.MAX_VALUE) {
            return 0;
        } else if (x < 0 && -newInt < Integer.MIN_VALUE) {
            return 0;
        } else {
            return (x < 0 ? (int) -newInt : (int) newInt);
        }


    }

    public static List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList();
        if (s.length() < 4 || s.length() > 12) {
            return result;
        }

        for (int i = 1; i < 4; i++) {
            String num1 = s.substring(0, i);   // 0, i = 1
            if (!isValidSubIp(num1)) continue; // true
            System.out.println("1:"+num1 + ":" + i + ":" + s.length());

            for (int j = i+1; j <= s.length() - 2; j++) { // i = 1, j = 2
                String num2 = s.substring(i, j);         // 0
                if (!isValidSubIp(num2)) continue;       // true
                System.out.println("2:"+num2);

                for (int k = j+1; k <= s.length() - 1; k++) { // i = 1, j = 2, k = 3
                    String num3 = s.substring(j, k);         // 0
                    if (!isValidSubIp(num3)) continue;
                    System.out.println("3:"+num3);

                    String num4 = s.substring(k);            // k = 3
                    if (!isValidSubIp(num4)) continue;
                    System.out.println("4:"+num4);

                    String ip = num1 + "." + num2
                            + "." + num3 + "." + num4;
                    result.add(ip);
                }
            }
        }

        return result;

    }

    public static boolean isValidSubIp(String sub) {
        try {
            Integer num = Integer.parseInt(sub);
            if (num < 0 || num > 255) return false;
        } catch (Exception e) {
            System.err.println(sub + " is not a valid Integer");
            return false;
        }
        return true;
    }

    public static int findMin(int[] num) {

        return findMin(num, 0, num.length - 1);

    }

    public static int findMin(int[] num, int minPos, int maxPos) {
        if (minPos == maxPos) {
            return num[minPos];
        }

        int posDiff = maxPos - minPos;
        if ((maxPos - minPos) == 1) {
            return (num[minPos] < num[maxPos] ? num[minPos] : num[maxPos]);
        }

        int midPos = minPos + (int) (posDiff / 2);

        System.out.println("MID:" + midPos + ":" + minPos + ":" + ((maxPos - minPos) / 2));
        if (num[midPos] > num[maxPos]) {
            return findMin(num, midPos, maxPos);
        } else {
            return findMin(num, minPos, midPos);
        }
    }

    public static List<String> fullJustify(String[] words, int L) {
        List<String> result = new ArrayList();

        int counter = 0;
        int stringsLength = 0;
        List<String> buffer = new ArrayList();
        for (int i = 0; i < words.length; i++) {
            boolean atLeastOneSpaceRequired = (buffer.size() > 0 ? true : false);
            if ((counter + words[i].length()) + (atLeastOneSpaceRequired ? 1 : 0) <= L) {
                buffer.add(words[i]);
                counter += words[i].length() + (atLeastOneSpaceRequired ? 1 : 0);
                stringsLength += words[i].length();
            } else {
                // Write the line
                int paddingRequired = L - stringsLength;

                StringBuilder sb = new StringBuilder();
                if (buffer.size() == 1) {
                    sb.append(buffer.get(0));
                    for (int p = 0; p < paddingRequired; p++) {
                        sb.append(" ");
                    }
                    result.add(sb.toString());
                } else {
                    // 3 words, 2 spacings, 5 padding: a   b  c  - padding1Count = 1
                    // 4 words, 3 spacings, 6 padding: a  b  c d - padding1Count = 2
                    int spacings = buffer.size() - 1;
                    int padding1Count = (int) (paddingRequired % spacings == 0 ? spacings : paddingRequired % spacings);
                    int padding1Size = (int) Math.ceil(paddingRequired / spacings);
                    System.out.println("PS:" + padding1Size + ":" + paddingRequired + ":" + spacings + ":" + (paddingRequired / spacings));

                    int padding2Size = padding1Size - 1;

                    for (int c = 0; c < buffer.size(); c++) {
                        String s = buffer.get(c);
                        sb.append(s);

                        System.out.println("W:" + s);

                        if (c != buffer.size() - 1) {
                            int wordPad = 0;
                            if (padding1Count > 0) {
                                wordPad = padding1Size;
                                padding1Count--;
                            } else {
                                wordPad = padding2Size;
                            }

                            for (int p = 0; p < wordPad; p++) {
                                sb.append(" ");
                            }
                        }
                    }

                    // Finally add the String
                    result.add(sb.toString());
                }

                // Reset
                buffer.clear();
                buffer.add(words[i]);
                counter = words[i].length();
                stringsLength = words[i].length();
            }

        }

        // Finally clear the buffer (left justify)
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String s : buffer) {
            if (first) { first = false; } else { sb.append(" "); }
            sb.append(s);
        }

        // Final padding
        while (sb.length() < L) {
            sb.append(" ");
        }

        result.add(sb.toString());

        return result;
    }


    public static int uniquePaths(int m, int n) {
        if (m == 1 && n == 1) return 1;

        int total = 0;
        if (m > 0) {
            total += uniquePaths(m - 1, n);
        }
        if (n > 0) {
            total += uniquePaths(m, n - 1);
        }

        return total;
    }

    public static void main(String[] args) {

        //System.out.println(findRepeatedDnaSequences("AAAAAAAAAAA"));


//        TreeNode tnRoot = new TreeNode(1);
//        tnRoot.left = new TreeNode(2);
//        tnRoot.left.right = new TreeNode(3);
//        System.out.println("start:" + tnRoot);
//        flatten(tnRoot);
//        System.out.println("end:" + tnRoot);

        //System.out.println(reverse(-2147483648));

        //System.out.println(restoreIpAddresses("0000"));

        //int[] num = {2, 1};
        //System.out.println(findMin(num));

        //String[] s = {"Here","is","an","example","of","text","justification."};
        //System.out.println(fullJustify(s, 16));

        //solveNQueens(4);

        //System.out.println(uniquePaths(23, 12));

    }
}
