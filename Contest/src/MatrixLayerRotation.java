
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
public class MatrixLayerRotation {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        int[][] data = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                data[i][j] = in.nextInt();
            }
        }
        int a = n;
        int b = m;
        int[][] result = new int[n][m];
        for (int i = 0; i < n && a > 0 && b > 0; i++) {
            int total = b;
            if (a > 1) {
                total += b;
                total += 2 * a;
                total -= 4;
            }

            int q = k % total;
            for (int j = i; j < i + b; j++) {
                int[] p = cal(i, j, q, i, i + a - 1, i, i + b - 1, n, m);
                result[p[0]][p[1]] = data[i][j];
            }
            for (int j = i; j < i + b; j++) {
                int[] p = cal(i + a - 1, j, q, i, i + a - 1, i, i + b - 1, n, m);

                result[p[0]][p[1]] = data[i + a - 1][j];
            }
            for (int j = i + 1; j + 1 < i + a; j++) {
                int[] p = cal(j, i, q, i, i + a - 1, i, i + b - 1, n, m);
             //   System.out.println(Arrays.toString(p) + " " + j + " " + i + " " + data[j][i] + " " + q);
                result[p[0]][p[1]] = data[j][i];
                p = cal(j, i + b - 1, q, i, i + a - 1, i, i + b - 1, n, m);
                result[p[0]][p[1]] = data[j][i + b - 1];
            }
            a -= 2;
            b -= 2;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                out.print(result[i][j] + " ");
            }
            out.println();
        }
        out.close();
    }

    static int[] cal(int x, int y, int move, int minX, int maxX, int minY, int maxY, int n, int m) {
        if (move == 0) {
            return new int[]{x, y};
        }
        if (x == minX) {
            if (y != minY) {
                int v = Integer.min(move, y - minY);
                return cal(x, y - v, move - v, minX, maxX, minY, maxY, n, m);
            } else if (n > 1) {
                return cal(x + 1, minY, move - 1, minX, maxX, minY, maxY, n, m);
            } else {
                return cal(x, maxY, move - 1, minX, maxX, minY, maxY, n, m);
            }
        } else if (x > minX && x < maxX) {
            if (y == minY) {
                int v = Integer.min(move, maxX - x);
                return cal(x + v, y, move - v, minX, maxX, minY, maxY, n, m);
            } else {
                int v = Integer.min(move, x - minX);
                return cal(x - v, y, move - v, minX, maxX, minY, maxY, n, m);
            }
        } else if (y != maxY) {
            int v = Integer.min(move, maxY - y);
            return cal(x, y + v, move - v, minX, maxX, minY, maxY, n, m);
        } else {
            return cal(x - 1, y, move - 1, minX, maxX, minY, maxY, n, m);
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
