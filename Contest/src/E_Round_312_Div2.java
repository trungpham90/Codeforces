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
public class E_Round_312_Div2 {

	public static long MOD = 1000000007;
	static int[][] node;
	static boolean[][] clear;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int q = in.nextInt();
		String line = in.next();

		node = new int[26][4 * n + 6];
		clear = new boolean[26][4 * n + 6];

		for (int i = 0; i < n; i++) {
			int index = line.charAt(i) - 'a';
			update(i, i, 0, n - 1, index, 0, 1, 0, false);
		}
		for (int i = 0; i < q; i++) {
			int l = in.nextInt() - 1;
			int r = in.nextInt() - 1;
			int v = in.nextInt();
			int[] count = new int[26];
			for (int j = 0; j < 26; j++) {
				count[j] = get(l, r, 0, n - 1, j, 0, 0, false);
				clear(l, r, 0, n - 1, j, 0, 0, false);
			}
			// System.out.println(Arrays.toString(count) + " " + l + " " + r);
			if (v == 1) {
				int start = l;
				int k = 0;
				while (start <= r) {
					for (int j = k; j < 26; j++) {
						if (count[j] > 0) {
							// System.out.println(i + " " + start);
							update(start, start + count[j] - 1, 0, n - 1, j, 0,
									count[j], 0, false);
							start += count[j];
							count[j] = 0;
							k = j + 1;
							break;
						}
					}
				}
			} else {
				int end = r;
				int k = 0;
				while (end >= l) {
					for (int j = k; j < 26; j++) {
						if (count[j] > 0) {
							// System.out.println("Inverse " + end + " " + i);
							update(end - count[j] + 1, end, 0, n - 1, j, 0,
									count[j], 0, false);
							end -= count[j];
							count[j] = 0;
							k = j + 1;
							break;
						}
					}
				}
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 26; j++) {
				if (get(i, i, 0, n - 1, j, 0, 0, false) == 1) {
					out.print((char) (j + 'a'));
					break;
				}
			}
		}

		out.close();
	}

	static int left(int index) {
		return 2 * index + 1;
	}

	static int right(int index) {
		return 2 * index + 2;
	}

	static int clear(int l, int r, int l1, int r1, int c, int index, int other,
			boolean update) {
		if (l1 > r || r1 < l) {
			if (update) {
				clear[c][index] = true;
				node[c][index] = other;
			}
			return node[c][index];
		}
		if (l <= l1 && r1 <= r) {

			clear[c][index] = true;
			node[c][index] = 0;

			return node[c][index];
		}

		int mid = (l1 + r1) >> 1;

		if (update) {
			clear[c][index] = false;
			node[c][index] = 0;
		}

		if (clear[c][index]) {
			clear[c][index] = false;
			int total = node[c][index] + other;
			int x = total * (mid - l1 + 1) / (r1 - l1 + 1);
			int y = total * (r1 - mid) / (r1 - l1 + 1);
			int a = clear(l, r, l1, mid, c, left(index), x, true);
			int b = clear(l, r, mid + 1, r1, c, right(index), y, true);
			node[c][index] = a + b;
			return node[c][index];
		} else {
			int total = other;
			int x = total * (mid - l1 + 1) / (r1 - l1 + 1);
			int y = total * (r1 - mid) / (r1 - l1 + 1);
			int a = clear(l, r, l1, mid, c, left(index), x, update);
			int b = clear(l, r, mid + 1, r1, c, right(index), y, update);
			node[c][index] = a + b;
			return node[c][index];
		}

	}

	static int get(int l, int r, int l1, int r1, int c, int index, int other,
			boolean update) {
		if (l1 > r || r1 < l) {
			if (update) {
				clear[c][index] = true;
				node[c][index] = other;
			}
			return 0;
		}
		if (l <= l1 && r1 <= r) {
			if (update) {
				clear[c][index] = true;
				node[c][index] = other;
			}

			return node[c][index];
		}
		int mid = (l1 + r1) >> 1;

		if (update) {
			clear[c][index] = false;
			node[c][index] = 0;
		}

		if (clear[c][index]) {
			clear[c][index] = false;
			int total = node[c][index] + other;
			int x = total * (mid - l1 + 1) / (r1 - l1 + 1);
			int y = total * (r1 - mid) / (r1 - l1 + 1);
			int a = get(l, r, l1, mid, c, left(index), x, true);
			int b = get(l, r, mid + 1, r1, c, right(index), y, true);

			node[c][index] = node[c][left(index)] + node[c][right(index)];
			return a + b;
		} else {
			int total = other;
			int x = total * (mid - l1 + 1) / (r1 - l1 + 1);
			int y = total * (r1 - mid) / (r1 - l1 + 1);
			int a = get(l, r, l1, mid, c, left(index), x, update);
			int b = get(l, r, mid + 1, r1, c, right(index), y, update);

			node[c][index] = node[c][left(index)] + node[c][right(index)];
			return a + b;
		}

	}

	static int update(int l, int r, int l1, int r1, int c, int index, int v,
			int other, boolean update) {
		if (l1 > r || r1 < l) {
			if (update) {
				clear[c][index] = true;
				node[c][index] = other;
			}
			return node[c][index];
		}
		if (l <= l1 && r1 <= r) {
			if (update) {
				clear[c][index] = true;
				node[c][index] = other;
			}
			node[c][index] += v * (r1 - l1 + 1) / (r - l + 1);
			return node[c][index];
		}
		int mid = (l1 + r1) >> 1;

		if (update) {
			clear[c][index] = false;
			node[c][index] = 0;
		}
		if (clear[c][index]) {
			clear[c][index] = false;
			int total = node[c][index] + other;
			int x = total * (mid - l1 + 1) / (r1 - l1 + 1);
			int y = total * (r1 - mid) / (r1 - l1 + 1);
			int a = update(l, r, l1, mid, c, left(index), v, x, true);
			int b = update(l, r, mid + 1, r1, c, right(index), v, y, true);
			node[c][index] = a + b;
			return node[c][index];
		} else {
			int total = other;
			int x = total * (mid - l1 + 1) / (r1 - l1 + 1);
			int y = total * (r1 - mid) / (r1 - l1 + 1);
			int a = update(l, r, l1, mid, c, left(index), v, x, update);
			int b = update(l, r, mid + 1, r1, c, right(index), v, y, update);
			node[c][index] = a + b;
			return node[c][index];
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
