
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
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
public class D_Edu_Round_2 {

    public static long MOD = 1000000007;

    static MathContext mc = new MathContext(50, RoundingMode.HALF_UP);
    static BigDecimal TWO = BigDecimal.valueOf(2.0);

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();
        Point a = new Point(in.nextDouble(), in.nextDouble());
        double r1 = in.nextDouble();
        Point b = new Point(in.nextDouble(), in.nextDouble());
        double r2 = in.nextDouble();
        BigDecimal R1 = BigDecimal.valueOf(r1);
        BigDecimal R2 = BigDecimal.valueOf(r2);
        BigDecimal d = dist(a, b);
       // System.out.println("HE HE");
        a = new Point(BigDecimal.ZERO, BigDecimal.ZERO);
        b = new Point(d.negate(), BigDecimal.ZERO);
        Point data = intersection(d, r1, r2);
        
        //  System.out.println(a + " " + b + " " + data);
        // System.out.println(Arrays.toString(data));
        if (data == null) {
            if (d.compareTo(R1.add(R2)) >= 0) {
                out.println(0);
            } else if (r2 > r1) {

                out.println(Math.PI * r1 * r1);
            } else {
                out.println(Math.PI * r2 * r2);
            }
        } else {
            BigDecimal angle1 = getAngle(a, data, BigDecimal.valueOf(r1));
            BigDecimal angle2 = getAngle(b, data, BigDecimal.valueOf(r2));

            if (data.x.compareTo(a.x) < 0 && data.x.compareTo(b.x) > 0) {
                BigDecimal area = arcCircle(angle1, BigDecimal.valueOf(r1)).add(arcCircle(angle2, BigDecimal.valueOf(r2))).subtract(
                        area(b, a, data));
                out.println(area.multiply(TWO));
            } else if (r2 > r1) {
                BigDecimal area = arcCircle(angle1, R1).subtract(arcCircle(angle2, R2)).add(
                        area(b, a, data));              
                out.println(BigDecimal.valueOf(Math.PI * r1 * r1).subtract(area.multiply(TWO)));
            } else {
                BigDecimal area = arcCircle(angle2, R2).subtract(arcCircle(angle1, R1)).add(area(b, a, data));
                out.println(BigDecimal.valueOf(Math.PI * r2 * r2).subtract(area.multiply(TWO)));
            }

        }
        out.close();
    }

    static BigDecimal arcCircle(BigDecimal angle, BigDecimal r) {
        BigDecimal area = angle.multiply(r.multiply(r)).divide(TWO);
        return area;
    }

    static BigDecimal area(Point a, Point b, Point c) {
        // System.out.println(a + " " + b + " " + c);
        BigDecimal X1 = a.x.subtract(c.x);
        BigDecimal Y1 = b.y.subtract(a.y);
        BigDecimal X2 = a.x.subtract(b.x);
        BigDecimal Y2 = c.y.subtract(a.y);
        BigDecimal area = X1.multiply(Y1).subtract(X2.multiply(Y2)).divide(BigDecimal.valueOf(2.0));
        return area.abs();
    }

    static BigDecimal getAngle(Point o, Point x, BigDecimal r) {
        BigDecimal dist;
        if (o.x.compareTo(x.x) > 0) {
            dist = o.x.subtract(x.x);
        } else {
            dist = x.x.subtract(o.x);
        }
        //System.out.println(dist);
        BigDecimal angle = atan(x.y.divide(dist , mc));
        return angle;
    }

    static Point intersection(BigDecimal d, double r1, double r2) {
        if (d.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }
        BigDecimal R2 = BigDecimal.valueOf(r2);
        BigDecimal R1 = BigDecimal.valueOf(r1);

        BigDecimal X = R2.multiply(R2).subtract(R1.multiply(R1)).subtract(d.multiply(d)).divide(d.multiply(TWO) , mc);
       // System.out.println("HO HO");
        BigDecimal other = R1.multiply(R1).subtract(X.multiply(X));
        if (other.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }
        BigDecimal Y = sqrt(other);

        return new Point(X, Y);
    }

    static BigDecimal dist(Point a, Point b) {
        BigDecimal x = a.x.subtract(b.x);
        BigDecimal y = a.y.subtract(b.y);
        
        return sqrt(x.multiply(x).add(y.multiply(y)));
    }

    static BigDecimal atan(BigDecimal x) {
        BigDecimal s = BigDecimal.valueOf(1, 1); // 0.1
        int t = 0;
        while (x.compareTo(s) > 0) {
            x = x.subtract(s, mc).divide(BigDecimal.ONE.add(x.multiply(s, mc), mc), mc);
            t++;
        }
        if (t > 0) {
            return atan(s).multiply(BigDecimal.valueOf(t), mc).add(atan(x), mc);
        }
        BigDecimal res = BigDecimal.ZERO;
        BigDecimal mult = x;
        BigDecimal x2 = x.multiply(x, mc);
        int i = 1;
        BigDecimal eps = BigDecimal.valueOf(1, mc.getPrecision());
        while (mult.abs().compareTo(eps) > 0) {
            res = res.add(mult.divide(BigDecimal.valueOf(i), mc), mc);
            mult = mult.multiply(x2, mc).negate();
            i += 2;
        }
        return res;
    }

    static BigDecimal getPi() {
        return BigDecimal.valueOf(4).multiply(atan(BigDecimal.ONE), mc);
    }

    static BigDecimal getPi2() {
        BigDecimal res = atan(BigDecimal.ONE);
        return res.add(res, mc);
    }

    static BigDecimal sqrt(BigDecimal x) {
        int xscale = x.scale();
        int bits = x.unscaledValue().bitLength() + (int) (Math.log(10) * xscale);
        BigDecimal y = new BigDecimal(BigInteger.ONE.shiftLeft(bits / 2), xscale);
        for (int i = 0; i < 100; i++) {
            BigDecimal ny = y.add(x.divide(y, mc), mc).divide(TWO, mc);
            if (ny.equals(y)) {
                break;
            }
            y = ny;
        }
        return y;
    }

    static BigDecimal atan2(BigDecimal y, BigDecimal x) {
        if (y.signum() > 0 && x.signum() <= 0) {
            return getPi2().add(atan2(x.negate(), y), mc);
        }
        if (y.signum() <= 0 && x.signum() <= 0) {
            return atan2(y.negate(), x).subtract(getPi2(), mc);
        }
        if (y.signum() < 0) {
            return atan2(y.negate(), x).negate();
        }
        if (y.compareTo(x) > 0) {
            return getPi2().subtract(atan2(x, y), mc);
        }
        return atan(y.divide(x, mc));
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

    public static class Point {

        BigDecimal x, y;

        public Point(double start, double end) {
            this.x = BigDecimal.valueOf(start);
            this.y = BigDecimal.valueOf(end);
        }

        public Point(BigDecimal x, BigDecimal y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point [x=" + x + ", y=" + y + "]";
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
