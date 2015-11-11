
import java.util.Arrays;
import java.util.PriorityQueue;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class SubdividedSlimes {

    int[][] dp;

    public int needCut(int s, int m) {
        if (s * (s - 1) / 2 < m) {
            return -1;
        }
        dp = new int[s + 1][s];
        for (int[] a : dp) {
            Arrays.fill(a, -1);
        }
        for (int i = 1; i < s; i++) {
            int v = cal(s, i);
            if (v >= m) {
                return i;
            }
        }


        return -1;
    }

    public int cal(int s, int turn) {
        if (s == 1) {
            return 0;
        }
        if (turn == 0) {
            return 0;
        }
        if (dp[s][turn] != -1) {
            return dp[s][turn];
        }
        int result = 0;
        int max = 0;

        int i = s / (turn + 1);
        int v = i * (s - i);
        for (int j = 0; j < turn; j++) {
            int a = cal(i, j);
            int b = cal(s - i, turn - j - 1);
            if (result < v + a + b) {
                result = v + a + b;
                max = i;
            }
        }

       // System.out.println(s + " " + turn + " " + max);
        return dp[s][turn] = result;
    }

    public static void main(String[] args) {
        int[] s = {1000, 490000};
        System.out.println(new SubdividedSlimes().needCut(s[0], s[1]));
    }
}
