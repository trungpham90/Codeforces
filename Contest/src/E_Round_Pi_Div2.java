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
public class E_Round_Pi_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		int s = in.nextInt() - 1;
		int t = in.nextInt() - 1;
		ArrayList<Integer>[] from = new ArrayList[n];
		ArrayList<Integer>[] to = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			from[i] = new ArrayList();
			to[i] = new ArrayList();
		}
		int[] a = new int[m];
		int[] b = new int[m];
		long[] c = new long[m];
		for (int i = 0; i < m; i++) {
			a[i] = in.nextInt() - 1;
			b[i] = in.nextInt() - 1;
			c[i] = in.nextInt();
			from[a[i]].add(i);
			to[b[i]].add(i);
		}

		long[] dist = new long[n];
		Arrays.fill(dist, -1);
		dist[s] = 0;
		PriorityQueue<Point> q = new PriorityQueue<>(n,
				new Comparator<Point>() {
					@Override
					public int compare(Point o1, Point o2) {
						return Long.compare(o1.y, o2.y);
					}
				});
		q.add(new Point(s, 0));

		while (!q.isEmpty()) {
			Point p = q.poll();
			if (dist[p.x] == p.y) {
				for (int i : from[p.x]) {
					long x = dist[p.x] + c[i];
					if (dist[b[i]] == -1 || dist[b[i]] > x) {
						dist[b[i]] = x;
						q.add(new Point(b[i], x));
					}
				}
			}
		}
		long[] dist2 = new long[n];
		Arrays.fill(dist2, -1);
		dist2[t] = 0;
		q.add(new Point(t, 0));
		while (!q.isEmpty()) {
			Point p = q.poll();
			if (dist2[p.x] == p.y) {
				for (int i : to[p.x]) {
					long x = dist2[p.x] + c[i];
					if (dist2[a[i]] == -1 || dist2[a[i]] > x) {
						dist2[a[i]] = x;
						q.add(new Point(a[i], x));
					}
				}
			}
		}
		boolean[] have = new boolean[n];
		boolean[] join = new boolean[n];
		join[s] = true;
		boolean[] check = new boolean[n];
		q.add(new Point(s, 0));
		check[s] = true;
		while (!q.isEmpty()) {
			int node = q.poll().x;
			if (q.isEmpty()) {
				join[node] = true;
			}
			int count = 0;
			int last = -1;
			for (int i : from[node]) {
				if (dist[node] + dist2[b[i]] + c[i] == dist[t]
						&& dist2[b[i]] != -1) {
					count++;
					last = b[i];
					if (!check[b[i]]) {
						check[b[i]] = true;
						q.add(new Point(b[i], dist[b[i]]));
					}
				}
			}
			if (count == 1 && join[node]) {
				have[last] = true;
			}
		}

		// System.out.println(Arrays.toString(have));
		long[] result = new long[m];
		for (int i = 0; i < m; i++) {
			if (dist[a[i]] != -1 && dist2[b[i]] != -1) {
				long x = dist[a[i]];
				long y = dist2[b[i]];

				if (x + y + c[i] > dist[t]) {
					long cost = dist[t] - x - y;
					if (cost >= 0 && c[i] - cost + 1 < c[i]) {
						result[i] = c[i] - cost + 1;
					} else {
						result[i] = -1;
					}
				} else if (x + y + c[i] == dist[t] && !have[b[i]]) {
					if (c[i] > 1) {
						result[i] = 1;
					} else {
						result[i] = -1;
					}
				}
			} else {
				result[i] = -1;
			}
			if (result[i] > 0) {
				out.println("CAN " + result[i]);
			} else if (result[i] == 0) {
				out.println("YES");
			} else {
				out.println("NO");
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

		int x;
		long y;

		public Point(int start, long end) {
			this.x = start;
			this.y = end;
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
