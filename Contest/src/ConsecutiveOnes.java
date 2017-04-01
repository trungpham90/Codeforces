
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
public class ConsecutiveOnes {

    long[][][][] dp;

    public long get(long n, int k) {
        dp = new long[2][2][63][63];
        for (long[][][] a : dp) {
            for (long[][] b : a) {
                for (long[] c : b) {
                    Arrays.fill(c, -2);
                }
            }
        }
        return cal(62, 0, 0, 0, n, k);
    }

    public long cal(int index, int last, int cons, int greater, long n, int k) {

        if (greater == 1 && cons == 1) {
            return 0;
        }
        if (index < 0) {
            return  cons == 1 ? 0 : -1;
        }
        if (dp[cons][greater][index][last] != -2) {
            return dp[cons][greater][index][last];
        }
        long result = -1;
        long cur = (((1L << index) & n) != 0) ? 1 : 0;
        for (long i = 0; i < 2; i++) {
            if (cur <= i || greater == 1) {
                int next = (i == 1) ? last + 1 : 0;
                long v = cal(index - 1, next, next >= k ? 1 : cons, i > cur ? 1 : greater, n, k);
                if (v != -1) {
                 //   System.out.println(v + " " + index + " " + last + " " + i);
                    long tmp = (i << index) | v;
                    if (result == -1 || result > (tmp)) {
                        result = tmp;
                    }
                }
            }
        }
        return dp[cons][greater][index][last] = result;
    }

    public static void main(String[] args) {

        System.out.println(new ConsecutiveOnes().get(183881129413730L, 1));
    }
}
