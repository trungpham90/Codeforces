
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
public class B_Round_371_Div1 {

    public static long MOD = 1000000007;
    static int c = 0;

    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));

        Scanner in = new Scanner();
        int n = in.nextInt();
        int minX = -1;
        int start = 1;
        int end = n;
        c = 0;
        while (start <= end) {
            int mid = (start + end) >> 1;
            c = increaseC(c);
            System.out.println("? " + mid + " 1 " + n + " " + n);
            System.out.flush();

            int v = in.nextInt();
            if (v == 2) {
                minX = mid;
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        //System.out.println("Minx " + minX);
        int maxX = -1;
        start = minX;
        end = n;
        while (start <= end) {
            int mid = (start + end) >> 1;
            c = increaseC(c);
            System.out.println("? " + minX + " 1 " + mid + " " + n);
            System.out.flush();
            int v = in.nextInt();
            if (v == 2) {
                maxX = mid;
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        // System.out.println("Maxx " + maxX);
        int minY = -1;
        start = 1;
        end = n;
        while (start <= end) {
            int mid = (start + end) >> 1;
            c = increaseC(c);
            System.out.println("? " + minX + " " + mid + " " + maxX + " " + n);
            System.out.flush();

            int v = in.nextInt();
            if (v == 2) {
                minY = mid;
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        // System.out.println("MinY " + minY);
        int maxY = -1;
        start = minY;
        end = n;
        while (start <= end) {
            int mid = (start + end) >> 1;
            c = increaseC(c);
            System.out.println("? " + minX + " " + minY + " " + maxX + " " + mid);
            System.out.flush();

            int v = in.nextInt();
            if (v == 2) {
                maxY = mid;
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        // System.out.println("MaxY " + maxY);
        int middleMinX = maxX;
        start = minX;
        end = maxX;
        while (start <= end) {
            int mid = (start + end) >> 1;
            c = increaseC(c);
            System.out.println("? " + minX + " " + minY + " " + mid + " " + maxY);
            System.out.flush();

            int v = in.nextInt();
            if (v == 1) {
                middleMinX = mid;
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        //System.out.println("MiddleMinX " + middleMinX);
        int middleMaxX = -1;
        start = middleMinX + 1;
        end = maxX;
        while (start <= end) {
            int mid = (start + end) >> 1;
            c = increaseC(c);
            System.out.println("? " + mid + " " + minY + " " + maxX + " " + maxY);
            System.out.flush();

            int v = in.nextInt();
            if (v == 1) {
                middleMaxX = mid;
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        // System.out.println("MiddleMaxX " + middleMaxX);
        if (middleMaxX == -1) {
            int middleMinY = -1;
            start = minY;
            end = maxY;
            while (start <= end) {
                int mid = (start + end) >> 1;
                c = increaseC(c);
                System.out.println("? " + minX + " " + minY + " " + maxX + " " + mid);
                System.out.flush();

                int v = in.nextInt();
                if (v == 1) {
                    middleMinY = mid;
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
            //System.out.println("MiddleMinY " + middleMinY);
            int middleMaxY = -1;
            start = middleMinY + 1;
            end = maxY;
            while (start <= end) {
                int mid = (start + end) >> 1;
                c = increaseC(c);
                System.out.println("? " + minX + " " + mid + " " + maxX + " " + maxY);
                System.out.flush();

                int v = in.nextInt();
                if (v == 1) {
                    middleMaxY = mid;
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
            //System.out.println("MiddleMaxY " + middleMaxY);
            if (minX == maxX) {
                System.out.println("! " + minX + " " + minY + " " + maxX + " " + middleMinY + " " + minX + " " + middleMaxY + " " + maxX + " " + maxY);
                System.out.flush();
            } else {
                int[] a = calX(minX, maxX, minY, middleMinY, in);
                int[] b = calX(minX, maxX, middleMaxY, maxY, in);
                check(a);
                check(b);
                System.out.println("! " + a[0] + " " + minY + " " + a[1] + " " + middleMinY + " " + b[0] + " " + middleMaxY + " " + b[1] + " " + maxY);
                System.out.flush();
            }
        } else if (minY == maxY) {
            System.out.println("! " + minX + " " + minY + " " + middleMinX + " " + maxY + " " + middleMaxX + " " + minY + " " + maxX + " " + maxY);
            System.out.flush();
        } else {
            int[] a = calY(minX, middleMinX, minY, maxY, in);
            int[] b = calY(middleMaxX, maxX, minY, maxY, in);
            check(a);
            check(b);
            System.out.println("! " + minX + " " + a[0] + " " + middleMinX + " " + a[1] + " " + middleMaxX + " " + b[0] + " " + maxX + " " + b[1]);
            System.out.flush();
        }

    }

    static void check(int[] v) {
        if (v[0] == -1 || v[1] == -1) {
            throw new NullPointerException();
        }
    }

    static int increaseC(int c) {
        if (c == 200) {
            throw new NullPointerException();
        }
        return c + 1;
    }

    public static int[] calY(int minX, int maxX, int minY, int maxY, Scanner in) {
        c = increaseC(c);
        System.out.println("? " + minX + " " + minY + " " + maxX + " " + (maxY - 1));
        System.out.flush();
        int v = in.nextInt();
        c = increaseC(c);
        System.out.println("? " + minX + " " + (minY + 1) + " " + maxX + " " + maxY);
        System.out.flush();
        int o = in.nextInt();
        if (v == 1 && o == 1) {
            int a = -1;
            int start = minY;
            int end = maxY;
            while (start <= end) {
                int mid = (start + end) >> 1;
                c = increaseC(c);
                System.out.println("? " + minX + " " + minY + " " + maxX + " " + mid);
                System.out.flush();
                if (in.nextInt() == 1) {
                    a = mid;
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
            int b = -1;
            start = minY;
            end = a;
            while (start <= end) {
                int mid = (start + end) >> 1;
                c = increaseC(c);
                System.out.println("? " + minX + " " + mid + " " + maxX + " " + a);
                System.out.flush();
                if (in.nextInt() == 1) {
                    b = mid;
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
            return new int[]{b, a};
        } else if (v == 1) {
            int a = -1;
            int start = minY;
            int end = maxY;
            while (start <= end) {
                int mid = (start + end) >> 1;
                c = increaseC(c);
                System.out.println("? " + minX + " " + minY + " " + maxX + " " + mid);
                System.out.flush();
                if (in.nextInt() == 1) {
                    a = mid;
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
            return new int[]{minY, a};
        } else if (o == 1) {
            int b = -1;
            int start = minY;
            int end = maxY;
            while (start <= end) {
                int mid = (start + end) >> 1;
                c = increaseC(c);
                System.out.println("? " + minX + " " + mid + " " + maxX + " " + maxY);
                System.out.flush();
                if (in.nextInt() == 1) {
                    b = mid;
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
            return new int[]{b, maxY};
        } else {
            return new int[]{minY, maxY};
        }
    }

    public static int[] calX(int minX, int maxX, int minY, int maxY, Scanner in) {
        c = increaseC(c);
        System.out.println("? " + minX + " " + minY + " " + (maxX - 1) + " " + maxY);
        System.out.flush();
        int v = in.nextInt();
        c = increaseC(c);
        System.out.println("? " + (minX + 1) + " " + minY + " " + maxX + " " + maxY);
        System.out.flush();
        int o = in.nextInt();
        if (v == 1 && o == 1) {
            int a = -1;
            int start = minX;
            int end = maxX;
            while (start <= end) {
                int mid = (start + end) >> 1;
                c = increaseC(c);
                System.out.println("? " + minX + " " + minY + " " + mid + " " + maxY);
                System.out.flush();
                if (in.nextInt() == 1) {
                    a = mid;
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
            int b = -1;
            start = minX;
            end = a;
            while (start <= end) {
                int mid = (start + end) >> 1;
                c = increaseC(c);
                System.out.println("? " + mid + " " + minY + " " + a + " " + maxY);
                System.out.flush();
                if (in.nextInt() == 1) {
                    b = mid;
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
            return new int[]{b, a};
        } else if (v == 1) {
            int a = -1;
            int start = minX;
            int end = maxX;
            while (start <= end) {
                int mid = (start + end) >> 1;
                c = increaseC(c);
                System.out.println("? " + minX + " " + minY + " " + mid + " " + maxY);
                System.out.flush();
                if (in.nextInt() == 1) {
                    a = mid;
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
            return new int[]{minX, a};
        } else if (o == 1) {
            int b = -1;
            int start = minX;
            int end = maxX;
            while (start <= end) {
                int mid = (start + end) >> 1;
                c = increaseC(c);
                System.out.println("? " + mid + " " + minY + " " + maxX + " " + maxY);
                System.out.flush();
                if (in.nextInt() == 1) {
                    b = mid;
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
            return new int[]{b, maxX};
        } else {
            return new int[]{minX, maxX};
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