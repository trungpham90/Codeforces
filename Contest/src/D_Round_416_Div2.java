
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * #
 * 
 * @author pttrung
 */
public class D_Round_416_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		String[] data = new String[n];
		int startX = 0, startY = 0;
		for (int i = 0; i < n; i++) {
			data[i] = in.next();
			for (int j = 0; j < m; j++) {
				if (data[i].charAt(j) == 'F') {
					startX = i;
					startY = j;
				}
			}
		}
		LinkedList<int[]> q = new LinkedList<>();
		int[][] check = new int[n][m];
		int[] X = { 0, 0, 1, -1 };
		int[] Y = { 1, -1, 0, 0 };
		q.add(new int[] { startX, startY });
		for (int[] a : check) {
			Arrays.fill(a, -1);
		}
		check[startX][startY] = 0;
		while (!q.isEmpty()) {
			int[] v = q.poll();
			for (int i = 0; i < 4; i++) {
				int x = v[0] + X[i];
				int y = v[1] + Y[i];
				if (x >= 0 && x < n && y >= 0 && y < m && data[x].charAt(y) != '*' && check[x][y] == -1) {
					check[x][y] = 1 + check[v[0]][v[1]];
					q.add(new int[] { x, y });
				}
			}
		}

		char[] dir = { 'R', 'L', 'D', 'U' };
		int x = 0, y = 0;
		while (x != startX || y != startY) {
			for (int i = 0; i < 4; i++) {
				int nxtX = x + X[i];
				int nxtY = y + Y[i];
				if (nxtX >= 0 && nxtX < n && nxtY >= 0 && nxtY < m && data[nxtX].charAt(nxtY) != '*'
						&& check[x][y] == 1 + check[nxtX][nxtY]) {
					System.out.println(dir[i]);
					int a = in.nextInt() - 1;
					int b = in.nextInt() - 1;
					if (a != nxtX || b != nxtY) {
						switch (i) {
						case 0:
							System.out.println('L');
							in.nextInt();
							in.nextInt();

							dir[0] = 'L';
							dir[1] = 'R';
							break;
						case 1:
							System.out.println('R');
							in.nextInt();
							in.nextInt();

							dir[0] = 'L';
							dir[1] = 'R';
							break;
						case 2:
							System.out.println('U');
							in.nextInt();
							in.nextInt();

							dir[2] = 'U';
							dir[3] = 'D';
							break;
						case 3:
							System.out.println('D');
							in.nextInt();
							in.nextInt();

							dir[2] = 'U';
							dir[3] = 'D';
							break;
						}
					}
					x = nxtX;
					y = nxtY;
					break;
				}
			}
		}

		out.close();
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
