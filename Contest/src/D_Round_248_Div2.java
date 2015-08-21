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
public class D_Round_248_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		int z = in.nextInt();
		int[][] data = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				data[i][j] = in.nextInt();
			}
		}
		TreeSet<Point>[] hor = new TreeSet[n];
		TreeSet<Point>[] ver = new TreeSet[m];

		for (int i = 0; i < n; i++) {
			int start = -1;
			hor[i] = new TreeSet();
			for (int j = 0; j < m; j++) {
				if (data[i][j] == 1) {
					if (start == -1) {
						start = j;
					}
				} else if (start != -1) {
					hor[i].add(new Point(start, j - 1));
					start = -1;
				}
			}
			if (start != -1) {
				hor[i].add(new Point(start, m - 1));
			}
		}
		for (int i = 0; i < m; i++) {
			int start = -1;
			ver[i] = new TreeSet();
			for (int j = 0; j < n; j++) {
				if (data[j][i] == 1) {
					if (start == -1) {
						start = j;
					}
				} else if (start != -1) {
					ver[i].add(new Point(start, j - 1));
					start = -1;
				}
			}
			if (start != -1) {
				ver[i].add(new Point(start, n - 1));
			}

		}

		for (int i = 0; i < z; i++) {
			int op = in.nextInt();
			int x = in.nextInt() - 1;
			int y = in.nextInt() - 1;
			if (op == 1) {
				data[x][y] = 1 - data[x][y];

				if (data[x][y] == 1) {
					Point p = hor[x].floor(new Point(y, y));
					if (p != null && p.y + 1 == y) {

						hor[x].remove(p);
						p.y = y;
						Point q = hor[x].ceiling(new Point(y, y));
						if (q != null && q.x == y + 1) {
							hor[x].remove(q);
							p.y = q.y;
						}
						hor[x].add(p);

					} else {
						Point q = hor[x].ceiling(new Point(y, y));
						if (q != null && q.x == y + 1) {
							hor[x].remove(q);
							q.x = y;
							hor[x].add(q);
						} else if (q == null || q.x > y + 1) {
							hor[x].add(new Point(y, y));
						}
					}
					p = ver[y].floor(new Point(x, x));
					if (p != null && p.y + 1 == x) {

						ver[y].remove(p);
						p.y = x;
						Point q = ver[y].ceiling(new Point(x, x));
						if (q != null && q.x == x + 1) {
							ver[y].remove(q);
							p.y = q.y;
						}
						ver[y].add(p);

					} else {
						Point q = ver[y].ceiling(new Point(x, x));
						if (q != null && q.x == x + 1) {
							ver[y].remove(q);
							q.x = x;
							ver[y].add(q);
						} else if (q == null || q.x > x + 1) {
							ver[y].add(new Point(x, x));
						}
					}

				} else {
					Point p = hor[x].floor(new Point(y, y));
					hor[x].remove(p);

					if (p.y >= y + 1) {
						hor[x].add(new Point(y + 1, p.y));
					}
					if (p.x <= y - 1) {
						p.y = y - 1;
						hor[x].add(p);
					}
					p = ver[y].floor(new Point(x, x));

					ver[y].remove(p);

					if (p.y >= x + 1) {
						ver[y].add(new Point(x + 1, p.y));
					}
					if (p.x <= x - 1) {
						p.y = x - 1;
						ver[y].add(p);
					}
				}
				// System.out.println(x + " " + y + " " + ver[y]);
			} else {

				int result = 0;
				Point tmp = new Point(y, y);
				Point first = hor[x].floor(tmp);
				if (first == null || first.y < y) {

				} else {
					int s = first.x;
					int e = first.y;

					for (int j = x; j < n; j++) {
						Point p = hor[j].floor(tmp);
						if (p == null || p.y < y) {
							break;
						}
						s = Math.max(p.x, s);
						e = Math.min(p.y, e);
						result = Math.max(result, (e - s + 1) * (j - x + 1));
					}
					s = first.x;
					e = first.y;
					for (int j = x; j >= 0; j--) {
						Point p = hor[j].floor(tmp);
						if (p == null || p.y < y) {
							break;
						}
						s = Math.max(p.x, s);
						e = Math.min(p.y, e);
						result = Math.max(result, (e - s + 1) * (x - j + 1));
					}
					tmp = new Point(x, x);
					first = ver[y].floor(tmp);
					s = first.x;
					e = first.y;
					for (int j = y; j < m; j++) {
						Point p = ver[j].floor(tmp);
						if (p == null || p.y < x) {
							break;
						}
						s = Math.max(p.x, s);
						e = Math.min(p.y, e);
						result = Math.max(result, (e - s + 1) * (j - y + 1));
					}
					s = first.x;
					e = first.y;
					for (int j = y; j >= 0; j--) {
						Point p = ver[j].floor(tmp);
						if (p == null || p.y < x) {
							break;
						}
						s = Math.max(p.x, s);
						e = Math.min(p.y, e);
						result = Math.max(result, (e - s + 1) * (y - j + 1));
					}
				}
				out.println(result);
			}

		}
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
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
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
