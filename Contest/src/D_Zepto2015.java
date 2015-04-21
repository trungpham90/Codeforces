/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
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
public class D_Zepto2015 {

    static long Mod = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int k = in.nextInt();
        String line = in.next();
        boolean[] check = new boolean[n];
        int[] z = zAlgo(line);
        for (int i = k; i <= n; i += k) {
            int l = i / k;
            int start = l;
            check[i - 1] = true;
            // System.out.println(start + " " + i);
            for (int j = 1; j < k; j++) {
                if (z[start] < l) {
                    check[i - 1] = false;
                    break;
                }
                start += l;
            }
        }
        int last = -1;
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                if (check[i - 1]) {
                    int l = i / k;
                    int nxt = Math.min(i + z[i] - 1, i + l - 1);
                    last = Math.max(last, nxt);
                }
            }
            if (last >= i) {
                out.print(1);
            } else if (check[i]) {
                out.print(1);
            } else {
                out.print(0);
            }
        }
        out.close();
    }

    public static int[] zAlgo(String line) {
        char[] s = line.toCharArray();
        int n = s.length;
        int[] z = new int[n];
        int L = 0, R = 0;
        for (int i = 1; i < n; i++) {
            if (i > R) {
                L = R = i;
                while (R < n && s[R - L] == s[R]) {
                    R++;
                }
                z[i] = R - L;
                R--;
            } else {
                int k = i - L;
                if (z[k] < R - i + 1) {
                    z[i] = z[k];
                } else {
                    L = i;
                    while (R < n && s[R - L] == s[R]) {
                        R++;
                    }
                    z[i] = R - L;
                    R--;
                }
            }
        }
        return z;
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
