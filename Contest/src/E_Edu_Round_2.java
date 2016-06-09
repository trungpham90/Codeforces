
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
public class E_Edu_Round_2 {

    public static long MOD = 1000000007;
    static int cur = 0;
    static int[] first, second, index;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        long[] color = new long[n];
        for (int i = 0; i < n; i++) {
            color[i] = in.nextInt();
        }
        ArrayList<Integer>[] map = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            map[i] = new ArrayList();
        }
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt() - 1;
            int b = in.nextInt() - 1;
            map[a].add(b);
            map[b].add(a);
        }
        first = new int[n];
        second = new int[n];
        index = new int[n];
        Arrays.fill(first, -1);
        dfs(0, map);
        long[] result = new long[n];
        int max = (int) Math.max(1, Math.ceil(Math.sqrt(n)));
        Point[] data = new Point[n];
        for (int i = 0; i < n; i++) {
            data[i] = new Point(first[i], second[i]);
        }
        Arrays.sort(data);
        ArrayList<Point>[] list = new ArrayList[(int) Math.ceil((double) n
                / max)];
        int tmp = 0;
        int count = 0;
        list[tmp] = new ArrayList();
        for (int i = 0; i < n; i++) {
            if (count == max) {
                tmp++;
                list[tmp] = new ArrayList();
                count = 0;
            }
            list[tmp].add(data[i]);
            count++;
        }
        for (ArrayList<Point> a : list) {
            Collections.sort(a, new Comparator<Point>() {

                @Override
                public int compare(Point o1, Point o2) {
                    return Integer.compare(o1.y, o2.y);
                }
            });
        }
        int[] total = new int[n + 1];

        TreeSet<Integer> q = new TreeSet<>();
        long[] set = new long[n + 1];
        int x = 0, y = -1;
        for (ArrayList<Point> a : list) {
            for (Point p : a) {
                while (y < p.y) {
                    y++;
                    total[(int) color[index[y]]]++;
                    int v = total[(int) color[index[y]]];
                    if (set[v - 1] > 0) {

                        set[v - 1] -= color[index[y]];

                    }

                    set[v] += color[index[y]];
                    q.add(v);

                }
                while (y > p.y) {
                    total[(int) color[index[y]]]--;
                    int v = total[(int) color[index[y]]];
                    if (set[v + 1] > 0) {

                        set[v + 1] -= color[index[y]];

                    }

                    set[v] += color[index[y]];
                    q.add(v);
                    y--;
                }
                while (x < p.x) {
                    total[(int) color[index[x]]]--;
                    int v = total[(int) color[index[x]]];
                    if (set[v + 1] > 0) {

                        set[v + 1] -= color[index[x]];

                    }

                    set[v] += color[index[x]];
                    q.add(v);
                    x++;
                }
                while (x > p.x) {
                    x--;
                    total[(int) color[index[x]]]++;
                    int v = total[(int) color[index[x]]];
                    if (set[v - 1] > 0) {

                        set[v - 1] -= color[index[x]];

                    }

                    set[v] += color[index[x]];
                    q.add(v);
                }
                //System.out.println(set.lastEntry().getKey() + " " + index[p.x]);
                while (set[q.last()] == 0) {
                    q.pollLast();
                }
                result[index[p.x]] = set[q.last()];
            }
        }
        for (long i : result) {
            out.print(i + " ");
        }
        out.close();
    }

    static void dfs(int node, ArrayList<Integer>[] map) {
        first[node] = cur;
        index[cur++] = node;
        for (int i : map[node]) {
            if (first[i] == -1) {
                dfs(i, map);
            }
        }
        second[node] = cur - 1;
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
