
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class ReplacingDigit {

    public int getMaximumStockWorth(int[] A, int[] d) {
        ArrayList<int[]> list = new ArrayList();
        for (int i = 0; i < A.length; i++) {
            String v = "" + A[i];
            int n = v.length();
            for (int j = 0; j < n; j++) {
                list.add(new int[]{v.charAt(j) - '0', n - j - 1});
            }
        }
        Collections.sort(list, (a, b) -> {
            if (a[1] != b[1]) {
                return Integer.compare(b[1], a[1]);
            }
            return Integer.compare(a[0], b[0]);

        });
        int result = 0;
        for (int[] a : list) {
            boolean found = false;
            for (int i = 9; i > a[0]; i--) {
                if (d[i - 1] > 0) {
                    d[i - 1]--;
                    found = true;
                //    System.out.println(Arrays.toString(a) + " " + i);
                    result += i * Math.pow(10, a[1]);
                    break;
                }
            }
            if (!found) {
                result += a[0] * Math.pow(10, a[1]);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] data = {{100, 90},
        {0, 0, 0, 0, 2, 1, 0, 0, 0}};
        System.out.println(new ReplacingDigit().getMaximumStockWorth(data[0], data[1]));
    }

}
