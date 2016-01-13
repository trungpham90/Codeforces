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
public class A_GCJ_APAC_2016_Round2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(new FileOutputStream(new File(
				"output.txt")));
		// PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int t = in.nextInt();
		for (int z = 0; z < t; z++) {
			int n = in.nextInt();
			int m = in.nextInt();
			int k = in.nextInt();
			int[][] edge = new int[2][m];
			int[][] cost = new int[24][m];
			ArrayList<Integer>[] map = new ArrayList[n];
			for (int i = 0; i < n; i++) {
				map[i] = new ArrayList();
			}
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < 2; j++) {
					edge[j][i] = in.nextInt() - 1;
					map[edge[j][i]].add(i);
				}
				for (int j = 0; j < 24; j++) {
					cost[j][i] = in.nextInt();
				}
			}
			int[][] dist = new int[24][n];
			for (int[] a : dist) {

				Arrays.fill(a, -2);

			}
			for (int s = 0; s < 24; s++) {
				int[][] tmp = new int[24][n];
				for (int[] a : tmp) {
					Arrays.fill(a, Integer.MAX_VALUE);
				}
				Arrays.fill(dist[s], -1);
				tmp[s][0] = 0;
				PriorityQueue<State> q = new PriorityQueue<>();
				q.add(new State(0, s, 0));
				while (!q.isEmpty()) {
					State cur = q.poll();
					if (cur.d == tmp[cur.t][cur.p]) {
						for (int j : map[cur.p]) {
							int nxt = edge[0][j] == cur.p ? edge[1][j]
									: edge[0][j];

							for (int h = 0; h < 24; h++) {
								int time = (cur.t + h + cost[(cur.t + h) % 24][j]) % 24;
								if (tmp[time][nxt] > cur.d + h
										+ cost[(cur.t + h) % 24][j]) {
									tmp[time][nxt] = cur.d + h
											+ cost[(cur.t + h) % 24][j];
									q.add(new State(nxt, time, tmp[time][nxt]));
								}
							}
						}
					}
				}

				for (int j = 0; j < 24; j++) {
					for (int h = 0; h < n; h++) {
						if (tmp[j][h] != Integer.MAX_VALUE
								&& (tmp[j][h] < dist[s][h] || dist[s][h] == -1)) {
							dist[s][h] = tmp[j][h];
						}
					}

				}
			}
			out.print("Case #" + (z + 1) + ":");
			for (int i = 0; i < k; i++) {
				int d = in.nextInt() - 1;
				int s = in.nextInt();
				int result = dist[s][d];
				for(int j = 0; j < 24; j++){
					int time = (s + j) % 24;
					if(dist[time][d] != -1){
						result = Math.min(result, dist[time][d] + j);
					}
				}
				out.print(" " + result);
			}
			out.println();
		}
		out.close();
	}

	static class State implements Comparable<State> {
		int p, t, d;

		public State(int p, int t, int d) {
			super();
			this.p = p;
			this.t = t;
			this.d = d;
		}

		@Override
		public int compareTo(State o) {
			// TODO Auto-generated method stub
			return Integer.compare(d, o.d);
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
			// br = new BufferedReader(new InputStreamReader(System.in));
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					new File("A-large-practice.in"))));
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
