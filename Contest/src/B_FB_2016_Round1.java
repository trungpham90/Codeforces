
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
public class B_FB_2016_Round1 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new FileOutputStream(new File(
                "output.txt")));
        // PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int T = in.nextInt();
        for (int z = 0; z < T; z++) {
            int l = in.nextInt();
            int n = in.nextInt();
            int m = in.nextInt();
            long d = in.nextInt();
            long[] data = new long[n];
            for (int i = 0; i < n; i++) {
                data[i] = in.nextInt();
            }

            int total = l;
            long[] pre = new long[l];
            PriorityQueue<Point> wash = new PriorityQueue<>();
            for (int i = 0; i < n; i++) {
                wash.add(new Point(data[i], i));
            }
            for (int i = 0; i < l; i++) {
                Point p = wash.poll();
                pre[i] = p.x;
                wash.add(new Point(p.x + data[p.y], p.y));
            }
            Arrays.sort(pre);
            BigInteger re = null;
            if (l > m) {
                BigInteger[] res = new BigInteger[m];
                for (int i = 0; i < l; i++) {
                    BigInteger tmp = BigInteger.valueOf(pre[i]);
                    if (res[i % m] == null) {
                        res[i % m] = tmp.add(BigInteger.valueOf(d));
                    } else {
                        res[i % m] = (res[i % m].compareTo(tmp) > 0 ? res[i % m] : tmp).add(BigInteger.valueOf(d));
                    }
                    re = (re == null || re.compareTo(res[i % m]) < 0) ? res[i % m] : re;
                }

            } else {
                re = BigInteger.valueOf(pre[l - 1]).add(BigInteger.valueOf(d));
            }
            out.println("Case #" + (z + 1) + ": " + re.toString());
        }
        out.close();
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

        long x;
        int y;

        public Point(long start, int end) {
            this.x = start;
            this.y = end;
        }

        @Override
        public int compareTo(Point o) {
            return Long.compare(x, o.x);
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
            //br = new BufferedReader(new InputStreamReader(System.in));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("laundro_matt.txt"))));
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
