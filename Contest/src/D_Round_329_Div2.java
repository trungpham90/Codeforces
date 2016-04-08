
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
public class D_Round_329_Div2 {

    public static long MOD = 1000000007;
    static int[] first, last, path;
    static int cur = 0;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        Edge[] data = new Edge[n - 1];
        ArrayList<Integer>[] map = new ArrayList[n];
        ArrayList<Integer>[] q = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            map[i] = new ArrayList();
            q[i] = new ArrayList();
        }
        path = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            path[i] = i;
            data[i] = new Edge(in.nextInt() - 1, in.nextInt() - 1,
                    in.nextLong());
            map[data[i].x].add(i);
            map[data[i].y].add(i);
        }
        first = new int[n];
        last = new int[n];

        FT tree = new FT(2 * n + 10);

        Q[] query = new Q[m];
        for (int i = 0; i < m; i++) {
            int type = in.nextInt();
            if (type == 1) {
                query[i] = new Q(1, in.nextInt() - 1, in.nextInt() - 1,
                        in.nextLong());
                q[query[i].a].add(i);
                q[query[i].b].add(i);
            } else {
                query[i] = new Q(2, in.nextInt() - 1, 0, in.nextLong());
            }
        }
        int[] pa = new int[n];
        pa[0] = -1;
        dfs(0, new int[n], new boolean[n], pa, data, map, q, query);
        for (Q tmp : query) {
            if (tmp.t == 1) {
                long start = tmp.y;
                int s = tmp.a;
                while (pa[s] != -1 && start > 0) {
                    int edge = f(pa[s], path);                    
                    if(edge == -1){
                        break;
                    }
                    int p = getPa(data[edge].x, data[edge].y);
                    if (first[p] < first[tmp.result]) {
                        break;
                    } else {
                        start /= data[edge].z;
                    }
                    if (p != tmp.result) {
                        s = p;
                    }else{
                        break;
                    }
                }
                s = tmp.b;
                while (pa[s] != -1 && start > 0) {
                    
                    int edge = f(pa[s], path);
                    if(edge == -1){
                        break;
                    }
                    int p = getPa(data[edge].x, data[edge].y);
                    if (first[p] < first[tmp.result]) {
                        break;
                    } else {
                        start /= data[edge].z;
                    }
                    if (p != tmp.result) {
                        s = p;
                    }else{
                        break;
                    }
                }
                out.println(start);
            } else {

                data[tmp.a].z = tmp.y;
                if (data[tmp.a].z == 1) {
                    int v = getPa(data[tmp.a].x, data[tmp.a].y);
                    path[tmp.a] = f(pa[v], path);
                }
            }
        }
        out.close();
    }

    static int getPa(int a, int b) {
        if (first[a] < first[b]) {
            return a;
        }
        return b;
    }

    static class Q {

        int t, a, b, result;
        long y;

        public Q(int t, int a, int b, long y) {
            super();
            this.t = t;
            this.a = a;
            this.b = b;
            this.y = y;
        }
    }

    static void dfs(int index, int[] u, boolean[] color, int[] pa, Edge[] data,
            ArrayList<Integer>[] map, ArrayList<Integer>[] q, Q[] query) {
        first[index] = ++cur;

        u[index] = index;

        // System.out.println((index +1) + " " + log);
        for (int i : map[index]) {
            Edge p = data[i];
            int nxt = p.x == index ? p.y : p.x;
            if (first[nxt] == 0) {

                pa[nxt] = i;
                if (p.z == 1) {
                    if (pa[index] != -1) {
                        path[i] = f(pa[index], path);
                    }else{
                        path[i] = -1;
                    }
                }
                dfs(nxt, u, color, pa, data, map, q, query);
                u[f(nxt, u)] = index;
            }
        }
        color[index] = true;
        for (int i : q[index]) {
            int nxt = query[i].a == index ? query[i].b : query[i].a;
            if (color[nxt]) {
                query[i].result = f(nxt, u);
            }
        }
        last[index] = cur;

    }

    static int f(int index, int[] u) {
        if(index == -1){
            return -1;
        }
        return u[index] = (index == u[index] ? index : f(u[index], u));
    }

    static class Edge {

        int x, y;
        long z;

        public Edge(int x, int y, long z) {
            super();
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
            return Integer.compare(x, o.x);
        }
    }

    public static class FT {

        double[] data;

        FT(int n) {
            data = new double[n];

        }

        public void update(int index, double value) {
            while (index < data.length) {
                data[index] += value;
                index += (index & (-index));
            }
        }

        public double get(int index) {
            double result = 0;
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
