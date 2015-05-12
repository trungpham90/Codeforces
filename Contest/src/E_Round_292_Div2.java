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
public class E_Round_292_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		long[] d = new long[n];
		long[] h = new long[n];
		Point[] p = new Point[m];
		for (int i = 0; i < n; i++) {
			d[i] = in.nextInt();
		}
		for (int i = 0; i < n; i++) {
			h[i] = in.nextInt();
		}
		for (int i = 0; i < m; i++) {
			p[i] = new Point(in.nextInt() - 1, in.nextInt() - 1);
		}
		Node root = new Node(0, 2 * n - 2);

		long total = 0;
		for (int i = 0; i < 2 * n - 1; i++) {
			update(total, h[i % n], i, root);
			total += d[i % n];
		}

		for (int i = 0; i < m; i++) {
			if (p[i].x <= p[i].y) {
				int l = p[i].y + 1;
				int r = (p[i].x - 1 + n) % n;
				if (r < l) {
					r += n;
				}
				out.println(calMax(l, r, root).val);
			} else {
				int l = p[i].y + 1;
				int r = p[i].x - 1;
				out.println(calMax(l, r, root).val);
			}
		}

		out.close();
	}

	static Node calMax(int l, int r, Node node) {
		//System.out.println("CUR " + node);
		if (node.l > r || node.r < l) {
			return null;
		}
		//System.out.println("IN " + node);
		
		if (l <= node.l && node.r <= r) {
			return node;
		} else {
			Node a = calMax(l, r, node.left);
			Node b = calMax(l, r, node.right);
			Node result = new Node(l, r);
			if (a != null && b != null) {
				long v = a.min + b.max;
				result.val = v;
				if (a.val != -1) {
					result.val = max(a.val, result.val);
				}
				if (b.val != -1) {
					
					result.val = max(b.val, result.val);
				}
				result.min = max(a.min, b.min);
				result.max = max(a.max, b.max);

			} else if (a != null) {

				result = a;
			} else {
				result = b;
			}
			//System.out.println(node + " " + result);
			return result;
		}

	}

	static boolean update(long dist, long height, int index, Node node) {

		if (node.l > index || node.r < index) {
			return false;
		}
		if (node.l == node.r) {
			node.max = dist + 2 * height;
			node.min = -dist + 2 * height;
			node.update = true;
			return true;
		}
		int mid = (node.l + node.r) / 2;
		if (node.left == null) {
			node.left = new Node(node.l, mid);
		}
		if (node.right == null) {
			node.right = new Node(mid + 1, node.r);
		}
		
		node.min = max(node.min, -dist + 2 * height);
		node.max = max(node.max, dist + 2 * height);
		node.update = true;
		update(dist, height, index, node.left);
		update(dist, height, index, node.right);

		long val = -1;
		if(node.left.update && node.right.update){
			val = node.left.min + node.right.max;
		}
		if (node.left.val != -1) {
			val = max(val, node.left.val);
		}
		if (node.right.val != -1) {
			val = max(val, node.right.val);
		}
		if ((node.val == -1 || node.val < val)) {
			
			node.val = val;
		}

		return true;
	}
	static long max(long a, long b){
		return a > b ? a : b;
	}
	static long min(long a, long b){
		return a < b ? a : b;
	}

	static class Node {
		int l, r;
		Node left = null, right = null;
		boolean update = false;
		long min;
		long max;
		long val;

		public Node(int l, int r) {
			super();
			this.l = l;
			this.r = r;
			min = Long.MIN_VALUE;
			max = 0;
			val = -1;
		}

		@Override
		public String toString() {
			return "Node [l=" + l + ", r=" + r + ", min=" + min + ", max="
					+ max + ", val=" + val + "]";
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
