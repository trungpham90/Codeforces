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
public class E_Round_321_Div2 {

	public static long MOD = 1000000007;
	static long[] pow, hash, cur, hashPow;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int q = in.nextInt() + in.nextInt();
		String line = in.next();
		pow = new long[n + 1];
		hashPow = new long[n + 1];
		pow[0] = 1;
		long prime = 10;
		for (int i = 1; i <= n; i++) {
			pow[i] = (pow[i - 1] * prime) % MOD;
			hashPow[i] = (hashPow[i - 1] + pow[i - 1]) % MOD;
		}

		cur = new long[4 * n + 10];
		hash = new long[4 * n + 10];
		Arrays.fill(cur, -1);
		for (int i = 0; i < n; i++) {
			update(0, line.charAt(i) - '0', 0, n - 1, i, i);
			//System.out.println(get(0, 0, n - 1, i, i));
		}
		for (int i = 0; i < q; i++) {
			int t = in.nextInt();
			int l = in.nextInt() - 1;
			int r = in.nextInt() - 1;
			int c = in.nextInt();
			if (t == 1) {
				update(0, c, 0, n - 1, l, r);
//				for (int j = 0; j < n; j++) {
//					System.out.println(get(0, 0, n - 1, j, j));
//				}
			} else {
				
				if (r - l + 1 <= c) {
					out.println("YES");
				} else {
					long a = get(0, 0, n - 1, l + c, r);
					long b = get(0, 0, n - 1, l, r - c);
				//	System.out.println(a + " " + b);
					if (a == b) {
						out.println("YES");
					} else {
						out.println("NO");
					}
				}
			}
		}

		out.close();
	}

	static void update(int index, int v, int l1, int r1, int l, int r) {
		if (l1 > r || r1 < l) {
			return;
		}
		if (l <= l1 && r1 <= r) {
			cur[index] = v;
			hash[index] = (hashPow[r1 - l1 + 1] * v) % MOD;
			return;
		}
		int mid = (r1 + l1) >> 1;
		push(index, l1, r1);
		update(left(index), v, l1, mid, l, r);
		update(right(index), v, mid + 1, r1, l, r);
		hash[index] = (hash[left(index)] + (hash[right(index)]
				* pow[mid - l1 + 1]) % MOD)
				% MOD;

	}

	static long get(int index, int l1, int r1, int l, int r) {
		if (l1 > r || r1 < l) {
			return 0;
		}
		if (l <= l1 && r1 <= r) {
			return hash[index];
		}
		int mid = (r1 + l1) >> 1;
		push(index, l1, r1);
		long a = get(left(index), l1, mid, l, r);
		long b = get(right(index), mid + 1, r1, l, r);
		int len = Math.max(0, Math.min(mid, r) - Math.max(l , l1) + 1);
		//System.out.println(len + " " + l + " " + r + " " + l1 + " " + r1);
		return (a + (b * pow[len]) % MOD) % MOD;
	}

	static void push(int index, int l, int r) {
		if (cur[index] >= 0) {
			if (l < r) {
				int mid = (l + r) >> 1;
				cur[left(index)] = cur[index];
				cur[right(index)] = cur[index];
				hash[left(index)] = (cur[index] * hashPow[mid - l + 1]) % MOD;
				hash[right(index)] = (cur[index] * hashPow[r - mid]) % MOD;
				cur[index] = -1;
			}
		}
	}

	static int left(int index) {
		return 2 * index + 1;
	}

	static int right(int index) {
		return 2 * index + 2;
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
