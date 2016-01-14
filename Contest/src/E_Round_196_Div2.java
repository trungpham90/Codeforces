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
public class E_Round_196_Div2 {

	public static long MOD = 1000000007;
	static int[][] dp;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		long[] data = new long[n];
		for (int i = 0; i < n; i++) {
			data[i] = in.nextLong();
		}

		if (n > 1) {
			dp = new int[1 << n][1 << n];
			for (int[] a : dp) {
				Arrays.fill(a, -2);
			}
			int result = Integer.MAX_VALUE;
			for (int i = 1; i < (1 << n); i++) {
				int v = cal(i, i, n, data);
				// System.out.println(Integer.toBinaryString(i) + " " + (v));
				if (v != -1)
					result = Math.min(result, v + (Integer.bitCount(i) ==1 ? 0 : 1));
			}
			out.println(result);
		} else {
			out.println(count(data[0], true) + 1);
		}
		out.close();
	}

	public static int cal(int mask, int child, int n, long[] data) {
		if (dp[mask][child] != -2) {
			return dp[mask][child];
		}
		if (Integer.bitCount(mask) == n) {
			int result = 0;
			for (int i = 0; i < n; i++) {
				if (((1 << i) & child) != 0) {
					result += count(data[i] , true) + 1;
				}
			}
			return dp[mask][child] = result;
		}
		if (Integer.bitCount(child) == 0) {
			return -1;
		}
		int result = -1;
		for (int i = 0; i < n; i++) {
			if (((1 << i) & child) != 0) {
				int v = cal(mask, child ^ (1 << i), n, data);
				if (v != -1) {

					result = v + count(data[i], true) + 1;
				}
				for (int j = 1; j < (1 << n); j++) {
					if ((~mask & j) == j) {
						long other = canMatch(j, i, data);
						if (other != -1) {
							int tmp = cal(mask | j, (child | j) ^ (1 << i), n,
									data);
							if (tmp != -1) {
								tmp += count(other, false) + 1;
								if (result == -1 || result > tmp) {
									result = tmp;
								}
							}
						}
					}
				}
				break;
			}
		}
		return dp[mask][child] = result;
	}

	public static int count(long v, boolean direct) {
		if (v == 1) {
			return 0;
		}
		int c = 0;
		boolean prime = true;
		for (long i = 2; i * i <= v; i++) {
			while (v % i == 0) {
				prime = false;
				v /= i;
				c++;
			}
		}
		if (prime) {
			return direct ? 0 : 1;
		}
		return (v == 1 ? c : c + 1);
	}

	public static long canMatch(int mask, int index, long[] data) {
		long cur = data[index];
		for (int j = 0; j < data.length; j++) {
			if (((1 << j) & mask) != 0) {
				if (cur % data[j] != 0) {
					return -1;
				}
				cur /= data[j];
			}
		}
		return cur;
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
