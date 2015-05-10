
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class DevuAndPlantingTrees {

    int[] dp;

    public int maximumTreesDevuCanGrow(String[] garden) {
        dp = new int[garden[0].length()];
        Arrays.fill(dp, -1);
        return cal(0, garden);
    }

    public int cal(int index, String[] g) {
        if (index >= g[0].length()) {
            return 0;
        }
        if (dp[index] != -1) {
            return dp[index];
        }
        boolean ok = true;
        int nxt = 0;
        for (int i = 0; i < 2 && index + i < g[0].length(); i++) {
            if (g[0].charAt(index + i) == '*') {
                nxt = index + i + 2;
                ok = false;
                break;
            } else if (g[1].charAt(index + i) == '*') {
                nxt = index + i + 2;
                ok = false;
                break;
            }
        }
        int result ;
        if (ok) {
            result = Math.max(1 + cal(index + 2, g), cal(index + 1, g));
        } else {
           // System.out.println(nxt  + " " + index);
            result = 1 + cal(nxt, g);
        }
        return dp[index] = result;
    }

    public static void main(String[] args) {
        String[] g = {".....*..*..........*............................*",
                      "*..*.............*...*.*.*.*..*.....*.*...*...*.."};
        System.out.println(new DevuAndPlantingTrees().maximumTreesDevuCanGrow(g));
    }
}
