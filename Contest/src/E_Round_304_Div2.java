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
public class E_Round_304_Div2 {

	public static long MOD = 1000000007;
	static long min;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		int[] a = new int[n + 1];
		int[] b = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			a[i] = in.nextInt();
		}
		int need = 0;
		int dif = 0;
		for (int i = 1; i <= n; i++) {
			b[i] = in.nextInt();
			need +=  a[i];
			if (b[i] > a[i]) {
				
				dif += (b[i] - a[i]);
			} else {
				dif -= (a[i] - b[i]);
			}
		}

		Point[] data = new Point[m];
		for (int i = 0; i < m; i++) {
			data[i] = new Point(in.nextInt(), in.nextInt());
		}
		if (dif == 0) {
			int index = n + 1;
			int[] point = new int[n + 1];
			for (int i = 1; i <= n; i++) {
				point[i] = index++;
			}
			int[][] map = new int[2 + 2 * n][2 + 2 * n];
			int end = 2 * n + 1;
			for (int i = 1; i <= n; i++) {
				map[0][i] = a[i];
				map[i][point[i]] = 1000;
				
				map[point[i]][end] = b[i];
				
			}
			
			for (Point p : data) {
				map[p.x][point[p.y]] = 1000;
				
				map[p.y][point[p.x]] = 1000;
				
			}
			int max = 0;
			while (true) {
				int[] pa = new int[2 * n + 2];
				Arrays.fill(pa, -1);
				pa[0] = 0;
				LinkedList<Integer> q = new LinkedList();
				q.add(0);

				while (!q.isEmpty()) {
					int node = q.pollFirst();
					for (int i = 0; i < 2 * n + 2; i++) {
						if (map[node][i] > 0 && pa[i] == -1) {

							pa[i] = node;
							q.add(i);
						}
					}
				}
				if (pa[end] == -1) {
					break;
				}
				min = Integer.MAX_VALUE;
				augment(end, map, pa);
				if (min == 0) {
					break;
				}
				max += min;
			}
			//System.out.println(max);
			if (need == max) {
				out.println("YES");
				int[][] result = new int[n + 1][n + 1];
				int[] o = new int[n + 1];
				for (Point p : data) {
					if (map[p.x][point[p.y]] < 1000) {
						result[p.x][p.y] = 1000 - map[p.x][point[p.y]] ;
						o[p.x] += result[p.x][p.y];
					} 
					if (map[p.y][point[p.x]] < 1000) {
						result[p.y][p.x] = 1000 - map[p.y][point[p.x]] ;
						o[p.y] += result[p.y][p.x];
					}
				}
				for (int i = 1; i <= n; i++) {

					for (int j = 1; j <= n; j++) {
						if (i == j) {
							// System.out.println(o[i] + " " + a[i]);
							out.print((a[i] - o[i]) + " ");
						} else {
							out.print(result[i][j] + " ");
						}
					}
					out.println();
				}
			} else {
				out.println("NO");
			}
		} else {
			out.println("NO");
		}
		out.close();
	}

	public static void augment(int index, int[][] map, int[] pa) {
		if (pa[index] != index) {
			min = Math.min(min, map[pa[index]][index]);

			augment(pa[index], map, pa);
			map[pa[index]][index] -= min;
			map[index][pa[index]] += min;
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
