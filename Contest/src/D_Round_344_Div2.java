
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
public class D_Round_344_Div2 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        String[] s = new String[n];
        String[] t = new String[m];
        for (int i = 0; i < n; i++) {
            s[i] = in.next();
        }
        for (int i = 0; i < m; i++) {
            t[i] = in.next();
        }
        ArrayList<State> list = new ArrayList();
        ArrayList<State> other = new ArrayList();
        for (String v : s) {
            String[] tmp = v.split("-");
            long num = Integer.parseInt(tmp[0]);
            char c = tmp[1].charAt(0);
            if (!list.isEmpty()) {
                if (list.get(list.size() - 1).c == c) {
                    list.get(list.size() - 1).count += num;
                } else {
                    list.add(new State(num, c));
                }
            } else {
                list.add(new State(num, c));
            }
        }
        for (String v : t) {
            String[] tmp = v.split("-");
            long num = Integer.parseInt(tmp[0]);
            char c = tmp[1].charAt(0);
            if (!other.isEmpty()) {
                if (other.get(other.size() - 1).c == c) {
                    other.get(other.size() - 1).count += num;
                } else {
                    other.add(new State(num, c));
                }
            } else {
                other.add(new State(num, c));
            }
        }
        long result = 0;
        if (other.size() <= 2) {
            if (other.size() == 1) {
                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).c == other.get(0).c && list.get(i).count >= other.get(0).count){
                        result += list.get(i).count - other.get(0).count + 1;
                    }
                }
            }else{
                for (int i = 0; i < list.size() - 1; i++) {
                    if(list.get(i).c == other.get(0).c && list.get(i).count >= other.get(0).count){
                        if(list.get(i + 1).c == other.get(1).c && list.get(i + 1).count >= other.get(1).count){
                            result++;
                        }
                    }
                }
            }
        } else {
            ArrayList<State> tmp = new ArrayList(other);
            tmp.remove(0);
            tmp.remove(tmp.size() - 1);
            int[] kmp = KMP(tmp);
            boolean[] check = new boolean[list.size()];
            int i = 0;
            int j = 0;
            while (i < list.size()) {
                while (j >= 0 && (tmp.get(j).c != list.get(i).c || tmp.get(j).count != list.get(i).count)) {
                    j = kmp[j];
                }
                i++;
                j++;
                if (j == tmp.size()) {
                    j = kmp[j];
                    check[i - tmp.size()] = true;
                }
            }
           // System.out.println(Arrays.toString(check));
            for (int k = 1; k < check.length ; k++) {
                if (check[k] && k > 0 && k + tmp.size() < check.length) {
                    if (list.get(k - 1).c == other.get(0).c && list.get(k - 1).count >= other.get(0).count) {
                        if (list.get(k + tmp.size()).c == other.get(other.size() - 1).c && list.get(k + tmp.size()).count >= other.get(other.size() - 1).count) {
                            result++;
                        }
                    }
                }
            }
        }
        out.println(result);
        out.close();
    }

    static class State {

        long count;
        char c;

        public State(long count, char c) {
            this.count = count;
            this.c = c;
        }
    }

    public static int[] KMP(ArrayList<State> val) {
        int i = 0;
        int j = -1;
        int[] result = new int[val.size() + 1];
        result[0] = -1;
        while (i < val.size()) {
            while (j >= 0 && (val.get(j).c != val.get(i).c || val.get(j).count != val.get(i).count)) {
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
