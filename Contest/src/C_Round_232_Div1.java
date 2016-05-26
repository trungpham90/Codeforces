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
public class C_Round_232_Div1 {

	public static long MOD = 1000000007;
	static int[] low, high, dist;
	static int cur = 0;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		ArrayList<Integer>[] map = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			map[i] = new ArrayList();
		}
		for (int i = 0; i < n - 1; i++) {
			map[in.nextInt() - 1].add(i + 1);
		}
		low = new int[n];
		high = new int[n];
		dist = new int[n];
		dfs(0, 0, map);
		Node root = new Node(0, n - 1);
		int q = in.nextInt();
		for (int i = 0; i < q; i++) {
			int t = in.nextInt();
			if (t == 1) {
				int node = in.nextInt() - 1;
				int x = in.nextInt();
				int k = in.nextInt();
				update((x + ((long)dist[node] * k) % MOD) % MOD, k % MOD, low[node], high[node], root);
				// System.out.println(x + dist[node]*k + " " + k);
			} else {
				int node = in.nextInt() - 1;
				long[] a = get(low[node], root);

				long result = (a[0] - ((long) dist[node] * a[1] ) % MOD) % MOD;
				result = (result + MOD) % MOD;
				out.println(result);
			}
		}
		out.close();
	}

	static long[] get(int index, Node node) {
		if (node.l > index || node.r < index) {
			return null;
		}
		if (node.l == index && node.r == index) {
			return new long[] { node.sum, node.k };
		}
		push(node);
		long[] a = get(index, node.left);
		long[] b = get(index, node.right);
		if (a == null) {
			return b;
		}
		return a;
	}

	static void update(long sum, long k, int l, int r, Node node) {
		// System.out.println(node.l + " " + node.r + " " + l + " " + r);
		if (node.l > r || node.r < l) {
			return;
		}
		if (l <= node.l && node.r <= r) {
			node.sum += sum;
			node.sum %= MOD;
			node.k += k;
			node.k %= MOD;
			return;
		}
		push(node);
		update(sum, k, l, r, node.left);
		update(sum, k, l, r, node.right);
	}

	static void push(Node node) {
		if (node.left == null) {
			int mid = (node.l + node.r) / 2;
			node.left = new Node(node.l, mid);
			node.right = new Node(mid + 1, node.r);
		}
		node.left.sum += node.sum;
		node.left.sum %= MOD;
		node.right.sum += node.sum;
		node.right.sum %= MOD;
		node.left.k += node.k;
		node.left.k %= MOD;
		node.right.k += node.k;
		node.right.k %= MOD;
		node.sum = 0;
		node.k = 0;
	}

	static void dfs(int index, int d, ArrayList<Integer>[] map) {
		low[index] = cur++;
		dist[index] = d;
		for (int i : map[index]) {
			dfs(i, d + 1, map);
		}
		high[index] = cur - 1;
	}

	static class Node {
		int l, r;
		Node left, right;
		long sum, k;

		public Node(int l, int r) {
			super();
			this.l = l;
			this.r = r;
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
			return Integer.compare(x, o.x);
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
