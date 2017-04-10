
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
import java.util.Iterator;
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
public class A_GCJ2017_Qual {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new FileOutputStream(new File(
                "output.txt")));
       // PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int t = in.nextInt();
        for (int z = 0; z < t; z++) {
            String line = in.next();
            int k = in.nextInt();
            int re = cal(0, new boolean[line.length()], k, line);
            int other = bruteforce(line, k);
            if (re != other) {
                System.out.println(re + " " + other + " " + line + " " + k);
            }

            if (re != -1) {
                out.println("Case #" + (z + 1) + ": " + re);
            } else {
                out.println("Case #" + (z + 1) + ": IMPOSSIBLE");
            }
        }
        out.close();
    }

    static int bruteforce(String line, int k) {
        HashMap<String, Integer> map = new HashMap();
        LinkedList<String> q = new LinkedList();
        q.add(line);
        map.put(line, 0);
        while (!q.isEmpty()) {
            String v = q.poll();
            int cost = map.get(v);
            if (check(v)) {
                return cost;
            }
            for (int i = 0; i < v.length(); i++) {
                if (i + k <= v.length()) {
                    String a = v.substring(0, i);
                    String c = v.substring(i + k);
                    String b = rev(v.substring(i, i + k));
                    String re = a + b + c;
                    if (!map.containsKey(re)) {
                        map.put(re, cost + 1);
                        q.add(re);
                    }
                }
            }
        }
        return -1;
    }

    static boolean check(String v) {
        for (int i = 0; i < v.length(); i++) {
            if (v.charAt(i) != '+') {
                return false;
            }
        }
        return true;
    }

    static String rev(String tmp) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tmp.length(); i++) {
            if (tmp.charAt(i) == '+') {
                builder.append('-');
            } else {
                builder.append('+');
            }
        }
        return builder.toString();
    }

    static int cal(int index, boolean[] flip, int k, String line) {
        if (index == line.length()) {
            return 0;
        }
        boolean flipped = flip[index];

        boolean happy = (line.charAt(index) == '+' && !flipped) || (line.charAt(index) == '-' && flipped);
        int result = -1;
        if (happy) {
            result = cal(index + 1, flip, k, line);
        } else if (index + k <= line.length()) {
            int st = index;
            int ed = index + k - 1;
            for (int i = index; i < index + k; i++) {
                flip[i] = !flip[i];
            }

            int v = cal(index + 1, flip, k, line);
            if (v != -1) {
                result = 1 + v;
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
            // br = new BufferedReader(new InputStreamReader(System.in));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("A-large.in"))));
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
