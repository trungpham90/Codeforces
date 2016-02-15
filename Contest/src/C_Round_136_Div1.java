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
public class C_Round_136_Div1 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int[][] data = new int[2][n];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < n; j++) {
				data[i][j] = in.nextInt() - 1;
			}
		}
		HashMap<Integer, Integer> map = new HashMap();
		for (int i = 0; i < n; i++) {
			map.put(data[0][i], i);
		}
		int[] minNeg = new int[4 * n + 1];
		int[] valNeg = new int[4 * n + 1];
		int[] addNeg = new int[4 * n + 1];
		int[] minPos = new int[4 * n + 1];
		int[] valPos = new int[4 * n + 1];
		int[] addPos = new int[4 * n + 1];
		PriorityQueue<Point> q = new PriorityQueue<>(n,
				new Comparator<Point>() {

					@Override
					public int compare(Point o1, Point o2) {
						if (o1.y != o2.y)
							return Integer.compare(o1.y, o2.y);
						return Integer.compare(o1.x, o2.x);
					}
				});
		for (int i = 0; i < n; i++) {
			int v = i - map.get(data[1][i]);
			if (v >= 0) {
				q.add(new Point(data[1][i], v));
				set(0, data[1][i], v, 0, n - 1, minPos, valPos, addPos);
				set(0, data[1][i], n, 0, n - 1, minNeg, valNeg, addNeg);
			} else {

				set(0, data[1][i], n, 0, n - 1, minPos, valPos, addPos);
				set(0, data[1][i], v, 0, n - 1, minNeg, valNeg, addNeg);
			}
		}

		for (int i = 0; i < n; i++) {
			if (i == 0) {
				int a = Math.min(minNeg[0], minPos[0]);
				out.println(a);
			} else {

				//LinkedList<Point>list = new LinkedList();
				while (!q.isEmpty() && q.peek().y - i < 0) {
					Point p = q.poll();

					set(0, p.x, 4*n, 0, n - 1, minPos, valPos, addPos);
					set(0, p.x, p.y - i + 1, 0, n - 1, minNeg, valNeg, addNeg);
//					 System.out.println(p.x + " " + p.y + " " + minNeg[0] +
//					 " " + minPos[0] + " " + i);
				}
				//System.out.println(minPos[0]);
				addPos[0] = -1;
				addNeg[0] = -1;
				set(0, data[1][i - 1], 4*n, 0, n - 1, minPos, valPos, addPos);
				set(0, data[1][i - 1], 4*n, 0, n - 1, minNeg, valNeg, addNeg);
				int v = n - 1 - map.get(data[1][i - 1]);
			//	System.out.println(v + " " + data[1][ i - 1] + " " + minPos[0] + " " + minNeg[0]);
				if (v >= 0) {
					set(0, data[1][i - 1], v, 0, n - 1, minPos, valPos, addPos);
				} else {
					set(0, data[1][i - 1], v, 0, n - 1, minNeg, valNeg, addNeg);
				}
				if (v + i >= 0) {
					q.add(new Point(data[1][i - 1], v + i));
				}
				//System.out.println("END " + minNeg[0] + " " + minPos[0]);
				int a = Math.min(minNeg[0], minPos[0]);
				out.println(a);
			}
		}

		out.close();
	}

	static void set(int index, int p, int v, int l, int r, int[] min,
			int[] val, int[] add) {
		if (l > p || r < p) {
			return;
		}
		if (l == p && r == p) {
			add[index] = 0;
			val[index] = v;
			min[index] = Math.abs(val[index]);
			return;
		}
		push(index, min, val, add);

		int mid = (l + r) >> 1;
		set(left(index), p, v, l, mid, min, val, add);
		set(right(index), p, v, mid + 1, r, min, val, add);
//		 System.out.println(p + " " + v + " " + l + " " + r + " " +
//		 min[left(index)] + " " + min[right(index)]);
		min[index] = Math.min(min[left(index)], min[right(index)]);
		val[index] = Math.abs(val[left(index)]) > Math.abs(val[right(index)]) ? val[right(index)] : val[left(index)];

	}

	static void push(int index, int[] min, int[] val, int[] add) {
		if (add[index] != 0) {
			add[left(index)] += add[index];
			add[right(index)] += add[index];
			val[left(index)] += add[index];
			val[right(index)] += add[index];
			min[left(index)] = Math.abs(val[left(index)]);
			min[right(index)] = Math.abs(val[right(index)]);
			add[index] = 0;
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
