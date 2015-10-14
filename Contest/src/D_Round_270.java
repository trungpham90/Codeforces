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
public class D_Round_270 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		long[][] data = new long[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				data[i][j] = in.nextInt();
			}
		}
		boolean ok = true;
		for (int i = 0; i < n && ok; i++) {
			for (int j = 0; j < n && ok; j++) {
				if (i == j) {
					ok &= data[i][j] == 0;
				} else {
					ok &= (data[i][j] == data[j][i] && data[i][j] > 0);
				}
			}
		}
		if (!ok) {
			out.println("NO");
		} else {
			PriorityQueue<Point> q = new PriorityQueue<>();
			long[] dist = new long[n];
			int[] pa = new int[n];
			Arrays.fill(dist, Long.MAX_VALUE);
			dist[0] = 0;
			q.add(new Point(0, 0));
			while (!q.isEmpty()) {
				Point p = q.poll();
				if (p.x == dist[p.y]) {
					for (int i = 0; i < n; i++) {
						if (dist[i] > dist[p.y] + data[p.y][i]) {
							dist[i] = dist[p.y] + data[p.y][i];
							q.add(new Point(dist[i], i));
							pa[i] = p.y;
						}else if(i != p.y && dist[i] == dist[p.y] + data[p.y][i]){
							pa[i] = p.y;
						}
					}
				}
			}

			ArrayList<Integer>[] map = new ArrayList[n];
			int[] union = new int[n];
			for (int i = 0; i < n; i++) {
				
				map[i] = new ArrayList();
			}
			for (int i = 1; i < n; i++) {
				map[pa[i]].add(i);
			}
			//System.out.println(Arrays.toString(map));
			int[]c = new int[n];
			boolean v = check(0, n, dist, union, c, data, map);
			for(int i : c){
				v &= (i == 1);
			}
			if (v) {
				out.println("YES");
			} else {
				out.println("NO");
			}

		}
		out.close();
	}

	static boolean check(int index, int n, long[] dist, int[] union, int[] c,
			long[][] data, ArrayList<Integer>[] map) {
		union[index] = index;
		
		boolean result = true;
		for (int i : map[index]) {
			result &= check(i, n, dist, union, c, data, map);
			union[find(i, union)] = index;
			if (!result) {
				break;
			}
		}
		if (result) {
			c[index] = 1;
			for (int i = 0; i < n; i++) {
				if (c[i] == 1 && i != index) {
					int v = find(i, union);
					long d = dist[i] + dist[index] - 2L * dist[v];
					// System.out.println(v + " " + i + " " + index + " " + d);
					if (data[i][index] != d) {
						if (n == 1139) {
							System.out.println(v + " " + i + " " + index + " "
									+ d + " " + data[i][index] + " " + dist[v] + " " + dist[i] + " " + data[i][v] + " " + dist[index] + " " +data[index][v] );
						}
						result = false;
						break;
					}
				}
			}
		}
		return result;
	}

	static int find(int index, int[] u) {
		if (index != u[index]) {
			return u[index] = find(u[index], u);
		}
		return index;
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

		long x;
		int y;

		public Point(long start, int end) {
			this.x = start;
			this.y = end;
		}

		@Override
		public int compareTo(Point o) {
			return Long.compare(x, o.x);
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
