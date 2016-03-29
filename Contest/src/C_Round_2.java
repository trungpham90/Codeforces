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
 * 
 * @author pttrung
 */
public class C_Round_2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		Circle[] data = new Circle[3];
		for (int i = 0; i < 3; i++) {
			data[i] = new Circle(in.nextInt(), in.nextInt(), in.nextInt());
		}
		double start = 0;
		double end = 1.3*Math.PI;
		Point result = null;
		for (int i = 0; i < 1000000; i++) {
			double mid = (start + end) / 2;
			double a = data[0].r / Math.sin(mid);
			double b = data[1].r / Math.sin(mid);
			double c = data[2].r / Math.sin(mid);
			Point p = cal(data[0], data[1], data[2], a, b, c);

			// System.out.println(p + " " + Math.toDegrees(mid) + " " + a + " "
			// + b + " " + c);
			if (p == null) {
				end = mid;
			} else {

				// System.out.println(p + " "
				// + dist(p.x, p.y, data[0].x, data[0].y) + " "
				// + dist(p.x, p.y, data[1].x, data[1].y) + " "
				// + dist(p.x, p.y, data[2].x, data[2].y) + " " + a + " "
				// + b + " " + c + " " + Math.toDegrees(mid));
				if (Math.abs(dist(p.x, p.y, data[2].x, data[2].y) - c) < 1e-9) {
					result = p;
					break;
				} else if (dist(p.x, p.y, data[2].x, data[2].y) < c) {
					start = mid;
				} else {
					end = mid;
				}
			}
		}
		if (result != null) {
			out.println(result.x + " " + result.y);
		}

		out.close();
	}

	public static Point cal(Circle a, Circle b, Circle c, double r1, double r2,
			double r3) {

		if (b.y == a.y) {
			// System.out.println("HE HE");
			double A = r1 * r1 - r2 * r2 - a.x * a.x + b.x * b.x;
			double x1 = A / (2 * (b.x - a.x));
			double B = r1 * r1 - (a.x - x1) * (a.x - x1);
			if (B < 0 && Math.abs(B) > 1e-7) {
				return null;
			}else if(B < 0){
				B = 0;
			}
			double y1 = a.y - Math.sqrt(B);
			double x2 = x1;
			double y2 = a.y + Math.sqrt(B);
			double d1 = dist(x1, y1, c.x, c.y);
			double d2 = dist(x2, y2, c.x, c.y);

			if (d1 <= d2) {
				return new Point(x1, y1);
			}
			return new Point(x2, y2);
		} else {
			double A = r1 * r1 - r2 * r2 + b.x * b.x - a.x * a.x + b.y * b.y
					- a.y * a.y;
			double B = A / (2 * (b.y - a.y));
			double rate = (a.x - b.x) / (b.y - a.y);
			double a1 = rate * rate + 1;
			double b1 = -2 * rate * (a.y - B) - 2 * a.x;
			double c1 = a.x * a.x + (a.y - B) * (a.y - B) - r1 * r1;
			double delta = (b1 * b1 - (4 * a1 * c1));
			// System.out.println(delta);
			if (delta < 0 && Math.abs(delta) > 1e-5) {
				return null;
			} else {
				if (Math.abs(delta) < 0) {
					delta = 0;
				}
				double x1 = (-b1 + Math.sqrt(delta)) / (2 * a1);
				double y1 = rate * x1 + B;
				double d1 = dist(x1, y1, c.x, c.y);

				double x2 = (-b1 - Math.sqrt(delta)) / (2 * a1);
				double y2 = rate * x2 + B;
				double d2 = dist(x2, y2, c.x, c.y);

				if (d1 <= d2)
					return new Point(x1, y1);
				return new Point(x2, y2);

			}
		}
	}

	static double dist(double x1, double y1, double x2, double y2) {
		double X = x1 - x2;
		double Y = y1 - y2;
		return Math.sqrt(X * X + Y * Y);
	}

	// 60.76252 39.23748
	public static Point cal(double a1, double b1, double c1, double a2,
			double b2, double c2) {
		System.out.println(a1 + " " + b1 + " " + c1 + " " + a2 + " " + b2 + " "
				+ c2);
		if (a1 == 0) {
			if (a2 == 0) {
				return null;
			}
			double y = -c1 / b1;
			double x = (-c2 - b2 * y) / a2;
			return new Point(x, y);
		} else {
			if (b1 == 0 && b2 == 0) {
				return null;
			}
			double y = (c2 * a1 - c1 * a2) / (b1 * a2 - b2 * a1);
			double x = (-c2 - b2 * y) / a2;
			return new Point(x, y);
		}
	}

	static class Circle {
		double x, y, r;

		public Circle(int x, int y, int r) {
			super();
			this.x = x;
			this.y = y;
			this.r = r;
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

	public static class Point {

		double x, y;

		public Point(double start, double end) {
			this.x = start;
			this.y = end;
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
			// System.setOut(new PrintStream(new
			// BufferedOutputStream(System.out), true));
			br = new BufferedReader(new InputStreamReader(System.in));
			// br = new BufferedReader(new InputStreamReader(new
			// FileInputStream(new File("input.txt"))));
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
