
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class BearFair {

    boolean[][][] dp;

    public String isFair(int n, int b, int[] upTo, int[] quantity) {
        Point[] data = new Point[upTo.length];

        for (int i = 0; i < upTo.length; i++) {
            data[i] = new Point(upTo[i], quantity[i]);
        }
        Arrays.sort(data, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Integer.compare(o1.x, o2.x);
            }
        });
        ArrayList<Point> list = new ArrayList(Arrays.asList(data));
        if (data[data.length - 1].x != b) {
            list.add(new Point(b, n));
        }
        dp = new boolean[1 + n / 2 ][1 + n / 2][list.size()];
        boolean v = cal(0, 0, 0, n, list);
        if (v) {
            return "fair";
        }
        return "unfair";
    }

    boolean cal(int index, int even, int odd, int n, ArrayList<Point> list) {
        if (index == list.size()) {
            if (even == odd && odd == n / 2) {
                return true;
            }
            return false;
        }
        if (even > n / 2 || odd > n / 2) {
            return false;
        }
        if (dp[even][odd][index]) {
            return false;
        }
        dp[even][odd][index] = true;
        Point p = list.get(index);
        int a = 0;
        int b = 0;
        int last = index == 0 ? 0 : list.get(index - 1).x;
        for (int i = last + 1; i <= p.x; i++) {
            if (i % 2 == 0) {
                a++;
            } else {
                b++;
            }
        }
        int total = list.get(index).y - (index == 0 ? 0 : list.get(index - 1).y);
        if (total < 0) {
            return false;
        }
        for (int i = 0; i <= a && i <= total; i++) {
            int left = total - i;
            if (left <= b) {
                if (cal(index + 1, even + i, odd + left, n, list)) {
                    return true;
                }
            }
        }
        return false;
    }

    class Point {

        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {

        //{14, 842, , 
        int n = 8;
        int b = 14;
        //{8, 14, {1, 4, 5, 8, 9, 11, 12, 13}, {0, 2, 2, 4, 4, 6, 7, 7}}

        int[] u = {1, 4, 5, 8, 9, 11, 12, 13};
        int[] q = {0, 2, 2, 4, 4, 6, 7, 7};
        System.out.println(new BearFair().isFair(n, b, u, q));
    }
}
