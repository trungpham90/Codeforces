
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
public class TrySail {

    public int get(int[] s) {
        int total = 0;
        for (int v : s) {
            total ^= v;
        }
        boolean[][][] dp = new boolean[2][256][256];
        dp[0][0][0] = true;
        int cur = 1;
        for (int k = 0; k < s.length; k++) {
            for (int i = 0; i < 256; i++) {
                for (int j = 0; j < 256; j++) {

                    if (dp[1 - cur][i][j]) {
                       // System.out.println(i + " " + j + " " + (i ^ s[k]) + " " + (j ^ s[k]) + " " + k);
                        dp[cur][i ^ s[k]][j] = true;
                        dp[cur][i][j ^ s[k]] = true;
                        dp[cur][i][j] = true;
                    }

                }
            }
            cur = 1 - cur;
        }
        int result = 0;
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {

                if (dp[1 - cur][i][j]) {

                    int h = total ^ i ^ j;
                   // System.out.println(i + " " + j + " " + h);
                    result = Integer.max(result, i + j + h);
                }

            }
        }
        return result;
    }

    void set(int cur, int a, int b, boolean[][][] dp) {
        int[] v = {a, b};
        Arrays.sort(v);
        dp[cur][v[0]][v[1]] = true;
    }

    public static void main(String[] args) {
        int[] v = {2, 3, 3};
        System.out.println(new TrySail().get(v));
    }
}
