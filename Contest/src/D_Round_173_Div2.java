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
public class D_Round_173_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int[] data = new int[n];
		for (int i = 0; i < n; i++) {
			data[i] = in.nextInt();
		}
		Arrays.sort(data);
		if (n == 1) {
			if (data[0] == 0) {
				out.println("BitAryo");
			} else {
				out.println("BitLGM");
			}
		} else if (n == 2) {
			byte[][][] dp = new byte[2][data[0] + 1][data[1] + 1];
			for (byte[][] a : dp) {
				for (byte[] b : a) {
					Arrays.fill(b, (byte)-1);
				}
			}
			byte r = cal((byte)0, data[0], data[1], dp);
			if (r == 0) {
				out.println("BitLGM");
			} else {
				out.println("BitAryo");
			}
		} else {
			byte[][][][] dp = new byte[2][data[0] + 1][data[1] + 1][data[2] + 1];
			for (byte[][][] a : dp) {
				for (byte[][] b : a) {
					for (byte[] c : b)
						Arrays.fill(c, (byte)-1);
				}
			}
			byte r = cal2((byte)0, data[0], data[1], data[2], dp);
			if (r == 0) {
				out.println("BitLGM");
			} else {
				out.println("BitAryo");
			}
		}
		out.close();
	}

	static byte cal(byte index, int a, int b, byte[][][] dp) {
		if (a == 0 && b == 0) {
			return (byte)(1 - index);
		}
		if (dp[index][a][b] != -1) {
			return dp[index][a][b];
		}
		byte result =(byte) (1 - index);
		for (int i = 1; i <= a; i++) {
			int x = Math.min(a - i, b);
			int y = Math.max(a - i, b);
			byte v = cal(result, x, y, dp);
			if (v == index) {
				result = index;
				break;
			}
		}
		if (result != index) {
			for (int i = 1; i <= b; i++) {
				int x = Math.min(b - i, a);
				int y = Math.max(b - i, a);
				int v = cal(result, x, y, dp);
				if (v == index) {
					result = index;
					break;
				}
			}
			if (result != index) {
				for (int i = 1; i <= Math.min(a, b); i++) {
					int x = Math.min(b - i, a - i);
					int y = Math.max(b - i, a - i);
					int v = cal(result, x, y, dp);
					if (v == index) {
						result = index;
						break;
					}
				}
			}
		}
		return dp[index][a][b] = result;
	}

	static byte cal2(byte index, int a, int b, int c, byte[][][][] dp) {

		if (a == 0) {
			if ((b ^ c) == 0)
				return (byte)(1 - index);
			return index;
		}
		if (dp[index][a][b][c] != -1) {
			return dp[index][a][b][c];
		}
		byte result =(byte) (1 - index);
		if ((a ^ b ^ c) != 0) {
			result = index;
		}

		if (result != index) {
			for (int i = 1; i <= a; i++) {

				int v = cal2(result, a - i, b - i, c - i, dp);
				if (v == index) {
					result = index;
					break;
				}
			}
		}

		return dp[index][a][b][c] = result;
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
