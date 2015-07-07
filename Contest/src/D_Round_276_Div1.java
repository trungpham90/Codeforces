
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
public class D_Round_276_Div1 {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = in.nextInt();
        }
        long[] dp = new long[n];
        ArrayList<Integer> rootMax = new ArrayList();
        ArrayList<Integer> rootMin = new ArrayList();
        ArrayList<Integer> rMx = new ArrayList();
        ArrayList<Long> rMxV = new ArrayList();
        ArrayList<Integer> rMn = new ArrayList();
        ArrayList<Long> rMnV = new ArrayList();
        for (int i = 0; i < n; i++) {
            int x , y ;
            while(!rootMin.isEmpty() && data[rootMin.get(rootMin.size() - 1)] >= data[i]){
                rootMin.remove(rootMin.size() - 1);
            }
            if(!rootMin.isEmpty()){
                x = rootMin.get(rootMin.size() - 1) + 1;
            }else{
                x = 0;
            }
            rootMin.add(i);
            while(!rootMax.isEmpty() && data[rootMax.get(rootMax.size() - 1)] <= data[i]){
                rootMax.remove(rootMax.size() - 1);
            }
            if(!rootMax.isEmpty()){
                y = rootMax.get(rootMax.size() - 1) + 1;
            }else{
                y = 0;
            }
            rootMax.add(i);
         //   System.out.println("S "+ i + " "+ x + " " + y);
            long re = (i > 0 ? dp[i - 1] : 0);
            if (x <= i - 1) {
                int start = 0;
                int end = rMx.size() - 1;
                int index = -1;
                while (start <= end) {
                    int mid = (start + end) / 2;
                    if (rMx.get(mid) >= x) {
                        if (index == -1 || index > mid) {
                            index = mid;
                        }
                        end = mid - 1;
                    } else {
                        start = mid + 1;
                    }
                }
                long a = rMxV.get(index);
                re = re < (a - data[i]) ? a - data[i] : re;
            }
            if (y <= i - 1) {
                int start = 0;
                int end = rMn.size() - 1;
                int index = -1;
                while (start <= end) {
                    int mid = (start + end) / 2;
                    if (rMn.get(mid) >= y) {
                        if (index == -1 || index > mid) {
                            index = mid;
                        }
                        end = mid - 1;
                    } else {
                        start = mid + 1;
                    }
                }
                long b = rMnV.get(index);
                // System.out.println("MN " + b);
                re = re < (b + data[i]) ? b + data[i] : re;
            }
            // System.out.println("Re "  + re);
            dp[i] = re;
           
            long a = (i > 0 ? dp[i - 1]: 0) + data[i];
            while(!rMxV.isEmpty() && a > rMxV.get(rMxV.size() - 1)){
                rMx.remove(rMx.size() - 1);
                rMxV.remove(rMxV.size() - 1);
            }
            rMx.add(i);
            rMxV.add(a);
            a = (i > 0 ? dp[i - 1]: 0) - data[i];
            while(!rMnV.isEmpty() && a > rMnV.get(rMnV.size() - 1)){
                rMn.remove(rMn.size() - 1);
                rMnV.remove(rMnV.size() - 1);
            }
            rMn.add(i);
            rMnV.add(a);
        }
        out.println(dp[n - 1]);
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
