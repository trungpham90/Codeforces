
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
public class D_Round_339_Div2 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        long A = in.nextInt();
        long f = in.nextInt();
        long m = in.nextInt();
        long v = in.nextLong();
        Point[] data = new Point[n];
        for (int i = 0; i < n; i++) {
            data[i] = new Point(in.nextInt(), i);
        }
        Arrays.sort(data);
        long[] pre = new long[n];
        long[] post = new long[n];
        for (int i = 0; i < n; i++) {
            pre[i] = data[i].x;
            if (i > 0) {
                pre[i] += pre[i - 1];
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            post[i] = data[i].x;
            if (i + 1 < n) {
                post[i] += post[i + 1];
            }
        }
        long mx = -1;
        int ix = n - 1;
        long a = data[0].x;
        for (int i = n - 1; i >= 0; i--) {
            long left = v;
            if (i + 1 < n) {
                long need = (long)(n - i - 1) * A - post[i + 1];
                if (need > v) {
                    break;
                }
                left -= need;
            }
            long s = data[0].x;
            long e = A;
            long result = s;
            while (s <= e) {
                long mid = (s + e) >> 1;
                int st = 0;
                int ed = i;
                int index = -1;
                while (st <= ed) {
                    int md = (st + ed) >> 1;
                    if (data[md].x < mid) {
                        if (index == -1 || index < md) {
                            index = md;
                        }
                        st = md + 1;
                    } else {
                        ed = md - 1;
                    }
                }
                if (index == -1) {
                    if (result == -1 || result < mid) {
                        result = mid;
                    }
                    s = mid + 1;
                } else {
                    long need = (long)(index + 1) * mid - pre[index];
                    if (need <= left) {
                        if (result < mid) {
                            result = mid;
                        }
                        s = mid + 1;
                    } else {
                        e = mid - 1;
                    }
                }

            }
            long tmp = (long)(n - i - 1) * f + result * m;
            if (mx < tmp) {
                mx = tmp;
                ix = i;
                a = result;
            }
        }
        long need = A * n - post[0];
        if (need <= v) {
            out.println((long)n*f + m*A);
            for (int i = 0; i < n; i++) {
                out.print(A + " ");
            }
        } else {
            out.println(mx);
            long[] result = new long[n];
            for (int i = 0; i <= ix; i++) {
                if (data[i].x < a) {
                    result[data[i].y] = a;
                }else{
                    result[data[i].y] = data[i].x;
                }
            }
            for(int i = ix + 1; i < n; i++){
                result[data[i].y] =  A;
            }
            for(long o : result){
                out.print(o + " ");
            }
        }

        out.close();
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
