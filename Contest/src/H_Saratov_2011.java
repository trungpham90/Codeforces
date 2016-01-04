
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
public class H_Saratov_2011 {

    public static long MOD = 1000000007;
    static int min;

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new FileOutputStream(new File(
                "output.txt")));
       // PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        String[] data = new String[n];
        HashMap<String, Integer> map = new HashMap();
        HashSet<Integer>[] set = new HashSet[n];
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < n; i++) {
            set[i] = new HashSet();
            data[i] = in.next();
            if (data[i].length() > 4) {
                for (int j = 1; j < (1 << data[i].length()); j++) {
                    if (Integer.bitCount(j) <= 4) {
                        StringBuilder builder = new StringBuilder();
                        for (int k = 0; k < data[i].length(); k++) {
                            if (((1 << k) & j) != 0) {
                                builder.append(data[i].charAt(k));
                            }
                        }
                        String v = builder.toString();
                        if (!map.containsKey(v)) {
                            list.add(v);
                            map.put(v, map.size());
                        }
                        set[i].add(map.get(v));
                    }
                }
            } else {
                if (!map.containsKey(data[i])) {
                    list.add(data[i]);
                    map.put(data[i], map.size());
                }
                set[i].add(map.get(data[i]));
            }
            //  System.out.println(set[i]);
        }
        int total = 2 + n + map.size();
        int first = 1 + n;      
        int result = 0;
        int[] pa = new int[total];
        int[] check = new int[total];
        int cur = 1;
        int[] q = new int[total];
        int start;
        int end;
        int[] fix = new int[n];
        boolean[] st = new boolean[n];
        int[] back = new int[map.size()];
        boolean[] fw = new boolean[map.size()];
        Arrays.fill(st, true);
        Arrays.fill(fw, true);
        Arrays.fill(back, -1);
        while (true) {
            start = 0;
            end = 0;
            q[end++] = 0;
            check[0] = cur;

            while (start < end) {
                int node = q[start++];
                if (node == 0) {
                    for (int i = 1; i <= n; i++) {
                        if (check[i] != cur && st[i - 1]) {
                            check[i] = cur;
                            pa[i] = node;
                            q[end++] = i;
                        }
                    }
                } else if (node <= n) {
                    for (int i : set[node - 1]) {
                        int v = i + first;
                        if (check[v] != cur) {
                            check[v] = cur;
                            pa[v] = node;
                            q[end++] = v;
                        }
                    }
                } else {
                    if (check[total - 1] != cur && fw[node - first]) {
                        check[total - 1] = cur;
                        pa[total - 1] = node;
                        break;
                    }
                    int i = back[node - first];
                    if (i != -1 && check[i] != cur) {
                        check[i] = cur;
                        pa[i] = node;
                        q[end++] = i;
                    }

                }
            }            
            if (check[total - 1] != cur) {
                break;
            }
            cal(total - 1, pa, st, back, fw, fix, n, first, total);            
            result += 1;
            cur++;
        }        
        if (result != n) {
            out.println(-1);
        } else {
            for (int i = 0; i < n; i++) {
                out.println(list.get(fix[i]));
            }
        }
        out.close();
    }

    static void cal(int index, int[] pa, boolean[] st, int[] back, boolean[] fw, int[] fix, int n, int first, int total) {
        if (pa[index] != index) {

            if (pa[index] <= n && pa[index] > 0) {
                fix[pa[index] - 1] = index - first;
                st[pa[index] - 1] = false;
                back[index - first] = pa[index];
            }
            if (index == total - 1) {
                fw[pa[index] - first] = false;
            }

            cal(pa[index], pa, st, back, fw, fix, n, first, total);

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
            //br = new BufferedReader(new InputStreamReader(System.in));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("input.txt"))));
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
