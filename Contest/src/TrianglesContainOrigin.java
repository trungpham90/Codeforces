
import java.util.ArrayList;
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
public class TrianglesContainOrigin {

    public long count(int[] x, int[] y) {
        int n = x.length;

        long result = 0;
        for (int i = 0; i < n; i++) {
            ArrayList< Double> pos = new ArrayList();
            ArrayList<Double> neg = new ArrayList();
            for (int j = i + 1; j < n; j++) {

                double a = angle(x[i], y[i], x[j], y[j]);
                if (isClockWise(x[i], y[i], x[j], y[j])) {
                    neg.add(a);
                } else {
                    pos.add(a);
                }
                //   System.out.println(ng + " " + angle(data[i], data[j]) + " " + i + " " + j + " " + Math.toDegrees(a));

            }
            Collections.sort(pos);
            for (double v : neg) {
                double tmp = 2 * Math.PI - v;
                tmp -= Math.PI;
                int s = 0;
                int e = pos.size() - 1;
                int re = -1;
                while (s <= e) {
                    int mid = (s + e) >> 1;
                    if (pos.get(mid) >= tmp) {
                        if (re == -1 || re > mid) {
                            re = mid;
                        }
                        e = mid - 1;
                    } else {
                        s = mid + 1;
                    }
                }
                if (re != -1) {
                    result += pos.size() - re;
                }

            }
        }
        return result;
    }

    public boolean isClockWise(int a, int b, int c, int d) {
        long v = b * c - a * d;
        return v < 0;
    }

    public double angle(int a, int b, int c, int d) {
        double angle = Math.atan2(b, a) - Math.atan2(d, c);
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        if (angle > Math.PI) {
            angle = 2 * Math.PI - angle;
        }
        return angle;
    }

   
}
