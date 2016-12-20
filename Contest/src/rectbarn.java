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

/*
 ID: trungph2
 LANG: JAVA
 TASK: rectbarn
 */
public class rectbarn {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(new FileOutputStream(new File(
				"rectbarn.out")));
		// PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int r = in.nextInt();
		int c = in.nextInt();
		int p = in.nextInt();
		short[][] data = new short[p + r][2];
		for (int i = 0; i < p; i++) {
			data[i] = new short[] { (short) (in.nextInt() - 1),
					(short) (in.nextInt() - 1) };
		}
		for (int i = p; i < p + r; i++) {
			data[i] = new short[] { (short) (i - p), (short) (-1) };
		}
		short[][] up = new short[c][r];
		short[][] down = new short[c][r];
		for (short[] a : up) {
			Arrays.fill(a, (short) -1);
		}
		for (short[] a : down) {
			Arrays.fill(a, (short) -1);
		}
		for (int i = 0; i < p; i++) {

			up[data[i][1]][data[i][0]] = (short) data[i][0];
			down[data[i][1]][data[i][0]] = (short) data[i][0];
		}
		for (int i = 0; i < c; i++) {
			short re = -1;
			for (int j = 0; j < r; j++) {
				if (up[i][j] != j) {
					up[i][j] = re;
				}
				re = up[i][j];
			}
			re = -1;
			for (int j = r - 1; j >= 0; j--) {
				if (down[i][j] != j) {
					down[i][j] = re;
				}
				re = down[i][j];
			}
		}
		out.println(cal(r, c, data, up, down));
		out.close();
	}

	static int cal(int r, int c, short[][] data, short[][] up, short[][] down) {
		int result = 0;
		for (short[] p : data) {
			int t = -1;
			int d = -1;

			boolean stop = false;
			for (int i = p[1] + 1; i < c; i++) {

				int a = up[i][p[0]];
				int b = down[i][p[0]];
				// System.out.println(p.x + " " + p.y + " " + t + " " + d + " "
				// + i + " " + result + " " + a + " " + b);
				if (t != -1 && d != -1) {

					if (a != -1 && b != -1) {

						if (a > t || b < d) {
							int l = i - p[1] - 1;
							result = Math.max(result, l * (d - t - 1));
							if (a == p[0]) {
								stop = true;
								break;
							} else {
								t = Math.max(t, a);
								d = Math.min(d, b);
							}
						}
					} else if (a != -1 && a > t) {
						int l = i - p[1] - 1;
						result = Math.max(result, l * (d - t - 1));

						t = a;
					} else if (b != -1 && b < d) {
						int l = i - p[1] - 1;
						result = Math.max(result, l * (d - t - 1));
						d = b;
					}
				} else if (d != -1) {
					if (a != -1 && b != -1) {
						int l = i - p[1] - 1;
						result = Math.max(result, l * d);
						if (a == p[0]) {
							stop = true;
							break;
						} else {
							t = a;
							d = Math.min(d, b);
						}
					} else if (a != -1) {
						int l = i - p[1] - 1;
						result = Math.max(result, l * d);
						t = a;
					} else if (b != -1 && b < d) {
						int l = i - p[1] - 1;
						result = Math.max(result, l * d);
						d = b;
					}
				} else if (t != -1) {
					if (a != -1 && b != -1) {
						int l = i - p[1] - 1;
						result = Math.max(result, l * (r - t - 1));
						if (a == p[0]) {
							stop = true;
							break;
						} else {
							t = Math.max(a, t);
							d = b;
						}
					} else if (a != -1 && a > t) {
						int l = i - p[1] - 1;
						result = Math.max(result, l * (r - t - 1));

						t = a;
					} else if (b != -1) {
						int l = i - p[1] - 1;
						result = Math.max(result, l * (r - t - 1));
						d = b;
					}
				} else {

					if (a != -1 && b != -1) {
						int l = i - p[1] - 1;
						result = Math.max(result, l * r);
						if (a == p[0]) {
							stop = true;
							break;
						} else {
							t = a;
							d = b;
						}
					} else if (a != -1) {
						int l = i - p[1] - 1;
						result = Math.max(result, l * r);
						t = a;
					} else if (b != -1) {
						int l = i - p[1] - 1;
						result = Math.max(result, l * r);
						d = b;
					}

				}

			}
			if (!stop) {
				int l = c - p[1] - 1;
				if (t != -1 && d != -1) {
					result = Math.max(result, l * (d - t - 1));
				} else if (d != -1) {
					result = Math.max(result, l * d);
				} else if (t != -1) {
					result = Math.max(result, l * (r - t - 1));
				} else {
					result = Math.max(result, l * r);
				}
			}
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
			// br = new BufferedReader(new InputStreamReader(System.in));
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					new File("rectbarn.in"))));
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
