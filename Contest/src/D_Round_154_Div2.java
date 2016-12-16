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
public class D_Round_154_Div2 {

	public static long MOD = 1000000007;

	/*
	 * 5 4 13 cbbb bbbb cccb ccbc bcbb
	 */

	public static void main(String[] args) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(new FileOutputStream(new File(
				"output.txt")));
		// PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		int r = in.nextInt();
		char[][] data = new char[n][m];
		for (int i = 0; i < n; i++) {
			data[i] = in.next().toCharArray();
		}
		int[][] count = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				count[i][j] = (i > 0 ? count[i - 1][j] : 0);

				if (data[i][j] == 'a') {
					count[i][j]++;
				}

			}
		}

		long result = 0;
		int[][] tree = new int[26][m];
		int[] st = new int[26];
		int[] ed = new int[26];
		for (int i = 1; i < n; i++) {
			for (int k = 2; i - k + 1 >= 0; k++) {
				int re = 0;

				int val = count[i][0] - (i >= k ? count[i - k][0] : 0);

				Arrays.fill(st, 0);
				Arrays.fill(ed, 0);
				if (data[i][0] == data[i - k + 1][0]) {
					tree[data[i][0] - 'a'][ed[data[i][0] - 'a']++] = 0;
				}

				for (int j = 1; j < m; j++) {
					val += count[i][j] - (i >= k ? count[i - k][j] : 0);

					if (data[i][j] == data[i - k + 1][j]) {

						while (val > r) {
							val -= count[i][re]
									- (i - k >= 0 ? count[i - k][re] : 0);
							re++;
						}
						int index = data[i][j] - 'a';
						tree[index][ed[index]++] = j;
						if (re < j) {
							while (st[index] < ed[index]
									&& tree[index][st[index]] < re) {
								st[index]++;
							}
							result += ed[index] - st[index] - 1;
						}
					}
				}
			}
		}
		out.println(result);

		out.close();
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

		int[] data;

		FT(int n) {
			data = new int[n];
		}

		public void update(int index, int value) {
			while (index < data.length) {
				data[index] += value;
				index += (index & (-index));
			}
		}

		public int get(int index) {
			int result = 0;
			while (index > 0) {
				result += data[index];
				index -= (index & (-index));
			}
			return result;

		}

		public void clear() {
			Arrays.fill(data, 0);
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
			// br = new BufferedReader(new InputStreamReader(System.in));
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					new File("input.txt"))));
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
