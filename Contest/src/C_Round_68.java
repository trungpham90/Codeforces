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
public class C_Round_68 {

	public static long MOD = 1000000007;
	static int[] X = { 1, -1, -1, 1 };
	static int[] Y = { 1, 1, -1, -1 };

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
//		int x = Math.max(n, m);
//		n = n + m;
//		m = x;
//		n = n - m;
		boolean[] top = new boolean[m];
		boolean[][] topDir = new boolean[m][4];
		int result = 0;
		for (int i = 0; i < m; i++) {
			if (!top[i]) {
				cal(0, i, n, m, top, topDir);
				result++;
			}
		}
		out.println(result);
		out.close();
	}

	static int getDir(int x, int y, int n, int m, int dir) {
		if (x + 1 == n) {
			if (dir == 0) {
				return 1;
			} else if (dir == 3) {
				return 2;
			}
		} else if (x == 0) {
			if (dir == 2) {
				return 3;
			} else if (dir == 1) {
				return 0;
			}
		}
		if (y + 1 == m) {
			if (dir == 1) {
				return 2;
			} else if (dir == 0) {
				return 3;
			}
		} else if (y == 0) {
			if (dir == 3) {
				return 0;
			} else if (dir == 2) {
				return 1;
			}
		}
		throw new IllegalArgumentException(x + " " + y + " " + dir);

	}

	static void cal(int x, int y, int n, int m, boolean[] top,
			boolean[][] topDir) {
		int dir = 0;
		top[y] = true;
		topDir[y][0] = true;
		//System.out.println("Start " + x + " " + y);
		while (true) {
			while (x + X[dir] < 0 || x + X[dir] >= n || y + Y[dir] < 0
					|| y + Y[dir] >= m) {
				dir = getDir(x, y, n, m, dir);
			}

			int step = calStep(x, y, n, m, dir);
			x += step * X[dir];
			y += step * Y[dir];
			//System.out.println(x + " " + y + " " + dir);
			if (x == 0) {
				if (topDir[y][dir]) {
					return;
				}
				top[y] = true;
				topDir[y][dir] = true;
			}
		}

	}

	static int calStep(int x, int y, int n, int m, int dir) {
		int result = 0;
		if (X[dir] > 0) {
			result = n - x - 1;
		} else {
			result = x;
		}
		if (Y[dir] > 0) {
			result = Math.min(result, m - y - 1);
		} else {
			result = Math.min(result, y);
		}
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
