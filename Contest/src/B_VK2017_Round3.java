
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
public class B_VK2017_Round3 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int[][] data = new int[n][5];
        int[] count = new int[5];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 5; j++) {
                data[i][j] = in.nextInt();
                if (data[i][j] != -1) {
                    count[j]++;
                }
            }
        }
        int[] p = {500, 1000, 1500, 2000, 2500, 3000};
        int[] hold = new int[5];
        int re = -1;
        //int[] template = {1000, 1000, 500, 500, 500};
        for (int i : p) {
            hold[0] = i;
            for (int j : p) {
                hold[1] = j;
                for (int k : p) {
                    hold[2] = k;
                    for (int l : p) {
                        hold[3] = l;
                        for (int m : p) {
                            hold[4] = m;
                            int a = 0, b = 0;
                            for (int h = 0; h < 5; h++) {
                                if (data[0][h] != -1) {
                                    a += hold[h] - data[0][h] * hold[h] / 250;
                                }
                                if (data[1][h] != -1) {
                                    b += hold[h] - data[1][h] * hold[h] / 250;
                                }
                            }
                            if (a > b) {

                                for (int h = 0; h < 100 * n && (re == -1 || h < re); h++) {
                                    long need = 0;
                                    int mid = h;
                                    for (int z = 0; z < 5; z++) {
                                        long tmp = cal(hold[z], data[0][z] != -1, (mid + n), count[z]);
                                        if (tmp != -1) {
                                            need = Long.max(tmp, need);
                                        } else {
                                            need = -1;
                                            break;
                                        }
                                    }
                                    if (need <= h && need != -1) {
                                        if (re == -1 || re > mid) {
                                            re = (int) mid;
                                        }
                                        break;
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
        out.println(re);
        out.close();
    }

    static long cal(int need, boolean sol, int total, int count) {
        int bit = need / 500;
        int l = 1 << bit;
        int r = 1 << (bit - 1);

        if (need != 3000 && count * l <= total) {
            if (!sol) {
                return -1;
            }
            int re = total / l;
            while (re * l <= total) {
                re++;
            }
            if (re * r > total) {
                return -1;
            }
            return re - count;
        } else if (count * r > total) {
            return -1;
        }
        return 0;
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
