
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
public class D_Round_390_Div2 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int k = in.nextInt();
        End[] data = new End[2 * n];
        int index = 0;
        TreeSet<Integer> set = new TreeSet();
        for (int i = 0; i < n; i++) {
            int s = in.nextInt();
            int e = in.nextInt();
            set.add(s);
            set.add(e);
            data[index++] = new End(s, e, i, 0);
            data[index++] = new End(e, s, i, 1);
        }
        HashMap<Integer, Integer> map = new HashMap();
        HashMap<Integer, Integer> revert = new HashMap();
        index = 1;
        for (int i : set) {
            revert.put(index, i);
            map.put(i, index++);
        }
        FT ft = new FT(index + 2);
        for (End e : data) {
            e.s = map.get(e.s);
            e.o = map.get(e.o);
        }
        Arrays.sort(data, (a, b) -> {
            if (a.s != b.s) {
                return Integer.compare(a.s, b.s);
            }
            return Integer.compare(a.close, b.close);

        });
        int total = 0;
        int max = 0;
        int pos = -1;
        for (End e : data) {
            if (e.close == 0) {
                total++;
                ft.update(e.s, 1);
            } else {
                if (total >= k) {
                    int st = 1;
                    int ed = e.s;
                    int re = e.s;
                    while (st <= ed) {
                        int mid = (st + ed) >> 1;
                        if (ft.get(mid) >= k) {
                            re = mid;
                            ed = mid - 1;
                        } else {
                            st = mid + 1;
                        }
                    }
                    int a = revert.get(re);
                    int b = revert.get(e.s);
                    if (b - a + 1 > max) {
                        max = b - a + 1;
                        pos = e.index;
                    }
                }

                ft.update(e.o, -1);
                total--;
            }
        }
        out.println(max);
        if (max != 0) {
            TreeSet<End> q = new TreeSet<>((a, b) -> {
                if (a.s != b.s) {
                    return Integer.compare(a.s, b.s);
                }
                return Integer.compare(a.index, b.index);

            });
            for (End e : data) {
                if (e.close == 0) {
                    q.add(e);
                } else if (e.index == pos) {
                    while (q.size() > k) {
                        q.pollLast();
                    }
                    for (End o : q) {
                        out.print((o.index + 1) + " ");
                    }
                    break;
                } else {
                    q.remove(new End(e.o, e.s, e.index, 0));
                }
            }
        } else {
            for (int i = 0; i < k; i++) {
                out.print((i + 1) + " ");
            }
        }
        out.close();
    }

    static class End {

        int s, o, index, close;

        public End(int s, int o, int index, int close) {
            this.s = s;
            this.o = o;
            this.index = index;
            this.close = close;
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
