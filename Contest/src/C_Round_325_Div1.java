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
public class C_Round_325_Div1 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		long x = in.nextLong();
		long y = in.nextLong();
		if (x == y) {

			out.println("Impossible");

		} else {
			String v = cal(new Point(1, 0), new Point(0, 1), x, y);
			if (v == null) {
				out.println("Impossible");
			} else {
				out.println(v);
			}
		}
		out.close();
	}

	static String cal(Point a, Point b, long x, long y) {
		// System.out.println(a.x + " " + a.y + " " + b.x + " " + b.y);
		if (a.x == x && a.y == y) {
			return "";
		} else if (b.x == x && b.y == y) {
			return "";
		} else if (a.x + b.x > x || a.y + b.y > y) {
			return null;
		}
		int c = crossProduct(BigInteger.valueOf(a.x), BigInteger.valueOf(a.y),
				BigInteger.valueOf(x), BigInteger.valueOf(y));
		int d = crossProduct(BigInteger.valueOf(b.x), BigInteger.valueOf(b.y),
				BigInteger.valueOf(x), BigInteger.valueOf(y));

		int f = crossProduct(BigInteger.valueOf(a.x + b.x),
				BigInteger.valueOf(a.y + b.y), BigInteger.valueOf(x),
				BigInteger.valueOf(y));
		boolean removeA = d != f;
		// System.out.println(c + " " + d + " " + f);
		long s = 0;
		long e = 1000000000000000000L;
		long result = -1;
		while (s <= e) {
			long mid = (s + e) / 2;

			if (removeA) {
				
				int v = crossProduct(
						BigInteger.valueOf(a.x).add(
								BigInteger.valueOf(mid).multiply(
										BigInteger.valueOf(b.x))),
						BigInteger.valueOf(a.y).add(
								BigInteger.valueOf(mid).multiply(
										BigInteger.valueOf(b.y))),
						BigInteger.valueOf(x), BigInteger.valueOf(y));
				
				if (v == d) {

					e = mid - 1;
				} else {
					if (result == -1 || result < mid) {
						result = mid;
					}
					s = mid + 1;
				}
			} else {
				
				int v = crossProduct(
						BigInteger.valueOf(b.x).add(
								BigInteger.valueOf(mid).multiply(
										BigInteger.valueOf(a.x))),
						BigInteger.valueOf(b.y).add(
								BigInteger.valueOf(mid).multiply(
										BigInteger.valueOf(a.y))),
						BigInteger.valueOf(x), BigInteger.valueOf(y));
				
			//	System.out.println(v + " " + mid);
				if (v == c) {

					e = mid - 1;
				} else {
					if (result == -1 || result < mid) {
						result = mid;
					}
					s = mid + 1;
				}
			}
		}
		// if (true) {
		// System.out.println(result);
		// return "";
		// }
		String tmp = null;
		if (removeA) {
			tmp = cal(new Point(a.x + result * b.x, a.y + result * b.y), b, x,
					y);
		} else {
			tmp = cal(a, new Point(b.x + result * a.x, b.y + result * a.y), x,
					y);
		}
		if (tmp != null) {
			return (result - (tmp.isEmpty() ? 1 : 0)) + (removeA ? "B" : "A")
					+ tmp;
		}
		return null;

	}

	static int crossProduct(BigInteger x, BigInteger y, BigInteger x1,
			BigInteger y1) {
		BigInteger a = x.multiply(y1);

		BigInteger b = y.multiply(x1);

		return a.compareTo(b);
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

		long x, y;

		public Point(long start, long end) {
			this.x = start;
			this.y = end;
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
