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
public class D_Round_112_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		Point[] data = new Point[n - 1];
		ArrayList<Integer>[] map = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			map[i] = new ArrayList();
		}
		for (int i = 0; i < n - 1; i++) {
			data[i] = new Point(in.nextInt() - 1, in.nextInt() - 1);
			map[data[i].x].add(i);
			map[data[i].y].add(i);
		}
		int root = -1;
		int[] union = new int[n];
		boolean[] leaves = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (root == -1 || map[i].size() > map[root].size()) {
				root = i;
			}
			union[i] = i;
			leaves[i] = map[i].size() == 1;
		}
		leaves[root] = false;
		LinkedList<Integer> q = new LinkedList();
		q.add(root);
		int[] level = new int[n];
		Arrays.fill(level, -1);
		level[root] = 0;

		while (!q.isEmpty()) {
			int node = q.poll();
			for (int i : map[node]) {
				int nxt = data[i].x == node ? data[i].y : data[i].x;
				if (level[nxt] == -1) {
					level[nxt] = 1 + level[node];
					q.add(nxt);
					if (node != root) {
						union[find(node, union)] = union[nxt];
					}
				}
			}
		}
		int m = in.nextInt();
		FT[] tree = new FT[n];
		for (int i = 0; i < n; i++) {
			if (leaves[i]) {
				tree[i] = new FT(level[i] + 1);
				for (int j = 1; j <= level[i]; j++) {
					tree[i].update(j, 1);
				}
			}
		}
		for (int i = 0; i < m; i++) {
			int t = in.nextInt();
			if (t == 1) {
				int index = in.nextInt() - 1;
				int end = level[data[index].x] > level[data[index].y] ? data[index].x
						: data[index].y;
				int leaf = find(end, union);
				tree[leaf].update(level[end], -1);
			} else if (t == 2) {
				int index = in.nextInt() - 1;
				int end = level[data[index].x] > level[data[index].y] ? data[index].x
						: data[index].y;
				int leaf = find(end, union);
				tree[leaf].update(level[end], +1);
			} else {
				int a = in.nextInt() - 1;
				int b = in.nextInt() - 1;
				if (a == b) {
					out.println(0);
				} else if (a == root) {
					int x = find(b, union);
					if (tree[x].get(level[b]) != level[b]) {
						out.println(-1);
					} else {
						out.println(level[b]);
					}
				} else if (b == root) {
					int x = find(a, union);
					if (tree[x].get(level[a]) != level[a]) {
						out.println(-1);
					} else {
						out.println(level[a]);
					}
				} else {
					int x = find(a, union);
					int y = find(b, union);
					if (x != y) {
						long total = tree[x].get(level[a])
								+ tree[y].get(level[b]);
						if (total == level[a] + level[b]) {
							out.println(total);
						} else {
							out.println(-1);
						}
					} else {
						long minus = abs(tree[x].get(level[a])
								- tree[x].get(level[b]));
						if (minus != abs(level[a] - level[b])) {
							out.println(-1);
						} else {
							out.println(minus);
						}
					}
				}

			}
		}
		out.close();
	}

	static long abs(long v) {
		return v < 0 ? -v : v;
	}

	static int find(int index, int[] union) {
		return union[index] = (index == union[index] ? index : find(
				union[index], union));
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
