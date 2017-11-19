
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
public class E_Round_442_Div2 {

    public static long MOD = 1000000007;
    static int[] low, high;
    static int count = 0;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();

        int n = in.nextInt();
        low = new int[n];
        high = new int[n];
        ArrayList<Integer>[] map = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            map[i] = new ArrayList();
        }
        for (int i = 1; i < n; i++) {
            int v = in.nextInt() - 1;
            map[v].add(i);
        }
        int[] p = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = in.nextInt();
        }
        cal(0, map);
        Node root = new Node(0, count);
        for (int i = 0; i < n; i++) {

            addTotal(low[i], low[i], 1, root);
            if (p[i] == 1) {
                //System.out.println(i);
                add(low[i], low[i], 1, root);
            }
        }
        int q = in.nextInt();
        for (int i = 0; i < q; i++) {
            String v = in.next();
            int t = in.nextInt() - 1;
            if ("get".equals(v)) {
//                if (t == 2) {
//                    System.out.println(low[t] + " " + high[t] + " " + get(low[t], low[t], root) + " " + get(high[t], high[t], root));
//                }
                out.println(get(low[t], high[t], root));
            } else {
                reverse(low[t], high[t], root);
            }
        }
        out.close();
    }

    static void cal(int index, ArrayList<Integer>[] map) {
        low[index] = count++;
        for (int i : map[index]) {
            cal(i, map);
        }
        high[index] = count++;
    }

    static void add(int l, int r, int v, Node node) {
        if (l > node.r || r < node.l) {
            return;
        }
        if (l == node.l && node.r == r) {
            node.v = 1;
            return;
        }
        int mid = (node.l + node.r) / 2;
        if (node.left == null) {
            node.left = new Node(node.l, mid);
            node.right = new Node(mid + 1, node.r);
        }
        add(l, r, v, node.left);
        add(l, r, v, node.right);
        node.v = node.left.v + node.right.v;
    }

    static void addTotal(int l, int r, int v, Node node) {
        if (l > node.r || r < node.l) {
            return;
        }
        if (l == node.l && node.r == r) {
            node.total = 1;
            return;
        }
        int mid = (node.l + node.r) / 2;
        if (node.left == null) {
            node.left = new Node(node.l, mid);
            node.right = new Node(mid + 1, node.r);
        }
        addTotal(l, r, v, node.left);
        addTotal(l, r, v, node.right);
        node.total = node.left.total + node.right.total;
    }

    static void reverse(int l, int r, Node node) {
        if (l > node.r || r < node.l) {
            return;
        }
        if (l <= node.l && node.r <= r) {
            node.v = node.total - node.v;
            node.inver = !node.inver;
            return;
        }
        int mid = (node.l + node.r) / 2;
        if (node.left == null) {
            node.left = new Node(node.l, mid);
            node.right = new Node(mid + 1, node.r);
        }
        push(node);
        reverse(l, r, node.left);
        reverse(l, r, node.right);
        node.v = node.left.v + node.right.v;
    }

    static int get(int l, int r, Node node) {
        //System.out.println(node + " " + l + " " + r);
        if (l > node.r || r < node.l) {
            return 0;
        }
        if (l <= node.l && node.r <= r) {
            return node.v;
        }
        int mid = (node.l + node.r) / 2;
        if (node.left == null) {
            node.left = new Node(node.l, mid);
            node.right = new Node(mid + 1, node.r);
        }
        push(node);
        int a = get(l, r, node.left);
        int b = get(l, r, node.right);

        return a + b;
    }

    static void push(Node node) {
        if (node.inver) {
            node.inver = false;
            node.left.inver = !node.left.inver;
            node.left.v = node.left.total - node.left.v;
            node.right.inver = !node.right.inver;
            node.right.v = node.right.total - node.right.v;
            node.v = node.left.v + node.right.v;
        }
    }

    static class Node {

        Node left, right;
        int l, r, total = 0, v = 0;
        boolean inver = false;

        public Node(int l, int r) {
            this.l = l;
            this.r = r;
        }

        @Override
        public String toString() {
            return "Node{" + "l=" + l + ", r=" + r + ", total=" + total + ", v=" + v + ", inver=" + inver + '}';
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
