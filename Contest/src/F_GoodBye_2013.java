
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
public class F_GoodBye_2013 {

    public static long MOD = 1000000007;

    static int cur = 0;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int q = in.nextInt();
        int n = 4 + 2 * q;
        int[][] map = new int[n][3];
        for(int[]a : map){
            Arrays.fill(a, -1);
        }
        for (int i = 1; i < 4; i++) {
            map[0][i - 1] = i;
        }
        int index = 4;
        for (int i = 0; i < q; i++) {
            int v = in.nextInt() - 1;
            map[v][0] = index++;
            map[v][1] = index++;
        }
        int[] degree = new int[n];
        int[] time = new int[n];

        int[] data = new int[8 * n + 1];
        dfs(0, time, degree, data, 2 * n, map);
        int u = 1;
        int v = 2;
        int result = 2;
        for (int i = 0; i < q; i++) {
            int x = 5 + 2 * i;
            int a = get(0, Math.min(time[u], time[x]),
                    Math.max(time[u], time[x]), 0, 2 * n, data);
            int dist = degree[u] + degree[x] - 2 * degree[a];
            if (dist == result + 1) {
                result++;
                v = x;
            } else {
                // System.out.println(time[v] + " " +time[x] + " " + x + " " +
                // v);
                a = get(0, Math.min(time[v], time[x]),
                        Math.max(time[v], time[x]), 0, 2 * n, data);
                dist = degree[v] + degree[x] - 2 * degree[a];
                if (dist == result + 1) {
                    result++;
                    u = x;
                }
            }
            out.println(result);
        }
        out.close();
    }

    static void dfs(int index, int[] time, int[] degree, int[] data, int max,
            int[][] map) {

        time[index] = cur;
        if (map[index][0] == -1) {
            update(0, cur++, index, 0, max, data);
        }
        for (int i : map[index]) {
            if(i == -1){
                break;
            }
            degree[i] = 1 + degree[index];
            dfs(i, time, degree, data, max, map);
            update(0, cur++, index, 0, max, data);
        }

    }

    static void update(int index, int t, int v, int l, int r, int[] data) {
        // System.out.println(t + " " + v + " " + l + " " + r + " " +index );
        if (l > t || r < t) {
            return;
        }
        if (l == r) {
            data[index] = v;
            return;
        }
        int mid = (l + r) >> 1;
        update(left(index), t, v, l, mid, data);
        update(right(index), t, v, mid + 1, r, data);
        data[index] = Math.min(data[left(index)], data[right(index)]);
    }

    static int get(int index, int l, int r, int l1, int r1, int[] data) {
        if (l1 > r || r1 < l) {
            return Integer.MAX_VALUE;
        }
        if (l <= l1 && r1 <= r) {
            return data[index];
        }
        int mid = (l1 + r1) >> 1;
        int a = get(left(index), l, r, l1, mid, data);
        int b = get(right(index), l, r, mid + 1, r1, data);
        return Math.min(a, b);
    }

    static int left(int index) {
        return (index << 1) + 1;
    }

    static int right(int index) {
        return (index << 1) + 2;
    }

    static class Node {

        Node[] edge = new Node[3];
        int pa = -1;

        int height;

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
