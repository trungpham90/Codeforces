
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
public class D_FB_2016_Qual {

    public static long MOD = 1000000007;
    static int cur = 0;
    static long[][] dp;

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new FileOutputStream(new File(
                "output.txt")));
        // PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int t = in.nextInt();
        for (int z = 0; z < t; z++) {
            cur = 0;
            int n = in.nextInt();
            int k = in.nextInt();
            String[] data = new String[n];
            ArrayList<Node> list = new ArrayList();
            Node root = new Node(cur++);
            list.add(root);
            for (int i = 0; i < n; i++) {
                data[i] = in.next();
                add(0, root, data[i], list);
            }

            int[] nxt = new int[cur];
            int[] first = new int[cur];
            Arrays.fill(nxt, -1);
            Arrays.fill(first, -1);
            dp = new long[k + 1][cur];
            for (long[] a : dp) {
                Arrays.fill(a, -1);
            }
            LinkedList<Node> q = new LinkedList();
            q.add(root);
            while (!q.isEmpty()) {
                Node node = q.poll();
                int lst = -1;
                for (int i = 25; i >= 0; i--) {
                    if (node.child[i] != null) {
                        nxt[node.child[i].index] = lst;
                        lst = node.child[i].index;
                        first[node.index] = lst;
                        q.add(node.child[i]);
                    }
                }
            }
            long v = cal(0, k, list, nxt, first);
            //  out.println("TOTAL " + root.total + " " + Arrays.toString(nxt) + " " + Arrays.toString(first));
            out.println("Case #" + (z + 1) + ": " + v);
        }
        out.close();

    }

    static long cal(int index, int need, ArrayList<Node> list, int[] nxt, int[] first) {
        if (need == 0) {
            return 0;
        }
        if (index == -1) {
            return Integer.MAX_VALUE;
        }
        if (dp[need][index] != -1) {
            return dp[need][index];
        }

        Node node = list.get(index);
        long result = Integer.MAX_VALUE;
        if (nxt[index] == -1) {
            if (node.end) {
                result = Long.min(3 + cal(first[index], need - 1, list, nxt, first), result);
            } else {
                result = Long.min((index == 0 ? 0 : 2) + cal(first[index], need, list, nxt, first), result);
            }
        } else {
            result = Long.min(result, cal(nxt[index], need, list, nxt, first));

            for (int i = 1; i <= need && i <= node.total; i++) {
                long v = cal(nxt[index], need - i, list, nxt, first);
                if (node.end) {
                    v += 3 + cal(first[index], i - 1, list, nxt, first);
                } else {
                    v += 2 + cal(first[index], i, list, nxt, first);
                }
                result = Long.min(result, v);
            }
        }
        //System.out.println(result + " " + need + " " + index + " " + node.total);
        return dp[need][index] = result;
    }

    static void add(int index, Node pa, String data, ArrayList<Node> list) {
        pa.total++;
        if (index == data.length()) {
            pa.end = true;
            return;
        }
        int nxt = data.charAt(index) - 'a';
        if (pa.child[nxt] == null) {
            pa.child[nxt] = new Node(cur++);
            list.add(pa.child[nxt]);
        }
        add(index + 1, pa.child[nxt], data, list);
    }

    static class Node {

        int index;
        Node[] child = new Node[26];
        int total = 0;
        boolean end = false;

        Node(int index) {
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
            return x - o.x;
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
            //br = new BufferedReader(new InputStreamReader(System.in));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("text_editor.txt"))));
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
