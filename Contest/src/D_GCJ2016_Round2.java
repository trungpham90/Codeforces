
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
public class D_GCJ2016_Round2 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new FileOutputStream(new File(
                "output.txt")));
        // PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int t = in.nextInt();
        for (int z = 0; z < t; z++) {
            int n = in.nextInt();
            int[] data = new int[n];
            for (int i = 0; i < n; i++) {
                data[i] = convert(in.next());
            }
            int result = cal(0, 0, data, new int[n]);

            out.println("Case #" + (z + 1) + ": " + result);
        }
        out.close();
    }

    static int cal(int index, int total, int[] data, int[] cur) {
        if (index == data.length) {
            return cost(total, data.length, cur);
        }
        int result = 1000000;
        for (int i = 1; i < (1 << data.length); i++) {
            if ((data[index] & i) == data[index]) {
                cur[index] = i;
                int other = Integer.bitCount(i - data[index]);

                result = Integer.min(result, cal(index + 1, other + total, data, cur));
            }
        }
        return result;
    }

    static int cost(int other, int n, int[] data) {
        int[] check = new int[n];
        int result = other;
        int[] total = new int[n];
        for (int i = 0; i < n; i++) {
            if (check[i] == 0) {

                int[] set = new int[n];
                for (int k = 0; k < n; k++) {
                    if (((1 << k) & data[i]) != 0) {
                        set[k] = 1;
                    }
                }
                LinkedList<Integer> q = new LinkedList();
                q.add(i);
                check[i] = 2;
                while (!q.isEmpty()) {
                    int node = q.poll();
                    for (int j = 0; j < n; j++) {

                        if (check[j] == 0 && isCross(data[node], data[j])) {
                            check[j] = 2;
                            q.add(j);
                            if (data[node] != data[j]) {
                                return 1000000;
                            }
                        }
                    }
                }
                for (int j = 0; j < n; j++) {
                    if (set[j] == 1) {
                        total[j] = 1;
                    }
                }

            }
        }

        int left = n;
        for (int i : total) {
            left -= i;
        }
        boolean found = false;
        if (left > 0) {
            return 1000000;
        } else {
            int[] per = new int[n];
            for (int i = 0; i < n; i++) {
                per[i] = i;
            }

            do {
                found = true;
                for (int i = 0; i < n; i++) {
                    if (((1 << i) & data[per[i]]) == 0) {
                        // System.out.println(i + " " + Arrays.toString(per));
                        found = false;
                        break;
                    }
                }
            } while (nextPer(per) && !found);
        }
        //  System.out.println(left + " " + Arrays.toString(data) + " " + result + " " + found);
        if (found) {
            return result;
        }
        return 1000000;
    }

    static int convert(String v) {
        int result = 0;
        for (int i = 0; i < v.length(); i++) {
            if (v.charAt(i) == '1') {
                result |= (1 << i);
            }
        }
        return result;
    }

    static boolean isCross(int a, int b) {

        return (a & b) != 0;
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
            //br = new BufferedReader(new InputStreamReader(System.in));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D-small-attempt0.in"))));
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
