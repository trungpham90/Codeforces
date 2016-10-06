
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
public class F_Round_375_Div2 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] edge = new int[m][2];
        ArrayList<Integer>[] map = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            map[i] = new ArrayList();
        }
        for (int i = 0; i < m; i++) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            edge[i][0] = u;
            edge[i][1] = v;
            map[u].add(i);
            map[v].add(i);
        }
        int s = in.nextInt() - 1;
        int t = in.nextInt() - 1;
        int d1 = in.nextInt();
        int d2 = in.nextInt();
        boolean[] check = new boolean[n];
        ArrayList<Com> list = new ArrayList();
        ArrayList<Integer> result = new ArrayList();

        for (int i = 0; i < n; i++) {
            if (i != s && i != t && !check[i]) {
                Com c = cal(i, s, t, check, edge, map);
                result.addAll(c.list);
                if (c.toS == -1) {
                    d2--;
                    result.add(c.toT);
                } else if (c.toT == -1) {
                    d1--;
                    result.add(c.toS);
                } else {
                    list.add(c);
                }
            }
        }
        if (list.size() > 0) {
            Com tmp = list.get(0);
            d1--;
            d2--;
            result.add(tmp.toS);
            result.add(tmp.toT);
            for (int i = 1; i < list.size(); i++) {
                Com c = list.get(i);
                if (d1 >= d2) {
                    result.add(c.toS);
                    d1--;
                } else {
                    result.add(c.toT);
                    d2--;
                }
            }
            if (d1 >= 0 && d2 >= 0) {
                out.println("Yes");
                for (int i : result) {
                    out.println((edge[i][0] + 1) + " " + (edge[i][1] + 1));
                }
            } else {
                out.println("No");
            }
        } else {
            for (int i : map[s]) {
                if (nxt(s, edge[i]) == t) {
                    d1--;
                    d2--;
                    result.add(i);
                    break;
                }
            }
            if (d1 >= 0 && d2 >= 0) {
                out.println("Yes");
                for (int i : result) {
                    out.println((edge[i][0] + 1) + " " + (edge[i][1] + 1));
                }
            } else {
                out.println("No");
            }
        }

        out.close();
    }

    static Com cal(int index, int s, int t, boolean[] check, int[][] edge, ArrayList<Integer>[] map) {
        LinkedList<Integer> q = new LinkedList();
        q.add(index);
        Com result = new Com();
        check[index] = true;
        while (!q.isEmpty()) {
            int node = q.poll();
            for (int i : map[node]) {
                int o = nxt(node, edge[i]);
                if (o != s && o != t && !check[o]) {
                    check[o] = true;
                    q.add(o);
                    result.list.add(i);
                } else if (o == s && result.toS == -1) {
                    result.toS = i;
                } else if (o == t && result.toT == -1) {
                    result.toT = i;
                }
            }
        }
        return result;
    }

    static int nxt(int u, int[] edge) {
        return edge[0] == u ? edge[1] : edge[0];
    }

    static class Com {

        int toS = -1, toT = -1;
        ArrayList<Integer> list = new ArrayList();
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
