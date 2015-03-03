/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.sbt.practice.matrices.TextProcessing;

import java.util.Arrays;

/**
 *
 * @author dron
 */
public class CustomLevenstein {

    private static final int MAX_INDEX = 0x1000;
    private static final int TOTAL_DIFF = 100;
    private static final int HALF_DIFF = 50;
    private static final int SAME = 0;
    private static final int MINIMAL_CASE_DIFF = 1;
    private static final int EXTRA_DIFF = 400;

    private static final int[][] weight = new int[MAX_INDEX][MAX_INDEX];

    private static void fillPair(char a1, char a2, int value) {
        weight[a1][a2] = weight[a2][a1] = value;
    }

    static {

        for (char i = 0; i < MAX_INDEX; i++){
            Arrays.fill(weight[i], TOTAL_DIFF);
            weight[i][i] = SAME;
        }

        fillPair('а', 'о', HALF_DIFF); // 50% difference
        fillPair('е', 'э', HALF_DIFF); // 50% difference

        fillPair('ш', 'щ', HALF_DIFF); // 50% difference
        fillPair('в', 'ф', HALF_DIFF); // 50% difference
        fillPair('б', 'п', HALF_DIFF); // 50% difference
        fillPair('д', 'т', HALF_DIFF); // 50% difference

        fillPair('е', 'ё', MINIMAL_CASE_DIFF); // 1% difference
        fillPair('Е', 'Ё', MINIMAL_CASE_DIFF); // 1% difference

        for (char l = 'а'; l <= 'я'; l++ )
        {
            fillPair(l, Character.toUpperCase(l), MINIMAL_CASE_DIFF); // 1% of difference between lower and upper case
        }

    }

    @SuppressWarnings("empty-statement")
    public static int compute(String s, String t) {
        int n = s.length();
        int m = t.length();
        int[][] d = new int[n + 1][m + 1];

        // Step 1
        if (n == 0)
        {
            return m;
        }

        if (m == 0)
        {
            return n;
        }

        // Step 2
        for (int i = 1; i <= n; d[i][0] = d[i++-1][0] + TOTAL_DIFF);
        for (int j = 1; j <= m; d[0][j] = d[0][j++-1] + TOTAL_DIFF);

        // Step 3
        for (int i = 1; i <= n; i++) {
            //Step 4
            for (int j = 1; j <= m; j++) {
                // Step 5
                //int cost = weight[t.charAt(j - 1)][s.charAt(i - 1)];
                // Step 6
                d[i][j] = Math.min(
                    Math.min(d[i - 1][j] + TOTAL_DIFF, d[i][j - 1] + TOTAL_DIFF),
                    d[i - 1][j - 1] + weight[t.charAt(j - 1)][s.charAt(i - 1)]);
            }
        }

        if( n > 2 && m > 2 ) {

            int extraDiff = SAME;

            if (s.charAt(0)=='н' && s.charAt(1)=='е') {
                extraDiff = EXTRA_DIFF;
            }
            if (t.charAt(0)=='н' && t.charAt(1)=='е') {
                extraDiff = EXTRA_DIFF - extraDiff;
            }

            d[n][m] += extraDiff;
        }

        // Step 7
        return d[n][m];
    }

}
