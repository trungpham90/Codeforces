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
public class D_Yandex2011_Round1 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();

		int[] add = new int[n];
		int[] num = new int[n];
		TreeSet<Integer> number = new TreeSet();
		for (int i = 0; i < n; i++) {
			String v = in.next();
			if ("add".equals(v)) {
				add[i] = 1;
				num[i] = in.nextInt();
				number.add(num[i]);
			} else if ("del".equals(v)) {
				add[i] = -1;
				num[i] = in.nextInt();
				number.add(num[i]);
			}

		}

		HashMap<Integer, Integer> map = new HashMap(number.size());
		HashMap<Integer, Integer> rev = new HashMap(number.size());
		int index = 0;
		for (int i : number) {
			map.put(i, index);
			rev.put(index, i);
			index++;
		}

		for (int i = 0; i < n; i++) {
			if (add[i] != 0) {
				num[i] = map.get(num[i]);
			}
		}
		Node root = new Node(0, number.size() - 1);
		for (int i = 0; i < n; i++) {
			if (add[i] == 1) {
				update(num[i], rev.get(num[i]), 0, root);
				// System.out.println(Arrays.toString(root.val));
			} else if (add[i] == -1) {
				delete(num[i], 0, root);
			} else {
				out.println(root.val[2]);
			}
		}

		out.close();
	}

	static void delete(int index, int skip, Node node) {
		if (node.l > index || node.r < index) {

			node.skip += skip;
			node.skip %= 5;
			// System.out.println("PREVIOUS " + Arrays.toString(node.val) + " "
			// + skip);
			long[] tmp = Arrays.copyOf(node.val, node.val.length);
			for (int i = 0; i < 5; i++) {
				node.val[(i + skip) % 5] = tmp[i];
			}
			// System.out.println("MOVE " + node + " " +
			// Arrays.toString(node.val));
			return;

		}
		if (node.l == index && node.r == index) {
			node.skip += skip;
			node.skip %= 5;
			Arrays.fill(node.val, 0);
			node.val[node.skip] = 0;
			// System.out.println(node + " " + Arrays.toString(node.val));
			return;
		}
		int mid = (node.l + node.r) / 2;
		if (node.left == null) {
			node.left = new Node(node.l, mid);
			node.right = new Node(mid + 1, node.r);
		}
		skip += node.skip;
		skip %= 5;
		node.skip = 0;
		delete(index, skip, node.left);
		if (index < mid + 1) {
			delete(index, (skip - 1 + 5) % 5, node.right);
		} else {
			delete(index, skip, node.right);
		}
		for (int i = 0; i < 5; i++) {
			node.val[i] = node.left.val[i] + node.right.val[i];
		}
		// System.out.println(node + " " + Arrays.toString(node.val));
		return;
	}

	static void update(int index, int val, int skip, Node node) {
		if (node.l > index || node.r < index) {

			node.skip += skip;
			node.skip %= 5;
			// System.out.println("PREVIOUS " + Arrays.toString(node.val) + " "
			// + skip);
			long[] tmp = Arrays.copyOf(node.val, node.val.length);
			for (int i = 0; i < 5; i++) {
				node.val[(i + skip) % 5] = tmp[i];
			}
			// System.out.println("MOVE " + node + " " +
			// Arrays.toString(node.val));
			return;

		}
		if (node.l == index && node.r == index) {
			node.skip += skip;
			node.skip %= 5;
			Arrays.fill(node.val, 0);
			node.val[node.skip] = val;
			// System.out.println(node + " " + Arrays.toString(node.val));
			return;
		}
		int mid = (node.l + node.r) / 2;
		if (node.left == null) {
			node.left = new Node(node.l, mid);
			node.right = new Node(mid + 1, node.r);
		}
		skip += node.skip;
		skip %= 5;
		node.skip = 0;
		update(index, val, skip, node.left);
		if (index < mid + 1) {
			update(index, val, (skip + 1) % 5, node.right);
		} else {
			update(index, val, skip, node.right);
		}
		for (int i = 0; i < 5; i++) {
			node.val[i] = node.left.val[i] + node.right.val[i];
		}
		// System.out.println(node + " " + Arrays.toString(node.val));
		return;

	}

	static class Node {
		int l, r;
		long[] val = new long[5];
		int skip;
		Node left, right;

		Node(int l, int r) {
			this.l = l;
			this.r = r;
		}

		@Override
		public String toString() {
			return "Node [l=" + l + ", r=" + r + ", skip=" + skip + "]";
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
