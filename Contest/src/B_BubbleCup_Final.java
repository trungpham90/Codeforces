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
public class B_BubbleCup_Final {

	public static long MOD = 1000000007;
	static long result = 0;
	static ArrayList<int[]>[] map, q;

	static long[] pre;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		map = new ArrayList[n];
		q = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			map[i] = new ArrayList();
			q[i] = new ArrayList();
		}

		for (int i = 0; i < n - 1; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			int[] tmp = new int[3];
			tmp[0] = a;
			tmp[1] = b;
			tmp[2] = in.nextInt();
			map[a].add(tmp);
			map[b].add(tmp);
		}

		int k = in.nextInt();
		pre = new long[k];

		int start = 0;

		for (int i = 0; i < k; i++) {
			if (i > 0) {
				pre[i] = ((pre[i - 1] + 1) * 2 - 1 + MOD) % MOD;
			}
			int v = in.nextInt() - 1;
			int[] tmp = new int[2];
			tmp[0] = start;
			tmp[1] = v;

			if (v != start) {
				q[start].add(tmp);
				q[v].add(tmp);
			}
			start = v;
		}

		int[] up = new int[n];
		int[] down = new int[n];
		int[] endUp = new int[n];
		int[] endDown = new int[n];
		cal(0, 0, up, down, endUp, endDown, new int[n], new boolean[n]);

		// System.out.println(Arrays.toString(up));
		cal2(0, 0, up, down, endUp, endDown);

		out.println(result);
		out.close();
	}

	static int[] cal2(int index, int pa, int[] up, int[] down, int[] endUp,
			int[] endDown) {

		int[] re = { 0, 0 };
		for (int[] i : map[index]) {
			int nxt = i[0] == index ? i[1] : i[0];
			if (nxt != pa) {
				int[] tmp = cal2(nxt, index, up, down, endUp, endDown);
				// System.out.println((index + 1) + " " + (nxt + 1) + " "
				// + Arrays.toString(tmp));
				if (i[2] == 1) {
					if (index == i[0]) {
						result += pre[tmp[0]];
						result %= MOD;
					} else {
						result += pre[tmp[1]];
						result %= MOD;
					}
				}
				re[0] += tmp[0];
				re[1] += tmp[1];
			}
		}
		re[0] += up[index] - endUp[index];
		re[1] += down[index] - endDown[index];

		return re;
	}

	public static void cal(int index, int pa, int[] up, int[] down,
			int[] endUp, int[] endDown, int[] u, boolean[] check) {
		u[index] = index;
		for (int[] i : map[index]) {
			int nxt = i[0] == index ? i[1] : i[0];
			if (nxt != pa) {
				cal(nxt, index, up, down, endUp, endDown, u, check);
				u[find(nxt, u)] = index;
			}
		}
		check[index] = true;
		for (int[] i : q[index]) {
			int nxt = i[0] == index ? i[1] : i[0];
			if (check[nxt]) {
				int v = find(nxt, u);

				if (v != nxt && v != index) {
					endUp[v]++;
					endDown[v]++;
					if (nxt == i[0]) {
						up[nxt]++;
						down[index]++;
					} else {
						up[index]++;
						down[nxt]++;
					}
				} else if (v == nxt) {
					if (nxt == i[0]) {
						down[index]++;
						endDown[nxt]++;
					} else {
						up[index]++;
						endUp[nxt]++;
					}
				} else {
					if (nxt == i[0]) {
						up[nxt]++;
						endUp[index]++;
					} else {
						down[nxt]++;
						endDown[index]++;
					}
				}
			}
		}
	}

	static int find(int index, int[] u) {
		return u[index] = (index == u[index] ? index : find(u[index], u));
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
