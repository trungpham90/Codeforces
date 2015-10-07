
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * #
 *
 * @author pttrung
 */
public class Upvotes_Quora {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();

        int n = in.nextInt();
        int k = in.nextInt();
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = in.nextInt();
        }

        Node decrease = new Node(0, n - 1);
        Node increase = new Node(0, n - 1);
        int index = 0;
        for (int i = 0; i < n; i++) {
            update(true, i, data[i], increase);
            update(false, i, data[i], decrease);
            if (i >= k) {
                update(true, i - k, -1, increase);
                update(false, i - k, -1, decrease);
            }
            if (i + 1 >= k) {
                long v = (increase.total - decrease.total);
                out.println(v);
            }
        }
        out.close();
    }

    static void update(boolean increase, int index, int val, Node node) {
        if (node.l > index || node.r < index) {
            return;
        }
        if (node.l == index && node.r == index) {
            if (val == -1) {
                node.total = 0;
                node.isContinuos = false;
                node.a = -1;
                node.b = -1;
                node.leftSide = 0;
                node.rightSide = 0;
            } else {
                node.total = 0;
                node.isContinuos = true;
                node.a = val;
                node.b = val;
                node.leftSide = 1;
                node.rightSide = 1;

            }
//            System.out.println("UDPATE " + index + " " + node.total + " "
//                    + node.l + " " + node.r + " " + node.a + " " + node.b);
            return;
        }
        int mid = (node.r + node.l) >> 1;
        if (node.left == null) {
            node.left = new Node(node.l, mid);
            node.right = new Node(mid + 1, node.r);
        }
        update(increase, index, val, node.left);
        update(increase, index, val, node.right);

        if (node.left.a == -1 && node.right.b == -1) {
            // System.out.println("HO HO " + index + " " + node.l + " " +
            // node.r);

            node.total = 0;
            node.a = -1;
            node.b = -1;
            node.leftSide = 0;
            node.rightSide = 0;
            node.isContinuos = false;
        } else if (node.left.a == -1) {
            // System.out.println("HUC HUC " + index + " " + node.l + " " +
            // node.r);

            node.total = node.right.total;
            node.a = node.right.a;
            node.b = node.right.b;
            node.leftSide = node.right.leftSide;
            node.rightSide = node.right.rightSide;
            node.isContinuos = node.right.isContinuos;
        } else if (node.right.a == -1) {
            // System.out.println("HE HE " + index + " " + node.l + " " +
            // node.r);
            node.total = node.left.total;
            node.a = node.left.a;
            node.b = node.left.b;
            node.leftSide = node.left.leftSide;
            node.rightSide = node.left.rightSide;
            node.isContinuos = node.left.isContinuos;
        } else {
            // System.out.println("HA HA " + index + " " + node.l + " " +
            // node.r);

            if (node.left.isContinuos && node.right.isContinuos) {
                if (isContinuous(increase, node.left.b, node.right.a)) {
                    node.total = count(node.left.leftSide
                            + node.right.rightSide);
                } else {
                    node.total = node.left.total + node.right.total;
                }
            } else if (node.left.isContinuos) {
                if (isContinuous(increase, node.left.b, node.right.a)) {
                    node.total = node.right.total - count(node.right.leftSide)
                            + count(node.left.leftSide + node.right.leftSide);
                } else {
                    node.total = node.left.total + node.right.total;
                }
            } else if (node.right.isContinuos) {
                if (isContinuous(increase, node.left.b, node.right.a)) {
                    node.total = node.left.total - count(node.left.rightSide)
                            + count(node.left.rightSide + node.right.rightSide);
                } else {
                    node.total = node.left.total + node.right.total;
                }
            } else {

                if (isContinuous(increase, node.left.b, node.right.a)) {
                    node.total = node.left.total + node.right.total
                            - count(node.left.rightSide)
                            - count(node.right.leftSide)
                            + count(node.left.rightSide + node.right.leftSide);
                } else {
                    node.total = node.left.total + node.right.total;
                }
            }
            node.isContinuos = node.right.isContinuos && node.left.isContinuos
                    && isContinuous(increase, node.left.b, node.right.a);
            node.a = node.left.a;
            node.b = node.right.b;
            if (node.right.isContinuos) {
                if (isContinuous(increase, node.left.b, node.right.a)) {
                    node.rightSide = node.left.rightSide + node.right.leftSide;
                } else {
                    node.rightSide = node.right.rightSide;
                }

            } else {
                node.rightSide = node.right.rightSide;
            }
            if (node.left.isContinuos) {
                if (isContinuous(increase, node.left.b, node.right.a)) {
                    node.leftSide = node.left.leftSide + node.right.leftSide;
                } else {
                    node.leftSide = node.left.leftSide;
                }
            } else {
                node.leftSide = node.left.leftSide;
            }

        }
//        System.out.println(index + " " + node.l + " " + node.r + " "
//                + node.isContinuos + " " + node.a + " " + node.b + " "
//                + node.total);
    }

    static long count(int v) {
        long result = v - 1;
        return (result * (result + 1)) / 2L;

    }

    static boolean isContinuous(boolean increase, int left, int right) {
        if (increase) {
            if (left <= right) {
                return true;
            }
        } else {
            if (left >= right) {
                return true;
            }
        }
        return false;
    }

    static class Node {

        int l, r;
        long total;
        boolean isContinuos = false;
        int a = -1, b = -1;
        int leftSide = 0, rightSide = 0;
        Node left, right;

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
            // br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("input.txt"))));
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
