
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
public class D_Round_220_Div2 {

    public static long MOD = 1000000007;
    static int[] tree;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        int[] data = new int[m];
        int[] dp = new int[m];
        for (int i = 0; i < m; i++) {
            data[i] = in.nextInt();
            dp[i] = -1;
        }
        int cur = 0;
        int end = 0;
        int[] store = new int[n];
        tree = new int[4 * n + 1];

        for (int i = 0; i < n; i++) {
            int v = in.nextInt();
            if (v == -1) {

                // System.out.println("Start "+ result);
                int last = 0;
                int total = 0;
                for (int j = 0; j < m; j++) {
                    if (data[j] > cur) {
                        break;
                    }

                    int index = -1;
                    int st = Integer.max(last, dp[j]);
                    int ed = end - 1;
                    if (m == 1 && dp[j] != -1) {
                        ed = Integer.min(st + 1, ed);
                    }
                    while (st <= ed) {
                        int mid = (st + ed) / 2;
                        if (get(mid, 0, n - 1, 0) >= data[j] - total) {
                            index = mid;
                            ed = mid - 1;
                        } else {
                            st = mid + 1;
                        }
                    }
                    dp[j] = index;
                    total++;
                    last = index + 1;
                    // System.out.println(index + " " + data[j]);
                    update(index, 0, 0, n - 1, 0);
                    store[index] = -1;
                }
                cur -= total;

            } else {
                update(end, 1, 0, n - 1, 0);
                store[end++] = v;
                cur++;
            }
        }
        // System.out.println(start + " " + end);
        if (cur == 0) {
            out.println("Poor stack!");
        } else {
            for (int i = 0; i < end; i++) {
                if (store[i] != -1) {
                    out.print(store[i]);
                }
            }
        }
        out.close();
    }

    static int getLeft(int index) {
        return 2 * index + 1;
    }

    static int getRight(int index) {
        return 2 * index + 2;
    }

    static int get(int index, int l1, int r1, int pos) {
        if (l1 > index || r1 < 0) {
            return 0;
        }
        if (0 <= l1 && r1 <= index) {
            return tree[pos];
        }
        int mid = (l1 + r1) / 2;
        int a = get(index, l1, mid, getLeft(pos));
        int b = get(index, mid + 1, r1, getRight(pos));
        return a + b;
    }

    static void update(int index, int v, int l1, int r1, int pos) {
        if (l1 > index || r1 < index) {
            return;
        }
        if (l1 == r1) {
            tree[pos] = v;
            return;
        }

        int mid = (l1 + r1) / 2;

        update(index, v, l1, mid, getLeft(pos));
        update(index, v, mid + 1, r1, getRight(pos));
        tree[pos] = tree[getLeft(pos)] + tree[getRight(pos)];
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
