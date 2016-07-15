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
public class E_Round_361_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int k = in.nextInt();
		Point[] data = new Point[2 * n];
		int index = 0;
		for (int i = 0; i < n; i++) {
			int a = in.nextInt();
			int b = in.nextInt();
			data[index++] = new Point(a, 0);
			data[index++] = new Point(b, 1);
		}

		long[] fac = new long[n + 1];
		fac[0] = 1;
		for (int i = 1; i <= n; i++) {
			fac[i] = i * fac[i - 1];
			fac[i] %= MOD;
		}
		Arrays.sort(data, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				if (o1.x != o2.x) {
					return Integer.compare(o1.x, o2.x);
				}
				return Integer.compare(o1.y, o2.y);
			}
		});
		int count = 0;
		int lastOp = data[0].x;
		int lastCl = Integer.MIN_VALUE;
		long result = 0;
		for (Point p : data) {
			if (p.y == 0) {
				if (lastOp == p.x) {
					count++;
				} else {
//					System.out.println(p.x + " " + lastOp + " " + lastCl + " "
//							+ count);
					if (lastOp > lastCl) {
						if (count >= k) {
							long l = (p.x - lastOp) % MOD;
							result += l * com(count, k, fac);
							result %= MOD;
						}
					} else {
						if (count >= k) {
							long l = (p.x - lastCl - 1) % MOD;
							result += l * com(count, k, fac);
							result %= MOD;
						}
					}
					count++;
				}
				lastOp = p.x;
			} else {
				if (lastCl == p.x) {
					count--;
				} else {
				
					long l = 0;
					if (lastOp > lastCl) {
						if (count >= k) {
							l = (p.x - lastOp + 1) % MOD;
							result += l * com(count, k, fac);
							result %= MOD;
						}
					} else {
						if (count >= k) {
							l = (p.x - lastCl) % MOD;
							result += l * com(count, k, fac);
							result %= MOD;
						}
					}
//					System.out.println(p.x + " " + lastOp + " " + lastCl + " "
//							+ count + " " + l);
					count--;
				}
				lastCl = p.x;
			}
		}
		out.println(result);
		out.close();
	}

	static long com(int n, int k, long[] fac) {
		long result = (fac[n] * pow(fac[k], MOD - 2)) % MOD;
		result *= pow(fac[n - k], MOD - 2);
		result %= MOD;
		return result;
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
