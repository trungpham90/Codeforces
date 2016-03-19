
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
public class C_IndiaHacks_2016_Final {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = in.nextInt();
        }

        int c = 0;

        ArrayList<Integer> list = new ArrayList();
        for (int i = 0; i < n - 1; i++) {
            if (!check(i, data)) {

                list.add(i);
            }

        }
        HashSet<Point> set = new HashSet();
        if (list.size() > 10) {
            out.println(0);
        } else if (list.size() > 2) {
            int result = 0;
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    int x = list.get(i);
                    int y = list.get(j);
                    for (int k = -1; k < 2; k++) {
                        for (int h = -1; h < 2; h++) {
                            boolean ok = true;
                            swap(x + k, y + h, data);
                            for (int v : list) {
                                ok &= check(v, data);
                            }
                            if (ok && check(x + k, data) && check(y + h, data) && check(x + k - 1, data) && check(y + h - 1, data)) {
                                set.add(new Point(Integer.min(x + k, y + h), Integer.max(x + k, y + h)));
                            }
                            swap(x + k, y + h, data);
                        }
                    }
                }
            }
            out.println(set.size());
        } else if (list.size() == 2) {
            if (list.get(0) + 1 != list.get(1)) {
                int x = list.get(0);
                int y = list.get(1);
                int result = 0;
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        swap(x + i, y + j, data);

                        if (check(x, data) && check(y, data) && check(x + i, data) && check(y + j, data) && check(x + i - 1, data) && check(y + j - 1, data)) {
                            result++;
                        }
                        swap(x + i, y + j, data);
                    }
                }
                out.println(result);
            } else {
                int x = list.get(1);
                int result = 0;
                for (int i = 0; i < n; i++) {
                    swap(x, i, data);
                    if (check(i, data) && check(x, data) && check(i - 1, data) && check(x - 1, data)) {
                        //System.out.println(i + " " + x);
                        result++;
                    }
                    swap(x, i, data);
                }
                out.println(result);
            }

        } else {
            // System.out.println(list);
            int x = list.get(0);
            int result = 0;
            for (int i = 0; i < n; i++) {

                swap(x, i, data);

                if (check(i, data) && check(x, data) && check(i - 1, data) && check(x - 1, data)) {

                    result++;
                }
                swap(x, i, data);

                if (i != x) {
                    swap(x + 1, i, data);
                    if (check(i, data) && check(x, data) && check(x + 1, data) && check(i - 1, data)) {

                        result++;
                    }
                    swap(x + 1, i, data);
                }

            }
            out.println(result);
        }
        out.close();
    }

    static void swap(int x, int y, int[] data) {
        if (x < 0 || y < 0 || x >= data.length || y >= data.length) {
            return;
        }
        int tmp = data[x];
        data[x] = data[y];
        data[y] = tmp;
    }

    static boolean check(int index, int[] data) {
        if (index + 1 >= data.length || index < 0) {
            return true;
        }
        if (index % 2 == 0) {
            return data[index] < data[index + 1];
        } else {
            return data[index] > data[index + 1];
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
