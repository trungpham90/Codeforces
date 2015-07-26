
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class ABBADiv1 {

    int[][][] dp;

    public String canObtain(String initial, String t) {
        dp = new int[2][t.length()][t.length() + 1];
        for (int[][] a : dp) {
            for (int[] b : a) {
                Arrays.fill(b, -2);
            }
        }
        int result = -1;
        for (int i = 0; i < t.length() && i + initial.length() <= t.length(); i++) {
            String v = t.substring(i, i + initial.length());
            if (v.equals(initial)) {
                int re = cal(0, i, initial.length(), t);
                if (re != -1) {
                    result = re;
                    break;
                }
            } else if (eqRev(v, initial)) {
                int re = cal(1, i, initial.length(), t);
                if (re != -1) {
                    result = re;
                    break;
                }
            }
        }
        if (result != -1) {
            return "Possible";
        }
        return "Impossible";
    }

    public int cal(int rev, int s, int e, String t) {
        if (dp[rev][s][e] != -2) {
            return dp[rev][s][e];
        }
        //System.out.println(rev + " " + s + " " + e);
        if (e == t.length()) {
            if (rev == 0) {
                return 1;
            }
            return -1;
        }
        String cur = t.substring(s, s + e);

        if (rev == 1) {
            StringBuilder builder = new StringBuilder(cur);
            cur = builder.reverse().toString();
        }
        String a = cur + 'A';
        String b = cur + 'B';
        b = (new StringBuilder(b)).reverse().toString();
      //  System.out.println(a + " " + b);
        int result = -1;
        for (int i = 0; i < t.length() && i + a.length() <= t.length(); i++) {
            String v = t.substring(i, i + a.length());
            if (v.equals(a)) {
                int re = cal(0, i, a.length(), t);
                if (re != -1) {
                    result = 1;
                }
                break;
            } else if (eqRev(a, v)) {
                int re = cal(1, i, a.length(), t);
                if (re != -1) {
                    result = 1;
                }
                break;
            }
        }
        for (int i = 0; result == -1 && i < t.length() && i + b.length() <= t.length(); i++) {
            String v = t.substring(i, i + b.length());
            if (v.equals(b)) {
                int re = cal(0, i, b.length(), t);
                if (re != -1) {
                    result = 1;
                }
                break;
            } else if (eqRev(b, v)) {
                int re = cal(1, i, b.length(), t);
                if (re != -1) {
                    result = 1;
                }
                break;
            }
        }
        return dp[rev][s][e] = result;

    }

    boolean eqRev(String a, String b) {
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(a.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String[] data = {"A", "ABBA"};
        System.out.println(new ABBADiv1().canObtain(data[0], data[1]));
    }
}
