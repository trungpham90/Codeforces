
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
public class B_MemSQL2_Round2 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        long m = in.nextInt();
        long n = in.nextInt();
        PriorityQueue<Long> a = new PriorityQueue();
        PriorityQueue<Long> b = new PriorityQueue();
        for (int i = 0; i < m; i++) {
            a.add(in.nextLong());
        }
        for (int i = 0; i < n; i++) {
            b.add(in.nextLong());
        }

        //100000 100000
        //57034 65824 



        long[] sumA = new long[(int) m];
        long[] sumB = new long[(int) n];
        sumA[0] = a.poll();
        sumB[0] = b.poll();
        for (int i = 1; i < m; i++) {
            sumA[i] = sumA[i - 1] + a.poll();
        }
        for (int i = 1; i < n; i++) {
            sumB[i] = sumB[i - 1] + b.poll();
        }
        if (sumA[(int) m - 1] >= Long.MAX_VALUE / n || sumB[(int) n - 1] >= Long.MAX_VALUE / m) {
            BigInteger min;
            BigInteger M = BigInteger.valueOf(m);
            BigInteger N = BigInteger.valueOf(n);
            BigInteger c = f(0, n, M, sumA, sumB);
            BigInteger d = f(0, m, N, sumB, sumA);
            min = c.compareTo(d) > 0 ? d : c;
            int lo = 0;
            int hi = (int) m - 1;
            while (lo < hi) {
                int mid = (lo + hi) >> 1;

                BigInteger x = f(mid, n, M, sumA, sumB);
                BigInteger y = f(mid + 1, n, M, sumA, sumB);
                if (x.compareTo(y) < 0) {
                    hi = mid;
                    if (x.compareTo(min) < 0) {
                        min = x;
                    }
                } else {
                    lo = mid + 1;
                    if (y.compareTo(min) < 0) {
                        min = y;
                    }
                }
            }
            lo = 0;
            hi = (int) n - 1;
            while (lo < hi) {
                int mid = (lo + hi) >> 1;
                BigInteger x = f(mid, m, N, sumB, sumA);
                BigInteger y = f(mid + 1, m, N, sumB, sumA);
                if (x.compareTo(y) < 0) {
                    hi = mid;
                    if (min == null || x.compareTo(min) < 0) {
                        min = x;
                    }
                } else {
                    lo = mid + 1;
                    if (min == null || y.compareTo(min) < 0) {
                        min = y;
                    }
                }
            }
            out.println(min);
        } else {
            long min;


            long c = f(0, n, m, sumA, sumB);
            long d = f(0, m, n, sumB, sumA);
            min = c > d ? d : c;
            int lo = 0;
            int hi = (int) m - 1;
            while (lo < hi) {
                int mid = (lo + hi) >> 1;

                long x = f(mid, n, m, sumA, sumB);
                long y = f(mid + 1, n, m, sumA, sumB);
                if (x < y) {
                    hi = mid;
                    if (x < (min)) {
                        min = x;
                    }
                } else {
                    lo = mid + 1;
                    if (y < min) {
                        min = y;
                    }
                }
            }
            lo = 0;
            hi = (int) n - 1;
            while (lo < hi) {
                int mid = (lo + hi) >> 1;
                long x = f(mid, m, n, sumB, sumA);
                long y = f(mid + 1, m, n, sumB, sumA);
                if (x < y) {
                    hi = mid;
                    if (x < (min)) {
                        min = x;
                    }
                } else {
                    lo = mid + 1;
                    if (y < min) {
                        min = y;
                    }
                }
            }
            out.println(min);
        }
//        for (long i = 0; i < m; i++) {
//            BigInteger tmp = M.subtract(BigInteger.valueOf(i)).multiply(sumB[(int) n - 1]).add(i > 0 ? sumA[(int) i - 1] : BigInteger.ZERO);
//            if (min == null || min.compareTo(tmp) > 0) {
//                min = tmp;
//            }
//        }
//        for (long i = 0; i < n; i++) {
//
//            BigInteger tmp = N.subtract(BigInteger.valueOf(i)).multiply(sumA[(int) m - 1]).add(i > 0 ? sumB[(int) i - 1] : BigInteger.ZERO);
//            if (min == null || min.compareTo(tmp) > 0) {
//                min = tmp;
//            }
//        }


        out.close();
    }

    static BigInteger f(int index, long n, BigInteger M, long[] sumA, long[] sumB) {
        BigInteger tmp = M.subtract(BigInteger.valueOf(index)).multiply(BigInteger.valueOf(sumB[(int) n - 1])).add(index > 0 ? BigInteger.valueOf(sumA[(int) index - 1]) : BigInteger.ZERO);
        return tmp;
    }

    static long f(int index, long n, long M, long[] sumA, long[] sumB) {
        long tmp = (M - index) * sumB[(int) n - 1] + (index > 0 ? sumA[(int) index - 1] : 0);
        return tmp;
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
