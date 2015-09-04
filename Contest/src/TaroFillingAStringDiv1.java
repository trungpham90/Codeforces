
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class TaroFillingAStringDiv1 {

    public int getNumber(int n, int[] p, String value) {
        Point[] data = new Point[p.length];
        for (int i = 0; i < data.length; i++) {
            data[i] = new Point(p[i] - 1, value.charAt(i));
        }
        Arrays.sort(data);
        Point s = null;

        long result = 1;
        long MOD = (long) (1000000007);
        for (Point i : data) {
            //System.out.println(s + " " + i);
            if (s == null) {
                s = i;
                continue;
            }
            int dist = i.index - s.index - 1;
            if (i.c == s.c) {
                if (dist % 2 == 0) {
                    result *= (dist + 1);
                    result %= MOD;
                }
            } else {
                if (dist % 2 != 0) {
                    result *= (dist + 1);
                    result %= MOD;
                }
            }
            s = i;
        }
        return (int) result;
    }

    class Point implements Comparable<Point> {

        char c;
        int index;

        Point(int index, char c) {
            this.index = index;
            this.c = c;
        }

        @Override
        public String toString() {
            return "Point{" + "c=" + c + ", index=" + index + '}';
        }

        @Override
        public int compareTo(Point o) {
            return index - o.index;
        }
    }

    public static void main(String[] args) {
        int n = 3;
        int[] p = {2, 3};
        String v = "AB";
        System.out.println(new TaroFillingAStringDiv1().getNumber(n, p, v));

    }
}
