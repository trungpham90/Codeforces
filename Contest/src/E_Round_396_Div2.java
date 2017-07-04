
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * #
 * 
 * @author pttrung
 */
public class E_Round_396_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);

		Scanner in = new Scanner();
		int n = in.nextInt();
		ArrayList<Integer>[] map = new ArrayList[n];
		int[] data = new int[n];
		long total = 0;
		for (int i = 0; i < n; i++) {

			map[i] = new ArrayList<>();
			data[i] = in.nextInt();
			total += data[i];
		}
		for (int i = 0; i < n - 1; i++) {
			int u = in.nextInt() - 1;
			int v = in.nextInt() - 1;
			map[u].add(v);
			map[v].add(u);
		}
		int[][] dp = new int[n][20];
		int[] store = new int[n];
		cal(0, 0, dp, store, data, map);
		total += cal2(0, 0, new int[20], dp, data, store, map);
		out.println(total / 2);
		out.close();
	}

	static void cal(int index, int pa, int[][] dp, int[] store, int[] data, ArrayList<Integer>[] map) {

		for (int i : map[index]) {
			if (i != pa) {
				cal(i, index, dp, store, data, map);
				store[index] += store[i];

			}
		}
		for (int i : map[index]) {
			if (i != pa) {
				for (int j = 0; j < 20; j++) {
					if (((1 << j) & data[index]) != 0) {
						dp[index][j] -= dp[i][j];
					} else {
						dp[index][j] += dp[i][j];
					}
				}

			}
		}
		store[index]++;
		for (int j = 0; j < 20; j++) {
			if (((1 << j) & data[index]) != 0) {
				dp[index][j] += store[index];
			}
		}

		// System.out.println(Arrays.toString(dp[index]) + " " + (index + 1));
	}

	static long cal2(int index, int pa, int[] p, int[][] dp, int[] data, int[] store, ArrayList<Integer>[] map) {
		long result = 0;
		long cur = 0;
		int other = map.length - store[index];
		int[] nxt = new int[20];
		for (int i = 0; i < 20; i++) {
			cur += (1L << i) * dp[index][i];
			if (((1 << i) & data[index]) != 0) {
				nxt[i] += other - p[i];
				cur += (1L << i) * (other - p[i]);

			} else {
				nxt[i] += p[i];
				cur += (1L << i) * p[i];
			}

			nxt[i] += dp[index][i];
		}
		// System.out.println(cur + " " + (index + 1) + " " + Arrays.toString(p)
		// + " " + Arrays.toString(dp[index]));
		for (int i : map[index]) {
			if (i != pa) {
				for (int j = 0; j < 20; j++) {
					if (((1 << j) & data[index]) != 0) {
						nxt[j] -= store[i] - dp[i][j];
					} else {
						nxt[j] -= dp[i][j];
					}
				}
				// System.out
				// .println(Arrays.toString(nxt) + " " + (i + 1) + " " +
				// store[i] + " " + Arrays.toString(dp[i]));
				result += cal2(i, index, nxt, dp, data, store, map);
				for (int j = 0; j < 20; j++) {
					if (((1 << j) & data[index]) != 0) {
						nxt[j] += store[i] - dp[i][j];
					} else {
						nxt[j] += dp[i][j];
					}
				}
			}
		}
		return result + cur;
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
