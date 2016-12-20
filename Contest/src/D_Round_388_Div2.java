
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
public class D_Round_388_Div2 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int[] data = new int[n];
        ArrayList<Integer>[] t = new ArrayList[n];
        Arrays.fill(data, -1);
        int[][] store = new int[n][2];
        TreeSet<int[]> q = new TreeSet<>(new Comparator<int[]>() {

            @Override
            public int compare(int[] o1, int[] o2) {
                // TODO Auto-generated method stub
                return Integer.compare(o1[1], o2[1]);
            }
        });
        for (int i = 0; i < n; i++) {
            t[i] = new ArrayList();
        }
        for (int i = 0; i < n; i++) {
            int u = in.nextInt() - 1;
            int v = in.nextInt();
            store[i][0] = u;
            store[i][1] = v;
            t[u].add(i);
            data[u] = i;
        }
        for (int i = 0; i < n; i++) {
            if (data[i] != -1) {
                q.add(new int[]{i, data[i]});
            }
        }
        int z = in.nextInt();
        for (int i = 0; i < z; i++) {
            int k = in.nextInt();
            int[][] tmp = new int[k][2];
            for (int j = 0; j < k; j++) {
                int v = in.nextInt() - 1;
                tmp[j][0] = v;
                tmp[j][1] = data[v];
            }
            Arrays.sort(tmp, new Comparator<int[]>() {

                @Override
                public int compare(int[] o1, int[] o2) {
                    // TODO Auto-generated method stub
                    return Integer.compare(o2[1], o1[1]);
                }
            });

            for (int j = 0; j < k && !q.isEmpty(); j++) {
                if (q.last()[0] == tmp[j][0]) {
                    q.pollLast();
                } else {
                    break;
                }
            }
            if (!q.isEmpty()) {

                int last = q.last()[0];
                int lastIndex = t[last].size() - 1;
                int st = 0;
                int ed = t[last].size() - 1;
                int re = 0;
                while (st <= ed) {
                    int mid = (st + ed) >> 1;

                    int total = 0;
                    int dif = t[last].get(lastIndex) - t[last].get(mid) - (lastIndex - mid);
                    for (int j = 0; j < k; j++) {
                        int v = tmp[j][0];
                        total += getCount(t[last].get(mid), t[last].get(lastIndex), t[v]);
                    }
                    // System.out.println(mid + " " + total + " " + i + " " + dif + " " + t[last].get(lastIndex) + " " + t[last].get(mid) + " " + lastIndex);
                    if (total < dif) {
                        re = mid + 1;
                        st = mid + 1;
                    } else {
                        ed = mid - 1;
                    }

                }
                out.println(((last + 1)) + " " + store[t[last].get(re)][1]);
            } else {
                out.println("0 0");
            }
            for (int j = 0; j < k; j++) {
                if (tmp[j][1] != -1) {
                    q.add(tmp[j]);
                }
            }
        }
        out.close();
    }

    static int getCount(int st, int ed, ArrayList<Integer> list) {
        int s = 0;
        int e = list.size() - 1;
        int a = -1;
        while (s <= e) {
            int mid = (s + e) >> 1;
            if (list.get(mid) >= st) {
                a = mid;
                e = mid - 1;
            } else {
                s = mid + 1;
            }
        }
        s = 0;
        e = list.size() - 1;
        int b = -1;
        while (s <= e) {
            int mid = (s + e) >> 1;
            if (list.get(mid) <= ed) {
                b = mid;
                s = mid + 1;
            } else {
                e = mid - 1;
            }
        }
        if (a != -1 && b != -1) {
            return Math.max(0, b - a + 1);
        }
        return 0;
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
