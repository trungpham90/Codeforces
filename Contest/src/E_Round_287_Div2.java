
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
 *
 * Problem statement: http://codeforces.com/problemset/problem/507/E
 *
 * @author pttrung
 */
public class E_Round_287_Div2 {

    public static long MOD = 1000000007;
    public static int[] dp, next;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        ArrayList<Edge>[] map = new ArrayList[n];
        int[] dist = new int[n];
        dp = new int[n];
        next = new int[n];

        for (int i = 0; i < n; i++) {
            dist[i] = -1;
            dp[i] = -1;
            map[i] = new ArrayList();
        }
        dist[n - 1] = 0;
        int x = 0, y = 0;
        Edge[] data = new Edge[m];
        for (int i = 0; i < m; i++) {

            int a = in.nextInt() - 1;
            int b = in.nextInt() - 1;
            int z = in.nextInt();
            data[i] = new Edge(a, b, z);
            if (z == 0) {
                x++;
            } else {
                y++;
            }
            map[a].add(new Edge(a, b, z));
            map[b].add(new Edge(b, a, z));
        }

        LinkedList<Integer> q = new LinkedList();
        q.add(n - 1);
        while (!q.isEmpty()) {
            int node = q.removeFirst();
            for (Edge e : map[node]) {
                if (dist[e.y] == -1) {
                    dist[e.y] = 1 + dist[node];
                    q.add(e.y);
                }
            }
        }
        int v = cal(0, dist, map);
        int start = 0;
        boolean[] mark = new boolean[n];
        mark[0] = true;
        while (start != n - 1) {

            mark[next[start]] = true;
            start = next[start];
        }
        // System.out.println(v + " " + dist[0] + " " + x + " " + y);
        int result = dist[0] - v + y - v;
        out.println(result);
        for (Edge e : data) {
            if (mark[e.x] && mark[e.y]) {
                if (e.z == 0) {
                    out.println((1 + e.x) + " " + (1 + e.y) + " " + 1);
                }
            } else {
                if (e.z == 1) {
                    out.println((1 + e.x) + " " + (1 + e.y) + " " + 0);
                }
            }
        }

        out.close();
    }

    public static int cal(int node, int[] dist, ArrayList<Edge>[] map) {
        if (node + 1 == map.length) {
            return 0;
        }
        if (dp[node] != -1) {
            return dp[node];
        }
        int result = -1;
        for (Edge e : map[node]) {
            if (dist[e.y] + 1 == dist[node]) {
                int tmp = e.z + cal(e.y, dist, map);
                if (tmp > result) {
                    result = tmp;
                    next[node] = e.y;
                }
            }
        }
        return dp[node] = result;
    }

    static class Edge {

        int x, y, z;

        public Edge(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
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
