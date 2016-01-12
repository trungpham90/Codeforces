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
public class B_Round_134_Div1 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int k = in.nextInt();

		if (k < n) {
			out.println("IMPOSSIBLE");
		} else {
			int result = -1;
			boolean top = false;
			int other = 0;
			for (int i = 1; i <= k; i++) {
				if (i != k) {
					int v = cal(n, i, k);

					// System.out.println(v + " " + i + " " + k);
					if ((result == -1 || result > v) && v != -2) {
						result = v;
						other = i;
						top = false;
					}
				}
				int v = cal(n, k, i);

				// System.out.println(v + " " + k + " " + i);
				if ((result == -1 || result > v) && v != -2) {
					result = v;
					top = true;
					other = i;
				}

			}
			if (result == -1) {
				out.println("IMPOSSIBLE");
			} else {
				out.println(result);
				char[] re = new char[n];
				int t = top ? k : other;
				int b = top ? other : k;
				re[n - 1] = top ? 'T' : 'B';
				if (top) {
					t -= b;
				} else {
					b -= t;
				}
				for (int i = n - 2; i >= 0; i--) {
					if (t >= b) {
						re[i] = 'T';
						t -= b;
					} else {
						re[i] = 'B';
						b -= t;
					}

				}
				// System.out.println(t + " " + b);
				out.println(new String(re));
			}
		}

		out.close();
	}

	public static int cal(int index, int a, int b) {

		if ((a == 0 && b == 1) || (a == 1 && b == 0)) {

			return index == 0 ? 0 : -2;
		}
		if (a <= 0 || b <= 0 || index < 0) {
			return -2;
		}
		int result = -2;
		if (a >= b) {
			int x = a / b;
			// System.out.println(x + " " + index + " " + a + " " + b + " "
			// + (index - x) + " " + (a % b) + " " + b);
			int v = cal(index - x, a % b, b);
			if (v != -2) {
				if (b == 1) {
					result = a - 1;
				} else {
					result = v + (x - 1);
				}
			}
		} else {
			int x = b / a;
			// System.out.println(x + " " + index + " " + a + " " + b + " "
			// + (index - x) + " " + (a) + " " + (b % a));

			int v = cal(index - x, a, b % a);
			if (v != -2) {
				if (a == 1) {
					result = b - 2;
				} else {
					result = v + (x - 1);
				}
			}
		}
		// System.out.println(index + " " + a + " " + b + " " + result);
		return result;
	}

	public static String pad(String val, int n) {
		while (val.length() < n) {
			val = "0" + val;
		}
		return val;
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
