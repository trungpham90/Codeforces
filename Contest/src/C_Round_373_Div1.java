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
import java.util.Map.Entry;
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
public class C_Round_373_Div1 {

	public static int MOD = 1000000007;
	static HashMap<Long, int[]> map = new HashMap();
	static int[][] pre;
	static Node[] q;
	static int[] other;
	static int[] tmp;
	static int result;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		q = new Node[2 * n];
		other = new int[2 * n];
		pre = new int[64][4];
		pre[0][1] = 1;
		pre[0][2] = 1;
		pre[0][3] = 1;
		for (int i = 1; i < 64; i++) {
			pre[i][0] = pre[i - 1][0];
			pre[i][1] = pre[i - 1][1];
			pre[i][2] = pre[i - 1][2];
			pre[i][3] = pre[i - 1][3];
			quick(pre[i], pre[i - 1]);
		}
		Node root = new Node(0, n - 1);
		for (int i = 0; i < n; i++) {
			int v = in.nextInt() - 1;
			// v %= (MOD - 1);
			tmp = powFibMatrix(v);
			increase(i, i, v, root);
		}
		for (int i = 0; i < m; i++) {
			int t = in.nextInt();
			int l = in.nextInt() - 1;
			int r = in.nextInt() - 1;
			if (t == 2) {
				result = 0;
				get(l, r, root);
				out.println(result);
			} else {
				int x = in.nextInt();
				tmp = powFibMatrix(x);
				increase(l, r, x, root);

			}

		}

		out.close();
	}

	public static void increase(int l, int r, long x, Node node) {

		if (l <= node.l && node.r <= r) {
			node.add += x;
			quickFib(tmp, node);

			return;
		}

		int mid = (node.l + node.r) / 2;
		if (node.left == null) {
			node.left = new Node(node.l, mid);
			node.right = new Node(mid + 1, node.r);
		}
		pushDown(node);
		if (l <= node.left.r) {
			increase(l, r, x, node.left);
		}
		if (r >= node.right.l) {
			increase(l, r, x, node.right);
		}

		node.f0 = (node.left.f0 + node.right.f0) % MOD;
		node.f1 = (node.left.f1 + node.right.f1) % MOD;

	}

	public static void get(int l, int r, Node node) {

		if (l <= node.l && node.r <= r) {
			result += node.f1;
			result %= MOD;
			return;

		}

		pushDown(node);
		if (l <= node.left.r) {
			get(l, r, node.left);
		}
		if (r >= node.right.l) {
			get(l, r, node.right);

		}

	}

	public static void pushDown(Node node) {
		if (node.add != 0) {
			int[] cur = powFibMatrix(node.add);
			node.left.add += node.add;
			node.right.add += node.add;
			quickFib(cur, node.left);
			quickFib(cur, node.right);
			node.add = 0;
		}
	}

	public static int[] powFibMatrix(long p) {
		if (map.containsKey(p)) {
			return map.get(p);
		}

		int[] result = { 1, 0, 0, 1 };
		map.put(p, result);
		int i = 0;
		while (p > 0) {
			if ((p & 1) != 0) {
				quick(result, pre[i]);
			}
			i++;
			p >>= 1;
		}

		return result;

	}

	public static class Node {
		int f0 = 0;
		int f1 = 1;
		long add = 0;
		int l, r;
		Node left, right;

		public Node(int l, int r) {
			super();
			this.l = l;
			this.r = r;
		}
	}

	static void quickFib(int[] A, Node node) {

		int c = (int) ((A[0] * (long) node.f0 + A[1] * (long) node.f1) % MOD);
		node.f1 = (int) ((A[2] * (long) node.f0 + A[3] * (long) node.f1) % MOD);
		node.f0 = c;

	}

	static void quick(int[] A, int[] B) {

		int b = (int) ((A[1] * (long) B[0] + A[3] * (long) B[2]) % MOD);
		int c = (int) ((B[0] * (long) A[2] + B[2] * (long) A[3]) % MOD);
		A[0] = (int) ((A[0] * (long) B[0] + A[1] * (long) B[2]) % MOD);
		A[3] = (int) ((B[1] * (long) A[2] + B[3] * (long) A[3]) % MOD);
		A[1] = b;
		A[2] = c;

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
