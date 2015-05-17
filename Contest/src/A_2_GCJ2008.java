
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
public class A_2_GCJ2008 {

    public static long MOD = 1000000007;
    static int[][]dp;

    public static void main(String[] args) throws FileNotFoundException {
         PrintWriter out = new PrintWriter(new FileOutputStream(new File(
         "output.txt")));
  //      PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int t = in.nextInt();
        for (int z = 0; z < t; z++) {
            int m = in.nextInt();
            int v = in.nextInt();
            Node[] data = new Node[m];
            dp = new int[2][m];
            for(int[]a : dp){
                Arrays.fill(a,-2);
            }
            int index = 0;
            for (int i = 0; i < (m - 1) / 2; i++) {
                boolean and = in.nextInt() == 1;
                boolean change = in.nextInt() == 1;
                data[index] = new Node(index, false, change, and, 0);
                if (index > 0) {
                    int pa = 0;
                    if (index % 2 != 0) {
                        pa = index / 2;
                        data[pa].left = data[index];
                    } else {
                        pa = (index - 1) / 2;
                        data[pa].right = data[index];
                    }
                }
                index++;
            }
            for (int i = 0; i < (m + 1) / 2; i++) {
                int value = in.nextInt();
                data[index] = new Node(index, true, false, false, value);
                if (index > 0) {
                    int pa = 0;
                    if (index % 2 != 0) {
                        pa = index / 2;
                        data[pa].left = data[index];
                    } else {
                        pa = (index - 1) / 2;
                        data[pa].right = data[index];
                    }
                }
                index++;
            }
            int result = min(v, data[0]);
            if (result != -1) {
                out.println("Case #" + (z + 1) + ": " + result);
            } else {
                out.println("Case #" + (z + 1) + ": IMPOSSIBLE");
            }
        }
        out.close();
    }

    static int min(int target, Node node) {
        if (node.leaf) {
            if (node.val == target) {
                return 0;
            }
            return -1;
        }
        if(dp[target][node.index] != -2){
            return dp[target][node.index];
        }
        int result = -1;
        if (node.and) {
            if (target == 1) {
                int a = min(1, node.left);
                if (a != -1) {
                    int b = min(1, node.right);
                    if (b != -1) {
                        result = a + b;
                    }
                }
            } else {
                int a = min(1, node.left);
                int b = min(1, node.right);
                int c = min(0, node.left);
                int d = min(0, node.right);
                if (a != -1) {
                    if (d != -1) {
                        result = a + d;
                    }
                }
                if (b != -1) {
                    if (c != -1) {
                        if (result == -1 || result > b + c) {
                            result = b + c;
                        }
                    }
                }
                if (c != -1 && d != -1) {
                    if (result == -1 || result > c + d) {
                        result = c + d;
                    }
                }
            }
        } else {
            if (target == 1) {
                int a = min(1, node.left);
                int b = min(1, node.right);
                int c = min(0, node.left);
                int d = min(0, node.right);
                if (a != -1) {
                    if (d != -1) {
                        result = a + d;
                    }
                }
                if (b != -1) {
                    if (c != -1) {
                        if (result == -1 || result > b + c) {
                            result = b + c;
                        }
                    }
                }
                if (a != -1 && b != -1) {
                    if (result == -1 || result > a + b) {
                        result = a + b;
                    }
                }


            } else {

                int a = min(0, node.left);
                if (a != -1) {
                    int b = min(0, node.right);
                    if (b != -1) {
                        result = a + b;
                    }
                }

            }
        }

        if (node.change) {
            if (!node.and) {
                if (target == 1) {
                    int a = min(1, node.left);
                    if (a != -1) {
                        int b = min(1, node.right);
                        if (b != -1) {
                            if (result == -1 || result > 1 + a + b) {
                                result = 1 + a + b;
                            }
                        }
                    }
                } else {
                    int a = min(1, node.left);
                    int b = min(1, node.right);
                    int c = min(0, node.left);
                    int d = min(0, node.right);
                    if (a != -1) {
                        if (d != -1) {
                            if (result == -1 || result > 1 + a + d) {
                                result = 1 + a + d;
                            }
                        }
                    }
                    if (b != -1) {
                        if (c != -1) {
                            if (result == -1 || result > 1 + b + c) {
                                result = 1 + b + c;
                            }
                        }
                    }
                    if (c != -1 && d != -1) {
                        if (result == -1 || result > 1 + c + d) {
                            result = 1 + c + d;
                        }
                    }
                }
            } else {
                if (target == 1) {
                    int a = min(1, node.left);
                    int b = min(1, node.right);
                    int c = min(0, node.left);
                    int d = min(0, node.right);
                    if (a != -1) {
                        if (d != -1) {
                            if (result == -1 || result > 1 + a + d) {
                                result = 1 + a + d;
                            }
                        }
                    }
                    if (b != -1) {
                        if (c != -1) {
                            if (result == -1 || result > 1 + b + c) {
                                result = 1 + b + c;
                            }
                        }
                    }
                    if (a != -1 && b != -1) {
                        if (result == -1 || result > 1 + a + b) {
                            result = 1 + a + b;
                        }
                    }


                } else {

                    int a = min(0, node.left);
                    if (a != -1) {
                        int b = min(0, node.right);
                        if (b != -1) {
                            if (result == -1 || result > 1 + a + b) {
                                result = 1 + a + b;
                            }
                        }
                    }

                }
            }
        }
        return dp[target][node.index] = result;
    }

    static class Node {

        boolean leaf;
        boolean change;
        boolean and;
        int index, val;
        Node left, right;

        public Node(int index, boolean leaf, boolean change, boolean and, int val) {
            this.index = index;
            this.leaf = leaf;
            this.change = change;
            this.and = and;
            this.val = val;
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
            // System.setOut(new PrintStream(new BufferedOutputStream(System.out), true));
           // br = new BufferedReader(new InputStreamReader(System.in));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("A-large-practice.in"))));
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
