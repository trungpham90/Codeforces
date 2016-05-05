
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
public class J_Saratov_2011 {

    public static long MOD = 1000000007;
    static long min = Integer.MAX_VALUE;

    static int[] X = {1, -1, 1, -1};
    static int[] Y = {1, 1, -1, -1};

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new FileOutputStream(new File(
                "output.txt")));
        //PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        long[][] data = new long[n][2];
        long[][] other = new long[n][5];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                data[i][j] = in.nextLong();
                other[i][j] = abs(data[i][j]);
                other[i][j + 2] = data[i][j];
            }
            other[i][4] = i;

        }
        Arrays.sort(other, (long[] o1, long[] o2) -> Long.compare(o1[0], o2[0]));
        TreeSet<long[]> set = new TreeSet<>((a, b) -> {
            if (a[1] != b[1]) {
                return Long.compare(a[1], b[1]);
            } else {
                return Long.compare(a[4], b[4]);
            }
        });
        int start = 0;
        int c = 0, d = 0;
        for (int i = 0; i < n; i++) {
            while (other[i][0] - other[start][0] > min) {
                set.remove(other[start]);
                start++;
            }
            //  System.out.println(set.size());
            long[] tmp = {Long.MIN_VALUE, other[i][1] - min, Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE};
            while (set.higher(tmp) != null) {
                long[] v = set.higher(tmp);
                if (v[1] > other[i][1] + min) {
                    break;
                }
                long dist = dist(v, other[i]);
                if (dist < min) {
                    min = dist;
                    c = (int) v[4];
                    d = (int) other[i][4];
                }
                tmp = v;
            }
            set.add(other[i]);
        }

        int a = 0, b = 0;
        for (int i = 0; i < 4 && a == 0; i++) {
            for (int j = 0; j < 4; j++) {
                if (dist(X[i] * data[c][0], Y[i] * data[c][1], X[j] * data[d][0], Y[j] * data[d][1]) == min) {
                    a = i + 1;
                    b = j + 1;
                    break;
                }
            }
        }
        out.println((c + 1) + " " + a + " " + (d + 1) + " " + b);

        out.close();
    }

    static long abs(long v) {
        return v > 0 ? v : -v;
    }

    static long dist(long[] a, long[] b) {
        long x = a[0] - b[0];
        long y = a[1] - b[1];
        return x * x + y * y;
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
        double val = (x + a) * (x + a) + (b + y) * (b + y);

        return val;

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

    public static long pow(long a, long b) {
        if (b == 0) {
            return 1;
        }
        if (b == 1) {
            return a;
        }
        long val = pow(a, b / 2);
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
            // System.setOut(new PrintStream(new
            // BufferedOutputStream(System.out), true));
            //br = new BufferedReader(new InputStreamReader(System.in));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    new File("input.txt"))));
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
