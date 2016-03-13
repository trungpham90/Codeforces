
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
public class C_Round_345_Div1 {

    public static long MOD = 1000000007;
    static int[][] data;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        data = new int[n][m];
        PriorityQueue<Point> q = new PriorityQueue<>(n * m, (a, b) -> Integer.compare(data[a.x][a.y], data[b.x][b.y]));

        int[][] union = new int[n][m];
        for (int i = 0; i < n; i++) {
            HashMap<Integer, Integer> map = new HashMap();
            for (int j = 0; j < m; j++) {
                data[i][j] = in.nextInt();
                union[i][j] = i * m + j;
                q.add(new Point(i, j));
                if (!map.containsKey(data[i][j])) {
                    map.put(data[i][j], i * m + j);
                } else {
                    int v = map.get(data[i][j]);
                    int tmp = find(i, j, m, union);
                    union[tmp / m][tmp % m] = find(v / m, v % m, m, union);
                }
            }
        }
        for (int j = 0; j < m; j++) {
            HashMap<Integer, Integer> map = new HashMap();
            for (int i = 0; i < n; i++) {

                if (!map.containsKey(data[i][j])) {
                    map.put(data[i][j], find(i, j, m, union));
                } else {
                    int v = map.get(data[i][j]);
                    int tmp = find(i, j, m, union);
                    union[tmp / m][tmp % m] = find(v / m, v % m, m, union);
                }
            }
        }
        int[] row = new int[n];
        int[] col = new int[m];
        Arrays.fill(row, -1);
        Arrays.fill(col, -1);
        int[][] re = new int[n][m];
        while (!q.isEmpty()) {
            Point p = q.poll();
            //  System.out.println(data[p.x][p.y]);
            int index = find(p.x, p.y, m, union);
            int v = re[index / m][index % m];

            if (row[p.x] == -1 && col[p.y] == -1) {
                v = Integer.max(1, v);
                // System.out.println(p + " HE HE " + v);
            } else if (row[p.x] != -1 && col[p.y] != -1) {
                int a = find(p.x, row[p.x], m, union);
                int b = find(col[p.y], p.y, m, union);
                int x = re[a / m][a % m] + (data[a / m][a % m] == data[p.x][p.y] ? 0 : 1);
                int y = re[b / m][b % m] + (data[b / m][b % m] == data[p.x][p.y] ? 0 : 1);
                v = Integer.max(Integer.max(v, x), y);
                //  System.out.println(p + " " + x + " " + y);
            } else if (row[p.x] != -1) {
                int a = find(p.x, row[p.x], m, union);
                int x = re[a / m][a % m] + (data[a / m][a % m] == data[p.x][p.y] ? 0 : 1);
                v = Integer.max(v, x);
                // System.out.println(p + " " + x);
            } else {
                int b = find(col[p.y], p.y, m, union);
                int y = re[b / m][b % m] + (data[b / m][b % m] == data[p.x][p.y] ? 0 : 1);
                v = Integer.max(v, y);
                //  System.out.println(p + " " + y);
            }

            row[p.x] = p.y;
            col[p.y] = p.x;
            re[index / m][index % m] = v;

        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int v = find(i, j, m, union);
                out.print(re[v / m][v % m] + " ");
            }
            out.println();
        }

        out.close();
    }

    static int find(int x, int y, int m, int[][] union) {
        if (union[x][y] != x * m + y) {
            return union[x][y] = find(union[x][y] / m, union[x][y] % m, m, union);
        }
        return union[x][y];
    }

    static class State {

        int v;
        LinkedList<Integer> list = new LinkedList();

        public State(int v) {

            this.v = v;
        }

        @Override
        public String toString() {
            return "State{" + "v=" + v + ", list=" + list + '}';
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
        public String toString() {
            return "Point{" + "x=" + x + ", y=" + y + '}';
        }

        @Override
        public int compareTo(Point b) {
            if (x == b.x || y == b.y) {
                return Integer.compare(data[x][y], data[b.x][b.y]);
            } else {
                int c = Integer.compare(data[x][y], data[x][b.y]);
                int d = Integer.compare(data[b.x][b.y], data[x][b.y]);
                if (c != d) {
                    return Integer.compare(c, d);
                }
                c = Integer.compare(data[x][y], data[b.x][y]);
                d = Integer.compare(data[b.x][b.y], data[b.x][y]);
                return Integer.compare(c, d);
            }
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
