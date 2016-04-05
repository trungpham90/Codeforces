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
public class E_Round_5 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int[] data = new int[n];
		int[] u = new int[n];
		int max = -1;
		for (int i = 0; i < n; i++) {
			u[i] = i;
			data[i] = in.nextInt();
			if (max == -1 || (data[i] > data[max] || (data[i] == data[max] && data[i] != data[i - 1]))) {
				max = i;
			}
		}
		long[] check = new long[n];
		long result = 0;
		int[] q = new int[n];
		
		for (int i = 0; i < n; i++) {
			if (check[i] == 0) {
				int st = 0;
				int ed = 0;
				q[ed++] = i;
				check[i] = 1;
				long c = 1;
				while (st < ed) {
					int node = q[st++];
					for (int j = -1; j < 2; j++) {
						int v = (node + j + n) % n;
						if (check[v] == 0 && data[v] == data[i]) {
							c++;
							check[v] = -1;
							u[v] = i;
							q[ed++] = v;
						}
					}
				}
				check[i] = c;
				result += c * (c - 1) / 2L;
			}
		}
		int cur = max;
		int st = 0;

		HashSet<Point> set = new HashSet();
		// System.out.println(Arrays.toString(u) + " " +
		// Arrays.toString(check));
		for (int i = 0; i <= n; i++) {
			// long last = result;
			long other = 0;
			while (st > 0 && data[q[st - 1]] <= data[cur]) {
				int tmp = q[--st ];
				if (u[f(tmp, u)] != u[f(cur, u)]) {
					if (data[tmp] == data[cur]) {
						Point p = new Point(u[tmp], u[cur]);
						if (!set.contains(p)) {
							set.add(p);
							result += check[u[tmp]] * check[u[cur]];
							other = check[u[tmp]];
							u[f(tmp, u)] = f(cur, u);
						}
					} else {
						
						Point p = new Point(u[tmp], cur);
						if (!set.contains(p)) {
							set.add(p);
							result += check[u[tmp]];
						}
					}
				}
			}
			if (st > 0) {
				int tmp = q[st - 1];
				Point p = new Point(tmp, u[cur]);
				if (!set.contains(p)) {
					set.add(p);
					result += check[u[cur]];
				}
			}
			check[u[cur]] += other;
			q[st++] = cur;
			//System.out.println(Arrays.toString(q) + " " + st );
			cur = (cur + 1) % n;
		}
		out.println(result);
		out.close();

	}
	static int f(int index, int[]u){
		return u[index] = (index == u[index] ? index : f(u[index], u));
	}

	static int dist(int a, int b) {
		return Math.abs(b - a);
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
			this.x = Math.min(start, end);
			this.y = Math.max(start, end);

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
