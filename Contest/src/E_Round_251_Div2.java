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
public class E_Round_251_Div2 {

	public static long MOD = 1000000007;
	static final BigInteger mod = BigInteger.valueOf(MOD);

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		long[] pre = new long[100001];
		pre[0] = 1;
		for (int i = 1; i < pre.length; i++) {
			pre[i] = i * (pre[i - 1]);
			pre[i] %= MOD;
		}
		int q = in.nextInt();
		HashMap<Point, Long> dp = new HashMap();
		for (int i = 0; i < q; i++) {
			int n = in.nextInt();
			int f = in.nextInt();
			Point p = new Point(n, f);
			if (dp.containsKey(p)) {
				out.println(dp.get(p));
			} else if (f == 1) {
				if (n > 1) {
					out.println(0);
				} else {
					out.println(1);
				}
			} else if (f == n) {
				out.println(1);
			} else {
				long re;
				if (f * 2 > n) {
					re = cal(n - 1, f - 1, pre);
				} else {
					ArrayList<Integer> list = primeList(n);
					// System.out.println(list);
					if (list.size() == 1 && list.get(0) == n) {
						re = cal(n - 1, f - 1, pre);
					} else {
						long v = cal(n - 1, f - 1, pre);
						for (int j = 1; j < 1 << list.size(); j++) {
							int tmp = 1;
							for (int k = 0; k < list.size(); k++) {
								if (((1 << k) & j) != 0) {
									tmp *= list.get(k);
								}
							}
							if ((n / tmp) >= f) {
								if (Integer.bitCount(j) % 2 == 0)
									v += cal((n / tmp) - 1, f - 1, pre);
								else
									v -= cal((n / tmp) - 1, f - 1, pre);

								v += MOD;
								v %= MOD;
							}
						}
						re = v;
					}
				}
				out.println(re);
				dp.put(p, re);
			}
		}
		out.close();
	}

	static ArrayList<Integer> primeList(int a) {
		ArrayList<Integer> list = new ArrayList();
		for (int i = 2; i * i <= a; i++) {
			if (a % i == 0) {
				list.add(i);
				while (a % i == 0) {
					a /= i;
				}
			}
		}
		if (a != 1) {
			list.add(a);
		}
		return list;
	}

	static long cal(int a, int b, long[] pre) {
		BigInteger result = BigInteger.valueOf(pre[a]);
		result = result.multiply(BigInteger.valueOf(pre[b]).modInverse(mod))
				.multiply(BigInteger.valueOf(pre[a - b]).modInverse(mod));
		long v = result.mod(mod).longValue();
		// System.out.println(v + " HE HE" );
		v += MOD;
		v %= MOD;
		return v;
	}

	static int cal(int index, int left, int[] data) {
		if (index == data.length) {
			if (left == 0) {
				int start = data[0];
				for (int i = 1; i < data.length; i++) {
					start = (int) gcd(start, data[i]);
				}
				if (start == 1) {
					return 1;
				}
			}
			return 0;
		}
		if (left == 0) {
			return 0;
		}

		int result = 0;
		for (int i = 1; i <= left; i++) {
			data[index] = i;
			result += cal(index + 1, left - i, data);
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
