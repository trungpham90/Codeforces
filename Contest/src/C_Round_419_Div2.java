
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
public class C_Round_419_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		int[][] data = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				data[i][j] = in.nextInt();
			}
		}
		int[] row = new int[n];
		int[] col = new int[m];
		Arrays.fill(row, Integer.MAX_VALUE);
		Arrays.fill(col, Integer.MAX_VALUE);
		for (int i = 0; i < n; i++) {

			for (int j = 0; j < m; j++) {
				row[i] = Integer.min(row[i], data[i][j]);
				col[j] = Integer.min(col[j], data[i][j]);
			}
		}
		ArrayList<String> re = new ArrayList<>();
		boolean ok = true;
		int[] a = new int[n];
		int[] b = new int[m];
		for (int i = 0; i < n && ok; i++) {
			for (int j = 0; j < m; j++) {
				if (data[i][j] > row[i]) {
					if (b[j] < data[i][j] - row[i]) {
						if (data[i][j] - row[i] > col[j]) {
							ok = false;
							break;
						}
						while (b[j] < data[i][j] - row[i]) {
							b[j]++;
							re.add("col " + (j + 1));
						}
					}
				}
				if (data[i][j] > col[j]) {
					if (a[i] < data[i][j] - col[j]) {
						if (data[i][j] - col[j] > row[i]) {
							ok = false;
							break;
						}
						while (a[i] < data[i][j] - col[j]) {
							a[i]++;
							re.add("row " + (i + 1));
						}
					}
				}
			}
		}
		if (!ok) {
			out.println(-1);
		} else {

			for (int i = 0; i < n && ok; i++) {
				for (int j = 0; j < m; j++) {
					if (data[i][j] > a[i] + b[j]) {
						if (m >= n) {
							while (a[i] + b[j] < data[i][j] && addRow(a, b, i, data)) {
								a[i]++;
								re.add("row " + (i + 1));
							}
							while (a[i] + b[j] < data[i][j] && addCol(a, b, j, data)) {
								b[j]++;
								re.add("col " + (j + 1));
							}
						} else {

							while (a[i] + b[j] < data[i][j] && addCol(a, b, j, data)) {
								b[j]++;
								re.add("col " + (j + 1));
							}
							while (a[i] + b[j] < data[i][j] && addRow(a, b, i, data)) {
								a[i]++;
								re.add("row " + (i + 1));
							}
						}
					}
					if ((a[i] + b[j]) != data[i][j]) {
						ok = false;
						break;
					}
				}
			}
			if (ok) {
				out.println(re.size());
				for (String v : re) {
					out.println(v);
				}
			} else {
				out.println(-1);
			}
		}
		out.close();
	}

	public static boolean addRow(int[] a, int[] b, int index, int[][] data) {
		for (int i = 0; i < data[index].length; i++) {
			if (data[index][i] < a[index] + b[i] + 1) {
				return false;
			}
		}
		return true;
	}

	public static boolean addCol(int[] a, int[] b, int index, int[][] data) {
		for (int i = 0; i < data.length; i++) {
			if (data[i][index] < a[i] + b[index] + 1) {
				return false;
			}
		}
		return true;
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
