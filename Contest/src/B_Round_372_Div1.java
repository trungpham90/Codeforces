
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
public class B_Round_372_Div1 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        int L = in.nextInt();
        int s = in.nextInt();
        int t = in.nextInt();
        ArrayList<Integer>[] map = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            map[i] = new ArrayList();
        }
        int[][] edge = new int[m][2];
        long[] w = new long[m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 2; j++) {
                edge[i][j] = in.nextInt();
            }
            w[i] = in.nextLong();
            map[edge[i][0]].add(i);
            map[edge[i][1]].add(i);
        }
        long[] dist = new long[n];
        Arrays.fill(dist, -1);
        dist[s] = 0;
        PriorityQueue<Point> q = new PriorityQueue<>();
        q.add(new Point(s, 0));
        while (!q.isEmpty()) {
            Point p = q.poll();
            if (dist[p.x] == p.y) {
                for (int i : map[p.x]) {
                    if (w[i] != 0) {
                        int nxt = edge[i][0] == p.x ? edge[i][1] : edge[i][0];
                        if (dist[nxt] == -1
                                || dist[nxt] > dist[p.x] + (long) w[i]) {
                            dist[nxt] = dist[p.x] + (long) w[i];
                            q.add(new Point(nxt, dist[nxt]));
                        }
                    }
                }
            }
        }
        if (dist[t] != -1 && dist[t] < L) {
            out.println("NO");
        } else if (dist[t] == L) {

            out.println("YES");
            for (int i = 0; i < m; i++) {
                out.print(edge[i][0] + " " + edge[i][1] + " ");
                if (w[i] == 0) {
                    out.println((L + 1));
                } else {
                    out.println(w[i]);
                }
            }
        } else {
            Arrays.fill(dist, -1);
            dist[s] = 0;
            int[] pa = new int[n];
            Arrays.fill(pa, -1);
            q = new PriorityQueue<>();
            q.add(new Point(s, 0));
            pa[s] = -1;
            int[] num = new int[n];
            while (!q.isEmpty()) {
                Point p = q.poll();
                if (dist[p.x] == p.y) {
                    for (int i : map[p.x]) {
                        long cost = w[i] != 0 ? w[i] : 1;
                        int v = w[i] == 0 ? 1 : 0;
                        int nxt = edge[i][0] == p.x ? edge[i][1] : edge[i][0];
                        if (dist[nxt] == -1
                                || dist[nxt] > dist[p.x] + cost
                                || (dist[nxt] == dist[p.x] + cost && num[nxt] < num[p.x]
                                + v)) {
                            dist[nxt] = dist[p.x] + cost;
                            pa[nxt] = i;
                            num[nxt] = num[p.x] + v;
                            q.add(new Point(nxt, dist[nxt]));
                        }
                    }
                }
            }
            if (dist[t] <= L && pa[t] != -1) {
                out.println("YES");
                int start = t;
                int zero = -1;
                long total = 0;

                long left = 0;
                Arrays.fill(dist, -1);
                Arrays.fill(pa, -1);
                dist[s] = 0;

                q = new PriorityQueue<>();
                q.add(new Point(s, 0));
                while (!q.isEmpty()) {
                    Point p = q.poll();
                    if (dist[p.x] == p.y) {
                        for (int i : map[p.x]) {
                            long cost = 
                                     w[i] == 0 ? 1L : w[i];

                            int nxt = edge[i][0] == p.x ? edge[i][1]
                                    : edge[i][0];
                            if (dist[nxt] == -1
                                    || dist[nxt] > dist[p.x] + cost) {
                                dist[nxt] = dist[p.x] + cost;
                                pa[nxt] = i;
                                q.add(new Point(nxt, dist[nxt]));
                            }
                        }
                    }
                }
                start = t;
                zero = -1;
                total = 0;

                // if (L == 351789193)
                // System.out.println(each + " " + num[t] + " " + dist[t]);
                while (start != s) {
                    int v = pa[start];

                    // if(L == 351789193){
                    // System.out.println(edge[v][0] + " " + edge[v][1] +
                    // " " +
                    // edge[v][2]);
                    // }
                    if (w[v] == 0 || w[v] == -1) {
                        if (zero == -1) {
                            zero = v;

                        }
                        total += 1;
                        w[v] = -1;
                    } else {
                        total += w[v];
                    }
                    start = edge[v][0] == start ? edge[v][1] : edge[v][0];
                }

                left = L - total;
                if (zero != -1) {
                    w[zero] = 1L + left;
                    //  System.out.println(left + " " + total + " " + zero + " " + edge[zero][0] + " " + edge[zero][1]);
                }
                // System.out.println(zero + " " + edge[zero][0] + " " +
                // edge[zero][1]);
                do {
                    Arrays.fill(dist, -1);
                    Arrays.fill(pa, -1);
                    dist[s] = 0;

                    q = new PriorityQueue<>();
                    q.add(new Point(s, 0));
                    while (!q.isEmpty()) {
                        Point p = q.poll();
                        if (dist[p.x] == p.y) {
                            for (int i : map[p.x]) {
                                long cost = i == zero ? left + 1L
                                        : (w[i] == -1 ? 1L : w[i] == 0 ? L + 1 : w[i] );

                                int nxt = edge[i][0] == p.x ? edge[i][1]
                                        : edge[i][0];
                                if (dist[nxt] == -1
                                        || dist[nxt] > dist[p.x] + cost) {
                                    dist[nxt] = dist[p.x] + cost;
                                    pa[nxt] = i;
                                    q.add(new Point(nxt, dist[nxt]));
                                }
                            }
                        }
                    }
                    start = t;
                    zero = -1;
                    total = 0;

                    // if (L == 351789193)
                    // System.out.println(each + " " + num[t] + " " + dist[t]);
                    while (start != s) {
                        int v = pa[start];

                        // if(L == 351789193){
                        // System.out.println(edge[v][0] + " " + edge[v][1] +
                        // " " +
                        // edge[v][2]);
                        // }
                        if (w[v] == 0 || w[v] == -1) {
                            if (zero == -1) {
                                zero = v;

                            }
                            total += 1;
                            w[v] = -1;
                        } else {
                            total += w[v];
                        }
                        start = edge[v][0] == start ? edge[v][1] : edge[v][0];
                    }

                    left = L - total;
                    if (zero != -1) {
                        w[zero] = 1L + left;
                        //  System.out.println(left + " " + total + " " + zero + " " + edge[zero][0] + " " + edge[zero][1]);
                    }
                    // System.out.println(Arrays.toString(dist));
                } while (dist[t] < L);

                // if(L == 351789193){
                // System.out.println(left);
                // }
                // edge[zero][2] += left;
                for (int i = 0; i < m; i++) {
                    out.print(edge[i][0] + " " + edge[i][1] + " ");
                    if (w[i] == 0) {
                        out.println((L + 1));
                    } else if (i == zero) {

                        out.println(w[zero]);
                    } else if (w[i] == -1) {
                        out.println(1);
                    } else {
                        out.println(w[i]);
                    }
                }
            } else {
                out.println("NO");
            }
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

        int x;
        long y;

        public Point(int start, long end) {
            this.x = start;
            this.y = end;
        }

        @Override
        public int compareTo(Point o) {
            return Long.compare(y, o.y);
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
