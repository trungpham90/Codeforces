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
public class C_Round_FF_Div1 {

	public static long MOD = 1000000009;
	static long c = 276601605;
	static long a = 691504013;
	static long b = 308495997;
	static long[] sumA, sumB, powA, powB;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		int max = n + 10;
		powA = new long[max];
		powB = new long[max];
		sumA = new long[max];
		sumB = new long[max];
		sumA[0] = 1;
		sumB[0] = 1;
		powA[0] = 1;
		powB[0] = 1;
		for (int i = 1; i < max; i++) {
			powA[i] = (powA[i - 1] * a) % MOD;
			powB[i] = (powB[i - 1] * b) % MOD;
			sumA[i] = (sumA[i - 1] + powA[i]) % MOD;
			sumB[i] = (sumB[i - 1] + powB[i]) % MOD;
		}

		int[] data = new int[n];
		long[] sum = new long[n];
		for (int i = 0; i < n; i++) {
			data[i] = in.nextInt();
		}
		sum[0] = data[0];
		for (int i = 1; i < n; i++) {
			sum[i] = (data[i] + sum[i - 1]) % MOD;
		}
		Node root = new Node(0, n - 1);
		for (int i = 0; i < m; i++) {
			int t = in.nextInt();
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			if (t == 1) {
				update(a, b, 1, false, -1, -1, root);
			} else {
				long v = get(a, b, false, -1, -1, root);
				v += sum[b] - (a > 0 ? sum[a - 1] : 0) + MOD;
				v %= MOD;
				out.println(v);
			}
		}
		out.close();
	}

	static long get(int l, int r, boolean update, long preA, long preB,
			Node node) {
		if (node.l > r || node.r < l) {
			if (update) {
				node.a += preA;
				node.a %= MOD;

				node.b += preB;
				node.b %= MOD;
				node.val += (c * (MOD(preA * sumA[node.r - node.l] - preB
						* sumB[node.r - node.l]) % MOD))
						% MOD;
				node.val %= MOD;
				node.update = true;
			}
			return 0;
		}
		if (l <= node.l && node.r <= r) {
			if (update) {
				node.a += preA;
				node.a %= MOD;

				node.b += preB;
				node.b %= MOD;
				node.val += (c * (MOD(preA * sumA[node.r - node.l] - preB
						* sumB[node.r - node.l]) % MOD))
						% MOD;
				node.val %= MOD;
				node.update = true;
			}

			return node.val;
		}
		int mid = (node.l + node.r) >> 1;
		if (node.left == null) {
			node.left = new Node(node.l, mid);
			node.right = new Node(mid + 1, node.r);
		}
		if (node.update) {
			if (!update) {
				preA = 0;
				preB = 0;
				update = true;
			}
			preA += node.a;
			preA %= MOD;

			preB += node.b;
			preB %= MOD;
			node.a = 0;
			node.b = 0;
			node.update = false;
		}
		long a = get(l, r, update, preA, preB, node.left);
		long b = get(l, r, update, (preA * powA[mid + 1 - node.l]) % MOD,
				(preB * powB[mid + 1 - node.l]) % MOD, node.right);
		node.val = (node.left.val + node.right.val) % MOD;
		return (a + b) % MOD;
	}

	static void update(int l, int r, int start, boolean update, long preA,
			long preB, Node node) {
		if (node.l > r || node.r < l) {
			if (update) {
				node.a += preA;
				node.a %= MOD;

				node.b += preB;
				node.b %= MOD;
				node.val += (c * (MOD(preA * sumA[node.r - node.l] - preB
						* sumB[node.r - node.l]) % MOD))
						% MOD;
				node.val %= MOD;
				node.update = true;
			}
			return;
		}
		if (l <= node.l && node.r <= r) {
			if (update) {
				node.a += preA;
				node.a %= MOD;

				node.b += preB;
				node.b %= MOD;
				node.val += (c * (MOD(preA * sumA[node.r - node.l] - preB
						* sumB[node.r - node.l]) % MOD))
						% MOD;
				node.val %= MOD;
			}
			long x = powA[start + node.l - l] % MOD;
			long y = powB[start + node.l - l] % MOD;
			node.a += x;
			node.b += y;
			node.a %= MOD;
			node.b %= MOD;
			node.val += (c * (MOD(x * sumA[node.r - node.l] - y
					* sumB[node.r - node.l]) % MOD))
					% MOD;
			node.val %= MOD;
			node.update = true;
			return;
		}
		int mid = (node.l + node.r) >> 1;
		if (node.left == null) {
			node.left = new Node(node.l, mid);
			node.right = new Node(mid + 1, node.r);
		}
		if (node.update) {
			if (!update) {
				preA = 0;
				preB = 0;
				update = true;
			}
			preA += node.a;
			preA %= MOD;

			preB += node.b;
			preB %= MOD;
			node.a = 0;
			node.b = 0;
			node.update = false;
		}
		update(l, r, start, update, preA, preB, node.left);
		update(l, r, start, update, (preA * powA[mid + 1 - node.l]) % MOD,
				(preB * powB[mid + 1 - node.l]) % MOD, node.right);
		node.val = (node.left.val + node.right.val) % MOD;
	}

	static long MOD(long v) {
		v %= MOD;
		while (v < 0) {
			v += MOD;
		}
		return v;
	}

	static class Node {

		int l, r;
		long val;
		long a = 0, b = 0;
		boolean update = false;
		Node left, right;

		public Node(int l, int r) {
			this.l = l;
			this.r = r;
		}
	}

	static int left(int index) {
		return 2 * index + 1;
	}

	static int right(int index) {
		return 2 * index + 2;
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
