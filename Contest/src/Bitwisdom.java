
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class Bitwisdom {

    double[][][] dp;

    public double expectedActions(int[] p) {
        double tmp = (double) p[0] / 100;
        dp = new double[2][p.length][p.length];
        for (double[][] a : dp) {
            for (double[] b : a) {
                Arrays.fill(b, -1.0);
            }
        }
        double result = tmp * cal(1, 1, 1, p) + (1 - tmp) * cal(1, 0, 1, p);
        return result;
    }

    public double cal(int index, int last, int num, int[] p) {
        if (index == p.length) {
            if (num == 1) {
                if (last == 1) {
                    return 1;
                }
            }
            return num - 1;
        }
        if (dp[last][index][num] != -1) {
            return dp[last][index][num];
        }

        double tmp = (double) p[index] / 100;
        double result = 0;
        if (last == 1) {
            result = tmp * cal(index + 1, 1, num, p) + (1 - tmp) * cal(index + 1, 0, num + 1, p);
        } else {
            result = tmp * cal(index + 1, 1, num + 1, p) + (1 - tmp) * cal(index + 1, 0, num, p);
        }
        return dp[last][index][num] = result;
    }

    public static void main(String[] args) {
        int[] p = {37, 63, 23, 94, 12};
        System.out.println(new Bitwisdom().expectedActions(p));
    }
}
