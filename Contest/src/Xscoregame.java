/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
import java.util.*;

public class Xscoregame {

    int[][] dp;

    public int getscore(int[] A) {
        dp = new int[1 << 8][1 << A.length];
        for (int[] a : dp) {
            Arrays.fill(a, -1);
        }
        return cal(0, 0, A);
    }

    public int cal(int cur, int mask, int[] A) {
        //System.out.println(cur + " " + mask);
        if (Integer.bitCount(mask) == A.length) {
            return cur;
        }
        if (dp[cur][mask] != -1) {
            return dp[cur][mask];
        }
        int result = 0;
        for (int i = 0; i < A.length; i++) {
            if (((1 << i) & mask) == 0) {
                int v = cur + (cur ^ A[i]);
                int nxt = v & 0x3F;
                //  System.out.println(v + " " + nxt);
                int other = (v ^ nxt) << (A.length - Integer.bitCount(mask) - 1);
                other += cal(nxt, mask | (1 << i), A);
                result = Integer.max(result, other);
            }
        }
        return dp[cur][mask] = result;

    }

    public static void main(String[] args) {
        int[] a = {16, 30, 44, 44, 19, 44, 19, 38, 5, 36, 1, 46, 10, 13, 47};
        System.out.println(new Xscoregame().getscore(a));
    }
}
