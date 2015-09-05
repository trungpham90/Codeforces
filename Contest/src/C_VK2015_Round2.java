
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
public class C_VK2015_Round2 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int x1 = in.nextInt();
        int y1 = in.nextInt();
        int x2 = in.nextInt();
        int y2 = in.nextInt();
        int dist1 = x1 + y1;
        int min = Math.min(x2, y2);
        int dist2 = x2 + y2 - min;

        if (dist1 <= dist2) {
            out.println("Polycarp");
        } else {
            int a = x2 - min;
            int b = y2 - min;
            boolean cut = false;
            if (a <= x1 && x1 <= x2) {
                int time = x2 - x1;
                int other = y2 - time;
                if (y1 - time <= other) {
                    cut = true;
                }
            }
          //  System.out.println(cut);
            if (b <= y1 && y1 <= y2) {
                int time = y2 - y1;
                int other = x2 - time;
                if (x1 - time <= other) {
                    cut = true;
                }
            }
           // System.out.println(cut + " " + a + " " + b);
            
           // System.out.println(cut);
            if (cut) {
                out.println("Polycarp");
            } else {
                out.println("Vasiliy");
            }

        }
        //  boolean v = cal(0, x1, y1, x2, y2);
        //  out.println(v);
        out.close();
    }

    public static boolean cal(int index, int x1, int y1, int x2, int y2) {
        if (x1 == 0 && y1 == 0) {
            return index == 0;
        }
        if (x2 == 0 && y2 == 0) {
            return index == 1;
        }
        boolean result = false;
        if (index == 0) {
            if (x1 > 0 && (x1 - 1 != x2 || y1 != y2)) {


                boolean v = cal(1, x1 - 1, y1, x2, y2);
                if (!v) {
                    result = true;
                }
            }
            if (y1 > 0 && (x1 != x2 || y1 - 1 != y2)) {
                boolean v = cal(1, x1, y1 - 1, x2, y2);
                if (!v) {
                    result = true;
                }
            }
        } else {
            if (x2 > 0 && (x1 != x2 - 1 || y1 != y2)) {
                boolean v = cal(0, x1, y1, x2 - 1, y2);
                if (!v) {
                    result = true;
                }
            }
            if (y2 > 0 && (x1 != x2 || y1 != y2 - 1)) {
                boolean v = cal(0, x1, y1, x2, y2 - 1);
                if (!v) {
                    result = true;
                }
            }
            if (x2 > 0 && y2 > 0 && (x1 != x2 - 1 || y1 != y2 - 1)) {
                boolean v = cal(0, x1, y1, x2 - 1, y2 - 1);
                if (!v) {
                    result = true;
                }
            }
            boolean v = cal(0, x1, y1, x2, y2);
            if (!v) {
                result = true;
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
