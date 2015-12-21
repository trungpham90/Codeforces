
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
public class BoardEscapeDiv2 {

    int[] X = {0, 0, 1, -1};
    int[] Y = {1, -1, 0, 0};
    int[][][] dp;

    public String findWinner(String[] s, int k) {
        int result = 0;
        boolean ok = false;
        dp = new int[s.length][s[0].length()][k + 1];
        for (int[][] a : dp) {
            for (int[] b : a) {
                Arrays.fill(b, -1);
            }
        }
        for (int i = 0; i < s.length && !ok; i++) {
            for (int j = 0; j < s[i].length(); j++) {
                if (s[i].charAt(j) == 'T') {
                    result = cal(i, j, k, s);
                    ok = true;
                    break;
                }
            }
        }

        return result == 0 ? "Bob" : "Alice";
    }

    public int cal(int x, int y, int k, String[] s) {
        if (k == 0) {
            return 0;
        }
        if (s[x].charAt(y) == 'E') {
            return 0;
        }
        if (dp[x][y][k] != -1) {
            return dp[x][y][k];
        }
        int result = 0;
        for (int i = 0; i < 4 && result == 0; i++) {
            int a = X[i] + x;
            int b = Y[i] + y;
            if (a >= 0 && b >= 0 && a < s.length && b < s[0].length() && s[a].charAt(b) != '#') {
                int v = cal(a, b, k - 1, s);
                if (v == 0) {
                    result = 1;
                }
            }
        }
        return dp[x][y][k] = result;
    }
    public static void main(String[] args) {
        
    }
}
