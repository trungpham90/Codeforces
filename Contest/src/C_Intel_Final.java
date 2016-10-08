
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * #
 * @author pttrung
 */
public class C_Intel_Final {

    public static long MOD = 1000000007;
    static int[] X = {1, 1, -1, -1};
    static int[] Y = {1, -1, 1, -1};

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        HashMap<Point, Long> map = cal(n, m);
        Point[] data = new Point[k];
        for (int i = 0; i < k; i++) {
            data[i] = new Point(in.nextInt(), in.nextInt(), 0);
            long result = -1;
            for (int j = 0; j < 4; j++) {
                int x = -X[j];
                int y = -Y[j];
                int minX;
                if (x > 0) {
                    minX = n - data[i].x;
                } else {
                    minX = data[i].x;
                }
                int minY;
                if (y > 0) {
                    minY = m - data[i].y;
                } else {
                    minY = data[i].y;
                }
                int min = Integer.min(minX, minY);
                int a = data[i].x + min * x;
                int b = data[i].y + min * y;
                Point p = new Point(a, b, j);
                if (map.containsKey(p)) {
                    long t = map.get(p) + min;
                    result = (result == -1 || result > t) ? t : result;

                }
            }
            out.println(result);
        }

        out.close();
    }

    static HashMap<Point, Long> cal(int n, int m) {
        int x = 1;
        int y = 1;
        Point cur = new Point(0, 0, 0);

        HashMap<Point, Long> result = new HashMap();
        result.put(cur, 0L);
        long t = 0;
        do {
            int minX, minY;
            if (x > 0) {
                minX = n - cur.x;
            } else {
                minX = cur.x;
            }
            if (y > 0) {
                minY = m - cur.y;
            } else {
                minY = cur.y;
            }
            int min = Integer.min(minX, minY);
            t += min;
            Point nxt = new Point(cur.x + min * x, cur.y + min * y, getDir(minX == min ? -x : x, minY == min ? -y : y));
            result.put(nxt, t);
            cur = nxt;
            if (minX == min) {
                x = -x;
            }
            if (minY == min) {
                y = -y;
            }
        } while (!isCorner(cur, n, m));
        return result;
    }

    static int getDir(int x, int y) {
        int result = -1;
        for (int i = 0; i < 4; i++) {
            if (x == X[i] && y == Y[i]) {
                result = i;
                break;
            }
        }
        return result;
    }

    static boolean isCorner(Point p, int n, int m) {
        if (p.x == 0 && (p.y == 0 || p.y == m)) {
            return true;
        } else if (p.x == n && (p.y == 0 || p.y == m)) {
            return true;
        }
        return false;

    }

    public static int[] KMP(String val) {
        int i = 0;
        int j = -1;
        int[] result = new int[val.length() + 1];
        result[0] = -1;
        while (i < val.length()) {
            while (j >= 0 && val.charAt(j) != val.charAt(i)) {
                j = result[j];
            }
            j++;
            i++;
            result[i] = j;
        }
        return result;

    }

    public static boolean nextPer(int[] data) {
        int i = data.length - 1;
        while (i > 0 && data[i] < data[i - 1]) {
            i--;
        }
        if (i == 0) {
            return false;
        }
        int j = data.length - 1;
        while (data[j] < data[i - 1]) {
            j--;
        }
        int temp = data[i - 1];
        data[i - 1] = data[j];
        data[j] = temp;
        Arrays.sort(data, i, data.length);
        return true;
    }

    public static int digit(long n) {
        int result = 0;
        while (n > 0) {
            n /= 10;
            result++;
        }
        return result;
    }

    public static double dist(long a, long b, long x, long y) {
        double val = (b - a) * (b - a) + (x - y) * (x - y);
        val = Math.sqrt(val);
        double other = x * x + a * a;
        other = Math.sqrt(other);
        return val + other;

    }

    public static class Point implements Comparable<Point> {

        int x, y, d;

        public Point(int start, int end, int d) {
            this.x = start;
            this.y = end;
            this.d = d;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 67 * hash + this.x;
            hash = 67 * hash + this.y;
            hash = 67 * hash + this.d;
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
            if (this.d != other.d) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Point{" + "x=" + x + ", y=" + y + '}';
        }

        @Override
        public int compareTo(Point o) {
            return Integer.compare(x, o.x);
        }
    }

    public static class FT {

        long[] data;

        FT(int n) {
            data = new long[n];
        }

        public void update(int index, long value) {
            while (index < data.length) {
                data[index] += value;
                index += (index & (-index));
            }
        }

        public long get(int index) {
            long result = 0;
            while (index > 0) {
                result += data[index];
                index -= (index & (-index));
            }
            return result;

        }
    }

    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public static long pow(long a, long b, long MOD) {
        if (b == 0) {
            return 1;
        }
        if (b == 1) {
            return a;
        }
        long val = pow(a, b / 2, MOD);
        if (b % 2 == 0) {
            return val * val % MOD;
        } else {
            return val * (val * a % MOD) % MOD;

        }
    }

    static class Scanner {

        BufferedReader br;
        StringTokenizer st;

        public Scanner() throws FileNotFoundException {
            // System.setOut(new PrintStream(new BufferedOutputStream(System.out), true));
            br = new BufferedReader(new InputStreamReader(System.in));
            //  br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("input.txt"))));
        }

        public String next() {

            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
            return st.nextToken();
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public String nextLine() {
            st = null;
            try {
                return br.readLine();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }

        public boolean endLine() {
            try {
                String next = br.readLine();
                while (next != null && next.trim().isEmpty()) {
                    next = br.readLine();
                }
                if (next == null) {
                    return true;
                }
                st = new StringTokenizer(next);
                return st.hasMoreTokens();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }
}
