
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
public class B_Round_437_Div1 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        long s = in.nextInt();
        final long[][] data = new long[n][3];
        int[] p = new int[n];
        int[] q = new int[n];
        PriorityQueue<Integer> P = new PriorityQueue<>((x, y) -> Long.compare(data[y][1] - data[y][2], data[x][1] - data[x][2]));
        PriorityQueue<Integer> Q = new PriorityQueue<>((x, y) -> Long.compare(data[y][2] - data[y][1], data[x][2] - data[x][1]));
        long total = 0;
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < 3; j++) {
                data[i][j] = in.nextInt();

            }
            total += data[i][0];
            P.add(i);
            Q.add(i);
        }
        for (int i = 0; i < n; i++) {
            p[i] = P.poll();
            q[i] = Q.poll();
        }

        long need = total / s;
        if (need * s < total) {
            need++;
        }

        long st = 0;
        long ed = need;
        long re = 0;
        while (st < ed) {
            long mid = (st + ed) / 2;
            long x = cal(data, p, q, s * mid, s * (need - mid), n);
            long y = cal(data, p, q, s * (mid + 1), s * (need - mid - 1), n);
            //System.out.println(x + " " + y + " " + mid);
            if (x >= y) {
                ed = mid;
                re = Long.max(x, re);
            } else {
                st = mid + 1;
                re = Long.max(y, re);
            }
        }
        out.println(re);
        out.close();
    }

    static long cal(long[][] data, int[] p, int[] q, long a, long b, int n) {
        //System.out.println("Start " + a + " " + b);
        long re = 0;
        long[] hold = new long[n];

        int i = 0, j = 0;
        while (i < n && j < n) {
            if (data[p[i]][1] - data[p[i]][2] >= data[q[j]][2] - data[q[j]][1]) {
                int v = p[i++];
                long need = data[v][0] - hold[v];
                need = Long.min(need, a);
                a -= need;
                hold[v] += need;
                re += data[v][1] * need;
            } else {
                int v = q[j++];
                long need = data[v][0] - hold[v];
                need = Long.min(need, b);
                b -= need;
                hold[v] += need;
                re += data[v][2] * need;
            }
        }
        while (i < n) {
            int v = p[i++];
            long need = data[v][0] - hold[v];
            need = Long.min(need, a);
            a -= need;
            hold[v] += need;
            re += data[v][1] * need;
        }
        while (j < n) {
            int v = q[j++];
            long need = data[v][0] - hold[v];
            need = Long.min(need, b);
            b -= need;
            hold[v] += need;
            re += data[v][2] * need;
        }

        return re;
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
