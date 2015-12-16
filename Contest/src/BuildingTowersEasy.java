/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class BuildingTowersEasy {

    public int maxHeight(int n, int[] x, int[] t) {
        int result = 0;
        for (int i = 1; i <= n; i++) {
            int s = 0;
            int e = i - 1;
            int re = 0;
            while (s <= e) {
                int mid = (s + e) >> 1;
                boolean ok = true;
                for (int j = 0; j < x.length; j++) {
                    int dist = x[j] > i ? x[j] - i : i - x[j];
                    if (t[j] > mid) {
                        continue;
                    } else if (t[j] + dist < mid) {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    re = Math.max(re, mid);
                    s = mid + 1;
                } else {
                    e = mid - 1;
                }
            }
            result = Math.max(result, re);
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] data = {{4, 7, 13, 15, 18},
        {3, 8, 1, 17, 16}};
    }

}
