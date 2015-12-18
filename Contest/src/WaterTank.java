/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class WaterTank {

    public double minOutputRate(int[] t, int[] x, int c) {
        double start = 0;
        double end = 1000000;
        double result = -1;
        for (int i = 0; i < 100; i++) {
            double mid = (start + end) / 2;
            double cur = 0;
            boolean ok = true;
            for (int j = 0; j < t.length; j++) {
                
                
                if (x[j] <= mid) {
                    cur -= (mid - x[j]) * t[j];
                    cur = Math.max(0, cur);
                } else {
                    double over = x[j] - mid;
                    double left = c - cur;
                    double need = left / over;
                    if (need <= t[j]) {
                        ok = false;
                        break;
                    } else {
                        cur += over * t[j];
                    }
                }
            }
            //System.out.println(cur + " " + mid);
            if (ok) {
                result = (result == -1 || result > mid) ? mid : result;
                end = mid;
            } else {
                start = mid;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] data = {{3, 3},
        {1, 2}};
        System.out.println(new WaterTank().minOutputRate(data[0], data[1], 3));
    }
}
