package com.yahoo;

/**
 * Created on 3/29/15.
 */
public class LengthOfLongestSubstring {

    LengthOfLongestSubstring() {
        System.out.println(lengthOfLongestSubstring("aaabbb"));
    }

    public int lengthOfLongestSubstring(String s) {
        int longestSubstring = 0;

        int startSubstring = 0;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.indexOf(s.charAt(i), startSubstring);
            if (idx == i) {
                // Extend substring
                longestSubstring = (1 + idx - startSubstring > longestSubstring ? (1 + idx - startSubstring) : longestSubstring);
            } else {
                // Restart substring
                startSubstring = idx + 1;
            }
        }

        return longestSubstring;
    }

    public static void main(String[] args) {
        new LengthOfLongestSubstring();
    }
}
