
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
public class E_Round_248_Div2 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        Point[] data = new Point[m];
        ArrayList<Integer>[] map = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            map[i] = new ArrayList();
        }
        for (int i = 0; i < m; i++) {
            int a = in.nextInt() - 1;
            int b = in.nextInt() - 1;
            data[i] = new Point(a, b);
            map[a].add(i);
            map[b].add(i);
        }
        if (m % 2 != 0) {
            out.println("No solution");
        } else {
            boolean[] check = new boolean[n];
            int[] pair = new int[m];
            Arrays.fill(pair, -1);
            boolean ok = true;
            for (int i = 0; i < n && ok; i++) {
                if (!check[i]) {
                    int v = cal(i, -2, pair, check, data, map);
                    if (v != -1) {
                        ok = false;
                    }
                }
            }
            if (!ok) {
                out.println("No solution");
            } else {
                for (int i = 0; i < m; i++) {
                    if (pair[i] != -1) {
                        Point a = data[i];
                        Point b = data[pair[i]];
                        if (a.x == b.x) {
                            out.println((a.y + 1) + " " + (a.x + 1) + " " + (b.y + 1));
                        } else if (a.x == b.y) {
                            out.println((a.y + 1) + " " + (a.x + 1) + " " + (b.x + 1));
                        } else if (a.y == b.x) {
                            out.println((a.x + 1) + " " + (a.y + 1) + " " + (b.y + 1));
                        } else {
                            out.println((a.x + 1) + " " + (a.y + 1) + " " + (b.x + 1));
                        }
                        pair[pair[i]] = -1;
                    }
                }
            }

        }
        out.close();
    }

    public static int cal(int index, int last, int[] pair, boolean[] check, Point[] data, ArrayList<Integer>[] map) {
       // System.out.println(index + " " + last + " " + Arrays.toString(pair));
        check[index] = true;
        int result = (last < 0 || pair[last] == -1) ? last : -1;
        for (int i : map[index]) {
            if (i == last) {
                continue;
            }
            int nxt = data[i].x == index ? data[i].y : data[i].x;
         //   System.out.println("CALL " + result + " " + i + " " + nxt);
            if (pair[i] == -1) {
                if (result >= 0) {
                    pair[result] = i;
                    pair[i] = result;
                    result = -1;
                } else {
                    result = i;
                }
            }

            if (!check[nxt]) {

                result = cal(nxt, i, pair, check, data, map);
            }

        }
      //  System.out.println("PRE " + result + " " + index + " " + last + " " + Arrays.toString(pair));
        if (result != -1 && result != last) {
            if (last != -2) {
                Point p = data[pair[last]];
                if (p.x == index || p.y == index) {
                    pair[result] = pair[last];
                    pair[pair[last]] = result;
                    result = last;
                    pair[last] = -1;
                } else {
                    pair[result] = last;
                    int tmp = pair[last];
                    pair[last] = result;
                    pair[tmp] = -1;
                    result = tmp;

                }
            } else {
                result = -2;
            }
        }
      //  System.out.println("END " + index + " " + last + " " + Arrays.toString(pair) + " " + result);
        return result;
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
        public String toString() {
            return "Point{" + "x=" + x + ", y=" + y + '}';
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
