
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
public class CouplingPassions {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();

        int n = in.nextInt();
        HashSet<String> set = new HashSet();
        for (int i = 0; i < n; i++) {
            int t = in.nextInt();
            for (int j = 0; j < t; j++) {
                set.add(in.next());
            }
        }
        int m = in.nextInt();
        P[] data = new P[m];
        for (int i = 0; i < m; i++) {
            data[i] = new P();
            data[i].name = in.next();
            data[i].p = new Point(in.nextDouble(), in.nextDouble());
            int t = in.nextInt();
            for (int j = 0; j < t; j++) {
                String v = in.next();
                if (set.contains(v)) {
                    data[i].set.add(v);
                }
            }
        }
        int a = -1, b = -1;
        int result = -1;
        for (int i = 0; i < m; i++) {
            int max = -1;
            int index = -1;
            for (int j = i + 1; j < m; j++) {
                HashSet<String> total = new HashSet(data[i].set);
                total.addAll(data[j].set);
                if (total.size() > max) {
                    max = total.size();
                    index = j;
                } else if (total.size() == max && distanceBetween(data[i].p, data[j].p) < distanceBetween(data[i].p, data[index].p)) {
                    index = j;
                }
            }
            if(result < max){
                a = i;
                b = index;
                result = max;
            }else if(result == max && distanceBetween(data[a].p, data[b].p) > distanceBetween(data[i].p, data[index].p)){
                a = i;
                b = index;
            }
        }
        if(data[a].name.compareTo(data[b].name) > 0){
            a = a + b;
            b = a - b;
            a -= b;
        }
        out.println(data[a].name + " " + data[b].name);
        out.close();
    }

    static class P {

        String name;
        Point p;
        HashSet<String> set = new HashSet();

    }

    static double distanceBetween(Point point1, Point point2) {
        double EARTH_RADIUS = 6371;//in km
        double point1_lat_in_radians = Math.toRadians(point1.x);
        double point2_lat_in_radians = Math.toRadians(point2.x);
        double point1_long_in_radians = Math.toRadians(point1.y);
        double point2_long_in_radians = Math.toRadians(point2.y);

        return Math.acos(Math.sin(point1_lat_in_radians) * Math.sin(point2_lat_in_radians)
                + Math.cos(point1_lat_in_radians) * Math.cos(point2_lat_in_radians)
                * Math.cos(point2_long_in_radians - point1_long_in_radians)) * EARTH_RADIUS;
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

    public static class Point {

        double x, y;

        public Point(double start, double end) {
            this.x = start;
            this.y = end;
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
