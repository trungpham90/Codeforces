
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.lang.Long;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Problem statement: http://codeforces.com/problemset/problem/491/A
 *
 * @author thepham
 *
 */
public class C_Zepto2015 {

    static long Mod = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();

        long c = in.nextInt();
        long[] h = new long[2];
        for (int i = 0; i < 2; i++) {
            h[i] = in.nextInt();
        }
        long[] w = new long[2];
        for (int i = 0; i < 2; i++) {
            w[i] = in.nextInt();
        }
        if (w[0] < w[1]) {
            long tmp = h[0];
            h[0] = h[1];
            h[1] = tmp;
            tmp = w[0];
            w[0] = w[1];
            w[1] = tmp;
        }
        long gcd = gcd(w[0], w[1]);
        long lcm = (w[0] / gcd) * w[1];
        long max = Long.max((lcm / w[0]) * h[0], (lcm / w[1]) * h[1]);
        //System.out.println(max + " " + lcm);
        long total = (c / lcm);
        if (total > 0) {
            total--;
            c %= lcm;
            c += lcm;
            total *= max;
        }

        long st = 0;
        long ed = c / w[0];
        long result = cal(w, h, c, ed);
        while (st < ed) {
            long mid = (st + ed) / 2;
            long a = cal(w, h, c, mid);
            long b = cal(w, h, c, mid + 1);
            //System.out.println(a + " " + b + " " + st + " " + ed);
            if (a < b) {
                st = mid + 1;
                result = Long.max(result, b);
            } else {
                ed = mid - 1;
                result = Long.max(result, a);
            }
        }
       // System.out.println(st + " " + ed + " " + result);
        for (int i = 0; i <= c / w[0]; i++) {
            //if (cal(w, h, c, i) > cal(w, h, c, i - 1) && cal(w, h, c, i) > cal(w, h, c, i + 1)) {
            result = Long.max(result, cal(w, h, c, i));
            //}
        }
        out.println(result + total);
        out.close();
    }

    static long cal(long[] w, long[] h, long c, long num) {
        long left = c - num * w[0];
        left /= w[1];
        return num * h[0] + left * h[1];
    }

    public static long f(long x, long c, long[] h, long[] w) {
        long left = c - (x * w[0]);
        if (left < 0) {
            return 0;
        }
        long result = x * h[0] + (left / w[1]) * h[1];

        // System.out.println(result + " " + c + " " + x);
        return result;
    }

    public static int[] buildKMP(String val) {
        int[] data = new int[val.length() + 1];
        int i = 0;
        int j = -1;
        data[0] = -1;
        while (i < val.length()) {
            while (j >= 0 && val.charAt(i) != val.charAt(j)) {
                j = data[j];
            }
            i++;
            j++;

            data[i] = j;
            // System.out.println(val + " " + i + " " + data[i]);
        }
        return data;
    }

    static int find(int index, int[] u) {
        if (u[index] != index) {
            return u[index] = find(u[index], u);
        }
        return index;
    }

    static int crossProduct(Point a, Point b) {
        return a.x * b.y + a.y * b.x;
    }

    static long squareDist(Point a) {
        long x = a.x;
        long y = a.y;
        return x * x + y * y;
    }

    static Point minus(Point a, Point b) {
        return new Point(a.x - b.x, a.y - b.y);
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

    static class Point {

        int x, y;

        public Point(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "{" + x + " " + y + "}";
        }
    }

    static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public static class FT {

        int[] data;

        FT(int n) {
            data = new int[n];
        }

        public void update(int index, int value) {
            while (index < data.length) {
                data[index] += value;
                index += (index & (-index));
            }
        }

        public int get(int index) {
            int result = 0;
            while (index > 0) {
                result += data[index];
                index -= (index & (-index));
            }
            return result;

        }
    }

    static class Scanner {

        BufferedReader br;
        StringTokenizer st;

        public Scanner() throws FileNotFoundException {
            // System.setOut(new PrintStream(new
            // BufferedOutputStream(System.out), true));
            br = new BufferedReader(new InputStreamReader(System.in));
            // br = new BufferedReader(new InputStreamReader(new
            // FileInputStream(
            // new File("input.txt"))));
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
