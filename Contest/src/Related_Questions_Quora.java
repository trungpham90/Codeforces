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
public class Related_Questions_Quora {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		long[] data = new long[n];
		ArrayList<Integer>[] map = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			map[i] = new ArrayList();
			data[i] = in.nextInt();
		}
		for (int i = 0; i < n - 1; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			map[a].add(b);
			map[b].add(a);
		}
		if (n == 1) {
			out.println(1);
		} else {
			double[] pre = new double[n];
			cal(0, 0, pre, data, map);
			double[] re = new double[n];
			int v = cal2(0, 0, 0, re, pre, data, map);
			out.println((v + 1));
		}
		out.close();
	}

	public static void cal(int index, int pa, double[] re, long[] data,
			ArrayList<Integer>[] map) {

		long tmp = 0;
		for (int i : map[index]) {
			if (i != pa) {
				cal(i, index, re, data, map);
				tmp += re[i];
			}
		}
		if (map[index].size() > 1 || pa == index) {
			tmp /= map[index].size() - (pa == index ? 0 : 1);

		}
		tmp += data[index];
		re[index] = tmp;
	}

	public static int cal2(int index, int pa, double paCost, double[] re,
			double[] pre, long[] data, ArrayList<Integer>[] map) {
		// System.out.println(index + " " + paCost);

		double total = (pa == index ? 0 : paCost + data[index]);
		for (int i : map[index]) {
			if (i != pa) {

				total += pre[i] + data[index];
			}
		}

		re[index] = total / map[index].size();
		int result = index;
		for (int i : map[index]) {
			if (i != pa) {
				double cost = 0;
				if (map[index].size() > 1) {
					cost = total - data[index] - pre[i];
					cost /= (map[index].size() - 1);
				} else {
					cost = data[index];
				}
				int v = cal2(i, index, cost, re, pre, data, map);
				if (re[v] < re[result]) {
					result = v;
				}
			}
		}
		return result;
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
