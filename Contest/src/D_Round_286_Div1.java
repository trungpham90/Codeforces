
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
 *
 * @author pttrung
 */
public class D_Round_286_Div1 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] data = new int[m][3];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 3; j++) {
                data[i][j] = in.nextInt() - 1;
            }
        }

        Arrays.sort(data, new Comparator<int[]>() {

            @Override
            public int compare(int[] o1, int[] o2) {
                // TODO Auto-generated method stub
                return Integer.compare(o1[2], o2[2]);
            }

        });
        LinkedList<Integer>[] q = new LinkedList[n];
        ArrayList<Integer>[] re = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            q[i] = new LinkedList();
            re[i] = new ArrayList();
        }
        int start = 0;
        int[] check = new int[n];
        int comp = 1;
        int cur = 1;
        for (int i = 0; i < m; i++) {
            if (i > 0 && (data[i][2] != data[i - 1][2])) {

                // System.out.println("Start " + start + " "+ i + " " +
                // data[i][2]);
                for (int j = start; j < i; j++) {
                    if (data[j][2] != data[i - 1][2]) {
                        throw new NullPointerException(start + " " + j + " "
                                + data[start][2] + " " + data[j][2]);
                    }
                    if (check[data[j][0]] < cur) {
                        // System.out.println(Arrays.toString(q) + " " +
                        // data[j][2]);
                        LinkedList<Integer> tmp = new LinkedList();
                        tmp.add(data[j][0]);
                        check[data[j][0]] = cur;

                        while (!tmp.isEmpty()) {

                            int node = tmp.poll();

                            // System.out.println(node + " " + comp);
                            re[node].add(comp);
                            while (!q[node].isEmpty()) {
                                int v = q[node].poll();
                                if (check[v] < cur) {
                                    check[v] = cur;
                                    tmp.add(v);
                                }
                            }
                        }
                        comp++;
                    }
                }
                cur++;
                start = i;
            }
            q[data[i][0]].add(data[i][1]);
            q[data[i][1]].add(data[i][0]);
        }
        for (int j = start; j < m; j++) {

            if (check[data[j][0]] < cur) {
                // System.out.println(Arrays.toString(q) + " " +
                // data[j][2]);
                LinkedList<Integer> tmp = new LinkedList();
                tmp.add(data[j][0]);
                check[data[j][0]] = cur;

                while (!tmp.isEmpty()) {

                    int node = tmp.poll();

                    // System.out.println(node + " " + comp);
                    re[node].add(comp);
                    while (!q[node].isEmpty()) {
                        int v = q[node].poll();
                        if (check[v] < cur) {
                            check[v] = cur;
                            tmp.add(v);
                        }
                    }
                }
                comp++;
            }
        }
        // System.out.println(Arrays.toString(re));
        HashMap<Point, Integer> set = new HashMap();
        int z = in.nextInt();
        for (int i = 0; i < z; i++) {
            Point p = new Point(in.nextInt() - 1, in.nextInt() - 1);
            if (set.containsKey(p)) {
                out.println(set.get(p));
            } else {
                int result = 0;
                if (re[p.x].size() > re[p.y].size()) {
                    for (Integer j : re[p.y]) {
                        // System.out.println(Collections.binarySearch(re[p.x],
                        // j));
                        if (Collections.binarySearch(re[p.x], j) >= 0) {
                            result++;
                        }
                    }
                } else {
                    for (Integer j : re[p.x]) {
                        // System.out.println(Collections.binarySearch(re[p.y],
                        // j));
                        if (Collections.binarySearch(re[p.y], j) >= 0) {
                            result++;
                        }
                    }
                }
                out.println(result);
                set.put(p, result);
            }
        }
        out.close();
    }

    static class Edge implements Comparable<Edge> {

        int x, y, c;

        public Edge(int x, int y, int c) {
            super();
            this.x = x;
            this.y = y;
            this.c = c;
        }

        @Override
        public int compareTo(Edge o) {

            return Integer.compare(c, o.c);
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
            this.x = Math.min(start, end);
            this.y = Math.max(start, end);
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
            // System.setOut(new PrintStream(new
            // BufferedOutputStream(System.out), true));
            br = new BufferedReader(new InputStreamReader(System.in));
            // br = new BufferedReader(new InputStreamReader(new
            // FileInputStream(new File("input.txt"))));
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
