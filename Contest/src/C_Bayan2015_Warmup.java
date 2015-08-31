
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
public class C_Bayan2015_Warmup {

    public static long MOD = 1000000007;
    // static int count = 0;
  

    public synchronized static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));

        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        int n = in.nextInt();
        int m = in.nextInt();
       
        int total = 0;
        String[] data = new String[n];
        for (int i = 0; i < n; i++) {
            data[i] = in.next();
        }
        int ok = 0;
        ArrayList<Shape> list = new ArrayList();
        int startX = -1;
        int startY = -1;
        for (int i = 0; i < n && ok == 0; i++) {
            int st = -1;
            int ed = -1;

            for (int j = 0; j < m; j++) {
                if (data[i].charAt(j) == 'X') {
                    if (startX == -1) {
                        startX = j;
                        startY = i;
                    }
                    if (st == -1) {
                        st = j;
                    } else if (ed + 1 != j) {
                        ok = 1;
                        break;
                    }
                    ed = j;
                }
            }
            if (ok == 0) {
                if (st != -1) {
                    total += ed - st + 1;
                    if (list.isEmpty()) {
                        Shape shape = new Shape(st, i, ed, i);
                        list.add(shape);
                    } else {
                        Shape last = list.get(list.size() - 1);
                        if (last.y2 + 1 != i) {
                            ok = 2;
                        } else if (last.x1 > st) {
                            ok = 3;
                        } else if (last.x2 > ed) {
                            ok = 4;
                        } else {
                            if (st != last.x1 || ed != last.x2) {
                                Shape tmp = new Shape(st, i, ed, i);
                                list.add(tmp);
                            } else {
                                last.y2 = i;
                            }
                        }
                    }
                }
            }
        }
        // System.out.println(list);
        if (ok == 0) {
            if (list.isEmpty()) {
                out.println(0);
            } else if (list.size() == 1) {
                Shape a = list.get(0);
                int w = a.x2 - a.x1 + 1;
                int h = a.y2 - a.y1 + 1;
                out.println(Math.min(w, h));
            } else {
                for (int i = 0; i < list.size(); i++) {
                    Shape a = list.get(i);

                    int nxt = 1;
                    while (nxt < list.size()) {
                        Shape b = list.get(nxt);
                        if (b.x1 <= a.x1 && a.x2 <= b.x2) {
                            a.y2 = b.y2;
                        } else {
                            break;
                        }
                        nxt++;
                    }
                }

                ArrayList<Shape> list2 = new ArrayList();

                for (int i = 0; i < m && ok == 0; i++) {
                    int st = -1;
                    int ed = -1;

                    for (int j = 0; j < n; j++) {
                        if (data[j].charAt(i) == 'X') {

                            if (st == -1) {
                                st = j;
                            } else if (ed + 1 != j) {
                                ok = 1;
                                break;
                            }
                            ed = j;
                        }
                    }
                    //System.out.println(ok);
                    if (ok == 0) {
                        if (st != -1) {

                            if (list2.isEmpty()) {
                                Shape shape = new Shape(i, st, i, ed);
                                list2.add(shape);
                            } else {
                                Shape last = list2.get(list2.size() - 1);
                                if (last.x2 + 1 != i) {
                                    ok = 2;
                                } else if (last.y1 > st) {
                                    ok = 3;
                                } else if (last.y2 > ed) {
                                    ok = 4;
                                } else {
                                    if (st != last.y1 || ed != last.y2) {
                                        Shape tmp = new Shape(i, st, i, ed);
                                        list2.add(tmp);
                                    } else {
                                        last.x2 = i;
                                    }
                                }
                            }
                        }
                    }
                    //System.out.println(list2);
                }

                for (int i = 0; i < list2.size(); i++) {
                    Shape a = list2.get(i);

                    int nxt = 1;
                    while (nxt < list2.size()) {
                        Shape b = list2.get(nxt);
                        if (b.y1 <= a.y1 && a.y2 <= b.y2) {
                            a.x2 = b.x2;
                        } else {
                            break;
                        }
                        nxt++;
                    }
                }
              //  System.out.println(list2);


                int w = -1;
                int h = -1;

                if (w == -1) {
                    Shape a = list.get(0);
                    Shape b = list.get(1);
                    int x = a.x2 - a.x1 + 1;
                    int y = a.y2 - a.y1 + 1;

                    int t = x * (b.y2 - b.y1 + 1), q = y * (list2.get(1).x2 - list2.get(1).x1 + 1);
                    // System.out.println(t + " " + q);
                    boolean k1 = verify(startX, startY, x, b.y2 - b.y1 + 1, total, data);
                    boolean k2 = verify(startX, startY, list2.get(1).x2 - list2.get(1).x1 + 1, y, total, data);

                    // System.out.println(k1 + " " + k2);


                    if (k1 && k2) {
                        out.println(Math.min(t, q));
                    } else if (k1) {
                        out.println(t);
                    } else if (k2) {
                        out.println(q);
                    } else {
                        out.println(-1);
                    }



                }


            }


        } else {
            out.println(-1);
        }
        out.close();
    }

    static boolean verify(int x, int y, int w, int h, int need, String[] data) {
        for (int i = y; i < y + h; i++) {
            for (int j = x; j < x + w; j++) {
                if (data[i].charAt(j) != 'X') {
                    return false;
                }
            }
        }
       
        LinkedList<Point> q = new LinkedList();
        q.add(new Point(x, y));
        Point last = null;
        int total = w * h;
        while (!q.isEmpty()) {
            Point p = q.poll();
            x = p.x;
            y = p.y;
            // System.out.println(x + " " + y);
            boolean ok = true;
            if (y + h < data.length) {
                for (int j = x; j < x + w; j++) {
                    if (data[y + h].charAt(j) != 'X') {
                        ok = false;
                        break;
                    }
                }

            } else {
                ok = false;
            }
            if (ok) {
                q.add(new Point(x, y + 1));
                total += w;
            } else {
                ok = true;
                if (x + w < data[0].length()) {
                    for (int i = y; i < y + h; i++) {
                        if (data[i].charAt(x + w) != 'X') {
                            ok = false;
                            break;
                        }
                    }
                } else {
                    ok = false;
                }
                if (ok) {
                    q.add(new Point(x + 1, y));
                    total += h;
                }
            }
        }
        // System.out.println(total + " " + w + " " + h);
        
        return total == need;
    }

    static class Shape {

        int x1, y1, x2, y2;

        public Shape(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        @Override
        public String toString() {
            return "Shape{" + "x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + '}';
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
