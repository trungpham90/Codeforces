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
public class F_Round_350_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		String line = in.next();
		String sub = in.next();
		int[] count = convert(sub);
		int[] total = convert(line);
		int n = line.length();
		if (n == 2 && "0".equals(sub)) {
			out.println("0");
		} else {

			boolean decreasing = decreasing(sub);

			String result = null;
			for (int i = 1; i <= line.length() && result == null; i++) {
				String v = "" + i;
				if (n - v.length() == i) {
					int[] minus = convert(v);
					if (isValid(total, minus, count)) {

						int[] tmp = minus(total, minus);
						tmp = minus(tmp, count);
						if (sub.charAt(0) != '0') {
							String other = min(tmp, i - sub.length());
							result = sub + other;

						}
						for (int j = 1; j < 10; j++) {
							if (tmp[j] > 0) {
								tmp[j]--;
								String other = min(tmp, i - sub.length() - 1);
								int pos = 0;
								for (int k = 0; k < other.length(); k++) {
									if (other.charAt(k) < sub.charAt(0)) {
										pos = k + 1;
									} else if (other.charAt(k) == sub.charAt(0)) {
										if (decreasing) {
											pos = k;
											break;
										} else {
											pos = k + 1;
										}
									} else {
										break;
									}
								}
								//System.out.println(i + " " + other + " " + sub);
								String cur = (char) (j + '0')
										+ other.substring(0, pos) + sub
										+ other.substring(pos);
								if (result == null || result.compareTo(cur) > 0) {
									result = cur;
								}

								break;
							}
						}
					}

				}
			}
			out.println(result);

		}
		out.close();
	}

	static boolean decreasing(String v) {
		for (int i = 1; i < v.length(); i++) {
			if (v.charAt(i) > v.charAt(i - 1)) {
				return false;
			} else if (v.charAt(i) < v.charAt(i - 1)) {
				return true;
			}
		}
		return true;
	}

	static int[] minus(int[] a, int[] b) {
		int[] result = new int[10];
		for (int i = 0; i < 10; i++) {
			result[i] = a[i] - b[i];
		}
		return result;
	}

	static String min(int[] total, int n) {
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < total[i]; j++) {
				result.append((char) (i + '0'));
			}
		}
		return result.toString();

	}

	static boolean isValid(int[] total, int[] minus, int[] sub) {
		for (int i = 0; i < 10; i++) {
			if (total[i] - minus[i] < sub[i]) {
				return false;
			}
		}
		return true;
	}

	static int[] convert(String v) {
		int[] result = new int[10];
		for (int i = 0; i < v.length(); i++) {
			result[v.charAt(i) - '0']++;
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
