
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
public class B_Round_243_Div1 {

    public static long MOD = 1000000007;
    static int[][][] dp;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        int h = in.nextInt();
        int[][] data = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                data[i][j] = in.nextInt();
            }
        }
        if (n <= h) {

            ArrayList<Point>[] map = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                map[i] = new ArrayList();
                int start = -1;
                for (int j = 0; j < m; j++) {
                    if (start == -1) {
                        start = j;
                    } else if (data[i][j] != data[i][start]) {
                        map[i].add(new Point(start, j - 1));
                        start = j;
                    }
                }
                map[i].add(new Point(start, m - 1));
                // System.out.println(map[i]);
            }
            int result = Integer.MAX_VALUE;
            dp = new int[2][m][m];
            for (int k = 0; k < (1 << n); k++) {
                int tmp = 0;
                for (int j = 0; j < m; j++) {
                    int a = 0, b = 0;
                    for (int i = 0; i < n; i++) {
                        int v = ((1 << i) & k) != 0 ? 1 : 0;
                        if (data[i][j] != v) {
                            a++;
                        } else {
                            b++;
                        }
                    }
                    tmp += Math.min(a, b);
                }
                result = Math.min(tmp, result);

            }
            //System.out.println(result);
            if (result > h) {
                out.println(-1);
            } else {
                out.println(result);
            }
        } else {
            int result = -1;
            for (int k = 0; k < n; k++) {
                int tmp = 0;
                for (int i = 0; i < n; i++) {
                    int x = 0;
                    int y = 0;
                    for (int j = 0; j < m; j++) {
                        if (data[i][j] != data[k][j]) {
                            x++;
                        } else {
                            y++;
                        }
                    }
                    tmp += Math.min(x, y);
                }
                if (tmp <= h && (result == -1 || result > tmp)) {
                    result = tmp;
                }
            }
            for (int k = 0; k < m; k++) {
                int tmp = 0;
                for (int j = 0; j < m; j++) {
                    int x = 0;
                    int y = 0;
                    for (int i = 0; i < n; i++) {

                        if (data[i][j] != data[i][k]) {
                            x++;
                        } else {
                            y++;
                        }
                    }
                    //System.out.println(x + " " + y + " " + j + " " +k);
                    tmp += Math.min(x, y);
                }
               // System.out.println(tmp + " " + k);
                if (tmp <= h && (result == -1 || result > tmp)) {
                    result = tmp;
                }
            }
            out.println(result);
        }

        out.close();
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
        public String toString() {
            return "Point{" + "x=" + x + ", y=" + y + '}';
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
