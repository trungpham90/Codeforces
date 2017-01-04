
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
public class PrimeDigitSums {

    public static long MOD = 1000000007;
    static boolean[] p;

    static int max = 400001;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int q = in.nextInt();

        long[] re = new long[max];

        p = new boolean[100];
        p[0] = true;
        p[1] = true;
        for (int i = 2; i < 100; i++) {
            if (!p[i]) {
                for (int j = i + i; j < 100; j += i) {
                    p[j] = true;
                }
            }
        }
        re[1] = 9;
        re[2] = 90;
        HashMap<Five, Integer> map = new HashMap<>();
        int index = 0;
        HashSet<Integer> list = new HashSet();
        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 10; b++) {
                for (int c = 0; c < 10; c++) {
                    if (!p[a + b + c]) {
                        if (a != 0) {
                            re[3]++;
                        }
                        for (int d = 0; d < 10; d++) {
                            if (!p[a + b + c + d] && !p[b + c + d]) {
                                if (a != 0) {
                                    re[4]++;
                                }
                                for (int e = 0; e < 10; e++) {
                                    if (!p[a + b + c + d + e] && !p[b + c + d + e] && !p[c + d + e]) {
                                        if (a != 0) {
                                            re[5]++;                                            
                                            list.add(index);
                                        }
                                        Five f = new Five(a, b, c, d, e);
                                        map.put(f, index);
                                        index++;
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        ArrayList<Integer>[] nxt = new ArrayList[map.size()];
        for (int i = 0; i < nxt.length; i++) {
            nxt[i] = new ArrayList();
        }

        for (Five f : map.keySet()) {
            int a = map.get(f);
            for (int i = 0; i < 10; i++) {
                if (!p[f.five[1] + f.five[2] + f.five[3] + f.five[4] + i] && !p[f.five[2] + f.five[3] + f.five[4] + i] && !p[f.five[3] + f.five[4] + i]) {
                    int b = map.get(new Five(f.five[1], f.five[2], f.five[3], f.five[4], i));
                    nxt[a].add(b);
                }
            }
        }
        long[][] pre = new long[2][393];
        for (int i : list) {
            pre[0][i] = 1;
        }
        int cur = 1;
        for (int i = 6; i < max; i++) {
            HashSet<Integer> tmp = new HashSet();
            Arrays.fill(pre[cur], 0);
            for (int j : list) {
                for (int k : nxt[j]) {
                    pre[cur][k] += pre[1 - cur][j];
                    pre[cur][k] %= MOD;
                    re[i] += pre[1 - cur][j];
                    re[i] %= MOD;
                    tmp.add(k);
                }
            }
            list = tmp;
            cur = 1 - cur;
        }

        for (int z = 0; z < q; z++) {

            int n = in.nextInt();
            out.println(re[n]);
        }
        out.close();
    }

    public static long[][] powSquareMatrix(long[][] A, long p) {
        long[][] unit = new long[A.length][A.length];
        for (int i = 0; i < unit.length; i++) {
            unit[i][i] = 1;
        }
        if (p == 0) {
            return unit;
        }
        long[][] val = powSquareMatrix(A, p / 2);
        if (p % 2 == 0) {
            return mulMatrix(val, val);
        } else {
            return mulMatrix(A, mulMatrix(val, val));
        }

    }

    public static long[][] mulMatrix(long[][] A, long[][] B) {
        long[][] result = new long[A.length][B[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                for (int k = 0; k < A[0].length; k++) {
                    result[i][j] += (A[i][k] * B[k][j]);
                }
            }
        }

        return result;
    }

    static class Five {

        int[] five = new int[5];

        Five(int a, int b, int c, int d, int e) {
            five[0] = a;
            five[1] = b;
            five[2] = c;
            five[3] = d;
            five[4] = e;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + Arrays.hashCode(this.five);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Five other = (Five) obj;
            if (!Arrays.equals(this.five, other.five)) {
                return false;
            }
            return true;
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
