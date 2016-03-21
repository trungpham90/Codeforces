
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
public class C_Round_90 {

    public static long MOD = 1000000007;
    static long[][][] dp;
    static HashMap<Long, Integer> map = new HashMap();
    static ArrayList<Long> list;
    static int n, m;
    static int[] next;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        n = in.nextInt();
        m = in.nextInt();
        long k = in.nextInt();
        long[][] data = new long[m][4];
        TreeSet<Long> set = new TreeSet();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 3; j++) {
                data[i][j] = in.nextLong();
            }
            for (long j = data[i][0]; j <= data[i][1]; j++) {
                set.add(j);
            }
            data[i][3] = i;
        }

        list = new ArrayList();
        for (long i : set) {
            map.put(i, list.size());
            list.add(i);
        }
        Arrays.sort(data, new Comparator<long[]>() {
            @Override
            public int compare(long[] a, long[] b) {
                return Long.compare(a[2], b[2]);
            }

        }
        );
        next = new int[m];
        Arrays.fill(next, m);
        for (int i = m - 2; i >= 0; i--) {
            if (data[i][2] < data[i + 1][2]) {
                next[i] = i + 1;
            } else {
                next[i] = next[i + 1];
            }
        }

        dp = new long[m][n + 1][list.size()];

        for (long[][] b : dp) {
            for (long[] c : b) {
                Arrays.fill(c, -1);
            }
        }

        long v = cal(0, n, 0, k, data);
        // out.println(v);

        if (v >= 0) {
            out.println("YES");
            int index = 0;
            int left = n;
            int val = 0;

            while (left > 0) {
                // System.out.println(index + " " + left + " " + last + " " + num + " " + (dp[index][left][last][num]));
                if (index + 1 < m && dp[index][left][val] == dp[index + 1][left][val]) {
                    index++;
                } else if (left == n) {
                    for (long i = data[index][0]; i <= data[index][1]; i++) {
                        if (dp[index][left][val] == i + (next[index] < m ? dp[next[index]][left - 1][map.get(i)] : 0) && (next[index] == m || dp[next[index]][left - 1][map.get(i)] >= 0)) {
                            out.println((data[index][3] + 1) + " " + (i));
                            left--;
                            val = map.get(i);
                            index = next[index];
                            break;
                        }
                    }
                } else {
                    long tmp = list.get(val);
                    if (data[index][0] <= tmp + k && data[index][1] >= tmp + k) {
                        int nxt = map.get(tmp + k);
                        if ((next[index] == m || dp[next[index]][left - 1][nxt] >= 0) && (next[index] < m ? dp[next[index]][left - 1][nxt] : 0) + tmp + k == dp[index][left][val]) {
                            out.println((data[index][3] + 1) + " " + (tmp + k));
                            left--;
                            val = nxt;
                            index = next[index];
                            continue;
                        }
                    }
                    int nxt = map.get(tmp * k);
                    out.println((data[index][3] + 1) + " " + (tmp * k));
                    left--;
                    val = nxt;
                    index = next[index];

                }
            }
        } else {
            out.println("NO");
        }
        out.close();
    }

    public static long cal(int index, int left, int v, long k, long[][] data) {
        if (left == 0) {
            if (index < m) {
                dp[index][left][v] = 0;
            }
            return 0;
        }
        if (m - index < left) {
            return -2;
        }

        if (dp[index][left][v] != -1) {
            return dp[index][left][v];
        }
        long result = -2;

        if (left == n) {

            for (long i = data[index][1]; i >= data[index][0]; i--) {

                // System.out.println((data[index][3] + 1) + "  " + (data[index][0] + i));
                int tmp = map.get(i);
                long val = cal(next[index], left - 1, tmp, k, data);
                if (val >= 0) {
                    result = max(result, val + i);
                    if (k == 1) {
                        break;
                    }
                }
            }
        } else {

            long tmp = list.get(v);
            if (data[index][0] <= tmp + k && data[index][1] >= tmp + k) {

                long val = cal(next[index], left - 1, map.get(tmp + k), k, data);
                if (val >= 0) {
                    result = max(result, tmp + k + val);
                }
            }
            if (data[index][0] <= k * tmp && k * tmp <= data[index][1]) {

                long val = cal(next[index], left - 1, map.get(k * tmp), k, data);
                if (val >= 0) {
                    result = max(result, tmp * k + val);
                }
            }
        }

        result = max(result, cal(index + 1, left, v, k, data));

        return dp[index][left][v] = result;

    }

    static long max(long a, long b) {
        return a > b ? a : b;
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
