
import java.util.Arrays;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class BearBall {

    public int countThrows(int[] x, int[] y) {
        int n = x.length;
        Point[] data = new Point[n];

        int[][] map = new int[n][];
        for (int i = 0; i < n; i++) {
            data[i] = new Point(x[i], y[i]);

        }
        for (int i = 0; i < n; i++) {
            HashMap<Point, Integer> tmp = new HashMap();
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    Point p = minus(data[i], data[j]);
                    if (!tmp.containsKey(p)) {
                        tmp.put(p, j);
                    } else if (dist(data[i], data[tmp.get(p)]) > dist(data[i], data[j])) {
                        tmp.put(p, j);
                    }
                }

            }
            map[i] = new int[tmp.size()];
            int index = 0;
            for (int j : tmp.values()) {
                map[i][index++] = j;
            }
            //  System.out.println(map[i].size());
        }
        int result = 0;
        int[] q = new int[n];
        int[] dist = new int[n];
        int st, ed;
        for (int i = 0; i < n; i++) {
            int count = 1;

            Arrays.fill(dist, -1);
            st = 0;
            ed = 0;
            q[ed++] = i;
            dist[i] = 0;
            while (st < ed && count < n) {
                int node = q[st++];
                for (int j : map[node]) {
                    if (dist[j] == -1) {
                        count++;
                        dist[j] = 1 + dist[node];
                        q[ed++] = j;
                    }
                }
            }
            for (int j = i + 1; j < n; j++) {
                result += 2 * dist[j];
            }
        }
        return result;

    }

    long dist(Point a, Point b) {
        long x = a.x - b.x;
        long y = a.y - b.y;
        return x * x + y * y;
    }

    Point minus(Point a, Point b) {
        int x = a.x - b.x;
        int y = a.y - b.y;

        if (x != 0 && y != 0) {
            int g = gcd(Math.abs(x), Math.abs(y));
            x /= g;
            y /= g;
        } else if (x == 0) {
            if (y > 0) {
                y = Integer.min(1, y);
            } else {
                y = -Integer.min(1, Math.abs(y));
            }
        } else if (x > 0) {
            x = Integer.min(1, x);
        } else {
            x = -Integer.min(1, Math.abs(x));
        }
        return new Point(x, y);
    }

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    class Point {

        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 67 * hash + this.x;
            hash = 67 * hash + this.y;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Point other = (Point) obj;
            if (this.x != other.x) {
                return false;
            }
            if (this.y != other.y) {
                return false;
            }
            return true;
        }

    }

    public static void main(String[] args) {
        int[][] data
                = {{1, 4, 2},
                {1, 10, 7}};
        System.out.println(new BearBall().countThrows(data[0], data[1]));
    }
}
