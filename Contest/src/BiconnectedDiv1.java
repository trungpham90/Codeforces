
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
public class BiconnectedDiv1 {

    final int MIN = -1000000;
    int[] dp;

    public int minimize(int[] w1, int[] w2) {
        int n = w1.length + 1;
        int result = 0;
        for (int i : w1) {
            result += i;
        }
        for (int i : w2) {
            result += i;
        }
        if (n <= 3) {
            return result;
        } else {
            dp = new int[n];

            Arrays.fill(dp, -1);

            return result - cal(1, n, w1, w2);
        }
    }

    int cal(int index, int n, int[] w1, int[] w2) {
        if (index + 1 == n) {
            return 0;
        }
        if (dp[index] != -1) {
            return dp[index];
        }
        int result = cal(index + 1, n, w1, w2);
        if (index + 1 < w1.length) {
            result = Integer.max(result, cal(index + 1, n, w1, w2) + w1[index]);
        }
        if (index + 1 < w2.length) {
            result = Integer.max(result, cal(index + 2, n, w1, w2) + w2[index]);
        }

        return dp[index] = result;
    }

    public static void main(String[] args) {
        int[][] data = {{7243, 7525, 8467, 6351, 9453, 8456, 8175, 5874, 6869, 7400, 6438, 8926, 6876},
        {2642, 1743, 3546, 4100, 2788, 3019, 2678, 1935, 1790, 2674, 3775, 1920}};
        System.out.println(new BiconnectedDiv1().minimize(data[0], data[1]));
    }
}
