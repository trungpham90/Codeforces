
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
public class A_GCJ2016_1B {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new FileOutputStream(new File(
                "output.txt")));
        //PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int t = in.nextInt();
        String[] data = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"};
       // int[] order = {0, 4, 5, 6, 2, 1, 7, 9, 3, 8};
       // char[] test = {'Z', 'U', 'F', 'X', 'W' , 'O', 'S', ''};
        for (int z = 0; z < t; z++) {
            String line = in.next();
            int[] count = new int[26];
            for (int i = 0; i < line.length(); i++) {
                count[line.charAt(i) - 'A']++;
            }
            int[] total = new int[10];
            int zero = count['Z' - 'A'];
            total[0] += zero;
            for (int i = 0; i < data[0].length(); i++) {
                count[data[0].charAt(i) - 'A'] -= zero;
            }
            int four = count['U' - 'A'];
            total[4] += four;
            for (int i = 0; i < data[4].length(); i++) {
                count[data[4].charAt(i) - 'A'] -= four;
            }
            int five = count['F' - 'A'];
            total[5] += five;
            for (int i = 0; i < data[5].length(); i++) {
                count[data[5].charAt(i) - 'A'] -= five;
            }
            int six = count['X' - 'A'];
            total[6] += six;
            for (int i = 0; i < data[6].length(); i++) {
                count[data[6].charAt(i) - 'A'] -= six;
            }
            int two = count['W' - 'A'];
            total[2] += two;
            for (int i = 0; i < data[2].length(); i++) {
                count[data[2].charAt(i) - 'A'] -= two;
            }
            int one = count['O' - 'A'];
            total[1] += one;
            for (int i = 0; i < data[1].length(); i++) {
                count[data[1].charAt(i) - 'A'] -= one;
            }
            int seven = count['S' - 'A'];
            total[7] += seven;
            for (int i = 0; i < data[7].length(); i++) {
                count[data[7].charAt(i) - 'A'] -= seven;
            }
            int nine = count['N' - 'A']/2;
            total[9] += nine;
            for (int i = 0; i < data[9].length(); i++) {
                count[data[9].charAt(i) - 'A'] -= nine;
            }
            int three = count['R' - 'A'];
            total[3] += three;
            for (int i = 0; i < data[3].length(); i++) {
                count[data[3].charAt(i) - 'A'] -= three;
            }
            int eight = count['H' - 'A'];
            total[8] += eight;
            for (int i = 0; i < data[8].length(); i++) {
                count[data[8].charAt(i) - 'A'] -= eight;
            }
            out.print("Case #" + (z + 1) + ": ");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < total[i]; j++) {
                    out.print(i);
                }
            }
            out.println();

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
            //br = new BufferedReader(new InputStreamReader(System.in));
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
