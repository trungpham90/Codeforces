
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * #
 * 
 * @author pttrung
 */
public class C_TinkOff_Eli {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int x1 = in.nextInt();
		int y1 = in.nextInt();
		int x2 = in.nextInt();
		int y2 = in.nextInt();
		int[][] data = new int[n][4];
		Entry[] p = new Entry[2 * n];
		int index = 0;
		boolean ok = true;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 4; j++) {
				data[i][j] = in.nextInt();
			}
			double[] tmp = cal(x1, y1, x2, y2, data[i]);
			if (tmp[0] > tmp[1]) {
				throw new NullPointerException(Arrays.toString(data[i]) + " " + Arrays.toString(tmp));
			}
			// System.out.println(Arrays.toString(tmp) + " " + i);
			if (tmp[0] == -1) {

				ok = false;
			} else {
				p[index++] = new Entry(tmp[0], true);
				p[index++] = new Entry(tmp[1], false);
			}
		}
		if (ok) {
			double re = -1;
			Arrays.sort(p, (a, b) -> {
				if (a.t != b.t) {
					return Double.compare(a.t, b.t);
				}
				if (a.in != b.in) {
					if (!a.in) {
						return -1;
					} else {
						return 1;
					}
				}
				return 0;

			});
			int count = 0;
			for (Entry x : p) {
				if (x.in) {
					count++;
				} else {
					count--;
				}
				if (count == n && re == -1) {
					re = x.t;
				}

			}
			out.println(re);
		} else {
			out.println(-1);
		}
		out.close();
	}

	static class Entry {
		double t;
		boolean in;

		public Entry(double t, boolean in) {
			super();
			this.t = t;
			this.in = in;
		}

	}

	static double[] cal(int x1, int y1, int x2, int y2, int[] mouse) {
		double[] re = { -1, -1 };
		if (isInside(x1, y1, x2, y2, 0, mouse)) {
			re[0] = 0;
			double a = calTime(mouse[0], x1, mouse[2]);
			if (a == -1) {
				a = calTime(mouse[0], x2, mouse[2]);

			}
			double b = calTime(mouse[1], y1, mouse[3]);
			if (b == -1) {
				b = calTime(mouse[1], y2, mouse[3]);

			}
			if (a == -1 || b == -1) {
				re[1] = Double.MAX_VALUE;
			} else {
				re[1] = Math.min(a, b);
			}
		} else {
			double a = Math.min(calTime(mouse[0], x1, mouse[2]), calTime(mouse[0], x2, mouse[2]));
			if (mouse[2] == 0) {
				if (x1 <= mouse[0] && mouse[0] <= x2) {
					a = 0;
				} else {
					return re;
				}
			}
			double b = Math.min(calTime(mouse[1], y1, mouse[3]), calTime(mouse[1], y2, mouse[3]));
			if (mouse[3] == 0) {
				if (y1 <= mouse[1] && mouse[1] <= y2) {
					b = 0;
				} else {
					return re;
				}
			}

			if (isInside(x1, y1, x2, y2, Math.max(a, b) + 1e-10, mouse)) {
				re[0] = Math.max(a, b);
				double c = Math.max(calTime(mouse[0], x1, mouse[2]), calTime(mouse[0], x2, mouse[2]));
				if (mouse[2] == 0) {
					c = Double.MAX_VALUE;
				}
				double d = Math.max(calTime(mouse[1], y1, mouse[3]), calTime(mouse[1], y2, mouse[3]));
				if (mouse[3] == 0) {
					d = Double.MAX_VALUE;
				}
				re[1] = Math.min(c, d);

			}
		}
		return re;

	}

	static double calTime(int source, int target, int v) {

		if (target > source) {
			if (v <= 0) {
				return -1;
			}
			double dist = target - source;
			return dist / v;
		} else if (target < source) {
			if (v >= 0) {
				return -1;
			}
			double dist = target - source;
			return dist / v;
		}
		return 0;
	}

	static boolean isInside(int x1, int y1, int x2, int y2, double t, int[] mouse) {
		double x = mouse[0] + t * mouse[2];
		double y = mouse[1] + t * mouse[3];
		// System.out.println(x + " " + y + " " + t + " " +
		// Arrays.toString(mouse));
		if (x1 < x && x < x2) {
			return y1 < y && y < y2;
		}
		return false;
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
