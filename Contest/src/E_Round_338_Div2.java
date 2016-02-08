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
public class E_Round_338_Div2 {

	public static long MOD = 1000000007;
	static long[] X = { 1, -1, -2, -1, 1, 2 };
	static long[] Y = { 2, 2, 0, -2, -2, 0 };

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		long n = in.nextLong();
		long x = 0;
		long y = 0;

		int index = 0;
		for (int i = 0; i < X.length && index < n; i++) {
			if (i != 1) {
				x += X[i];
				y += Y[i];
				index++;
			}
		}
		n -= 5;
		// int test = 0;
		// long a = x;
		// long b = y;
		// int[] last = { 1, 0, 1, 1, 1, 1 };
		// int[]total = new int[6];
		// while (test < n) {
		//
		// for (int i = 0; i < X.length && test < n; i++) {
		// last[i]++;
		// for(int j = 0; j < last[i] && test < n; j ++){
		// test++;
		// total[i]++;
		// }
		// }
		//
		//
		// }
		// System.out.println(Arrays.toString(total));
		// out.println(a + " " + b);

		if (n > 0) {
			long s = 0;
			long e = Integer.MAX_VALUE;
			long result = 0;
			while (s <= e) {
				long mid = (s + e) >> 1;
				// System.out.println(stepNeed(mid) + " " + n);
				if (stepNeed(mid) <= n) {
					if (result < mid) {
						result = mid;
					}
					s = mid + 1;

				} else {
					e = mid - 1;
				}
			}
			for(int i = 0; i < 6; i++){
				long step;
				if(i == 1){
					step = result*(result + 1)/2; 
				}else{
					step = (result + 1)*(result + 2)/2 - 1; 
				}
				//System.out.println(step + " " + i);
				x += step*X[i];
				y += step*Y[i];
			}

			long left = n - stepNeed(result);
			// System.out.println(stepNeed(result) + " " + left + " " + result);
			for (int i = 0; i < X.length && left > 0; i++) {
				long step;
				if (i == 1) {
					step = result + 1;
				} else {
					step = result + 2;
				}
				if (step > left) {
					step = left;
				}
				left -= step;
				x += step * X[i];
				y += step * Y[i];
			}

		}
		out.println(x + " " + y);
		out.close();
	}

	static long stepNeed(long n) {
		long result = (n + 1) * (3 * n + 5) - 5;
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
