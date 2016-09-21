
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
public class RepeatString {

    int[][][] dp;

    public int minimalModify(String s) {
        int n = s.length();
        dp = new int[n][n][n];
        for (int[][] a : dp) {
            for (int[] b : a) {
                Arrays.fill(b, -1);
            }
        }
        int result = n;
        for(int i = 1; i < n; i++){
            result = Integer.min(result, cal(0, i, i, s));
        }
        return result;
    }

    public int cal(int x, int y, int start, String s) {
        if (x == start) {
            return s.length() - y;
        }
        if (y == s.length()) {
            return start - x;
        }
        if (dp[x][y][start] != -1) {
            return dp[x][y][start];
        }
        int result;
        if (s.charAt(x) == s.charAt(y)) {
            result = cal(x + 1, y + 1, start, s);
        } else {
            int a = 1 + cal(x + 1, y, start, s);
            int b = 1 + cal(x + 1, y + 1, start, s);
            int c = 1 + cal(x, y + 1, start, s);
            result = Integer.min(Integer.min(a, b), c);
        }
        return dp[x][y][start] = result;
    }
    public static void main(String[] args) {
        String s = "aba";
        System.out.println(new RepeatString().minimalModify(s));
    }
}
