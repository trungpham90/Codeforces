
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
public class C_Round_356_Div1 {

    public static long MOD = 1000000007;
    static int[] X = {0, 1, 0, -1};
    static int[] Y = {1, 0, -1, 0};

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int k = in.nextInt();
        if (n == k) {
            out.println(n * n);
        } else {
            String[] data = new String[n];
            int[][] com = new int[n][n];
            for (int i = 0; i < n; i++) {
                data[i] = in.next();
            }
            int cur = 1;
            int[] list = new int[n * n + 1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    if (data[i].charAt(j) == '.') {
                        if (com[i][j] == 0) {
                            LinkedList<Point> q = new LinkedList();
                            q.add(new Point(i, j));
                            com[i][j] = cur;
                            int count = 1;
                            while (!q.isEmpty()) {
                                Point p = q.poll();
                                for (int h = 0; h < 4; h++) {
                                    int x = p.x + X[h];
                                    int y = p.y + Y[h];
                                    if (x >= 0 && x < n && y >= 0 && y < n && com[x][y] == 0 && data[x].charAt(y) == '.') {
                                        com[x][y] = cur;
                                        count++;
                                        q.add(new Point(x, y));
                                    }
                                }
                            }
                            list[cur] = count;
                            cur++;
                        }
                    }
                }
            }
//            for (int[] a : com) {
//                System.out.println(Arrays.toString(a));
//            }
            int[] track = new int[cur];
            int result = 0;
            int index = 0;
            int start = 0;
            int add = 1;
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < k; j++) {
                    list[com[i][j]]--;
                }
            }
            for (int i = 0; i + k <= n; i++) {
                for (int j = start; j + k <= n && j >= 0; j += add) {
                    index++;
                    int tmp = 0;

                    if (i > 0) {
                        for (int h = 0; h < k; h++) {
                            if (com[i - 1][j + h] != 0 && track[com[i - 1][j + h]] != index) {
                                track[com[i - 1][j + h]] = index;
                                tmp += list[com[i - 1][j + h]];
                            }
                        }
                    }
                    if (i + k < n) {
                        for (int h = 0; h < k; h++) {
                            if (com[i + k][j + h] != 0 && track[com[i + k][j + h]] != index) {
                                track[com[i + k][j + h]] = index;
                                tmp += list[com[i + k][j + h]];
                            }
                        }
                    }
                    if (j > 0) {
                        for (int h = 0; h < k; h++) {
                            if (com[i + h][j - 1] != 0 && track[com[i + h][j - 1]] != index) {
                                track[com[i + h][j - 1]] = index;
                                tmp += list[com[i + h][j - 1]];
                            }
                        }
                    }
                    if (j + k < n) {
                        for (int h = 0; h < k; h++) {
                            if (com[i + h][j + k] != 0 && track[com[i + h][j + k]] != index) {
                                track[com[i + h][j + k]] = index;
                                tmp += list[com[i + h][j + k]];
                            }
                        }
                    }

                    tmp += k * k;
                  //  System.out.println(tmp + " " + i + " " + j + " " + Arrays.toString(track));
                    result = Integer.max(result, tmp);
                    if (j + add + k <= n && j + add >= 0) {
                        for (int h = 0; h < k; h++) {
                            if (add == 1) {
                                list[com[i + h][j]]++;
                                list[com[i + h][j + k]]--;
                            } else {
                                list[com[i + h][j + k - 1]]++;
                                list[com[i + h][j - 1]]--;
                            }
                        }
                    }
                }
                add *= -1;
                start = n - k - start;
                if (i + k + 1 <= n) {
                    for (int h = 0; h < k; h++) {
                        list[com[i][h + start]]++;
                        list[com[i + k][h + start]]--;
                    }
                }
            }

            out.println(result);
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
