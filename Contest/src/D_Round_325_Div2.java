
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
public class D_Round_325_Div2 {

    public static long MOD = 1000000007;
    static int[][][] dp;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int t = in.nextInt();
        for (int z = 0; z < t; z++) {
            int n = in.nextInt();
            int m = in.nextInt();
            int startX = -1;
            int startY = -1;
            Train[] data = new Train[m];
            String[] line = new String[3];
            int index = 0;
            for (int i = 0; i < 3; i++) {
                line[i] = in.next();
                for (int j = 0; j < n; j++) {
                    if (line[i].charAt(j) != '.') {
                        if (line[i].charAt(j) == 's') {
                            startX = i;
                            startY = j;
                        } else {
                            int c = 0;
                            int start = j;
                            for (int k = j; k < n; k++) {
                                if (line[i].charAt(j) == line[i].charAt(k)) {
                                    c++;
                                    j = k;
                                } else {
                                    break;
                                }
                            }
                            data[index++] = new Train(i, start, c);
                        }
                    }
                }
            }
            //    System.out.println(Arrays.toString(data));
            dp = new int[2][3][n];
            int v = cal(startX, startY, 0, n, data);
            if (v == 1) {
                out.println("YES");
            } else {
                out.println("NO");
            }
        }
        out.close();
    }

    static int cal(int x, int y, int turn, int n, Train[] data) {
        if (y + 1 == n && turn == 0) {
            return 1;
        }
        if (dp[turn][x][y] != 0) {
            return dp[turn][x][y];
        }
        int result = 0;
        if (turn == 0) {
            int step = y;
            result = -1;
            boolean ok = true;
            for (Train t : data) {
                int sY = t.y - 2 * step;
                int eY = t.y + t.l - 1 - 2 * step;
              //  System.out.println("HERO " + sY + " " + eY + " " + t + " " + x + " " + y);
                if (x == t.x && sY <= y + 1 && y + 1 <= eY) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                for (int i = -1; i < 2; i++) {
                    int curX = i + x;
                    int curY = y + 1;

                    if (curX >= 0 && curX < 3) {
                        ok = true;
                        for (Train t : data) {
                            int sY = t.y - 2 * step;
                            int eY = t.y + t.l - 1 - 2 * step;
                         //   System.out.println("HERO " + sY + " " + eY + " " + t + " " + x + " " + y);
                            if (curX == t.x && sY <= curY && curY <= eY) {
                                ok = false;
                                break;
                            }
                        }
                        if (ok) {
                            int v = cal(curX, curY, 1, n, data);
                            if (v == 1) {
                                result = 1;
                                break;
                            }
                        }
                    }
                }
            }
        } else {

            int step = y;
            for (Train t : data) {
                int sY = t.y - 2 * step;
                int eY = t.y + t.l - 1 - 2 * step;
              //  System.out.println(sY + " " + eY + " " + t + " " + x + " " + y);
                if (x == t.x && sY <= y && y <= eY) {
                    result = -1;
                    break;
                }
            }
            if (result == 0) {
                result = cal(x, y, 0, n, data);
            }

        }
        return dp[turn][x][y] = result;
    }

    static class Train {

        int x, y, l;

        public Train(int x, int y, int l) {
            this.x = x;
            this.y = y;
            this.l = l;
        }

        @Override
        public String toString() {
            return "Train{" + "x=" + x + ", y=" + y + ", l=" + l + '}';
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
