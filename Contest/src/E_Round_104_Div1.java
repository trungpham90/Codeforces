
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
public class E_Round_104_Div1 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        String line = in.next();
        Node root = new Node(0, n - 1);
        for (int i = 0; i < n; i++) {
            set(i, line.charAt(i) == '4' ? 1 : 0, root);
        }
        for (int i = 0; i < m; i++) {
            String command = in.next();
            if ("switch".equals(command)) {
                int l = in.nextInt() - 1;
                int r = in.nextInt() - 1;
                update(l, r, root);
            } else {
               // System.out.println(root.left + " " + root.right);
                out.println(n - root.best);
            }

        }
        out.close();
    }

    static int get(int l, int r, Node node) {
        if (node.l > r || node.r < l) {
            return 0;
        }
        if (l <= node.l && node.r <= r) {
            return node.best;
        }
        push(node);
        int a = get(l, r, node.left);
        int b = get(l, r, node.left);
        return a + b;
    }

    static void update(int l, int r, Node node) {
        if (node.l > r || node.r < l) {
            return;
        }
        if (l <= node.l && node.r <= r) {
            change(node);
            return;
        }
        push(node);
        update(l, r, node.left);
        update(l, r, node.right);
        calibrate(node);
    }

    static void push(Node node) {
        if (node.inverse) {
            change(node.left);
            change(node.right);
            node.inverse = false;
            calibrate(node);
        }
    }

    static void calibrate(Node node) {
        node.best = Math.min(node.left.rightNum + node.right.best, node.left.best + node.right.leftNum);
        node.worst = Math.max(node.left.rightNum + node.right.worst, node.left.worst + node.right.leftNum);
        node.leftNum = node.left.leftNum + node.right.leftNum;
        node.rightNum = node.left.rightNum + node.right.rightNum;
    }

    static void change(Node node) {        
        node.leftNum = node.r - node.l + 1 - node.leftNum;
        node.rightNum = node.r - node.l + 1 - node.rightNum;
        int tmp = node.best;
        node.best = node.r - node.l + 1 - node.worst;
        node.worst = node.r - node.l + 1 - tmp;
        node.inverse = !node.inverse;
    }

    static void set(int index, int v, Node node) {
        if (node.l > index || node.r < index) {
            return;
        }
        if (node.l == index && node.r == index) {
            node.best = 0;
            node.worst = 1;
            node.leftNum = v;
            node.rightNum = 1 - v;
            return;
        }
        int mid = (node.l + node.r) >> 1;
        if (node.left == null) {
            node.left = new Node(node.l, mid);
            node.right = new Node(mid + 1, node.r);
        }
        set(index, v, node.left);
        set(index, v, node.right);
        calibrate(node);
    }

    static class Node {

        boolean inverse = false;
        int best = 0, worst = 0, leftNum = 0, rightNum = 0;
        int l, r;
        Node left, right;

        Node(int l, int r) {
            this.l = l;
            this.r = r;
        }

        @Override
        public String toString() {
            return "Node{" + "best=" + best + ", leftNum=" + leftNum + ", rightNum=" + rightNum + ", l=" + l + ", r=" + r + '}';
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
