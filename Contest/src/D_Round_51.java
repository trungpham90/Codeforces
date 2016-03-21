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
public class D_Round_51 {

	public static long MOD = 1000000007;
	static long[][][] dp;

	static int cached = 1;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int t = in.nextInt();

		int[] lcm = new int[1 << 8];
		TreeSet<Integer> set = new TreeSet();
		for (int i = 0; i < (1 << 8); i++) {
			int tmp = 1;
			for (int j = 2; j < 10; j++) {
				if (((1 << (j - 2)) & i) != 0) {
					tmp = (int) (tmp * j / gcd(tmp, j));
				}
			}

			lcm[i] = tmp;
			set.add(tmp);
		}

		int[] pos = new int[2521];
		int[] store = new int[48];
		int index = 0;
		for (int i : set) {
			pos[i] = index;
			store[index] = i;
			index++;
		}
		int[][] map = new int[48][10];
		for (int i = 0; i < 48; i++) {
			for (int j = 0; j < 10; j++) {
				if (j > 1) {
					int v = (int) (store[i] * j / gcd(store[i], j));
					map[i][j] = pos[v];
				} else {
					map[i][j] = i;
				}
			}
		}
		dp = new long[19][48][2520];
		for (long[][] a : dp) {
			for (long[] b : a) {
				Arrays.fill(b, -1);
			}
		}
		for (int z = 0; z < t; z++) {
			long l = in.nextLong();
			long r = in.nextLong();
			int[] digit = get(r);

			long result = cal(18, 0, 0, 0, store, digit, map);
			digit = get(l - 1);
			cached++;

			result -= cal(18, 0, 0, 0, store, digit, map);
			cached++;
			out.println(result);
		}
		// System.out.println(map);

		out.close();
	}

	static long cal(int index, int smaller, int lcm, int mod, int[] store,
			int[] digit, int[][] map) {

		if (smaller == 1 && dp[index][lcm][mod] != -1) {
			return dp[index][lcm][mod];
		}
		long result = 0;
		if (index != 0) {
			for (int i = 0; i < (smaller == 1 ? 10 : digit[index] + 1); i++) {
				if (digit[index] >= i || smaller == 1) {
					int nxt = (mod * 10 + i) % 2520;
					int l = map[lcm][i];

					result += cal(index - 1, i < digit[index] ? 1 : smaller, l,
							nxt, store, digit, map);
				}

			}
		} else {
			if (store[lcm] % 5 != 0) {
				for (int i = 0; i < (smaller == 1 ? 10 : digit[index] + 1); i++) {
					if (digit[index] >= i || smaller == 1) {
						if (i == 5) {
							if (store[lcm] % 2 != 0) {
								int nxt = (mod * 10 + i) % 2520;
								int l = map[lcm][i];
								result += nxt % store[l] == 0 ? 1 : 0;
							}
						} else {
							if (store[lcm] % 2 == 0 && i % 2 != 0) {
								continue;
							} else {
								int nxt = (mod * 10 + i) % 2520;
								int l = map[lcm][i];
								result += nxt % store[l] == 0 ? 1 : 0;
							}
						}
					}
				}
			} else {
				int nxt = (mod * 10) % 2520;
				result += nxt % store[lcm] == 0 ? 1 : 0;
				if (store[lcm] % 2 != 0 && (digit[index] >= 5 || smaller == 1)) {
					nxt = (mod * 10 + 5) % 2520;
					int l = map[lcm][5];
					result += nxt % store[l] == 0 ? 1 : 0;
				}
			}

		}
		if (smaller == 1) {
			dp[index][lcm][mod] = result;
		}
		return result;

	}

	public static int[] get(long v) {
		int[] result = new int[20];
		for (int i = 0; i < 20; i++) {
			result[i] = (int) (v % 10);
			v /= 10;
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
