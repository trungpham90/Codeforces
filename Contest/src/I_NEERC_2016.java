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
public class I_NEERC_2016 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int p = in.nextInt();
		int s = in.nextInt();
		int[][] data = new int[n][3];
		for (int i = 1; i < 3; i++) {

			for (int j = 0; j < n; j++) {
				data[j][0] = j;
				data[j][i] = in.nextInt();
			}
		}
		Arrays.sort(data, new Comparator<int[]>() {

			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o2[1], o1[1]);
			}
		});
		int[] check = new int[n];
		Arrays.fill(check, -1);
		int total = 0;
		for (int i = 0; i < p; i++) {
			check[data[i][0]] = 0;
			total += data[i][1];
		}
		// System.out.println(Arrays.toString(check) + " " + p);
		Arrays.sort(data, new Comparator<int[]>() {

			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o2[2], o1[2]);
			}
		});
		int count = 0;
		for (int i = 0; i < n && count < s; i++) {
			if (check[data[i][0]] == -1) {
				check[data[i][0]] = 1;
				total += data[i][2];
				count++;
			}
		}
		boolean change = true;
		while (change) {
			while (change) {
				change = false;
				for (int i = 0; i < n && !change; i++) {
					if (check[data[i][0]] == 0) {
						for (int j = 0; j < n; j++) {
							if (check[data[j][0]] == 1) {
								if (data[i][2] + data[j][1] > data[i][1]
										+ data[j][2]) {
									check[data[i][0]] = 1;
									check[data[j][0]] = 0;
									total += (data[i][2] + data[j][1]
											- data[i][1] - data[j][2]);
									change = true;
									break;
								}
							}
						}
					}
				}
			}
			int max = -1;
			int min = -1;
			for (int i = 0; i < n; i++) {
				if (check[data[i][0]] == -1) {
					if (max == -1 || data[i][1] > data[max][1]) {
						max = i;
					}
				} else if (check[data[i][0]] == 0) {
					if (min == -1 || data[i][1] < data[min][1]) {
						min = i;
					}
				}
			}
			if (max != -1 && min != -1 && data[max][1] > data[min][1]) {
				check[data[max][0]] = 0;
				check[data[min][0]] = -1;
				total += data[max][1] - data[min][1];
				change = true;
			}

			max = -1;
			int a = -1, b = -1;
			for (int i = 0; i < n; i++) {
				if (check[data[i][0]] == 0) {
					if (a == -1
							|| data[i][2] - data[i][1] > data[a][2]
									- data[a][1]) {
						a = i;
					}

				} else if (check[data[i][0]] == 1) {
					if (b == -1 || data[i][2] < data[b][2]) {
						b = i;
					}
				} else {
					if (max == -1 || data[i][1] > data[max][1]) {
						max = i;
					}
				}
			}
			if (a != -1 && b != -1 && max != -1
					&& data[a][2] + data[max][1] > data[a][1] + data[b][2]) {
				check[data[a][0]] = 1;
				check[data[max][0]] = 0;
				check[data[b][0]] = -1;
				total += (data[a][2] + data[max][1] - data[a][1] - data[b][2]);
				change = true;

			}

			max = -1;
			min = -1;
			for (int i = 0; i < n; i++) {
				if (check[data[i][0]] == -1) {
					if (max == -1 || data[i][2] > data[max][2]) {
						max = i;
					}
				} else if (check[data[i][0]] == 1) {
					if (min == -1 || data[i][2] < data[min][2]) {
						min = i;
					}
				}
			}
			if (max != -1 && min != -1 && data[max][2] > data[min][2]) {
				check[data[max][0]] = 1;
				check[data[min][0]] = -1;
				total += data[max][2] - data[min][2];
				change = true;
			}
			max = -1;

			a = -1;
			b = -1;
			for (int i = 0; i < n; i++) {
				if (check[data[i][0]] == 0) {
					if (a == -1 || data[i][1] < data[a][1]) {
						a = i;
					}

				} else if (check[data[i][0]] == 1) {
					if (b == -1
							|| data[i][1] - data[i][2] > data[b][1]
									- data[b][2]) {
						b = i;
					}
				} else {
					if (max == -1 || data[i][2] > data[max][2]) {
						max = i;
					}
				}
			}
			if (a != -1 && b != -1 && max != -1
					&& data[b][1] + data[max][2] > data[b][2] + data[a][1]) {
				check[data[b][0]] = 0;
				check[data[max][0]] = 1;
				check[data[a][0]] = -1;
				total += (data[b][1] + data[max][2] - data[b][2] - data[a][1]);
				change = true;

			}

		}
		out.println(total);
		// System.out.println(Arrays.toString(check));
		for (int i = 0; i < n; i++) {
			if (check[i] == 0) {
				out.print((i + 1) + " ");
			}
		}
		out.println();
		for (int i = 0; i < n; i++) {
			if (check[i] == 1) {
				out.print((i + 1) + " ");
			}
		}
		out.println();
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
