
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
public class C_Round_352_Div2 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        Point[] per = new Point[2];
        per[0] = new Point(in.nextInt(), in.nextInt());
        per[1] = new Point(in.nextInt(), in.nextInt());
        Point r = new Point(in.nextInt(), in.nextInt());
        int n = in.nextInt();
        Point[] data = new Point[n];
        double[] toRec = new double[n];
        double total = 0;
        for (int i = 0; i < n; i++) {
            data[i] = new Point(in.nextInt(), in.nextInt());
            toRec[i] = 2.0 * dist(r, data[i]);
            total += toRec[i];
        }
        double result = Double.MAX_VALUE;
        int[][] min = new int[2][2];
        for (int[] a : min) {
            Arrays.fill(a, -1);
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < n; j++) {
                double d = dist(per[i], data[j]) + dist(data[j], r) - toRec[j];
                for (int k = 0; k < 2; k++) {
                    if (min[i][k] == -1 || d <= dist(per[i], data[min[i][k]]) + dist(data[min[i][k]], r) - toRec[min[i][k]]) {
                        int last = min[i][k];
                        min[i][k] = j;
                        for (int h = k + 1; h < 2; h++) {
                            min[i][h] = last;
                        }
                        break;
                    }
                }
            }

            double v = total - toRec[min[i][0]] + dist(per[i], data[min[i][0]]) + dist(data[min[i][0]], r);
            result = Math.min(result, v);
        }
        if (n > 1) {
            // System.out.println(min[0][0] + " " + min[1][0] + " " + min[1][1] + " " + min[0][1]);
            if (min[0][0] != min[1][0]) {

                double v = total - toRec[min[0][0]] - toRec[min[1][0]] + dist(per[0], data[min[0][0]]) + dist(per[1], data[min[1][0]]) + dist(data[min[0][0]], r) + dist(data[min[1][0]], r);
                result = Math.min(result, v);
            } else {
                // System.out.println("HE HE");
                double v = total - toRec[min[0][0]] - toRec[min[1][1]] + dist(per[0], data[min[0][0]]) + dist(data[min[0][0]], r) + dist(per[1], data[min[1][1]]) + dist(data[min[1][1]], r);
                // System.out.println(v + " " + min[0][0] + " " + min[1][1]);
                result = Math.min(result, v);
                v = total - toRec[min[0][1]] - toRec[min[1][0]] + dist(per[0], data[min[0][1]]) + dist(data[min[0][1]], r) + dist(per[1], data[min[1][0]]) + dist(data[min[1][0]], r);
                //  System.out.println((total - toRec[min[0][1]] - toRec[min[1][0]]) + " " + min[0][1] + " " + min[1][0]);
                result = Math.min(result, v);
            }
        }
        out.println(result);
        out.close();
    }

    static double dist(Point a, Point b) {
        //  System.out.println("Go from " + a + " to " + b);
        long X = a.x - b.x;
        long Y = a.y - b.y;
        return Math.sqrt(X * X + Y * Y);
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
