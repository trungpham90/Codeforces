
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
public class D_Round_111_Div2 {

    public static long MOD = 1000000007;
    static int[] dist, low;
    static int track = 1;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();

        Edge[] data = new Edge[m];
        ArrayList<Integer>[] map = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            map[i] = new ArrayList();
        }
        for (int i = 0; i < m; i++) {
            data[i] = new Edge(in.nextInt() - 1, in.nextInt() - 1, in.nextInt(), i);
            map[data[i].u] = new ArrayList();
            map[data[i].v] = new ArrayList();
        }

        Arrays.sort(data, (a, b) -> Integer.compare(a.w, b.w));
        int[] u = new int[n];
        for (int i = 0; i < n; i++) {
            u[i] = i;
        }
        int[] min = new int[n];

        Arrays.fill(min, -1);
        int[] count = new int[m];
        int[] ver = new int[n];
        int cur = 1;
        dist = new int[n];
        low = new int[n];
        for (int i = 0; i < m; i++) {

            HashMap<Integer, ArrayList<Integer>> tmp = new HashMap();
            int end = i;
            for (int j = i; j < m; j++) {
                if (data[j].w != data[i].w) {
                    break;
                }
                end = j;
            }
            for (int j = i; j <= end; j++) {
                if (find(data[j].u, u) != find(data[j].v, u)) {
                    count[data[j].index] = 1;
                    int a = find(data[j].u, u);
                    int b = find(data[j].v, u);
                    if (!tmp.containsKey(a)) {
                        tmp.put(a, new ArrayList());
                    }
                    if (!tmp.containsKey(b)) {
                        tmp.put(b, new ArrayList());
                    }
                    tmp.get(a).add(j);
                    tmp.get(b).add(j);

                }
            }            
            for (int j : tmp.keySet()) {
                
                if (ver[j] != cur) {
                    
                    dfs(j, -1, ver, cur, u, count, data, tmp);
                }
            }

            for (int j = i; j <= end; j++) {
                if (count[data[j].index] > 0) {
                    if (find(data[j].u, u) != find(data[j].v, u)) {
                        u[find(data[j].u, u)] = find(data[j].v, u);
                    }
                }
            }
            cur++;
            i = end;
        }

        for (int i : count) {
            if (i == 0) {
                out.println("none");
            } else if (i == 1) {
                out.println("at least one");
            } else {
                out.println("any");
            }
        }
        out.close();
    }

    static void dfs(int index, int pa, int[] ver, int cur, int[] u, int[] count, Edge[] data, HashMap<Integer, ArrayList<Integer>> map) {
        ver[index] = cur;
        dist[index] = low[index] = track++;
        HashMap<Integer, Integer> tmp = new HashMap();
        for (int i : map.get(index)) {
            int a = find(data[i].u, u);
            int b = find(data[i].v, u);

            int nxt = a == index ? b : a;
            if (ver[nxt] != cur) {
                if(!tmp.containsKey(nxt)){
                    tmp.put(nxt, 1);
                }else{
                    tmp.put(nxt, tmp.get(nxt) +1);
                }
            }
        }
        for (int i : map.get(index)) {
            int a = find(data[i].u, u);
            int b = find(data[i].v, u);

            int nxt = a == index ? b : a;
            if (ver[nxt] != cur) {
                dfs(nxt, index, ver, cur, u, count, data, map);
                low[index] = Integer.min(low[index], low[nxt]);                
                if (dist[index] < low[nxt] && tmp.get(nxt) == 1) {
                    
                    count[data[i].index]++;
                }
            } else if (nxt != pa) {
                low[index] = Integer.min(low[index], dist[nxt]);
            }
        }
    }

    static int find(int index, int[] u) {
        if (index != u[index]) {
            return u[index] = find(u[index], u);
        }
        return index;
    }

    static class Edge {

        int u, v, w, index;

        public Edge(int u, int v, int w, int index) {
            super();
            this.u = u;
            this.v = v;
            this.w = w;
            this.index = index;
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
            this.x = Integer.min(start, end);
            this.y = Integer.min(start, end);
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
