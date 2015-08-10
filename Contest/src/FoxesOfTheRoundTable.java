
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class FoxesOfTheRoundTable {

    public int[] minimalDifference(int[] h) {
        Point[] data = new Point[h.length];
        for (int i = 0; i < h.length; i++) {
            data[i] = new Point(i, h[i]);
        }
        Arrays.sort(data);
        int start = 1;
        int end = h.length - 1;
        int[] result = new int[h.length];
        result[0] = data[0].x;
        int index = 1;
        int cur = 0;
        while (index < h.length) {
            if (cur == 0) {
                result[start++] = data[index++].x;
            } else {
                result[end--] = data[index++].x;
            }
            cur = 1 - cur;
        }
        return result;
    }

    class Point implements Comparable<Point> {

        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point o) {
            return y - o.y;
        }
    }
}
