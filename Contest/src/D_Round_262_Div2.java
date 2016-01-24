
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
public class D_Round_262_Div2 {

    public static long MOD = 1000000007;
    static long[][][][][][][] dp;

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        long l = in.nextLong();
        long r = in.nextLong();
        int k = in.nextInt();
        if (k == 1) {
            out.println(l);
            out.println(1);
            out.println(l);
        } else {
            long result = l;
            long s = l;
            long e = l;
            dp = new long[64][2][2][2][2][2][2];
            boolean found = false;
            for (long i = l; i <= r && i - l < 100; i++) {
                if (k >= 3) {
                    for (long[][][][][][] j : dp) {
                        for (long[][][][][] a : j) {
                            for (long[][][][] b : a) {
                                for (long[][][] c : b) {
                                    for (long[][] d : c) {
                                        for (long[] f : d) {
                                            Arrays.fill(f, -2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    long re = cal(63, i, 0, 0, 0, 0, 0, 0, l, r);
                    if(re != -1){
                        found = true;
                        out.println(0);
                        out.println(3);
                        out.print(i + " " + re + " " + (i ^ re));
                        break;
                    }
                }
                for (long j = l - 1; j < i; j++) {
                    if (i - j <= k) {
                        long a = xorUpToK(i) ^ xorUpToK(j);
                        if (a < result) {
                            result = a;
                            s = j + 1;
                            e = i;
                        }
                    }
                }
            }
            if(!found){
            out.println(result);
            out.println(e - s + 1);
            for (long i = s; i <= e; i++) {
                out.print(i + " ");
            }
            }
            out.println();
        }
        out.close();
    }

    public static long cal(int index, long need, int dif1, int dif2, int larger1, int smaller1, int larger2, int smaller2, long l, long r) {
        if (index < 0) {
            if (dif1 != 0 && dif2 != 0) {
                return 0;
            }
            return -1;
        }
        if(dp[index][dif1][dif2][larger1][smaller1][larger2][smaller2] != -2){
            return dp[index][dif1][dif2][larger1][smaller1][larger2][smaller2];
        }
        int cur = (((1L << index) & need) != 0) ? 1 : 0;
        int a = (((1L << index) & l) != 0) ? 1 : 0;
        int b = (((1L << index) & r) != 0) ? 1 : 0;
        long result = -1;
        if (cur == 0) {
            for (int i = 0; i < 2; i++) {
                if ((i >= a || larger1 == 1) && (i <= b || smaller1 == 1) && (i >= a || larger2 == 1) && (i <= b || smaller2 == 1)) {
                    long re = cal(index - 1, need, i != cur ? 1 : dif1, i != cur ? 1 : dif2, i > a ? 1 : larger1, i < b ? 1 : smaller1, i > a ? 1 : larger2, i < b ? 1 : smaller2, l, r);
                    if (re != -1) {
                        result = ((long) i << index) | re;
                        break;
                    }
                }
            }
        } else {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    if (i != j) {
                        if ((i >= a || larger1 == 1) && (i <= b || smaller1 == 1) && (j >= a || larger2 == 1) && (j <= b || smaller2 == 1)) {
                            long re = cal(index - 1, need, i != cur ? 1 : dif1, j != cur ? 1 : dif2, i > a ? 1 : larger1, i < b ? 1 : smaller1, j > a ? 1 : larger2, j < b ? 1 : smaller2, l, r);
                            if (re != -1) {
                                result = ((long) i << index) | re;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return dp[index][dif1][dif2][larger1][smaller1][larger2][smaller2] = result;
    }

    public static long xorUpToK(long k) {
        switch ((int) (k % 4)) {
            case 0:
                return k;
            case 1:
                return 1;
            case 2:
                return k + 1;
            default:
                return 0;
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
