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
public class A_Round_305_Div1 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		long m = in.nextInt();
		long h1 = in.nextInt();
		long a1 = in.nextInt();
		long x1 = in.nextInt();
		long y1 = in.nextInt();
		long h2 = in.nextInt();
		long a2 = in.nextInt();
		long x2 = in.nextInt();
		long y2 = in.nextInt();
		long start1 = h1;
		long start2 = h2;
		long min1 = -1;
		long min2 = -1;
		int[] time1 = new int[(int) m];
		int[] time2 = new int[(int) m];
		Arrays.fill(time1, -1);
		Arrays.fill(time2, -1);
		boolean ok = true;
		time1[(int) h1] = 0;
		time2[(int) h2] = 0;
		for (int i = 1; i <= 2 * m; i++) {
			long x = (h1 * x1 + y1) % m;
			long y = (h2 * x2 + y2) % m;
			if (time1[(int) x] == -1) {
				time1[(int) x] = i;
			} else {
				if (min1 == -1) {
					ok &= time1[(int) x] <= time1[(int) a1];
					min1 = i - time1[(int) x];
				}
			}
			if (time2[(int) y] == -1) {
				time2[(int) y] = i;
			} else {
				if (min2 == -1) {

					ok &= time2[(int) y] <= time2[(int) a2];
					min2 = i - time2[(int) y];
				}
			}
			h1 = x;
			h2 = y;
		}

		if (time1[(int) a1] == -1 || time2[(int) a2] == -1) {
			out.println(-1);
		} else {
			if (!ok) {
				if (time1[(int) a1] <= time2[(int) a2]) {
					h1 = start1;

					for (int i = 1; i <= time2[(int) a2]; i++) {
						h1 = (x1 * h1 + y1) % m;
					}
					if (h1 == a1) {
						out.println(time2[(int) a2]);
					} else {
						out.println(-1);
					}
				} else {
					h2 = start2;
					for (int i = 1; i <= time1[(int) a1]; i++) {
						h2 = (x2 * h2 + y2) % m;
					}
					if (h2 == a2) {
						out.println(time1[(int) a1]);
					} else {
						out.println(-1);
					}
				}
			} else {
				long v = time2[(int) a2] - time1[(int) a1];
				while (v < 0) {
					v += min1;
				}
				v %= min1;
				long need = (min1 - v) % min1;

				long result = -1;

				for (long i = 0; i < 2 * min1; i++) {
					if ((i * min2) % min1 == need
							&& i * min2 + time2[(int) a2] >= time1[(int) a1]) {
						result = i;
						break;
					}
				}

				if (result == -1) {
					out.println(result);
				} else {
					out.println(time2[(int) a2] + result * min2);
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
