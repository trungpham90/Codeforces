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
public class E_VK2015_Round1 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		int k = in.nextInt();
		int h = in.nextInt();
		Point[] data = new Point[k];
		for (int i = 0; i < k; i++) {
			data[i] = new Point(in.nextInt(), in.nextInt());
		}
		Square[] q = new Square[h];
		for (int i = 0; i < h; i++) {
			q[i] = new Square(i, in.nextInt(), in.nextInt(), in.nextInt(),
					in.nextInt());
		}
		Arrays.sort(q, new Comparator<Square>() {

			@Override
			public int compare(Square o1, Square o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.x2, o2.x2);
			}
		});
		Arrays.sort(data);
	//	System.out.println(Arrays.toString(data));
		boolean[] result = new boolean[h];
		int[] tree = new int[4 * Math.max(n, m) + 10];
		Arrays.fill(tree, -1);
		int index = 0;
		for (int i = 0; i < h; i++) {
			while (index < k && data[index].x <= q[i].x2) {
				update(0, data[index].y, data[index].x, 1, m, tree);
				index++;
			}
			int v = get(0, q[i].y1, q[i].y2, 1, m, tree);
			
			if (v < q[i].x1) {

			} else {
				result[q[i].index] = true;
			}

		}
		Arrays.sort(q, new Comparator<Square>() {

			@Override
			public int compare(Square o1, Square o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.y2, o2.y2);
			}
		});
		Arrays.sort(data, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.y, o2.y);
			}
		});

		tree = new int[4 * Math.max(n, m) + 10];
		Arrays.fill(tree, -1);
		index = 0;
		for (int i = 0; i < h; i++) {
			while (index < k && data[index].y <= q[i].y2) {
				update(0, data[index].x, data[index].y, 1, n, tree);
				index++;
			}
			int v = get(0, q[i].x1, q[i].x2, 1, n, tree);
//			System.out.println(v + " " + " " + q[i].x1 + " " + q[i].x2 + " "
//					+ q[i].index);
			if (v < q[i].y1) {

			} else {
				result[q[i].index] = true;
			}
		}
		for (boolean v : result) {
			if (v) {
				out.println("YES");
			} else {
				out.println("NO");
			}
		}

		out.close();
	}

	static int left(int index) {
		return 2 * index + 1;
	}

	static int right(int index) {
		return 2 * index + 2;
	}

	static int get(int index, int l1, int r1, int l, int r, int[] tree) {
		if (l > r1 || r < l1) {
			return Integer.MAX_VALUE;
		}
		if (l1 <= l && r <= r1) {
			return tree[index];
		}
		int mid = (l + r) >> 1;
		int a = get(left(index), l1, r1, l, mid, tree);
		int b = get(right(index), l1, r1, mid + 1, r, tree);

		return Math.min(a, b);

	}

	static void update(int index, int p, int v, int l, int r, int[] tree) {
		if (l > p || r < p) {
			return;
		}
		if (l == p && r == p) {
			tree[index] = v;
			return;
		}
		int mid = (l + r) >> 1;
		update(left(index), p, v, l, mid, tree);
		update(right(index), p, v, mid + 1, r, tree);

		tree[index] = Math.min(tree[left(index)], tree[right(index)]);

	}

	static class Square {
		int index, x1, y1, x2, y2;

		public Square(int index, int x1, int y1, int x2, int y2) {
			super();
			this.index = index;
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
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
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
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
