
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
public class B_Round_295_Div1 {

    public static long MOD = 1000000009;
    static int[] X = {-1, 0, 1};
    static int[] Y = {1, 1, 1};

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        Cube[] data = new Cube[n];
        TreeSet<Cube> set = new TreeSet();
        for (int i = 0; i < n; i++) {
            data[i] = new Cube(in.nextInt(), in.nextInt(), i);
            set.add(data[i]);
        }
        int[] count = new int[n];
        TreeSet<Integer> tree = new TreeSet();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                long x = data[i].x - X[j];
                long y = data[i].y - Y[j];
                if (set.contains(new Cube(x, y, 0))) {
                    count[i]++;
                }
            }

        }
        for (int i = 0; i < n; i++) {
            boolean ok = true;
            //System.out.println("CHECK " + data[i]);
            for (int j = 0; j < 3; j++) {
                long x = X[j] + data[i].x;
                long y = Y[j] + data[i].y;
                Cube tmp = new Cube(x, y, 0);

                if (set.contains(tmp)) {
                    //System.out.println("PRE " + tmp);
                    tmp = set.floor(tmp);
                    //System.out.println(tmp + " " + count[(int) tmp.z]);
                    if (count[(int) tmp.z] == 1) {
                        ok = false;
                        break;
                    }
                }
            }
            if (ok) {
                tree.add(i);
            }
        }

        long result = 0;
        int cur = 0;
        boolean[] check = new boolean[n];
        for (int i = n - 1; i >= 0; i--) {
            int index;
            //	System.out.println(tree);
            if (cur == 0) {
                index = tree.last();
            } else {
                index = tree.first();
            }
            tree.remove(index);
            check[index] = true;
            //System.out.println(index);

            result += (((long) index % MOD) * pow(n, i, MOD)) % MOD;
            result %= MOD;
            for (int j = 0; j < 3; j++) {
                long x = X[j] + data[index].x;
                long y = Y[j] + data[index].y;
                Cube tmp = new Cube(x, y, 0);
                if (set.contains(tmp)) {
                    tmp = set.floor(tmp);
                    if (!check[(int) tmp.z]) {
                        count[(int) tmp.z]--;
                        if (count[(int) tmp.z] == 1) {
                            for (int k = 0; k < 3; k++) {
                                long a = tmp.x - X[k];
                                long b = tmp.y - Y[k];
                                Cube nxt = new Cube(a, b, 0);
                                if (set.contains(nxt)) {
                                    nxt = set.floor(nxt);
                                    if (!check[(int) nxt.z]) {
                                        tree.remove((int) nxt.z);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (int j = 0; j < 3; j++) {
                long x = data[index].x - X[j];
                long y = data[index].y - Y[j];
                Cube tmp = new Cube(x, y, 0);
                if (set.contains(tmp)) {
                    tmp = set.floor(tmp);
                    if (!check[(int) tmp.z]) {
                        boolean ok = true;
                        for (int k = 0; k < 3; k++) {
                            long a = tmp.x + X[k];
                            long b = tmp.y + Y[k];
                            Cube nxt = new Cube(a, b, 0);
                            if (set.contains(nxt)) {
                                nxt = set.floor(nxt);
                                if (!check[(int) nxt.z]) {
                                    if (count[(int) nxt.z] == 1) {
                                        ok = false;
                                        break;
                                    }
                                }
                            }

                        }
                        if (ok) {
                            tree.add((int) tmp.z);
                        }
                    }
                }
            }
            cur = 1 - cur;
        }

        out.println(result);
        out.close();
    }

    static class Cube implements Comparable<Cube> {

        long x, y, z;

        public Cube(long x, long y, long z) {
            super();
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public int compareTo(Cube o) {
            if (x != o.x) {
                return Long.compare(x, o.x);
            }
            return Long.compare(y, o.y);
        }

        @Override
        public String toString() {
            return "Cube [x=" + x + ", y=" + y + ", z=" + z + "]";
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
