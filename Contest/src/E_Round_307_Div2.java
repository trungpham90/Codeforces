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
public class E_Round_307_Div2 {

	public static long MOD = 1000000007;
	static IntComp comp = new IntComp();

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int q = in.nextInt();
		int size = (int) Math.min(n, 5000);
		Box[] list = new Box[(int) Math.ceil((double) n / size)];

		ArrayList<Integer> tmp = new ArrayList();
		int index = 0;
		for (int i = 0; i < n; i++) {
			tmp.add(in.nextInt());
			if (tmp.size() == size) {
				Box box = new Box(i - tmp.size() + 1, tmp);
				tmp = new ArrayList();
				list[index++] = box;
			}
		}

		if (!tmp.isEmpty()) {
			list[index++] = new Box(n - tmp.size(), tmp);
		}
		for (int i = 0; i < q; i++) {
			int t = in.nextInt();
			if (t == 1) {
				int l = in.nextInt() - 1;
				int r = in.nextInt() - 1;
				int x = in.nextInt();
				for (int j = l / size; j < list.length; j++) {
					if (!list[j].update(l, r, x)) {
						break;
					}
				}
			} else {
				int start = -1;
				int end = -2;
				int x = in.nextInt();
				for (Box b : list) {
					int[] re = b.get(x);
					// System.out.println(Arrays.toString(re) + " " + b.s + " "
					// + b.e);
					if (re != null && re[1] != -2) {
						if (start == -1) {
							start = re[0];
						}
						end = re[1];
					}
				}
				out.println(end - start);
			}
		}
		out.close();
	}

	static class IntComp implements Comparator<int[]> {

		@Override
		public int compare(int[] o1, int[] o2) {
			if (o1[0] != o2[0]) {
				return Integer.compare(o1[0], o2[0]);
			}
			return o1[1] - o2[1];
		}

	}

	static class Box {
		int[][] list;
		long up = 0;
		int s, e;
		int[][] data;

		Box(int start, ArrayList<Integer> list) {
			this.s = start;
			this.e = start + list.size() - 1;
			this.list = new int[list.size()][2];
			data = new int[list.size()][2];
			for (int i = 0; i < list.size(); i++) {
				this.list[i][0] = list.get(i);
				this.list[i][1] = s + i;
				data[i] = this.list[i];
			}
			Arrays.sort(data, comp);
		}

		boolean update(int l, int r, int x) {
			if (l <= s && e <= r) {
				up += x;

			} else if (s > r || e < l) {
				return false;
			} else {
				for (int i = Math.max(0, l - s); i <= Math.min(e - s, r - s); i++) {
					list[i][0] += x;
				}

				Arrays.sort(data, comp);

			}
			return true;
		}

		int[] get(int x) {

			if (x >= up) {
				int[] result = { -1, -2 };
				x -= up;
				int st = 0;
				int ed = data.length - 1;

				while (st <= ed) {
					int mid = (st + ed) >> 1;
					if (data[mid][0] > x) {
						ed = mid - 1;
					} else if (data[mid][0] < x) {
						st = mid + 1;
					} else {
						if (result[0] == -1 || result[0] > data[mid][1]) {
							result[0] = data[mid][1];
						}
						ed = mid - 1;
					}
				}

				ed = data.length - 1;

				while (st <= ed) {
					int mid = (st + ed) >> 1;
					if (data[mid][0] > x) {
						ed = mid - 1;
					} else if (data[mid][0] < x) {
						st = mid + 1;
					} else {
						if (result[1] < data[mid][1]) {
							result[1] = data[mid][1];
						}
						st = mid + 1;
					}
				}

				return result;
			}
			return null;

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
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
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
			if (y != o.y) {
				return Integer.compare(y, o.y);
			}
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
