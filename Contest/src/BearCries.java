
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class BearCries {

    long mod = 1000000007;
    long[][][] dp;

    public int count(String m) {
        dp = new long[200][201][201];
        for (long[][] a : dp) {
            for (long[] b : a) {
                Arrays.fill(b, -1);
            }
        }
        return (int) cal(0, 0, 0, m);
    }

    public long cal(int index, int fill, int open, String m) {
        if (index == m.length()) {
            if (open == 0 && fill == 0) {
                return 1;
            }
            return 0;
        }
        if (dp[index][fill][open] != -1) {
            return dp[index][fill][open];
        }
        long result = 0;
        if (m.charAt(index) == ';') {
            if (fill > 0) {
                result += fill * cal(index + 1, fill - 1, open, m);
                result %= mod;
            }


            result += cal(index + 1, fill, open + 1, m);
            result %= mod;
        } else {
            result += fill * cal(index + 1, fill, open, m);
            result %= mod;
            if (open > 0) {
                result += open * cal(index + 1, fill + 1, open - 1, m);
                result %= mod;
            }
        }
        return dp[index][fill][open] = result;
    }

    public static void main(String[] args) {
        System.out.println(new BearCries().count(";_;;__;_;;"));
    }
}
