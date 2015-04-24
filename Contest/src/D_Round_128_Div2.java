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

public class D_Round_128_Div2 {
	static long Mod = 1000000007;
	static int num = 0;
	static int[] index, low;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();

		int a = in.nextInt();
		int b = in.nextInt();
		int n = in.nextInt();
		int[] v = new int[3];
		for (int i = 0; i < 3; i++) {
			v[i] = in.nextInt();
		}
		P start = new P((double) a / 2, n, 0);
		while (start.p[1] > 1e-9) {
			double[] time = new double[3];
			double min = Double.MAX_VALUE;
			for (int i = 0; i < 3; i++) {
				if (v[i] > 0) {
					if (i == 0) {
						time[0] = (a - start.p[0]) / v[i];
					} else if (i == 2) {
						time[2] = (b - start.p[2]) / v[i];
					} else {
						time[1] = Double.MAX_VALUE;
					}
				} else if (v[i] < 0) {

					time[i] = (-start.p[i]) / v[i];
				} else {
					time[i] = Double.MAX_VALUE;
				}
				min = Math.min(min, time[i]);
			}
			for (int i = 0; i < 3; i++) {
				start.p[i] += min * v[i];
				if (start.p[i] <= 1e-9) {
					v[i] = -v[i];
				}else if(i == 0 && Math.abs(start.p[i] - a) <= 1e-9){
					v[i] = -v[i];
				}else if(i == 2 && Math.abs(start.p[i] - b ) <= 1e-9){
					v[i] = -v[i];
				}
			}

		}
		out.println(start.p[0] + " " + start.p[2]);
		out.close();
	}

	static class P {
		double[] p = new double[3];

		public P(double x, double y, double z) {
			super();
			p[0] = x;
			p[1] = y;
			p[2] = z;
		}

	}

	public static int getMax(int a, int n, int i, ArrayList<Integer>[] map,
			Point[] data) {
		int[] check = new int[n];
		LinkedList<Integer> q = new LinkedList();
		check[a] = 0;
		q.add(a);
		int max = a;

		while (!q.isEmpty()) {
			int node = q.removeFirst();
			for (int j : map[node]) {
				if (j != i) {
					int nxt = data[j].x == node ? data[j].y : data[j].x;
					if (check[nxt] == 0 && nxt != a) {
						check[nxt] = check[node] + 1;
						if (check[nxt] > check[max]) {
							max = nxt;
						}
						q.add(nxt);
					}
				}
			}
		}
		q.add(max);
		check = new int[n];
		int other = max;
		while (!q.isEmpty()) {
			int node = q.removeFirst();
			for (int j : map[node]) {
				if (j != i) {
					int nxt = data[j].x == node ? data[j].y : data[j].x;
					if (check[nxt] == 0 && nxt != max) {
						check[nxt] = check[node] + 1;
						if (check[nxt] > check[other]) {
							other = nxt;
						}
						q.add(nxt);
					}
				}
			}
		}
		return check[other];
	}

	public static int get(int node, int[] union) {
		if (node != union[node]) {
			return union[node] = get(union[node], union);
		}
		return node;
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
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
