
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
public class C_CrocChamp2013_Round1 {

    public static long MOD = 1000000007;
    static ArrayList<StringBuilder>[][][][][]dp;
    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();

        int[] set = new int[10];
        Arrays.fill(set, -1);
        for (int i = 0; i < n; i++) {
            int v = in.nextInt();
            set[v] = i;
        }
        if (n > 6) {
            out.println(0);
        } else {
            dp = new ArrayList[2][1<<n][3][3][256];
            ArrayList<StringBuilder> result = cal(0, 0, 0, 0, 0, n, set);
            out.println(result.size());
            for (StringBuilder v : result) {
                out.println(v);
            }
        }
        out.close();
    }

    public static ArrayList<StringBuilder> cal(int index, int mask, int last, int need, int val, int n, int[] set) {
        if(dp[index][mask][last][need][val] != null){
            return dp[index][mask][last][need][val];
        }
        ArrayList<StringBuilder> result = new ArrayList();
        for (int i = 0; i < 256; i++) {
            if (check(i, set)) {
                for (int j = 0; j < 256; j++) {
                    if (check(j, set)) {
                        StringBuilder a = new StringBuilder();
                        a.append(i);
                        StringBuilder b = new StringBuilder();
                        b.append(j);
                        String v = "" + val;
                        if (last == 2) {
                            for (int k = 0; k < need; k++) {
                                b.append(v.charAt(k));
                            }
                        } else if (last == 1) {
                            a = a.reverse();
                            for (int k = v.length() - 1; k >= v.length() - need; k--) {
                                a.append(v.charAt(k));
                            }
                            a = a.reverse();
                        }
                        if (checkPalind(a, b, index == 1)) {

                            int nxt = calMask(mask, i, j, set);
                            if (index == 1) {
                                // System.out.println(Integer.bitCount(nxt) + " " + a + " " + b + " " + Integer.bitCount(mask));
                                if (Integer.bitCount(nxt) == n) {
                                    StringBuilder cur = new StringBuilder();
                                    cur.append(i).append(".").append(j);
                                    result.add(cur);
                                }
                            } else {
                                int nxtLast = a.length() == b.length() ? 0 : a.length() < b.length() ? 2 : 1;
                                int nxtNeed = Math.abs(a.length() - b.length());
                                int nxtVal = a.length() > b.length() ? i : j;

                                ArrayList<StringBuilder> tmp = cal(1, nxt, nxtLast, nxtNeed, nxtVal, n, set);

                                for (StringBuilder o : tmp) {
                                    StringBuilder cur = new StringBuilder();
                                    cur.append(i).append(".").append(o).append(".").append(j);
                                    result.add(cur);
                                }
                            }
                        }
                    }
                }
            }
        }
        return dp[index][mask][last][need][val] = result;
    }

    public static boolean checkPalind(StringBuilder a, StringBuilder b, boolean need) {
        if (need) {
            StringBuilder v = new StringBuilder(a);
            v.append(b);
            for (int i = 0; i < v.length() / 2; i++) {
                if (v.charAt(i) != v.charAt(v.length() - i - 1)) {
                    return false;
                }
            }
            return true;
        } else {
            for (int i = 0; i < Math.min(a.length(), b.length()); i++) {
                if (a.charAt(i) != b.charAt(b.length() - i - 1)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static int calMask(int mask, int a, int b, int[] set) {
        if (a == 0) {
            mask |= (1 << set[0]);
        } else {
            while (a > 0) {
                mask |= (1 << set[a % 10]);
                a /= 10;
            }
        }
        if (b == 0) {
            mask |= (1 << set[0]);
        } else {
            while (b > 0) {
                mask |= (1 << set[b % 10]);
                b /= 10;
            }
        }
        return mask;
    }

    public static boolean check(int a, int[] set) {
        if (a == 0) {
            return set[0] != -1;
        }
        while (a > 0) {
            if (set[a % 10] == -1) {
                return false;
            }
            a /= 10;
        }
        return true;
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
