
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
public class B_AimTech_Round3_Div1 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int[] data = new int[4];
        for (int i = 0; i < 4; i++) {
            data[i] = in.nextInt();
        }
        int zero = cal(data[0]);
        int one = cal(data[3]);
        int max = one * zero;
        //System.out.println(zero + " " + one);
        if (data[0] == 0 || data[3] == 0) {
            boolean ok = false;
            if (data[0] == 0 && data[3] == 0) {
                for (int i = 0; i < 2 && !ok; i++) {
                    for (int j = 0; j < 2; j++) {
                        max = i * j;
                        if (data[1] + data[2] == max && i + j > 0) {
                            out.println(build(i, j, data[1]));
                            ok = true;
                            break;
                        }
                    }
                }
            } else if (data[0] == 0) {
                for (int i = 0; i < 2 && !ok; i++) {

                    max = i * one;
                    if (data[1] + data[2] == max && i + one > 0) {
                        out.println(build(i, one, data[1]));
                        ok = true;
                        break;
                    }

                }
            } else {
                for (int i = 0; i < 2 && !ok; i++) {

                    max = i * zero;
                    if (data[1] + data[2] == max && zero + i > 0) {
                        out.println(build(zero, i, data[1]));
                        ok = true;
                        break;
                    }

                }
            }
            if(!ok){
                out.println("Impossible");
            }
        } else if (data[1] + data[2] != max) {
            out.println("Impossible");

        } else {

            out.println(build(zero, one, data[1]));
        }

        out.close();
    }

    static String build(int zero, int one, int v) {
        int start = 0;

        StringBuilder builder = new StringBuilder();
        while (start < v) {
            int need = v - start;
            if (need >= one) {
                start += one;
                builder.append("0");
                zero--;
            } else {
                while (one > need) {
                    builder.append("1");
                    one--;
                }
                start += need;
                builder.append("0");
                zero--;
            }
        }
        for (int i = 0; i < one; i++) {
            builder.append("1");
        }
        for (int i = 0; i < zero; i++) {
            builder.append("0");
        }
        return builder.toString();
    }

    static int cal(int v) {
        if (v == 0) {
            return 1;
        }
        long start = 0;
        long end = 1000000;
        while (start <= end) {
            long mid = (start + end) / 2;
            long tmp = mid * (mid - 1) / 2;
            if (tmp > v) {
                end = mid - 1;
            } else if (tmp == v) {
                return (int) mid;
            } else {
                start = mid + 1;
            }
        }
        return -1;

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
