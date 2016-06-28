
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
public class B_Round_359_Div1 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int q = in.nextInt();
        ArrayList<Integer>[] map = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            map[i] = new ArrayList();
        }
        for (int i = 0; i < n - 1; i++) {
            map[in.nextInt() - 1].add(i + 1);
        }
        int[] size = new int[n];
        int[] brand = new int[n];
        Arrays.fill(brand, -1);
        int[] level = new int[n];
        cal(0, 0, map, size, brand, level);
        int[] dp = new int[n];
        Node[] data = new Node[q];
        for (int i = 0; i < q; i++) {
            int v = in.nextInt() - 1;
            data[i] = new Node(v, level[v], i);
        }
        Arrays.sort(data, (a, b) -> Integer.compare(a.level, b.level));
        int[] re = new int[q];
        for (int i = 0; i < q; i++) {
            if (dp[data[i].node] == 0) {
                ArrayList<Integer> list = new ArrayList();
                int start = data[i].node;
                list.add(start);
                while (brand[start] != -1) {
                    start = brand[start];
                    list.add(start);
                }
                for (int j = 0; j < list.size(); j++) {
                    if (size[list.get(j)] == 1) {
                        dp[list.get(j)] = list.get(j) + 1;
                    } else {
                        start = j;
                        int end = list.size() - 1;
                        dp[list.get(j)] = list.get(end) + 1;
                        while (start <= end) {
                            int mid = (start + end) >> 1;
                            if (size[brand[list.get(mid)]] <= size[list.get(j)] / 2) {
                                dp[list.get(j)] = list.get(mid) + 1;
                                end = mid - 1;
                            } else {
                                start = mid + 1;
                            }
                        }
                    }
                }
            }
            re[data[i].index] = dp[data[i].node];
        }
        for (int i : re) {
            out.println(i);
        }

        out.close();
    }

    static class Node {

        int node, level, index;

        public Node(int node, int level, int index) {
            this.node = node;
            this.level = level;
            this.index = index;
        }

    }

    static int cal(int index, int l, ArrayList<Integer>[] map, int[] result, int[] brand, int[] level) {
        int store = -1;
        int max = 0;
        int size = 1;
        level[index] = l;
        for (int i : map[index]) {
            int v = cal(i, l + 1, map, result, brand, level);
            if (v > max) {
                max = v;
                store = i;
            }
            size += v;
        }
        result[index] = size;
        brand[index] = store;
        return size;

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