
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
public class E_Edu_Round_15 {

    public static long MOD = 1000000007;
    static long[] sum, min;
    static int[] check;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        long k = in.nextLong();
        sum = new long[n];
        min = new long[n];
        int[] nxt = new int[n];
        ArrayList<Integer>[] map = new ArrayList[n];

        long[] w = new long[n];

        for (int i = 0; i < n; i++) {
            nxt[i] = in.nextInt();
            map[i] = new ArrayList();
        }
        for (int i = 0; i < n; i++) {
            map[nxt[i]].add(i);
            w[i] = in.nextInt();
        }

        Node root = new Node(0, n - 1);
        check = new int[n];
        Arrays.fill(check, -1);
        long[] mod = new long[n + 1];
        long[] minCycle = new long[n + 1];
        minCycle[0] = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (check[i] == -1) {
                int length = 0;
                LinkedList<Integer> q = new LinkedList();
                q.add(i);
                check[i] = 0;
                while (!q.isEmpty()) {
                    int node = q.poll();
                    if (check[nxt[node]] == -1) {
                        check[nxt[node]] = 1 + check[node];
                        q.add(nxt[node]);
                    } else {
                        length = 1 + check[node] - check[nxt[node]];

                        int start = nxt[node];
                        for (int j = 1; j <= length; j++) {
                            mod[j] = mod[j - 1] + w[start];

                            minCycle[j] = Long.min(minCycle[j - 1], w[start]);
                            start = nxt[start];
                        }
                        //System.out.println("CYCLE " + length + " " + nxt[node]);
                        cal(nxt[node], nxt[node], length, mod, minCycle, 0, k, w, root, map);
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            out.println(sum[i] + " " + min[i]);
        }
        out.close();
    }

    static class Node {

        int l, r;
        Node left, right;
        long min = Long.MAX_VALUE, sum = 0;

        public Node(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    static void update(int index, long v, Node node) {
        if (node.l > index || node.r < index) {
            return;
        }
        if (node.l == node.r && node.l == index) {
            node.min = v;
            node.sum = v;
            return;
        }
        int mid = (node.l + node.r) / 2;
        if (node.left == null) {
            node.left = new Node(node.l, mid);
            node.right = new Node(mid + 1, node.r);
        }
        update(index, v, node.left);
        update(index, v, node.right);
        node.min = Long.min(node.left.min, node.right.min);
        node.sum = node.left.sum + node.right.sum;
    }

    static long getMin(int l, int r, Node node) {
        if (node == null || node.l > r || node.r < l) {
            return Long.MAX_VALUE;
        }
        if (l <= node.l && node.r <= r) {
            return node.min;
        }
        return Long.min(getMin(l, r, node.left), getMin(l, r, node.right));
    }

    static long getSum(int l, int r, Node node) {
        if (node == null || node.l > r || node.r < l) {
            return 0;
        }
        if (l <= node.l && node.r <= r) {
            return node.sum;
        }
        return getSum(l, r, node.left) + getSum(l, r, node.right);
    }

    static void cal(int index, int start, int length, long[] mod, long[] minCycle, int dist, long k, long[] w, Node root, ArrayList<Integer>[] map) {
        check[index] = -2;
        if (dist > 0) {
            update(dist, w[index], root);
        }
        
        if (dist >= k) {
            sum[index] = getSum(dist - (int) k + 1, dist, root);
            min[index] = getMin(dist - (int) k + 1, dist, root);
        } else {
            long left = k - dist;
            long other = (left / length) * mod[length] + mod[(int) (left % length)];
            //System.out.println("TEST " + index + " " + dist + " " + start + " " + other + " " + (left % length) + " " + left + " " + length );
            sum[index] = getSum(0, dist, root) + other;
            min[index] = Long.min(getMin(0, dist, root), left >= length ? minCycle[length] : minCycle[(int) (left % length)]);
        }
        for (int i : map[index]) {
            if (i != start) {
                cal(i, start, length, mod, minCycle, dist + 1, k, w, root, map);
            }
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
