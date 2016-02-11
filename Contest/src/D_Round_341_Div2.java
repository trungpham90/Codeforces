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
public class D_Round_341_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		double x = in.nextDouble();
		double y = in.nextDouble();
		double z = in.nextDouble();
		double min = Math.min(x, Math.min(y, z));
		if(x > 1 || y > 1 || z > 1){
			min = 1.01;
		}
		int a = cal(x, y, z);
		int b = cal(y, x, z);
		int c = cal(z, x, y);

		double e = cal2(a, x, y, z, min);
		double f = cal2(b, y, x, z, min);
		double g = cal2(c, z, x, y, min);
//		System.out.println(a + " " + b + " " + c + " " + e + " " + f + " " + g
//				+ " " + min);
		if (min < 1) {
			if (e <= f && e <= g) {
				if (a == 0) {
					out.println("x^y^z");
				} else if (a == 1) {
					out.println("x^z^y");
				} else {
					out.println("(x^y)^z");
				}
			} else if (f <= e && f <= g) {
				if (b == 0) {
					out.println("y^x^z");
				} else if (b == 1) {
					out.println("y^z^x");
				} else {
					out.println("(y^x)^z");
				}
			} else {
				if (c == 0) {
					out.println("z^x^y");
				} else if (c == 1) {
					out.println("z^y^x");
				} else {
					out.println("(z^x)^y");
				}
			}
		} else {
			if (e >= f && e >= g) {
				if (a == 0) {
					out.println("x^y^z");
				} else if (a == 1) {
					out.println("x^z^y");
				} else {
					out.println("(x^y)^z");
				}
			} else if (f >= e && f >= g) {
				if (b == 0) {
					out.println("y^x^z");
				} else if (b == 1) {
					out.println("y^z^x");
				} else {
					out.println("(y^x)^z");
				}
			} else {
				if (c == 0) {
					out.println("z^x^y");
				} else if (c == 1) {
					out.println("z^y^x");
				} else {
					out.println("(z^x)^y");
				}
			}
		}
		out.close();
	}

	static double cal2(int type, double x, double y, double z, double log) {
		double result;
		if (type == 0) {
			result = (z * Math.log(y)) + Math.log(Math.log(x) / Math.log(log));
		} else if (type == 1) {
			result = (y * Math.log(z)) + Math.log(Math.log(x) / Math.log(log));

		} else {
			result = Math.log(y) + Math.log(z)
					+ Math.log(Math.log(x) / Math.log(log));
			// System.out.println(Math.log(Math.log(x)));
		}
		
		if (Double.isNaN(result)) {
			result = Double.NEGATIVE_INFINITY;
		}
		return result;
	}

	static int cal(double x, double y, double z) {
		double a = z * Math.log(y);
		double b = y * Math.log(z);
		double c = (Math.log(y) + Math.log(z));
		// System.out.println(a + " " + b + " " + c + " " + x + " " + y + " " +
		// z);
		if (x >= 1) {
			if (a >= b && a >= c) {
				return 0;
			} else if (b >= a && b >= c) {
				return 1;
			}
		} else {
			if (a <= b && a <= c) {
				return 0;
			} else if (b <= a && b <= c) {
				return 1;
			}
		}
		return 2;
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
