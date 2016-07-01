
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
public class TheLetterN {

    public static long MOD = 1000000007;
    static Point p;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        System.out.println("HE HE");
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        Point[] data = new Point[n];
        Point[][] list = new Point[n][n - 1];
        for (int i = 0; i < n; i++) {
            data[i] = new Point(in.nextInt(), in.nextInt());
        }
        for (int i = 0; i < n; i++) {
            int index = 0;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    list[i][index++] = data[j];
                }
            }
            p = data[i];
            Arrays.sort(list[i], new Comparator<Point>() {

                @Override
                public int compare(Point o1, Point o2) {
                    double o = angleDirected(o1, p, o2);
                    if (o == 0) {
                        return (dist(p, o1) >= dist(p, o2)) ? -1 : 1;
                    }
                    return o < 0 ? -1 : 1;
                }
            });
        }
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                int a = cal(data[i], data[j], list[i]);
                int b = cal(data[j], data[i], list[j]);
//                System.out.println("start " + data[i] + " " + data[j]);
//                for (int k = 0; k < n - 1; k++) {
//                    System.out.println(angle(data[j], data[i], list[i][k]) + " " + orient(data[j], data[i], list[i][k]) + " " + list[i][k]);
//                }
                result += a * b;

            }
        }
        out.println(result);
        out.close();
    }

    static double angleDirected(Point p, Point q, Point r) {
        Point a = new Point(p.x - q.x, p.y - q.y);
        Point b = new Point(r.x - q.x, r.y - q.y);
        double angle = Math.atan2(b.y, b.x) - Math.atan2(a.y, a.x);
        return angle;
    }

    static int cal(Point p, Point q, Point[] data) {
        int st = 0;
        int ed = data.length - 1;
        int max = -1;
        while (st <= ed) {
            int mid = (st + ed) >> 1;
            int o = orient(q, p, data[mid]);
            if (o == 2) {
                if (angle(q, p, data[mid]) <= 90) {
                    max = Math.max(max, mid);
                    st = mid + 1;
                } else {
                    ed = mid - 1;
                }
            } else {
                st = mid + 1;
            }
        }
        st = 0;
        ed = max;
        int min = ed + 1;
        while (st <= ed) {
            int mid = (st + ed) >> 1;
            if (orient(q, p, data[mid]) == 2) {
                min = Math.min(min, mid);
                ed = mid - 1;
            } else {
                st = mid + 1;
            }
        }
        int other = 0;
        for (int i = 0; i < data.length; i++) {

            if (orient(q, p, data[i]) == 2 && angle(q, p, data[i]) <= 90) {
                other++;
            }
        }
        if (other != (max - min + 1)) {
            System.out.println(other + " " + max + " " + min + " " + p + " " + q + " " + Arrays.toString(data));
            for (int k = 0; k < data.length; k++) {
                System.out.println(angle(q, p, data[k]) + " " + orient(q, p, data[k]) + " " + data[k]);
            }
        }
        return other;
    }

    static double angle(Point p, Point q, Point r) {
        Point a = new Point(p.x - q.x, p.y - q.y);
        Point b = new Point(r.x - q.x, r.y - q.y);
        double dot = a.x * b.x + a.y * b.y;
        double result = dot / (Math.sqrt(dist(p, q)) * Math.sqrt(dist(r, q)));
        //System.out.println(p + " " + q + " " + r + " " + Math.toDegrees(result));
        return Math.toDegrees(Math.acos(result));
    }

    static int dist(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are colinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    static int orient(Point p, Point q, Point r) {
        Point a = new Point(p.x - q.x, p.y - q.y);
        Point b = new Point(r.x - q.x, r.y - q.y);
        int val = a.x * b.y - a.y * b.x;

        if (val == 0) {
            return 0; // colinear
        }
        return (val > 0) ? 1 : 2; // clock or counterclock wise
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

        int x, y;

        public Point(int start, int end) {
            this.x = start;
            this.y = end;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 47 * hash + this.x;
            hash = 47 * hash + this.y;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
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
