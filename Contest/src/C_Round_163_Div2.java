import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.lang.Long;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Problem statement: http://codeforces.com/problemset/problem/431/D
 * 
 * @author thepham
 * 
 */

public class C_Round_163_Div2 {
	static long Mod = 1000000007;
	static int[][][] dp;
	static int max = 100000;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int[] row = new int[n];
		int[] col = new int[n];
		Point[] data = new Point[n - 1];

		for (int i = 0; i < n - 1; i++) {
			data[i] = new Point(in.nextInt() - 1, in.nextInt() - 1);
			row[data[i].x]++;
			col[data[i].y]++;
		}

		int count = 0;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < n; i++) {
			int max = -1;
			for (int j = i; j < n; j++) {
				if (max == -1 || col[max] < col[j]) {
					max = j;
				}
			}
			if (max != i && max != -1) {
				count++;
				int tmp = col[max];
				col[max] = col[i];
				col[i] = tmp;
				for (Point p : data) {
					if (p.y == max) {
						p.y = i;
					} else if (p.y == i) {
						p.y = max;
					}
				}
				builder.append(2 + " " + (i + 1) + " " + (max + 1))
						.append("\n");
			}

		}
		for (int i = 0; i < n; i++) {
			int min = -1;

			int[] c = new int[n];
			for (Point p : data) {
				if (p.y >= i ) {
					c[p.x]++;
				}
			}
			for (int j = i; j < n; j++) {
				if (min == -1 || c[j] < c[min]) {
					min = j;
				}
			}
			if (min != i && min != -1) {
				count++;
				int tmp = row[min];
				row[min] = row[i];
				row[i] = tmp;
				for (Point p : data) {
					if (p.x == min) {
						p.x = i;
					} else if (p.x == i) {
						p.x = min;
					}
				}
				builder.append(1 + " " + (i + 1) + " " + (min + 1))
						.append("\n");
			}

		}
		out.println(count);
		out.println(builder.toString());
		out.close();
	}

	static long min(long a, long b) {
		if (a > b) {
			return b;
		}
		return a;
	}

	public static int[] buildKMP(String val) {
		int[] data = new int[val.length() + 1];
		int i = 0;
		int j = -1;
		data[0] = -1;
		while (i < val.length()) {
			while (j >= 0 && val.charAt(i) != val.charAt(j)) {
				j = data[j];
			}
			i++;
			j++;

			data[i] = j;
			// System.out.println(val + " " + i + " " + data[i]);
		}
		return data;
	}

	static int find(int index, int[] u) {
		if (u[index] != index) {
			return u[index] = find(u[index], u);
		}
		return index;
	}

	static int crossProduct(Point a, Point b) {
		return a.x * b.y + a.y * b.x;
	}

	static long squareDist(Point a) {
		long x = a.x;
		long y = a.y;
		return x * x + y * y;
	}

	static Point minus(Point a, Point b) {
		return new Point(a.x - b.x, a.y - b.y);
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

	static class Point {
		int x, y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public String toString() {
			return "{" + x + " " + y + "}";
		}

	}

	static long gcd(long a, long b) {
		if (b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}

	public static class FT {

		int[] data;

		FT(int n) {
			data = new int[n];
		}

		public void update(int index, int value) {
			while (index < data.length) {
				data[index] += value;
				index += (index & (-index));
			}
		}

		public int get(int index) {
			int result = 0;
			while (index > 0) {
				result += data[index];
				index -= (index & (-index));
			}
			return result;

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
			// FileInputStream(
			// new File("input.txt"))));
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
