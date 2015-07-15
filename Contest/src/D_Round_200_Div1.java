
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
public class D_Round_200_Div1 {

    public static long MOD = 1000000007;
    static int index = 0;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
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
        int[] s = new int[n];
        int[] e = new int[n];
        visit(0, 0, s, e, map);
        //System.out.println(Arrays.toString(s) + " " + Arrays.toString(e));
        int m = in.nextInt();
        Node fill = new Node(0, index - 1);
        Node pour = new Node(0, index - 1);
        for (int i = 0; i < m; i++) {
            int t = in.nextInt();
            int v = in.nextInt() - 1;
           
            if (t == 1) {
                update(s[v], e[v], i, false, fill);
            } else if (t == 2) {
                update(s[v], s[v], i, true, pour);
                update(e[v], e[v], i, true, pour);
            } else {
                int a = get(s[v], s[v], true, fill);
                int b = get(s[v], e[v], false, pour);
              //  System.out.println("QUERY " +  i + " "+ a + " " + b + " " + v);
                if (a <= b) {
                    out.println(0);
                } else {
                    out.println(1);
                }
            }
            //System.out.println("After " + i);
            //print(fill);
        }
        out.close();
    }
    static void print(Node node){
        if(node == null){
            return;
        }
        System.out.println(node.l + " " + node.r + " " + node.val);
        print(node.left);
        print(node.right);
    }

    static void visit(int node, int pa, int[] s, int[] e,
            ArrayList<Integer>[] map) {
        s[node] = index++;
        for (int i : map[node]) {
            if (i != pa) {
                visit(i, node, s, e, map);
            }
        }
        e[node] = index++;
    }

    static int get(int l, int r, boolean up, Node node) {
        if (node == null) {
            return -1;
        }
        if (node.l > r || node.r < l) {
            return -1;
        }
        if (l <= node.l && node.r <= r) {
            return node.val;
        }
        int result = Math.max(get(l, r, up, node.right),
                get(l, r, up, node.left));

        if (up) {
            result = Math.max(result, node.val);
        }
        return result;
    }

    static void update(int l, int r, int v, boolean up, Node node) {
        if (node.l > r || node.r < l) {
            return;
        }
        if (l <= node.l && node.r <= r) {
            node.val = v;
            return;
        }
        if (node.left == null) {
            node.left = new Node(node.l, (node.l + node.r) / 2);
            if (!up) {
                node.left.val = node.val;
            }
        }
        update(l, r, v, up, node.left);
        if (node.right == null) {
            node.right = new Node((node.l + node.r) / 2 + 1, node.r);
            if (!up) {
                node.right.val = node.val;
            }
        }
        update(l, r, v, up, node.right);
        if (up) {
            node.val = Math.max(node.val, Math.max(node.left.val, node.right.val));
        }
    }

    static class Node {

        int l, r;
        Node left = null, right = null;
        int val = -1;

        public Node(int l, int r) {
            super();
            this.l = l;
            this.r = r;
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
