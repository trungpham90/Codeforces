
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
public class E_Round_308_Div2 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        String line = in.next();
        ArrayList<Integer> pos = new ArrayList();
        HashMap<Integer, Integer> map = new HashMap();
        for (int i = 1; i < line.length(); i += 2) {
            if (line.charAt(i) == '*') {
                map.put(i, pos.size());
                pos.add(i);
            }
        }
        int num = pos.size();
        int[] last = new int[num];
        int[] nxt = new int[num];
        int l = -1;
        int index = 0;
        for (int i = 1; i < line.length(); i += 2) {
            if (line.charAt(i) == '+') {
                l = i;
            } else {
                last[index++] = l;
            }
        }
        index--;
        l = -1;
        for (int i = line.length() - 1; i >= 0; i--) {
            if (i % 2 != 0) {
                if (line.charAt(i) == '*') {
                    nxt[index--] = l;
                } else {
                    l = i;
                }
            }
        }

        long[] sum = new long[line.length()];
        for (int i = 0; i < line.length(); i++) {

            if (i == 0) {
                sum[i] = line.charAt(i) - '0';
            } else {
                if (i % 2 == 0) {
                    if (line.charAt(i - 1) == '+') {
                        sum[i] = line.charAt(i) - '0' + sum[i - 2];
                    } else {
                        int tmp = map.get(i - 1);
                        if (last[tmp] == -1) {
                            sum[i] = (line.charAt(i) - '0') * sum[i - 2];
                        } else {
                            sum[i] = (sum[i - 2] - sum[last[tmp]]) * (line.charAt(i) - '0') + sum[last[tmp]];
                        }
                    }
                } else {
                    sum[i] = sum[i - 1];
                }
            }
        }
        //System.out.println(Arrays.toString(last));
        //System.out.println(Arrays.toString(nxt));

        l = line.length();
        long result = sum[l - 1];
        for (int i = 0; i < l; i += 2) {
            for (int j = i + 2; j < l; j += 2) {
                long v;
                if (i == 0 || line.charAt(i - 1) == '+') {
                    if (j + 1 >= l || line.charAt(j + 1) == '+') {
                        continue;
                    } else {
                        if (last[map.get(j + 1)] < i) {
                            continue;
                        }
                        int nt = nxt[map.get(j + 1)];
                        if (nt == -1) {
                            nt = l - 1;
                        }
                        long y = (sum[nt] - sum[last[map.get(j + 1)]]) / (sum[j] - sum[last[map.get(j + 1)]]);
                        // System.out.println("FIRST CASE "+ i + " " + j  + " " + y);
                        v = ((i > 0) ? sum[i - 1] : 0) + (sum[j] - ((i > 0) ? sum[i - 1] : 0)) * y + sum[l - 1] - sum[nt];
                        result = result > v ? result : v;
                    }
                } else {
                    if (j + 1 >= l || line.charAt(j + 1) == '+') {
                        if (nxt[map.get(i - 1)] > j || nxt[map.get(i - 1)] == -1) {
                            continue;
                        }
                        long a = 0, b = 0;
                        int lt = last[map.get(i - 1)];
                        if (lt == -1) {
                            a = sum[i - 1];
                            b = (sum[nxt[map.get(i - 1)]]) / a;
                        } else {
                            a = sum[i - 1] - sum[lt];
                            b = (sum[nxt[map.get(i - 1)]] - sum[lt]) / a;
                        }

                        v = (lt != -1 ? sum[lt] : 0) + a * (sum[j] - sum[nxt[map.get(i - 1)]] + b) + sum[l - 1] - sum[j];
                        result = result > v ? result : v;
                    } else {
                        if (nxt[map.get(i - 1)] > j || nxt[map.get(i - 1)] == -1) {
                            continue;
                        }

                        long a = 0, b = 0;
                        int lt = last[map.get(i - 1)];
                        if (lt == -1) {
                            a = sum[i - 1];
                            b = (sum[nxt[map.get(i - 1)]]) / a;
                        } else {
                            a = sum[i - 1] - sum[lt];
                            b = (sum[nxt[map.get(i - 1)]] - sum[lt]) / a;
                        }
                        int nt = nxt[map.get(j + 1)];
                        if (nt == -1) {
                            nt = l - 1;
                        }
                        long y = (sum[nt] - sum[last[map.get(j + 1)]]) / (sum[j] - sum[last[map.get(j + 1)]]);
                        long x = (sum[nt] - sum[last[map.get(j + 1)]]) / y;
                        v = (lt != -1 ? sum[lt] : 0) + a * (x + b + sum[last[map.get(j + 1)]] - sum[nxt[map.get(i - 1)]]) * y + sum[l - 1] - sum[nt];
                        result = result > v ? result : v;
                    }
                }
                // System.out.println(i + " " + j + " "+ v);
            }
        }
        out.println(result);

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

    public static long pow(long a, long b) {
        if (b == 0) {
            return 1;
        }
        if (b == 1) {
            return a;
        }
        long val = pow(a, b / 2);
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
