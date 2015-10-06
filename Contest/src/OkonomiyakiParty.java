
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class OkonomiyakiParty {

    long MOD = 1000000007;
    long[][][] dp;

    public int count(int[] osize, int m, int k) {
        Arrays.sort(osize);
        int n = osize.length;
        dp = new long[n][n][n];
        for (long[][] a : dp) {
            for (long[] b : a) {
                Arrays.fill(b, -1);
            }
        }
        long result = 0;
        for (int i = 0; i < n; i++) {
            result += count(i + 1, i, 1, m, k, osize);
            result %= MOD;
        }
        return (int) result;
    }

    public long count(int index, int start, int have, int m, int k, int[] o) {
        if (index == o.length) {
            if (have == m) {
                return 1;
            }
            return 0;
        }
        if (have == m) {
            return 1;
        }
        if (dp[index][start][have] != -1) {
            return dp[index][start][have];
        }
        long result = 0;
        if (o[index] - o[start] <= k) {
            result += count(index + 1, start, have + 1, m, k, o);
        }
        result += count(index + 1, start, have, m, k, o);
        result %= MOD;
        return dp[index][start][have] = result;
    }
}
