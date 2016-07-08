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
public class D_Round_361_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int[] a = new int[n];
		int[] b = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		for (int i = 0; i < n; i++) {
			b[i] = in.nextInt();
		}
		int[] p = new int[n];
		int[] q = new int[n];
		int[][] pair = new int[n][2];
		long[][] count = new long[n][2];
		long last = 0;
		for (int[] x : pair) {
			Arrays.fill(x, -1);
		}
		int curA = 0, curB = 0;
		long result = 0;
		for (int i = 0; i < n; i++) {
			while (curA > 0 && a[p[curA - 1]] <= a[i]) {
				curA--;
				if(pair[p[curA]][0]  != -1){
					int other =  pair[p[curA]][0];
					pair[p[curA]][0] = -1;
					pair[other][1] = -1;
					last -= count[p[curA]][0];
					count[p[curA]][0] = 0;
					count[other][1] = 0;
				}
			}
			p[curA++] = i;
			while (curB > 0 && b[q[curB - 1]] >= b[i]) {
				curB--;
				if(pair[q[curB]][1]  != -1){
					int other =  pair[q[curB]][1];
					pair[q[curB]][1] = -1;
					pair[other][0] = -1;
					last -= count[q[curB]][1];
					count[q[curB]][1] = 0;
					count[other][0] = 0;
				}
			}
			q[curB++] = i;
			int st = 0;
			int ed = curA - 1;
			int res = -1;
			while (st <= ed) {
				int mid = (st + ed) >> 1;
				if (a[p[mid]] == b[i]) {
					res = mid;
					break;
				} else if (a[p[mid]] > b[i]) {
					st = mid + 1;
				} else {
					ed = mid - 1;
				}
			}

			if (res != -1) {
				int leftA = res != 0 ? p[res - 1] + 1 : 0;
				int leftB = curB > 1 ? q[curB - 2] + 1 : 0;
				// System.out.println(leftA + " " + p[res] + " " + leftB + " " +
				// i + " " + b[i]);
				int v = intersect(leftA, p[res], leftB, i);
				last += v;
				pair[p[res]][0] = i;
				pair[i][1] = p[res];
				count[p[res]][0] = v;
				count[i][1] = v;
			}
			if (a[i] != b[i]) {
				st = 0;
				ed = curB - 1;
				res = -1;
				while (st <= ed) {
					int mid = (st + ed) >> 1;
					if (b[q[mid]] == a[i]) {
						res = mid;
						break;
					} else if (b[q[mid]] > a[i]) {
						ed = mid - 1;
					} else {
						st = mid + 1;
					}
				}

				if (res != -1) {
					int leftB = res != 0 ? q[res - 1] + 1 : 0;
					int leftA = curA > 1 ? p[curA - 2] + 1 : 0;
					// System.out.println(leftA + " " + p[res] + " " + leftB +
					// " " + i + " " + b[i]);
					int v = intersect(leftA, i, leftB, q[res]);
					last += v;
					pair[q[res]][1] = i;
					pair[i][0] = q[res];
					count[q[res]][1] = v;
					count[i][0] = v;
				}
			}
			result += last;
		}
		out.println(result);
		out.close();
	}

	static int intersect(int l1, int r1, int l2, int r2) {
		if (l1 <= l2 && r2 <= r1) {
			return r2 - l2 + 1;
		} else if (l2 <= l1 && r1 <= r2) {
			return r1 - l1 + 1;
		} else if (l1 <= l2 && l2 <= r1) {
			return r1 - l2 + 1;
		} else if (l1 <= r2 && r2 <= r1) {
			return r2 - l1 + 1;
		}
		return 0;
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
