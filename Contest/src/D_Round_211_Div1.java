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
public class D_Round_211_Div1 {

	public static long MOD = 1000000007;
	static int[] first;
	static int[] last;
	static int cur = 0;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		int[] data = new int[n];
		first = new int[n];
		last = new int[n];
		ArrayList<Integer>[] map = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			data[i] = in.nextInt();
			map[i] = new ArrayList();
		}
		for (int i = 0; i < n - 1; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			map[a].add(b);
			map[b].add(a);
		}
		int[][] q = new int[m][2];
		for (int i = 0; i < m; i++) {
			q[i][0] = in.nextInt() - 1;
			q[i][1] = in.nextInt();
		}
		int[] store = new int[n];
		dfs(0, 0, data, store, map);

		Q[] query = new Q[m];
		for (int i = 0; i < m; i++) {
			query[i] = new Q(first[q[i][0]], last[q[i][0]], q[i][1], i);
		}
		// System.out.println(Arrays.toString(query));
		int length = (int) Math.max(1, Math.sqrt(cur));
		int total = cur / length;
		if (cur % length != 0) {
			total++;
		}
		ArrayList<Q>[] list = new ArrayList[total];
		Arrays.sort(query, new Comparator<Q>() {

			@Override
			public int compare(Q o1, Q o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.b, o2.b);
			}
		});
		int index = 0;
		for (int i = 0; i < list.length; i++) {
			list[i] = new ArrayList();
			int max = (i + 1) * length;
			while (index < m && query[index].b < max) {
				list[i].add(query[index++]);
			}
			// System.out.println(max);
			Collections.sort(list[i], new Comparator<Q>() {

				@Override
				public int compare(Q o1, Q o2) {
					if (o1.a != o2.a)
						return Integer.compare(o1.a, o2.a);
					return Integer.compare(o1.b, o2.b);
				}
			});
		}

		int start = 0;
		int end = 0;
		int[] count = new int[100001];
		count[store[0]]++;
		FT tree = new FT(n + 1);
		tree.update(count[store[0]], 1);
		int[] result = new int[m];
		for (ArrayList<Q> tmp : list) {
			for (Q x : tmp) {

				while (x.b < end) {

					count[store[end]]--;
					if (count[store[end]] > 0) {
						tree.update(count[store[end]], 1);
					}
					tree.update(count[store[end]] + 1, -1);
					end--;
				}
				while (x.b > end) {
					end++;
					count[store[end]]++;
					tree.update(count[store[end]], 1);
					if (count[store[end]] > 1) {
						tree.update(count[store[end]] - 1, -1);
					}

				}
				while (x.a > start) {

					count[store[start]]--;
					if (count[store[start]] > 0) {
						tree.update(count[store[start]], 1);
					}
					tree.update(count[store[start]] + 1, -1);
					start++;
				}
				while (x.a < start) {
					start--;
					count[store[start]]++;
					tree.update(count[store[start]], 1);
					if (count[store[start]] > 1) {
						tree.update(count[store[start]] - 1, -1);
					}

				}
				if (x.k <= n) {
					result[x.index] = (int) (tree.get(n) - (x.k > 1 ? tree
							.get(x.k - 1) : 0));
				}
			}
		}
		for (int i : result) {
			out.println(i);
		}

		out.close();
	}

	static class Q {
		int a, b, k, index;

		public Q(int a, int b, int k, int index) {
			super();
			this.a = a;
			this.b = b;
			this.k = k;
			this.index = index;
		}

		@Override
		public String toString() {
			return "Q [a=" + a + ", b=" + b + ", k=" + k + ", index=" + index
					+ "]";
		}

	}

	static void dfs(int index, int pa, int[] data, int[] store,
			ArrayList<Integer>[] map) {
		first[index] = cur;
		store[cur] = data[index];
		cur++;
		for (int i : map[index]) {
			if (i != pa) {
				dfs(i, index, data, store, map);
			}
		}
		last[index] = cur - 1;
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