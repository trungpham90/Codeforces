
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
public class RoadReform {

    public static long MOD = 1000000007;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] edge = new int[m][3];
        ArrayList<Integer>[] map = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            map[i] = new ArrayList();
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 3; j++) {
                edge[i][j] = in.nextInt();
            }
            edge[i][0]--;
            edge[i][1]--;
            map[edge[i][0]].add(i);
            map[edge[i][1]].add(i);

        }
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        PriorityQueue<Point> q = new PriorityQueue<>();
        q.add(new Point(0, 0));
        dist[0] = 0;
        while (!q.isEmpty()) {
            Point p = q.poll();
            if (p.y == dist[p.x]) {
                for (int i : map[p.x]) {
                    int v = other(p.x, edge[i]);
                    if (dist[v] == -1 || dist[v] > dist[p.x] + edge[i][2]) {
                        dist[v] = dist[p.x] + edge[i][2];
                        q.add(new Point(v, dist[v]));
                    }
                }
            }
        }
        int[] distN = new int[n];
        Arrays.fill(distN, -1);
        q = new PriorityQueue<>();
        q.add(new Point(n - 1, 0));
        distN[n - 1] = 0;
        while (!q.isEmpty()) {
            Point p = q.poll();
            if (p.y == distN[p.x]) {
                for (int i : map[p.x]) {
                    int v = other(p.x, edge[i]);
                    if (distN[v] == -1 || distN[v] > distN[p.x] + edge[i][2]) {
                        distN[v] = distN[p.x] + edge[i][2];
                        q.add(new Point(v, distN[v]));
                    }
                }
            }
        }
        Arrays.sort(distN);
     //   System.out.println(Arrays.toString(distN));
        long[]sum = new long[n];
        for(int i = 1 ; i < n; i++){
            sum[i] = distN[i] + sum[i - 1];
        }
        long re = 0;
        for(int i = 0; i < n - 1; i++){
            if(dist[i] < dist[n - 1]){
                int max = dist[n - 1] - 2 - dist[i];
                
                int st = 0;
                int ed = n - 1;
                int result = -1;
                while(st <= ed){
                    int mid = (st + ed) >> 1;
                    if(distN[mid] <= max){
                        result = mid;
                        st = mid + 1;
                    }else{
                        ed = mid - 1;
                    }
                }
                if(result != -1){
                    //System.out.println("TEST " + max + " " + result + " " + i + " " + dist[i] + " " + distN[result]);
                    re += (long)(result + 1) * (long) (dist[n - 1] - 1) - sum[result] - (long)(result + 1) *(long) dist[i];
                }
            }
        }
        out.println(re);
        out.close();
    }

    static int other(int u, int[] edge) {
        return edge[0] == u ? edge[1] : edge[0];
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
            return Integer.compare(y, o.y);
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
