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
public class C_Round_179_Div1 {

	public static long MOD = 1000000007;
	static int[][] o;
	static long[][][] dp;
	static long[][] c;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int k = in.nextInt();
		int a = 0;
		for (int i = 0; i < n; i++) {
			int v = in.nextInt();
			if (v == 50) {
				a++;
			}
		}
		o = new int[n + 1][a + 1];
		c = new long[51][51];
		c[0][0] = 1;
		for (int i = 1; i < 51; i++) {
			c[i][0] = 1;
			c[i][i] = 1;
			for (int j = 1; j < i; j++) {
				c[i][j] = c[i - 1][j] + c[i - 1][j - 1];
				c[i][j] %= MOD;
			}
		}
		for (int[] x : o) {
			Arrays.fill(x, -2);
		}
		int v = min(n, a, n, a, k);
		// System.out.println(v);
		if (v == -1) {
			out.println(-1);
			out.println(0);
		} else {
			dp = new long[v + 1][n + 1][a + 1];
			for (long[][] x : dp) {
				for (long[] y : x) {
					Arrays.fill(y, -1);
				}
			}
			out.println(v);
			out.println(cal(0, n, a, n, a, k, v));
		}
		out.close();
	}

	public static long cal(int index, int left, int a, int n, int f, int k,
			int total) {
		if (index > total) {
			return 0;
		}

		if (dp[index][left][a] != -1) {
			return dp[index][left][a];
		}
		if (index + 1 == total) {
			if (a * 50 + (left - a) * 100 <= k) {
				return 1;
			}
			return 0;
		}
		long result = 0;
		for (int i = 0; i <= a && i * 50 <= k; i++) {
			for (int x = 0; x <= left - a && x * 100 + i * 50 <= k; x++) {

				int other = n - left + x + i;
				int nxt = f - a + i;
				if (x + i == 0) {
					continue;
				}
				// System.out.println(left + " " + a + " " + other + " " + nxt);
				if (left == x + i) {
					result += 1;
					// System.out.println("HE HE");
					result %= MOD;
					break;
				} else {
					long con = c[a][i] * c[left - a][x];
					con %= MOD;
					for (int j = 1; j <= other; j++) {
						for (int h = Math.min(j, nxt); h >= 0; h--) {
							if (h * 50 + (j - h) * 100 <= k
									&& other - nxt >= j - h) {

								long v = con
										* cal(index + 2, left - x - i + j, a
												- i + h, n, f, k, total);
								v %= MOD;

								v *= c[nxt][h] * c[other - nxt][j - h];
								v %= MOD;
								result += v;
								result %= MOD;
							} else {
								break;
							}
						}
					}
				}
			}
		}
		return dp[index][left][a] = result;
	}

	public static int min(int left, int a, int n, int f, int k) {
		// System.out.println(left + " " + a);
		if (left == 0) {
			return 0;
		}
		if (o[left][a] != -2) {
			return o[left][a];
		}
		int result = -1;
		for (int i = 0; i <= a && i * 50 <= k; i++) {
			int x = k - i * 50;
			x = x / 100;
			x = Math.min(left - a, x);
			int other = n - left + x + i;
			int nxt = f - a + i;
			if (left == x + i) {
				result = 1;
				break;
			} else {
				int tmp = Integer.MAX_VALUE;
				if (nxt > 0 && (x + i > 1 || x == 1)) {
					tmp = 2 + min(left - x - i + 1, a - i + 1, n, f, k);
				}
				if (other > 0 && other != nxt && x + i > 1) {
					tmp = Math.min(tmp,
							2 + min(left - x - i + 1, a - i, n, f, k));
				}
				if (tmp != Integer.MAX_VALUE && (result == -1 || result > tmp)) {
					result = tmp;
				}
			}
		}
		return o[left][a] = result;
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
