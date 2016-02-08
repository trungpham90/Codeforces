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
public class D_AimTech_2016_Div2 {

	public static long MOD = 1000000007;
	static int[] unit = { 0, 1, -1 };
	static int index;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int N = in.nextInt();
		long n = N;
		long a = in.nextInt();
		long b = in.nextInt();
		int[] data = new int[N];
		for (int i = 0; i < n; i++) {
			data[i] = in.nextInt();
		}
		if (n == 1) {
			out.println(0);
		} else {
			long result = a * (n - 1);
			HashSet<Long> set = new HashSet();
			long[] pre = new long[N];
			for (int i : unit) {
				long v = data[0] + i;
				for (long k = 2; k * k <= v; k++) {
					if (v % k == 0) {
						while (v % k == 0) {
							v /= k;
						}
						if (set.contains(k)) {
							continue;
						}
						set.add(k);
						long tmp = a * (n - 1) + (i != 0 ? b : 0);
						long min = tmp;
						pre[0] = tmp;
						for (int j = 1; j < n; j++) {
							if (data[j] % k == 0) {
								tmp -= a;
								min = min(tmp, min);
							} else if (data[j] % k == 1
									|| data[j] % k == (k - 1)) {
								tmp += b - a;
								min = min(tmp, min);
							} else {
								for (int h = j; h < n; h++) {
									pre[h] = min;
								}
								break;
							}
							//System.out.println(tmp + " " + j);
							pre[j] = min;
						}
						result = min(min, result);
						result = min(cal(k, pre, a, b, data), result);
					}
				}
				
				if (v != 1) {
					if (set.contains(v)) {
						continue;
					}
					set.add(v);
					long k = v;
					long tmp = a * (n - 1) + (i != 0 ? b : 0);
					long min = tmp;

					pre[0] = tmp;
					for (int j = 1; j < n; j++) {
						if (data[j] % k == 0) {
							tmp -= a;
							min = min(tmp, min);
						} else if (data[j] % k == 1 || data[j] % k == (k - 1)) {
							tmp += b - a;
							min = min(tmp, min);
						} else {
							for (int h = j; h < n; h++) {
								pre[h] = min;
							}
							break;
						}
						//System.out.println(min  + " " + k + " " + j);

						pre[j] = min;
					}
					result = min(min, result);
					result = min(cal(k, pre, a, b, data), result);
				}
			}
			//System.out.println(result);
			set.clear();
			for (int i : unit) {
				long v = data[N - 1] + i;
				for (long k = 2; k * k <= v; k++) {
					if (v % k == 0) {
						while (v % k == 0) {
							v /= k;
						}
						if (set.contains(k)) {
							continue;
						}
						set.add(k);
						long tmp = a * (n - 1) + (i != 0 ? b : 0);
						long min = tmp;

						for (int j = N - 2; j >= 0; j--) {
							if (data[j] % k == 0) {
								tmp -= a;
								min = min(tmp, min);
							} else if (data[j] % k == 1
									|| data[j] % k == (k - 1)) {
								tmp += b - a;
								min = min(tmp, min);
							} else {

								break;
							}

						}
						result = min(min, result);

					}
				}
				if (v != 1) {
					if (set.contains(v)) {
						continue;
					}
					set.add(v);
					long k = v;
					long tmp = a * (n - 1) + (i != 0 ? b : 0);
					long min = tmp;

					for (int j = N - 2; j >= 0; j --) {
						if (data[j] % k == 0) {
							tmp -= a;
							min = min(tmp, min);
						} else if (data[j] % k == 1 || data[j] % k == (k - 1)) {
							tmp += b - a;
							min = min(tmp, min);
						} else {

							break;
						}

					}
					result = min(min, result);
				}
			}

			out.println(result);

		}

		out.close();
	}

	static long cal(long k, long[] pre, long a, long b, int[] data) {
		long result = pre[data.length - 1];
		long cur = 0;
		for (int i = data.length - 1; i >= 0; i--) {
			if (data[i] % k == 0) {
				cur -= a;
			} else if (data[i] % k == 1 || data[i] % k == k - 1) {
				cur += b - a;
			} else {
				break;
			}
			long tmp = cur + (i > 0 ? pre[i - 1] : data.length * a);
			//System.out.println(tmp + " " + i + " " + k);
			result = min(tmp, result);

		}
		return result;
	}

	static long min(long a, long b) {
		return a < b ? a : b;
	}

	static long min(long a, long b, int cur) {
		if (a < b) {
			index = cur;
			return a;
		}
		return b;
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
