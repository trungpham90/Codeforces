
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
public class D_Round_374_Div2 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        long k = in.nextInt();
        long x = in.nextInt();
        long[] data = new long[n];
        int neg = 0;
        int zero = 0;
        int[] q = new int[n];
        int negMax = -1;
        int posMin = -1;
        for (int i = 0; i < n; i++) {
            data[i] = in.nextInt();
            if (data[i] < 0) {
                neg++;
                if (negMax == -1 || data[negMax] < data[i]) {
                    negMax = i;
                }
            } else if (data[i] == 0) {
                q[zero++] = i;
            } else if (posMin == -1 || data[posMin] > data[i]) {
                posMin = i;
            }
        }

        if (k >= zero) {
            if (neg % 2 != 0) {
                for (int i = 0; i < zero; i++) {
                    data[q[i]] += x;
                    k--;
                }
                updateMax(get(data), data, k, x);
            } else if (zero > 0) {
                data[q[0]] -= x;
                k--;
                for (int i = 1; i < zero; i++) {
                    data[q[i]] += x;
                    k--;
                }
                updateMax(get(data), data, k, x);
            } else if (posMin != -1 && negMax != -1) {
                if (data[posMin] >= -data[negMax]) {
                    if (x * k > -data[negMax]) {
                        long need = -data[negMax] / x;
                        if (need * x <= -data[negMax]) {
                            need++;
                        }
                        k -= need;
                        data[negMax] += need * x;
                        updateMax(get(data), data, k, x);
                    } else {
                        updateMin(data, k, x);
                    }
                } else if (x * k > data[posMin]) {
                    long need = data[posMin] / x;
                    if (need * x <= data[posMin]) {
                        need++;
                    }
                    k -= need;
                    data[posMin] -= need * x;
                    updateMax(get(data), data, k, x);
                } else {
                    updateMin(data, k, x);
                }

            } else if (posMin != -1) {
                if (x * k > data[posMin]) {
                    long need = data[posMin] / x;
                    if (need * x <= data[posMin]) {
                        need++;
                    }
                    k -= need;
                    data[posMin] -= need * x;
                    updateMax(get(data), data, k, x);
                } else {
                    updateMin(data, k, x);
                }
            } else if (x * k > -data[negMax]) {
                long need = -data[negMax] / x;
                if (need * x <= -data[negMax]) {
                    need++;
                }
                k -= need;
                data[negMax] += need * x;
                updateMax(get(data), data, k, x);
            } else {
                updateMin(data, k, x);
            }
        }
        for (int i = 0; i < n; i++) {
            out.print(data[i] + " ");
        }

        out.close();
    }

    static PriorityQueue<Point> get(long[] data) {
        PriorityQueue<Point> q = new PriorityQueue();
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                q.add(new Point(i, -data[i]));
            } else {
                q.add(new Point(i, data[i]));
            }
        }
        return q;
    }

    static void updateMax(PriorityQueue<Point> q, long[] data, long k, long x) {
        for (int i = 0; i < k; i++) {
            Point p = q.poll();
            if (data[p.x] > 0) {
                data[p.x] += x;                
            } else {
                data[p.x] -= x;                
            }
            q.add(new Point(p.x, abs(data[p.x])));
        }
    }

    static void updateMin(long[] data, long k, long x) {
        int min = -1;
        for (int i = 0; i < data.length; i++) {
            if (min == -1 || abs(data[min]) > abs(data[i])) {
                min = i;
            }
        }
        for (int i = 0; i < k; i++) {
            if (data[min] < 0) {
                data[min] += x;
            } else {
                data[min] -= x;
            }
        }
    }
    
    static long abs(long v){
        return v < 0 ? -v : v;
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

        int x;
        long y;

        public Point(int start, long end) {
            this.x = start;
            this.y = end;
        }

        @Override
        public int compareTo(Point o) {
            return Long.compare(y, o.y);
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
