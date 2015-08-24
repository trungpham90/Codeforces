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
public class B_Round_317_Div1 {

	public static long MOD = 1000000007;
	static int[][] dp;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int k = in.nextInt();
		int[] data = new int[n];
		long re = 0;
		for (int i = 0; i < n; i++) {
			data[i] = in.nextInt();

		}
		Arrays.sort(data);

		boolean[] check = new boolean[n];
		int total = 0;
		ArrayList<Integer> list = new ArrayList();

		for (int i = 0; i < n; i++) {
			if (!check[i]) {
				int c = 0;
				for (int j = i; j < n; j += k) {
					c++;
					check[j] = true;
				}
				if (c > 1) {
					total += c;
					list.add(c);
				}
			}
		}
		// System.out.println(list);
		int start = -1;
		for (int i = total - 1; i < n; i++) {
			int v = data[i] - data[i - total + 1];
			if (start == -1 || v < data[start] - data[start - total + 1]) {
				start = i;

			}
		}
		re = data[start] - data[start - total + 1];
		if (list.size() > 1) {
			int a = list.get(0);
			int b = list.get(list.size() - 1);
			int c = 0, d = 0;
			if (a != b) {
				d = list.size();
				for (int i : list) {
					if (i == a) {
						c++;
						d--;
					}
				}
			} else {
				c = list.size();
			}
			dp = new int[c + 1][d + 1];
			for (int[] x : dp) {
				Arrays.fill(x, -1);
			}
			re -= max(c, d, a, b, start - total + 1, start, data);
		}
		out.println(re);

		out.close();
	}

	public static int max(int a, int b, int l, int m, int index, int end,
			int[] data) {
		if (index >= end) {
			return 0;
		}
		if (dp[a][b] != -1) {
			return dp[a][b];
		}
		int result = 0;
		if (a > 0) {
			int nxt = index + l;
			if (nxt < end) {
				result += data[nxt] - data[nxt - 1];
			}
			result += max(a - 1, b, l, m, nxt, end, data);
		}
		if (b > 0) {
			int nxt = index + m;
			int v = 0;
			if (nxt < end) {
				v += data[nxt] - data[nxt - 1];
			}
			v += max(a, b - 1, l, m, nxt, end, data);
			if (result < v) {
				result = v;
			}
		}
		return dp[a][b] = result;

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
