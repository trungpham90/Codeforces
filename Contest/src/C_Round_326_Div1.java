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
public class C_Round_326_Div1 {

	public static long MOD = 1000000007;
	private int[][] node, result;
	private int[] point, re, store;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		new C_Round_326_Div1().start();
	}

	private int cal(int index, int pa, int d, int[] dist, boolean[] color,
			int[] u, int[] lca, ArrayList<Integer>[] map,
			ArrayList<Integer>[] q, int[][] data) {
		dist[index] = d;
		color[index] = true;
		int result = d;
		for (int i : map[index]) {
			if (i != pa) {
				int v = cal(i, index, d + 1, dist, color, u, lca, map, q, data);
				u[find(i, u)] = index;
				result = Math.max(result, v);
			}
		}
		for (int i : q[index]) {
			int other = data[0][i] == index ? data[1][i] : data[0][i];
			if (color[other]) {
				lca[i] = find(other, u);
			}
		}
		return result;

	}

	private void start() throws FileNotFoundException {
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		int q = in.nextInt();
		store = new int[10];
		ArrayList<Integer>[] map = new ArrayList[n];
		ArrayList<Integer>[] list = new ArrayList[n];
		ArrayList<Integer>[] p = new ArrayList[n];
		int[] u = new int[n];
		for (int i = 0; i < n; i++) {
			map[i] = new ArrayList();
			list[i] = new ArrayList();
			p[i] = new ArrayList();
			u[i] = i;
		}
		for (int i = 0; i < n - 1; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			map[a].add(b);
			map[b].add(a);
		}

		for (int i = 0; i < m; i++) {
			int v = in.nextInt() - 1;
			if (list[v].size() < 10) {
				list[v].add(i + 1);
			}

		}
		int[][] data = new int[3][q];
		result = new int[10][q];
		re = new int[q];
		for (int i = 0; i < q; i++) {

			data[0][i] = in.nextInt() - 1;
			data[1][i] = in.nextInt() - 1;
			data[2][i] = in.nextInt();
			p[data[0][i]].add(i);
			// System.out.println(data[1][i] + " " + data[0][i]);
			if (data[1][i] != data[0][i]) {
				p[data[1][i]].add(i);
			}
		}
		int[] lca = new int[q];
		int[] dist = new int[n];
		int max = cal(0, 0, 0, dist, new boolean[n], u, lca, map, p, data);
		node = new int[10][4 * max + 3];
		point = new int[4 * max + 3];
		cal2(0, 0, max, dist, lca, map, list, p);
		for (int i = 0; i < q; i++) {
			int v = Math.min(re[i], data[2][i]);
			out.print(v);

			for (int j = 0; j < v; j++) {
				out.print(" " + result[j][i]);
			}
			out.println();
		}
		out.close();
	}

	void cal2(int index, int pa, int max, int[] dist, int[] lca,
			ArrayList<Integer>[] map, ArrayList<Integer>[] list,
			ArrayList<Integer>[] q) {

		update(dist[index], dist[index], 0, max, 0, false, list[index]);
		for (int i : map[index]) {
			if (i != pa) {
				cal2(i, index, max, dist, lca, map, list, q);
			}
		}

		for (int i : q[index]) {
			int min = dist[lca[i]];
			if (re[i] == 0)
				get(min, dist[index], 0, max, 0, i);
			else
				get(min + 1, dist[index], 0, max, 0, i);

		}
		update(dist[index], dist[index], 0, max, 0, true, list[index]);

	}

	static int find(int index, int[] u) {
		if (index != u[index]) {
			return u[index] = find(u[index], u);
		}
		return index;
	}

	void get(int l, int r, int l1, int r1, int index, int pos) {
		if (l1 > r || r1 < l) {
			return;
		}
		if (l <= l1 && r1 <= r) {
			if (point[index] > 0) {
				joinResult(pos, index);
			}

			return;
		}
		int mid = (l1 + r1) >> 1;
		get(l, r, l1, mid, left(index), pos);
		get(l, r, mid + 1, r1, right(index), pos);

	}

	static int left(int index) {
		return (index << 1) + 1;
	}

	static int right(int index) {
		return (index << 1) + 2;
	}

	void joinResult(int pos, int index) {
		int cur = 0;
		int x = 0, y = 0;
		while (cur < 10 && (x < re[pos] || y < point[index])) {
			if (x < re[pos] && y < point[index]) {
				int c = result[x][pos];
				int d = node[y][index];
				if (c < d) {
					store[cur++] = c;
					x++;
				} else {
					store[cur++] = d;
					y++;
				}
			} else if (x < re[pos]) {
				store[cur++] = result[x][pos];
				x++;
			} else {
				store[cur++] = node[y][index];
				y++;
			}
		}
		re[pos] = cur;
		for (int i = 0; i < cur; i++) {
			result[i][pos] = store[i];
		}
	}

	void update(int l, int r, int l1, int r1, int index, boolean remove,
			ArrayList<Integer> list) {
		if (l1 > r || r1 < l) {
			return;
		}
		if (l <= l1 && r1 <= r) {

			if (!remove) {
				point[index] = list.size();
				for (int i = 0; i < point[index]; i++) {
					node[i][index] = list.get(i);
				}

			} else {
				point[index] = 0;
			}
			return;
		}
		int mid = (l1 + r1) >> 1;

		update(l, r, l1, mid, left(index), remove, list);
		update(l, r, mid + 1, r1, right(index), remove, list);
		join(index);

	}

	void join(int index) {
		point[index] = 0;

		int x = 0, y = 0;
		while (point[index] < 10
				&& (x < point[left(index)] || y < point[right(index)])) {
			if (x < point[left(index)] && y < point[right(index)]) {
				int c = node[x][left(index)];
				int d = node[y][right(index)];
				if (c < d) {
					node[point[index]++][index] = c;
					x++;
				} else {
					node[point[index]++][index] = d;
					y++;
				}
			} else if (x < point[left(index)]) {
				node[point[index]++][index] = node[x][left(index)];
				x++;
			} else {
				node[point[index]++][index] = node[y][right(index)];
				y++;
			}
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
			if (x != o.x)
				return o.x - x;
			return y - o.y;
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
