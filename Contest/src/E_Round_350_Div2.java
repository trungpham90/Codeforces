
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
public class E_Round_350_Div2 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        int p = in.nextInt();
        String line = "(" + in.next() + ")";
        Bracket root = new Bracket(0, line.length() - 1);
        Bracket[] data = new Bracket[line.length()];
        data[0] = root;
        data[line.length() - 1] = root;
        Stack<Integer> s = new Stack();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '(') {
                s.push(i);
                data[i] = new Bracket(i, 0);
                if (i > 0) {
                    data[i].prevOpen = data[i - 1];
                    if (line.charAt(i - 1) == '(') {
                        data[i - 1].nxtOpen = data[i];
                    } else {
                        data[i - 1].nxtClose = data[i];
                    }
                }
            } else {
                int v = s.pop();
                data[v].end = i;
                data[i] = data[v];
                data[i].prevClose = data[i - 1];
                if (line.charAt(i - 1) == '(') {
                    data[i - 1].nxtOpen = data[i];
                } else {
                    data[i - 1].nxtClose = data[i];
                }
            }
        }

        boolean open = data[p].start == p;
        Bracket cur = data[p];
        String cmd = in.next();
        boolean[] deleted = new boolean[n + 2];
        for (int i = 0; i < m; i++) {
            if (cmd.charAt(i) == 'L') {
                Bracket nxt;
                if (open) {
                    nxt = cur.prevOpen;
                    if (nxt.end >= cur.end) {
                        open = true;
                    } else {
                        open = false;
                    }
                } else {
                    nxt = cur.prevClose;
                    if (nxt.end == cur.end) {
                        open = true;
                    } else {
                        open = false;
                    }
                }
                cur = nxt;
            } else if (cmd.charAt(i) == 'R') {
                Bracket nxt;
                if (open) {
                    nxt = cur.nxtOpen;
                    if (nxt.end < cur.end) {
                        open = true;
                    } else {
                        open = false;
                    }
                } else {
                    nxt = cur.nxtClose;
                    if (nxt.start > cur.end) {
                        open = true;
                    } else {
                        open = false;
                    }
                }
                cur = nxt;
            } else {
                deleted[cur.start] = true;
                Bracket prev = cur.prevOpen;
                Bracket nxt = cur.nxtClose;
                update(prev, cur, nxt);
                if (nxt.end < line.length() - 1) {
                    open = cur.end < nxt.start;
                    cur = nxt;
                } else {
                    open = cur.end < prev.end;
                    cur = prev;
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            if (deleted[i]) {
                i = data[i].end;
            } else if (i == data[i].start) {
                out.print('(');
            } else {
                out.print(')');
            }
        }
        out.close();
    }

    static void update(Bracket prev, Bracket cur, Bracket nxt) {
        boolean openPrev = cur.end < prev.end;
        boolean openNxt = cur.end < nxt.start;
        if (openPrev) {
            if (openNxt) {
                prev.nxtOpen = nxt;
                nxt.prevOpen = prev;
            } else {
                prev.nxtOpen = nxt;
                nxt.prevClose = prev;
            }
        } else if (openNxt) {
            prev.nxtClose = nxt;
            nxt.prevOpen = prev;
        } else {
            prev.nxtClose = nxt;
            nxt.prevClose = prev;
        }
    }

    static class Bracket implements Comparable<Bracket> {

        int start, end;
        Bracket prevOpen, prevClose, nxtOpen, nxtClose;

        public Bracket(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public String shortName() {
            return start + " " + end;
        }

        @Override
        public String toString() {
            return "Bracket{" + "start=" + start + ", end=" + end + ", prevO=" + getShortName(prevOpen) + ", nxtO=" + getShortName(nxtOpen) + ", prevC=" + getShortName(prevClose) + ", nxtC=" + getShortName(nxtClose) + '}';
        }

        @Override
        public int compareTo(Bracket o) {
            return Integer.compare(start, o.start);
        }
    }

    static String getShortName(Bracket b) {
        if (b == null) {
            return null;
        }
        return b.shortName();
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
