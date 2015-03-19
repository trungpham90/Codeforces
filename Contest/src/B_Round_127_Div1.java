
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
 *
 *
 * 3 2 3 5
 * -2 -1 4 -1 2 7 3
 *
 * 10 1 -10617 30886 -7223 -63085 47793 -61665 -14614 60492 16649 -58579 3 8 1
 * 10 4 7 1 7 3 7
 *
 * 22862 -34877
 *
 * @author pttrung
 */
public class B_Round_127_Div1 {

    public static long MOD = 1000000007;
    static int[] X = {0, 1};
    static int[] Y = {1, 0};

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        long[][] data = new long[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                data[i][j] = in.nextInt();

            }
        }
        int a = 0, b = 0;
        int lo = 0;
        int hi = n;
        long minRow = Long.MAX_VALUE;
        long minCol = minRow;
        while (lo < hi) {
            int mid = (lo + hi) >> 1;
            long x = cal(true, mid, data);
            long y = cal(true, mid + 1, data);
            //System.out.println(x + " " + y + " " + mid);
            if (x <= y) {
                hi = mid;
                if (x < minRow || (x == minRow && mid < a)) {
                    minRow = x;
                    a = mid;
                }
            } else {
                lo = mid + 1;
                if (y < minRow || (y == minRow && mid + 1 < a)) {
                    minRow = y;
                    a = mid + 1;
                }
            }
        }
        lo = 0;
        hi = m;
        while (lo < hi) {
            int mid = (lo + hi) >> 1;
            long x = cal(false, mid, data);
            long y = cal(false, mid + 1, data);
           // System.out.println(x + " " + y + " " + mid);
            if (x <= y) {
                hi = mid;
                if (x < minCol || (x == minCol && mid < b)) {
                    minCol = x;
                    b = mid;
                }
            } else {
                lo = mid + 1;
                if (y < minCol || (y == minCol && mid + 1 < b)) {
                    minCol = y;
                    b = mid + 1;
                }
            }
        }
        out.println((minRow + minCol));
        out.println(a + " " + b);

        out.close();
    }

    public static long cal(boolean isRow, int x, long[][] data) {
        long result = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                long add = 0;
                if (isRow) {
                    if (x > i + 1) {
                        add = x - i - 1;
                    } else if (x < i) {
                        add = i - x;
                    }
                } else {
                    if (x > j + 1) {
                        add = x - j - 1;
                    } else if (x < j) {
                        add = j - x;
                    }
                }
                long tmp = 2 + 4 * add;
                result += tmp * tmp * data[i][j];
            }
        }
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
