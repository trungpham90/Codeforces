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
public class D_Edu_Round_3 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		int k = in.nextInt();
		int s = in.nextInt();
		long[][] data = new long[n][2];

		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < n; i++) {
				data[i][j] = in.nextInt();
			}
		}

		int[][] min = new int[n][2];
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < 2; j++) {
				if (data[i][j] < data[min[i - 1][j]][j]) {
					min[i][j] = i;
				} else {
					min[i][j] = min[i - 1][j];
				}
			}
		//	System.out.println(i + " " + min[i][0] + " " + min[i][1]);
		}
		ArrayList<Point> one = new ArrayList();
		ArrayList<Point> two = new ArrayList();
		for (int i = 0; i < m; i++) {
			int t = in.nextInt();
			if (t == 1) {
				one.add(new Point(in.nextInt(), i));
			} else {
				two.add(new Point(in.nextInt(), i));
			}
		}
		Collections.sort(one);
		Collections.sort(two);
		long[] o = new long[k + 1];
		long[] t = new long[k + 1];
		for (int i = 0; i < k; i++) {
			if (i < one.size()) {
				o[i + 1] = o[i] + one.get(i).x;
			} else {
				o[i + 1] = -1;
			}
			if (i < two.size()) {
				t[i + 1] = t[i] + two.get(i).x;
			} else {
				t[i + 1] = -1;
			}
		}
		int start = 0;
		int end = n - 1;
		int result = -1;
		while (start <= end) {
			int mid = (start + end) >> 1;
			long mn = -1;
			for (int i = 0; i <= k; i++) {
				if (o[i] != -1 && t[k - i] != -1) {
					long v = o[i] * data[min[mid][0]][0] + t[k - i]
							* data[min[mid][1]][1];
					if (mn == -1 || v < mn) {
						mn = v;
					}
				}
			}
//			System.out.println(mn + " " + mid + " " + min[mid][0] + " "
//					+ min[mid][1]);
			if (mn != -1 && mn <= s) {
				if (result == -1 || result > mid) {
					result = mid;
				}
				end = mid - 1;
			} else {
				start = mid + 1;
			}
		}

		if (result != -1) {
			out.println(result + 1);
			int re = -1;
			long mn = -1;
			for (int i = 0; i <= k; i++) {
				if (o[i] != -1 && t[k - i] != -1) {
					long v = o[i] * data[min[result][0]][0] + t[k - i]
							* data[min[result][1]][1];
					if (mn == -1 || v < mn) {
						mn = v;
						re = i;
					}
				}
			}

			for (int i = 0; i < re; i++) {
				out.println((one.get(i).y + 1) + " " + (min[result][0] + 1));
			}
			for (int i = 0; i < k - re; i++) {
				out.println((two.get(i).y + 1) + " " + (min[result][1] + 1));
			}
		} else {
			out.println(result);
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

		long x; int y;

		public Point(int start, int end) {
			this.x = start;
			this.y = end;
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
			return Long.compare(x, o.x);
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
