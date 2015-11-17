
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
public class B_Round_113_Div2 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        Point[] data = new Point[n];
        ArrayList<Point> list = new ArrayList();
        Point min = null;
        for (int i = 0; i < n; i++) {
            data[i] = new Point(in.nextInt(), in.nextInt(), 0);
            if (min == null
                    || (min.y > data[i].y || (min.y == data[i].y && min.x > data[i].x))) {
                min = data[i];
            }
            list.add(data[i]);
        }
        int m = in.nextInt();
        Point[] other = new Point[m];
        for (int i = 0; i < m; i++) {
            other[i] = new Point(in.nextInt(), in.nextInt(), 1);
            if (min == null
                    || (min.y > other[i].y || (min.y == other[i].y && min.x > other[i].x))) {
                min = other[i];
            }
            list.add(other[i]);
        }
        final Point o0 = min;
        list.remove(o0);
        Collections.sort(list, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                int o = orientation(o0, o1, o2);
                if (o == 0) {
                    return (dist(o0, o2) <= dist(o0, o1)) ? -1 : 1;
                }
                return (o == 2) ? -1 : 1;
            }
        });
        list.add(0, o0);
        Stack<Point> s = new Stack<>();

        s.push(list.get(0));
        s.push(list.get(1));
        s.push(list.get(2));
        // System.out.println(list);
        for (int i = 3; i < n + m; i++) {

            while (orientation(nxtToTop(s), s.peek(), list.get(i)) == 1) {
                s.pop();
            }

            s.push(list.get(i));
            // System.out.println(s);
        }
        // System.out.println(s);
        boolean ok = true;
        for (Point p : s) {
            if (p.from == 1) {
                ok = false;
                break;
            }
        }
        Collections.sort(list, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                int o = orientation(o0, o1, o2);
                if (o == 0) {
                    return (dist(o0, o2) >= dist(o0, o1)) ? -1 : 1;
                }
                return (o == 2) ? -1 : 1;
            }
        });

        s = new Stack<>();

        s.push(list.get(0));
        s.push(list.get(1));
        s.push(list.get(2));
        // System.out.println(list);
        for (int i = 3; i < n + m; i++) {

            while (orientation(nxtToTop(s), s.peek(), list.get(i)) == 1) {
                s.pop();
            }

            s.push(list.get(i));
            // System.out.println(s);
        }
        // System.out.println(s);

        for (Point p : s) {
            if (p.from == 1) {
                ok = false;
                break;
            }
        }

        // System.out.println(s);

        if (ok) {
            out.println("YES");
        } else {
            out.println("NO");
        }
        out.close();
    }

    static Point nxtToTop(Stack<Point> s) {
        Point a = s.pop();
        Point result = s.peek();
        s.push(a);
        return result;
    }

    // A utility function to return square of distance between p1 and p2
    static long dist(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are colinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    static int orientation(Point p, Point q, Point r) {
        long val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

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

    public static class Point {

        long x, y;
        int from;

        public Point(int start, int end, int from) {
            this.x = start;
            this.y = end;
            this.from = from;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (x ^ (x >>> 32));
            result = prime * result + (int) (y ^ (y >>> 32));
            return result;
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
            Point other = (Point) obj;
            if (x != other.x) {
                return false;
            }
            if (y != other.y) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Point [x=" + x + ", y=" + y + ", from=" + from + "]";
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
