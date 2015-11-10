
import java.util.Arrays;
import java.util.HashSet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class Bracket107 {

    public int yetanother(String s) {
        int result = 0;
        int n = s.length();

        int max = 0;
        HashSet<String> set = new HashSet();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char[] tmp = new char[n];
                int index = 0;
                int k = 0;
                for (; index < j; k++) {
                    if (k != i) {
                        tmp[index++] = s.charAt(k);
                    }
                }
                tmp[index++] = s.charAt(i);

                for (; k < n; k++) {
                    if (k != i) {
                        tmp[index++] = s.charAt(k);
                    }
                }



                // System.out.println("CUR " + new String(tmp));
                if (isValid(tmp)) {
                    String cur = new String(tmp);
                    int v = lcs(s, cur, n);
                    if (v == n) {
                        continue;
                    }
                    
                    if (v == max) {
                        if(set.contains(cur)){
                            continue;
                        }
                        set.add(cur);
                        result++;
                    } else if (v > max) {
                        set.add(cur);
                        max = v;
                        result = 1;
                    }
                }
            }
        }
        System.out.println(max);
        return result;
    }

    private boolean isValid(char[] s) {
        int o = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i] == '(') {
                o++;
            } else {
                o--;
            }
            if (o < 0) {
                return false;
            }
        }
        //   System.out.println(new String(s));
        return o == 0;
    }

    public int lcs(String a, String b, int n) {
        int[][] c = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (a.charAt(i) == b.charAt(j)) {
                    c[i][j] = 1 + (i > 0 && j > 0 ? c[i - 1][j - 1] : 0);
                } else {
                    c[i][j] = Math.max(i > 0 ? c[i - 1][j] : 0, j > 0 ? c[i][j - 1] : 0);
                }
            }
        }
        return c[n - 1][n - 1];
    }

    public static void main(String[] args) {
        System.out.println(new Bracket107().yetanother("((())())"));
    }
}
