
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
public class E_8VC_2016_Round1 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        long[] data = new long[n];
        for (int i = 0; i < n; i++) {
            data[i] = in.nextInt();
        }
        Arrays.sort(data);
        long[] total = new long[n];
        total[0] = data[0];
        for (int i = 1; i < n; i++) {
            total[i] = data[i] + total[i - 1];
        }
        long[] result = {0, 1};
        int index = 0;
        int l = 0;
        boolean two = false;
        for (int i = 0; i < n; i++) {
            //System.out.println(result);
            int left = i;
            int right = n - i - 1;
            int start = 0;
            int end = left > right ? right : left;

            while (start < end) {
                int mid = (start + end) / 2;
                // System.out.println(mid + " " +i + " " + end);
                long[] a = cal(false, mid, i, n, new long[]{data[i], 1}, total);
                long[] b = cal(false, mid + 1, i, n, new long[]{data[i], 1}, total);
                if (compare(a, b) > 0) {
                    end = mid - 1;
                    if (compare(result, a) < 0) {
                        result = a;
                        index = i;
                        l = mid;
                        two = false;
                    }
                } else {
                    start = mid + 1;
                    if (compare(result, b) < 0) {
                        result = b;
                        index = i;
                        l = mid + 1;
                        two = false;
                    }
                }
            }
            // System.out.println("After " + result);
            if (i > 0) {
                left = i - 1;
                right = n - i - 1;
                start = 0;
                end = left > right ? right : left;
                //System.out.println(left + " " + right + " " + i);
                long[] mean = {data[i] + data[i - 1], 2};
                while (start < end) {
                    int mid = (start + end) / 2;
                    // System.out.println(mid + " " +i + " " + end);
                    long[] a = cal(true, mid, i, n, mean, total);
                    long[] b = cal(true, mid + 1, i, n, mean, total);
                    if (compare(a, b) > 0) {
                        end = mid - 1;
                        if (compare(result, a) < 0) {
                            result = a;
                            index = i;
                            l = mid;
                            two = true;
                        }
                    } else {
                        start = mid + 1;
                        if (compare(result, b) < 0) {
                            result = b;
                            index = i;
                            l = mid + 1;
                            two = true;
                        }
                    }
                }
            }
        }
        //out.println(result);
        if (two) {
            out.println(l * 2 + 2);
            for (int i = 0; i < n; i++) {
                if (i <= index && i >= index - l - 1) {
                    out.print(data[i] + " ");
                }
            }
            for (int i = n - 1; i >= 0; i--) {
                if (i >= n - l) {
                    out.print(data[i] + " ");
                }
            }
        } else {
            out.println(l * 2 + 1);
            for (int i = 0; i < n; i++) {
                if (i <= index && i >= index - l) {
                    out.print(data[i] + " ");
                }
            }
            for (int i = n - 1; i >= 0; i--) {
                if (i >= n - l) {
                    out.print(data[i] + " ");
                }
            }
        }
        out.close();
    }

    static long[] cal(boolean two, int need, int index, int n, long[] mean,
            long[] total) {

        long a = total[n - 1] - total[n - need - 1];

        long b = total[index]
                - (index >= need + 1 ? total[index - need - 1] : 0);
        if (two) {
            b = total[index]
                    - (index >= need + 2 ? total[index - need - 2] : 0);
        }
        //System.out.println(a + " " + b + " " + index + " " + need + " " + mean);
        long[] x = {(a + b), (need * 2 + 1 + (two ? 1 : 0))};

        minus(x, mean);
        return x;
    }

    static void minus(long[] x, long[] y) {
        long v = gcd(abs(x[1]), abs(y[1]));
        long a = x[0] * y[1] / v;
        long b = y[0] * x[1] / v;
        a -= b;
        b = (x[1] * y[1]) / v;
        x[0] = a;
        x[1] = b;
    }

    static long abs(long v) {
        return v > 0 ? v : -v;
    }

    static int compare(long[] a, long[] b) {
        long v = gcd(abs(a[1]), abs(b[1]));
        long x = a[0] * b[1] / v;
        long y = b[0] * a[1] / v;
        return Long.compare(x, y);
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
        public int compareTo(Point o) {
            return x - o.x;
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
