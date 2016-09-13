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
public class E_Round_367_Div2 {

	public static long MOD = 1000000007;
	static Comparator<int[]> com = new Comparator<int[]>() {

		@Override
		public int compare(int[] o1, int[] o2) {
			// TODO Auto-generated method stub
			return Integer.compare(o1[0], o2[0]);
		}
	};

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		int q = in.nextInt();
		int[][] data = new int[n][m];
		Node head = null;
		Node last = null, lastLayer = null;
		for (int i = 0; i < n; i++) {

			for (int j = 0; j < m; j++) {
				data[i][j] = in.nextInt();

			}
		}

		for (int i = 0; i < n; i++) {
			// System.out.println(lastLayer + " " + (lastLayer != null ?
			// (lastLayer.u == head) + " " + lastLayer.u : null) + " " + i);
			if (i % 2 == 0) {

				for (int j = 0; j < m; j++) {
					if (head == null) {
						head = new Node(data[i][j]);
						last = head;
					} else {
						Node node = new Node(data[i][j]);
						if (last != null) {
							last.r = node;
						}
						node.l = last;
						if (lastLayer != null) {
							lastLayer.d = node;
							node.u = lastLayer;
						}
						last = node;
					}
					if (lastLayer != null) {
						lastLayer = lastLayer.r;
					}

				}
				lastLayer = last;
				last = null;
			} else {
				for (int j = m - 1; j >= 0; j--) {
					//System.out.println(lastLayer + " " + (lastLayer == head));
					Node node = new Node(data[i][j]);
					if (last != null) {
						last.l = node;
					}
					node.r = last;
					if (lastLayer != null) {
						lastLayer.d = node;
						node.u = lastLayer;
					}
					last = node;

					if (lastLayer != null) {
						lastLayer = lastLayer.l;
					}
					
				}

				lastLayer = last;
				last = null;
			}
		}
		// System.out.println(head);

		Node tmp;
		for (int i = 0; i < q; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			int c = in.nextInt() - 1;
			int d = in.nextInt() - 1;
			int h = in.nextInt();
			int w = in.nextInt();
			Node x = head;
			Node y = head;
			// System.out.println(i);
			for (int j = 0; j < a; j++) {
				x = x.d;
			}
			for (int j = 0; j < c; j++) {
				// System.out.println(y);
				y = y.d;

			}
			for (int j = 0; j < b; j++) {
				x = x.r;
			}
			for (int j = 0; j < d; j++) {
				//System.out.println(i + " " + y);
				y = y.r;
			}
			if(head == x ){
				head = y;
			}else if(head == y){
				head = x;
			}
			//System.out.println(x + " " + y);
			Node curX = x, curY = y;
			for (int j = 0; j < w; j++) {
				tmp = curX.u;
				curX.u = curY.u;
				if (curY.u != null) {
					curY.u.d = curX;
				}
				curY.u = tmp;
				if (tmp != null) {
					tmp.d = curY;
				}
				if (j + 1 < w) {
					curX = curX.r;
					curY = curY.r;
				}
			}
			//System.out.println(curX + " " + curY);
			for (int j = 0; j < h; j++) {
				tmp = curX.r;
				curX.r = curY.r;
				if (curY.r != null) {
					curY.r.l = curX;
				}
				curY.r = tmp;
				if (tmp != null) {
					tmp.l = curY;
				}
				if (j + 1 < h) {
					curX = curX.d;
					curY = curY.d;
				}
			}
			for (int j = 0; j < w; j++) {
				tmp = curX.d;
				curX.d = curY.d;
				if (curY.d != null) {
					curY.d.u = curX;
				}
				curY.d = tmp;
				if (tmp != null) {
					tmp.u = curY;
				}
				if (j + 1 < w) {
					curX = curX.l;
					curY = curY.l;
				}
			}
			for (int j = 0; j < h; j++) {
				tmp = curX.l;
				curX.l = curY.l;
				if (curY.l != null) {
					curY.l.r = curX;
				}
				curY.l = tmp;
				if (tmp != null) {
					tmp.r = curY;
				}
				if (j + 1 < h) {
					curX = curX.u;
					curY = curY.u;
				}
			}
			//System.out.println(curX + " " + curY);
		}
		for (int i = 0; i < n; i++) {
			if (i % 2 == 0) {
				for (int j = 0; j < m; j++) {
					data[i][j] = head.v;
					if (j + 1 < m) {
						head = head.r;
					}
				}
				head = head.d;
			} else {
				for (int j = m - 1; j >= 0; j--) {
					data[i][j] = head.v;
					if (j > 0) {
						head = head.l;
					}
				}
				head = head.d;
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j : data[i]) {
				out.print(j + " ");
			}
			out.println();
		}

		out.close();
	}

	static class Node {
		Node l, r, u, d;
		int v;

		Node(int v) {
			this.v = v;
		}

		static String getString(Node node) {
			if (node == null) {
				return "nul ";
			} else {
				return "" + node.v;
			}
		}

		@Override
		public String toString() {
			return "Node [l=" + getString(l) + ", r=" + getString(r) + ", u="
					+ getString(u) + ", d=" + getString(d) + ", v=" + v + "]";
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

		int[] data;
		int n;

		FT(int n) {
			this.n = n;
			data = new int[4 * n + 1];
		}

		int left(int index) {
			return 2 * index + 1;
		}

		int right(int index) {
			return 2 * index + 2;
		}

		public void update(int index, int value) {
			set(index, value, 0, n - 1, 0);
		}

		private void set(int pos, int v, int l, int r, int index) {
			if (l > pos || r < pos) {
				return;
			}
			if (pos == l && pos == r) {
				if (v == 0) {
					data[index] = 0;
				} else {
					data[index] = pos;
				}
				return;
			}
			int mid = (l + r) / 2;
			set(pos, v, l, mid, left(index));
			set(pos, v, mid + 1, r, right(index));
			// System.out.println(data[index] + " " + l + " " + r);
			data[index] = Math.max(data[left(index)], data[right(index)]);

		}

		private int get(int pos, int l, int r, int index) {
			if (l > pos) {
				return 0;
			}
			if (r <= pos) {

				return data[index];
			}
			int mid = (l + r) / 2;
			int a = get(pos, l, mid, left(index));
			int b = get(pos, mid + 1, r, right(index));
			return Math.max(a, b);
		}

		public int floor(int v) {
			int result = get(v, 0, n - 1, 0);
			// System.out.println("Floor " + result + " " + v );
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
