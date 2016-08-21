
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
public class D_Round_368_Div2 {

    public static long MOD = 1000000007;
    static int[] dp;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();
        boolean[] inv = new boolean[n];
        int[] count = new int[n];
        boolean[][] store = new boolean[n][m];
        dp = new int[q + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        int total = 0;
        int[][] qu = new int[q + 1][];
        ArrayList<Integer>[] map = new ArrayList[q + 1];
        map[0] = new ArrayList();
        qu[0] = new int[]{0};
        for (int i = 1; i <= q; i++) {
            int t = in.nextInt();
            map[i] = new ArrayList();
            switch (t) {
                case 1: {
                    map[i - 1].add(i);
                    qu[i] = new int[3];
                    qu[i][0] = t;
                    for (int j = 1; j <= 2; j++) {
                        qu[i][j] = in.nextInt() - 1;
                    }
                    break;
                }
                case 2: {
                    map[i - 1].add(i);
                    qu[i] = new int[3];
                    qu[i][0] = t;
                    for (int j = 1; j <= 2; j++) {
                        qu[i][j] = in.nextInt() - 1;
                    }
                    break;
                }
                case 3: {
                    map[i - 1].add(i);
                    qu[i] = new int[2];
                    qu[i][0] = t;

                    qu[i][1] = in.nextInt() - 1;

                    break;
                }
                default:
                    qu[i] = new int[2];
                    qu[i][0] = t;

                    qu[i][1] = in.nextInt();
                    map[qu[i][1]].add(i);
                    break;
            }
        }
        cal(0, 0, m, inv, store, count, map, qu);
        for (int i = 1; i <= q; i++) {
            out.println(dp[i]);
        }
        out.close();
    }

    static void cal(int index, int total, int m, boolean[] inv, boolean[][] store, int[] count, ArrayList<Integer>[] map, int[][] qu) {
        boolean op = false;
        if (qu[index][0] == 1) {
            if (insert(qu[index][1], qu[index][2], inv, store, count)) {
                op = true;
                total++;
            }
        } else if (qu[index][0] == 2) {
            if (remove(qu[index][1], qu[index][2], inv, store, count)) {
                op = true;
                total--;
            }
        } else if (qu[index][0] == 3) {
            op = true;
            total -= count[qu[index][1]];
            total += m - count[qu[index][1]];
            count[qu[index][1]] = m - count[qu[index][1]];;
            inv[qu[index][1]] = !inv[qu[index][1]];
        }
        dp[index] = total;
        for (int i : map[index]) {
            cal(i, total, m, inv, store, count, map, qu);
        }
        if (op) {
            if (qu[index][0] == 2) {
                if (insert(qu[index][1], qu[index][2], inv, store, count)) {
                    total++;
                }
            } else if (qu[index][0] == 1) {
                if (remove(qu[index][1], qu[index][2], inv, store, count)) {
                    total--;
                }
            } else if (qu[index][0] == 3) {
                total -= count[qu[index][1]];
                total += m - count[qu[index][1]];
                count[qu[index][1]] = m - count[qu[index][1]];
                inv[qu[index][1]] = !inv[qu[index][1]];
            }
        }

    }

    static boolean remove(int i, int j, boolean[] inv, boolean[][] store, int[] count) {
        if (inv[i]) {
            if (!store[i][j]) {
                store[i][j] = true;
                count[i]--;
                return true;
            }
        } else if (store[i][j]) {
            store[i][j] = false;
            count[i]--;
            return true;
        }
        return false;
    }

    static boolean insert(int i, int j, boolean[] inv, boolean[][] store, int[] count) {
        if (inv[i]) {
            if (store[i][j]) {
                store[i][j] = false;
                count[i]++;
                return true;
            }
        } else if (!store[i][j]) {
            store[i][j] = true;
            count[i]++;
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
