
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
public class B_Round_310_Div1 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        Dist[] data = new Dist[n - 1];
        Bri[] bridges = new Bri[m];
        long lastL = -1;
        long lastR = -1;
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                lastL = in.nextLong();
                lastR = in.nextLong();
            } else {
                long l = in.nextLong();
                long r = in.nextLong();
                data[i - 1] = new Dist(i - 1, l - lastR, r - lastL);
                lastL = l;
                lastR = r;
            }
        }
        TreeSet<Bri> set =new TreeSet<>(new Comparator<Bri>(){

            @Override
            public int compare(Bri o1, Bri o2) {
                if(o1.v != o2.v){
                    return Long.compare(o1.v, o2.v);
                }
                return o1.index - o2.index;
            }
        });
        for (int i = 0; i < m; i++) {
            bridges[i] = new Bri(i, in.nextLong());
            set.add(bridges[i]);
        }

        Arrays.sort(bridges, new Comparator<Bri>() {
            @Override
            public int compare(Bri o1, Bri o2) {
                return Long.compare(o1.v,o2.v);
            }
        });
        Arrays.sort(data, new Comparator<Dist>() {
            @Override
            public int compare(Dist o1, Dist o2) {
                if (o1.min != o2.min) {
                    return Long.compare(o1.min, o2.min);
                }
                return Long.compare(o1.max, o2.max);
            }
        });
      //  System.out.println(Arrays.toString(data));
      //  System.out.println(Arrays.toString(bridges));
        int start = m - 1;
        int[] result = new int[n - 1];
        boolean ok = true;
        for (int i = n - 2; i >= 0; i--) {
            if(set.isEmpty()){
                ok = false;
                break;
            }
            Bri b = set.floor(new Bri(m, data[i].max));
            if(b == null){
                ok = false;
                break;
            }else{
                if(b.v >= data[i].min){
                    result[data[i].index] = b.index + 1;
                    set.remove(b);
                }else{
                    ok = false;
                    break;
                }
            }
        }
        if (ok) {
            out.println("Yes");
            for (int i : result) {
                out.print(i + " ");
            }
        } else {
            out.println("No");
        }
        out.close();
    }
    static class Bri{
        int index;
        long v;

        public Bri(int index, long v) {
            this.index = index;
            this.v = v;
        }

        @Override
        public String toString() {
            return "Bri{" + "v=" + v + '}';
        }
        
    }
    static class Dist {

        int index;
        long min, max;

        public Dist(int index, long min, long max) {
            this.index = index;
            this.min = min;
            this.max = max;
        }

        @Override
        public String toString() {
            return "Dist{" + "min=" + min + ", max=" + max + '}';
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
