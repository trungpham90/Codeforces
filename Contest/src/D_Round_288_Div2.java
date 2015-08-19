
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
public class D_Round_288_Div2 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        String[] data = new String[n];
        HashMap<String, ArrayList<String>> map = new HashMap();
        final HashMap<String, Integer> countIn = new HashMap();
        final HashMap<String, Integer> countOut = new HashMap();
        HashMap<String, Integer> sub = new HashMap();
        for (int i = 0; i < n; i++) {
            data[i] = in.next();
            String a = data[i].substring(0, 2);
            String b = data[i].substring(1);
            if (!sub.containsKey(a)) {
                sub.put(a, 0);
            } else {
                sub.put(a, sub.get(a) + 1);
            }
            if (!sub.containsKey(b)) {
                sub.put(b, 0);
            } else {

                sub.put(b, sub.get(b) + 1);
            }
            if (!map.containsKey(a)) {
                map.put(a, new ArrayList());
            }
            map.get(a).add(b);
            if (!countIn.containsKey(b)) {
                countIn.put(b, 0);
            }
            if (!countOut.containsKey(a)) {
                countOut.put(a, 0);
            }
            countIn.put(b, countIn.get(b) + 1);
            countOut.put(a, countOut.get(a) + 1);
        }
        boolean ok = true;
        String start = null;
        int c = 0;
        for (String v : sub.keySet()) {
            int x = countIn.containsKey(v) ? countIn.get(v) : 0;
            int y = countOut.containsKey(v) ? countOut.get(v) : 0;
            if (y > 0 && start == null) {
                start = v;
            }
            if (x != y) {
                c++;
                if (Math.abs(x - y) != 1) {
                    ok = false;
                    break;
                }
                if (x + 1 == y) {
                    start = v;
                }
            }
        }
        //System.out.println(c);
        ok &= (c == 0 || c == 2);
        if (ok) {

            // System.out.println(start);
            StringBuilder result = new StringBuilder();
            HashMap<String, Integer> count = new HashMap();
            Stack<String> forward = new Stack();
            Stack<String> backward = new Stack();
            //backward.add(start);
            for (String v : map.keySet()) {
                count.put(v, 0);
            }
            //System.out.println(start);

            while (true) {
                if (map.containsKey(start)
                        && map.get(start).size() > count.get(start)) {
                    String tmp = map.get(start).get(count.get(start));
                    count.put(start, count.get(start) + 1);
                    forward.push(start.charAt(0) + tmp);
                    start = tmp;
                } else {
                    break;
                }
            }
            while (!forward.isEmpty()) {
                start = forward.pop();
                backward.push(start);
                start = start.substring(0, 2);
                while (true) {
                    if (map.containsKey(start)
                            && map.get(start).size() > count.get(start)) {
                        String tmp = map.get(start).get(count.get(start));
                        count.put(start, count.get(start) + 1);
                        forward.push(start.charAt(0) + tmp);
                        start = tmp;
                    } else {
                        break;
                    }
                }
            }
            result.append(backward.pop());
            while (!backward.isEmpty()) {
                String v = backward.pop();
                result.append(v.charAt(2));

            }
            if (result.length() == n + 2) {
                out.println("YES");
                out.println(result);
            } else {
                out.println("NO");
            }
        } else {
            out.println("NO");
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
