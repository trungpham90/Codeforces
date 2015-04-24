
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class CountryGroupHard {

    int[] dp;

    public String solve(int[] a) {
        dp = new int[a.length];
        Arrays.fill(dp,-1);
        int v = cal(0, a);
        if (v == 1) {
            return "Sufficient";
        }else{
            return "Insufficient";
        }
    }

    int cal(int index, int[] a) {
        if (index == a.length) {
            return 1;
        }
        if (dp[index] != -1) {
            return dp[index];
        }
        int result = 0;
        for (int i = 1; i <= a.length - index; i++) {
            boolean ok = true;
            for (int j = 0; j < i; j++) {
                if (a[j + index] != 0 && a[j + index] != i) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                int tmp = cal(index + i, a);
                result += tmp;
                if(result >= 2){
                    result = 2;
                    break;
                }
            }
        }
        return dp[index] = result;
    }

    public static void main(String[] args) {
        int[] a = {2, 2, 0, 2, 2};
        System.out.println(new CountryGroupHard().solve(a));
    }
}
