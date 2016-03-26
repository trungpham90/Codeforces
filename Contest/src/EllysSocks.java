
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class EllysSocks {

    int[][] dp;

    public int getDifference(int[] s, int p) {
        Arrays.sort(s);
        dp = new int[p + 1][s.length];
        for (int[] a : dp) {
            Arrays.fill(a, -1);
        }
        int result = cal(0, p, s);
        return result;
    }

    public int cal(int index, int left, int[] s) {
        if (left == 0) {
            return 0;
        }

        if (index + 1 >= s.length) {
            return Integer.MAX_VALUE;
        }
        if (dp[left][index] != -1) {
            return dp[left][index];
        }
        int result = cal(index + 1, left, s);
        int v = Integer.max((s[index + 1] - s[index]), cal(index + 2, left - 1, s));

        return dp[left][index] = Integer.min(v, result);
    }
}
