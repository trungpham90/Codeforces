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
public class E_Round_386_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		int[] data = new int[n];
		for (int i = 0; i < n; i++) {
			data[i] = in.nextInt();
		}
		HashSet<Integer> odd = new HashSet<>();
		HashSet<Integer> even = new HashSet<>();
		int o = 0, e = 0;
		boolean ok = true;
		LinkedList<Integer> oddNorm = new LinkedList<>();
		LinkedList<Integer> oddRep = new LinkedList<>();
		LinkedList<Integer> evenRep = new LinkedList<>();
		LinkedList<Integer> evenNorm = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			if (data[i] % 2 == 0) {
				if (!even.contains(data[i])) {
					even.add(data[i]);
					evenNorm.add(i);
				} else {

					evenRep.add(i);
				}

				e++;
			} else {
				if (!odd.contains(data[i])) {
					odd.add(data[i]);
					oddNorm.add(i);
				} else {

					oddRep.add(i);
				}
				o++;
			}
		}
		int startOdd = 1;
		int startEven = 2;
		int re = 0;
		// System.out.println(o + " " + e);
		if (o != e) {
			if (o > e) {
				for (int i = 0; i < (o - e) / 2; i++) {
					int index;
					if (!oddRep.isEmpty()) {
						index = oddRep.poll();
					} else {
						index = oddNorm.poll();
					}

					while (even.contains(startEven)) {
						startEven += 2;
					}
					if (startEven > m) {
						ok = false;
						break;
					}
					// System.out.println(index + " " + startEven);
					data[index] = startEven;
					startEven += 2;

					re++;
				}
			} else if (o < e) {
				for (int i = 0; i < (e - o) / 2; i++) {
					int index;
					if (!evenRep.isEmpty()) {
						index = evenRep.poll();
					} else {
						index = evenNorm.poll();
					}
					while (odd.contains(startOdd)) {
						startOdd += 2;
					}
					if (startOdd > m) {
						ok = false;
						break;
					}
					data[index] = startOdd;

					startOdd += 2;
					re++;
				}
			}
		}

		while (!oddRep.isEmpty() && ok) {
			int index = oddRep.poll();
			while (odd.contains(startOdd)) {
				startOdd += 2;
			}
			if (startOdd > m) {
				ok = false;
			}
			re++;
			data[index] = startOdd;
			startOdd += 2;
		}
		while (!evenRep.isEmpty() && ok) {
			int index = evenRep.poll();
			while (even.contains(startEven)) {
				startEven += 2;
			}
			if (startEven > m) {
				ok = false;
			}
			re++;
			data[index] = startEven;
			startEven += 2;
		}
		if (ok) {
			out.println(re);
			for (int i : data) {
				out.print(i + " ");
			}
		} else {
			out.println(-1);
		}
		out.close();
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
