
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
public class C_VK2015_Round_3 {

    public static int[][] dp;
    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        dp = new int[n + 1][n + 1];
        for (int[] a : dp) {
            Arrays.fill(a, -1);
        }
        int[] data = new int[n + 1];
        for (int i = 0; i < n; i++) {
            data[i + 1] = in.nextInt();
        }
        int max = 0;
        boolean ok = true;
        HashSet<Point> set = new HashSet();
        for (int i = 1; i <= n; i++) {
            int cycle = cal(i, 1, data, new int[n + 1]);

            int v = 0;
            for (int j = 1; j <= n; j++) {
                int a = f(i, j, data);
                int b = f(a, j, data);
                if (a == b) {
                    v = j;
                    break;
                }
            }
            max = Math.max(v, max);
            v %= cycle;
            if (v != 0) {
                ok = false;
            }
            set.add(new Point(cycle, v));
        }
        if (ok) {
            long re = 1;
            for(Point p : set){
                re = re*p.x/gcd(re, p.x);
            }
            long start = re;
            while(re < max){
                re += start;
            }
            out.println(re);
        } else {
            //System.out.println(max + " " + set);
            out.println(CRT(set, max));
        }
        out.close();
    }

    public static long CRT(HashSet<Point> set, long min) {
        long M = 1;
        long[] m = new long[set.size()];
        long[] x = new long[set.size()];
        int index = 0;
        for (Point p : set) {
            M *= p.x;
            m[index] = p.x;
            x[index] = p.y;
            index++;
        }
        long[] a = new long[set.size()];
        long[] b = new long[set.size()];
        long result = 0;
        for (int i = 0; i < index; i++) {
            a[i] = M / m[i];
            b[i] = modularLinearSolver(a[i], 1, m[i]);
            result += x[i] * a[i] * b[i];
        }
        while (result < min) {
            result += M;
        }
        return result;

    }

    static long[] extendEuclid(long a, long b) {
        if (b == 0) {
            return new long[]{a, 1, 0};
        }
        long[] re = extendEuclid(b, a % b);
        long x = re[2];
        long y = re[1] - (a / b) * re[2];
        re[1] = x;
        re[2] = y;
        return re;

    }

    static long modularLinearSolver(long a, long b, long n) {
        long[] ex = extendEuclid(a, n);
        if (b % ex[0] != 0) {
            throw new IllegalArgumentException("No solution");
        }
        long result = (ex[1] * (b / ex[0])) % n;

        return result;
    }

    public static int cal(int index, int step, int[] data, int[] last) {
        if (last[index] != 0) {
            return step - last[index];
        }
        last[index] = step;
        return cal(data[index], step + 1, data, last);
    }

    public static int f(int index, int left, int[] data) {
        if (left == 0) {
            return index;
        }
        return f(data[index], left - 1, data);
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
