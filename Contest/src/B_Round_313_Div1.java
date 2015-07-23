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
public class B_Round_313_Div1 {

	public static long MOD = 1000000007;
	static int[] rate;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		String a = in.next();
		String b = in.next();
		if (a.equals(b)) {
			out.println("YES");
		} else {
			if (a.length() % 2 != 0) {
				out.println("NO");
			} else {
				int n = a.length();
				int total = 0;
				while (n % 2 == 0) {
					n /= 2;
					total++;
				}
				rate = new int[total + 1];
				rate[0] = 1;
				for (int i = 1; i < rate.length; i++) {
					rate[i] = 2 * rate[i - 1];
				}
				int[][] x = new int[26][a.length()];
				int[][] y = new int[26][b.length()];
				for (int i = 0; i < a.length(); i++) {
					int index = a.charAt(i) - 'a';
					x[index][i]++;
					index = b.charAt(i) - 'a';
					y[index][i]++;
					if (i > 0) {
						for (int j = 0; j < 26; j++) {
							x[j][i] += x[j][i - 1];
							y[j][i] += y[j][i - 1];
						}
					}
				}
				boolean ok = true;
				for (int i = 0; i < 26; i++) {
					if (x[i][a.length() - 1] != y[i][a.length() - 1]) {
						ok = false;
						break;
					}
				}
				if (ok) {
					boolean v = equivalent(0, 0, total, n, a, b, x, y);
					if (v) {
						out.println("YES");
					} else {
						out.println("NO");
					}
				} else {
					out.println("NO");
				}
			}
		}
		out.close();
	}

	static boolean equivalent(int index, int other, int ratio, int min,
			String x, String y, int[][] a, int[][] b) {
		// System.out.println(index + " " + other + "  " + ratio);

		int length = rate[ratio];
		length *= min;
		
		for(int i = 0; i < 26; i++){
			int m = a[i][index + length - 1] - (index > 0 ? a[i][index - 1] : 0);
			int n = b[i][other + length - 1] - (other > 0 ? b[i][other - 1] : 0);
			if(m != n){
				return false;
			}
		}
		boolean ok = true;
		for (int i = 0; i < length && ok; i++) {
			if (x.charAt(i + index) != y.charAt(other + i)) {
				ok = false;
				
			}
		}
		if (ratio == 0 || ok) {
			return ok;
		}

		if ((equivalent(index, other, ratio - 1, min, x, y, a, b) && equivalent(
				index + length / 2, other + length / 2, ratio - 1, min, x, y,
				a, b))
				|| (equivalent(index, other + length / 2, ratio - 1, min, x, y,
						a, b) && equivalent(index + length / 2, other,
						ratio - 1, min, x, y, a, b))) {
			return true;
		}

		return false;

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
