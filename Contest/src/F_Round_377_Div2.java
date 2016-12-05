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
public class F_Round_377_Div2 {

	public static long MOD = 1000000007;

	static int index = 0, com = 0;
	static int[] low, cur, store, num, pa;
	static int[][] edge;
	static ArrayList<Integer>[] map;
	static boolean[] check, onStack, mark;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		map = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			map[i] = new ArrayList();
		}
		edge = new int[m][2];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < 2; j++) {
				edge[i][j] = in.nextInt() - 1;
				map[edge[i][j]].add(i);
			}
		}
		pa = new int[n];
		low = new int[n];
		cur = new int[n];
		store = new int[n];
		num = new int[n];
		Arrays.fill(cur, -1);
		onStack = new boolean[n];
		Stack<Integer> s = new Stack();
		for (int i = 0; i < n; i++) {
			if (cur[i] == -1) {
				strongConnect(i, s);
			}
		}
		int max = 0;
		for (int i = 0; i < com; i++) {
			if (num[i] > num[max]) {
				max = i;
			}
		}
		check = new boolean[n];
		mark = new boolean[m];
		for (int i = 0; i < n; i++) {
			if (store[i] == max) {
				fixDirect(i, max);
				break;
			}
		}
		// System.out.println(Arrays.toString(cur) + " " +
		// Arrays.toString(low));
		out.println(num[max]);
		for (int i = 0; i < m; i++) {
			out.println((edge[i][0] + 1) + " " + (edge[i][1] + 1));
		}
		out.close();
	}

	static void fixDirect(int v, int max) {
		Stack<Integer> q = new Stack();
		Stack<Integer> st = new Stack();
		q.add(v);
		st.add(0);
		check[v] = true;
		while (!q.isEmpty()) {
			v = q.pop();
			int start = st.pop();
			// System.out.println("TMP " + v + " " + q);

			// System.out.println(v + " " + pa);
			for (int j = start; j < map[v].size(); j++) {
				int i = map[v].get(j);
				// System.out.println("Process " + i);

				int nxt = edge[i][0] == v ? edge[i][1] : edge[i][0];

				if (!check[nxt]) {
					check[nxt] = true;
					mark[i] = true;
					if (store[v] == store[nxt]) {
						if (edge[i][0] != v) {
							edge[i][0] = v;
							edge[i][1] = nxt;
						}
					} else {
						if (edge[i][1] != v) {
							edge[i][1] = v;
							edge[i][0] = nxt;
						}
					}

					if (j + 1 < map[v].size()) {
						q.add(v);
						st.add(j + 1);
					}
					q.add(nxt);
					st.add(0);
					break;
				} else if (!mark[i]) {
					// System.out.println("MARK " + edge[i][0] + " " +
					// edge[i][1]);
					mark[i] = true;
					if (edge[i][0] != v) {
						edge[i][0] = v;
						edge[i][1] = nxt;
					}

				}
			}
		}

	}

	static void strongConnect(int v, Stack<Integer> s) {
		Stack<Integer> q = new Stack();
		Stack<Integer> st = new Stack();

		q.add(v);
		st.add(0);
		int prev = -1;
		loop: while (!q.isEmpty()) {
			v = q.pop();
			int start = st.pop();
			// System.out.println("HEAD " + v);
			if (cur[v] == -1) {
				cur[v] = low[v] = index++;
				// System.out.println("CUR " + v + " " + cur[v] + " " + low[v]);

				onStack[v] = true;
				s.push(v);
			}
			if (prev != -1 && pa[prev] == v) {
				low[v] = Math.min(low[v], low[prev]);
			}
			for (int j = start; j < map[v].size(); j++) {
				int i = map[v].get(j);
				int nxt = edge[i][0] == v ? edge[i][1] : edge[i][0];
				if (cur[nxt] == -1) {
					// System.out.println("PROCESS " + i);
					pa[nxt] = v;
					q.add(v);
					st.add(j + 1);
					q.add(nxt);
					st.add(0);
					continue loop;

				} else if (onStack[nxt] && nxt != pa[v]) {
					low[v] = Math.min(low[v], cur[nxt]);
				}

			}
			// System.out.println("FINISH " + v + " " + cur[v] + " " + low[v]);
			prev = v;
			if (cur[v] == low[v]) {
				// System.out.println("COM " + v);
				int count = 1;
				while (s.peek() != v) {
					int top = s.pop();
					onStack[top] = false;
					store[top] = com;
					count++;
				}
				store[s.pop()] = com;
				onStack[v] = false;
				num[com] = count;
				com++;
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
			if (x != o.x) {
				return Integer.compare(o.x, x);
			}
			return Integer.compare(y, o.y);
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
