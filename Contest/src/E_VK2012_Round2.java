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
import java.util.Queue;
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

public class E_VK2012_Round2 {
	static long Mod = 1000000007;
	static int[][][] dp;
	static int max = 100000;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		HashMap<Integer, ArrayList<Box>> map = new HashMap();
		int l = 0;
		for (int i = 0; i < n; i++) {
			int c = in.nextInt();
			long s = in.nextLong();
			if (map.containsKey(c)) {
				map.get(c).add(new Box(i, c, s));
			} else {
				map.put(c, new ArrayList());
				map.get(c).add(new Box(i, c, s));
			}
			l = Math.max(l, map.get(c).size());
		}
		for (int c : map.keySet()) {
			Collections.sort(map.get(c));
		}

		int[][] data = new int[l][2];
		long[][] cur = new long[l][2];
		for (int[] a : data) {
			Arrays.fill(a, -1);
		}
		for (int c : map.keySet()) {

			long[] re = new long[map.get(c).size()];
			for (int i = 0; i < re.length; i++) {
				re[i] = map.get(c).get(re.length - i - 1).size;
				if (i > 0) {
					re[i] += re[i - 1];

				}
				if (data[i][0] == -1) {
					data[i][0] = c;
					cur[i][0] = re[i];
				} else if (cur[i][0] < re[i]) {
					data[i][1] = data[i][0];
					cur[i][1] = cur[i][0];
					data[i][0] = c;
					cur[i][0] = re[i];
				} else if (data[i][1] == -1 || cur[i][1] < re[i]) {
					data[i][1] = c;
					cur[i][1] = re[i];
				}
			}

		}
		long max = 0;
		long total = 0;
		int[] re = new int[2];
		for (int i = 0; i < l; i++) {
			if (data[i][1] != -1) {
				long tmp = cur[i][0] + cur[i][1];
				if (tmp > max) {
					max = tmp;
					total = 2 * i;
					re[0] = data[i][0];
					re[1] = data[i][1];
				}
			}
			if (i > 0) {
				if (data[i][0] != data[i - 1][0]) {
					long tmp = cur[i][0] + cur[i - 1][0];
					if (tmp > max) {
						max = tmp;
						total = 2 * i - 1;
						re[0] = data[i][0];
						re[1] = data[i - 1][0];
					}
				} else {
					if (data[i - 1][1] != -1) {
						long tmp = cur[i][0] + cur[i - 1][1];
						if (tmp > max) {
							max = tmp;
							total = 2 * i - 1;
							re[0] = data[i][0];
							re[1] = data[i - 1][1];
						}
					}
					if (data[i][1] != -1 && data[i][1] != data[i - 1][0]) {
						long tmp = cur[i][1] + cur[i - 1][0];
						if (tmp > max) {
							max = tmp;
							total = 2 * i - 1;
							re[0] = data[i][1];
							re[1] = data[i - 1][0];
						}
					}
				}
			}
		}
		out.println(max);
		out.println((total + 2));
		int start = 0;
		int[] size = new int[2];
		int[] count = new int[2];
		size[0] = map.get(re[0]).size();
		size[1] = map.get(re[1]).size();
		for (int i = 0; i < total + 2; i++) {
			out.print((map.get(re[start]).get(size[start] - count[start] - 1).index + 1)
					+ " ");
			count[start]++;
			start = 1 - start;
		}

		out.close();
	}

	static class Box implements Comparable<Box> {

		int index, c;
		long size;

		public Box(int index, int c, long size) {
			super();
			this.index = index;
			this.c = c;
			this.size = size;
		}

		@Override
		public int compareTo(Box o) {
			// TODO Auto-generated method stub
			return Long.compare(size, o.size);
		}

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
