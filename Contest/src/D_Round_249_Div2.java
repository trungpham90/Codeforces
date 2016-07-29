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
public class D_Round_249_Div2 {

	public static long MOD = 1000000007;
	static int[] X = { 1, 1, -1, -1 };
	static int[] Y = { -1, 1, 1, -1 };

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		String[] data = new String[n];
		for (int i = 0; i < n; i++) {
			data[i] = in.next();
		}
		int[][][] type = new int[n][m][4];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int h = 0; h < 4; h++) {
					for (int k = 0; k <= Math.min(n, m); k++) {
						int x = i + k * X[h];
						int y = j + k * Y[h];
						if (x >= 0 && y >= 0 && x < n && y < m
								&& data[x].charAt(y) == '0') {
							type[i][j][h]++;
						} else {
							break;
						}
					}
				}
			}
		}

		int[] map = { 1, 0, 3, 2 };
		long result = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (data[i].charAt(j) == '0') {
					for (int h = 0; h < 4; h++) {
						for (int k = 1; k < Math.min(n, m); k++) {
							int x1 = i + k * X[map[h]];
							int y1 = j;
							int x2 = i;
							int y2 = j + k * Y[map[h]];
							if (x1 >= 0 && x2 >= 0 && y1 >= 0 && y2 >= 0
									&& x1 < n && x2 < n && y1 < m && y2 < m
									&& data[x1].charAt(y1) == '0'
									&& data[x2].charAt(y2) == '0') {
								if (type[x2][y2][h] >= k + 1) {

									result++;
								}
							} else {
								break;
							}
						}
					}
					for (int k = 1; k < Math.min(n, m); k++) {
						int x1 = i + k;
						int x2 = i - k;
						if (x1 >= 0 && x2 >= 0 && x1 < n && x2 < n
								&& data[x1].charAt(j) == '0'
								&& data[x2].charAt(j) == '0') {
							if (j + k < m && data[i].charAt(j + k) == '0') {
								if (type[i][j + k][0] >= k + 1
										&& type[i][j + k][3] >= k + 1) {
									result++;
								}
							}
							if (j - k >= 0 && data[i].charAt(j - k) == '0') {
								if (type[i][j - k][1] >= k + 1
										&& type[i][j - k][2] >= k + 1) {
									result++;
								}
							}
						} else {
							break;
						}
					}
					for (int k = 1; k < Math.min(n, m); k++) {
						int y1 = j + k;
						int y2 = j - k;
						if (y1 >= 0 && y2 >= 0 && y1 < m && y2 < m
								&& data[i].charAt(y1) == '0'
								&& data[i].charAt(y2) == '0') {
							if (i + k < n && data[i + k].charAt(j) == '0') {
								if (type[i + k][j][2] >= k + 1
										&& type[i + k][j][3] >= k + 1) {
									result++;
								}
							}
							if (i - k >= 0 && data[i - k].charAt(j) == '0') {
								if (type[i - k][j][0] >= k + 1
										&& type[i - k][j][1] >= k + 1) {
									result++;
								}
							}
						} else {
							break;
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
