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

public class C_Round_207_Div1 {
	static long Mod = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int[] data = new int[4 + 1];
		for (int i = 0; i < n; i++) {
			data[in.nextInt()]++;
		}
	// System.out.println(Arrays.toString(data));
		int min = Math.min(data[1], data[2]);
		data[2] -= min;
		data[1] -= min;
		data[3] += min;
		int total = min;
		boolean ok = true;
		if (data[1] > 0) {
			for (int i = data[1] / 3; i >= 0; i--) {
				int a = data[1] - i * 3;
				if (a % 4 == 0) {
					data[1] = 0;
					total += 2 * i + 3 * (a / 4);
					break;
				}
			}

			
			if (data[1] > 0) {

				int x = data[1] / 3;
				total += 2 * x;
				data[1] -= 3 * x;
				data[3] += x;

				if (data[1] == 2) {
					if (data[4] > 0) {
						data[4]--;
						data[3]++;
						total += 2;
						data[1] = 0;
					} else if (data[3] > 1) {
						data[3] -= 2;
						data[4] += 2;
						data[1] = 0;
						total += 2;
					} else {
						ok = false;
					}
				} else if (data[1] == 1) {
					if (data[3] > 0) {
						total++;
						data[3]--;
						data[4]++;
						data[1] = 0;
					} else if (data[4] > 1) {
						total += 2;
						data[3] += 2;
						data[4] -= 2;
						data[1] = 0;
					}else{
						ok = false;
					}
				}

			}
		} else if (data[2] > 0) {
			int x = data[2] / 3;
			total += 2 * x;
			data[2] -= 3 * x;
			data[3] += 2 * x;

			if (data[2] == 2) {
				total += 2;
				data[2] = 0;
				data[4]++;
			} else if (data[2] == 1) {
				if (data[4] > 0) {
					total++;
					data[4]--;
					data[3]++;
					data[2]--;
				} else if (data[3] > 1) {
					data[4] += 2;
					data[3] -= 2;
					total += 2;
					data[2]--;
				} else {
					ok = false;
				}
			}

		}
		if (!ok) {
			out.println(-1);
		} else {
			out.println(total);
		}
		out.close();
	}

	static class Node implements Comparable<Node> {

		int val, num;

		public Node(int val, int num) {
			this.val = val;
			this.num = num;
		}

		@Override
		public int compareTo(Node o) {
			if (num != o.num) {
				return num - o.num;
			}
			return val - o.val;
		}
	}

	static class Segment implements Comparable<Segment> {
		int x, y;

		public Segment(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public String toString() {
			return x + " " + y;
		}

		@Override
		public int compareTo(Segment o) {
			if (x != o.x) {
				return x - o.x;
			}
			return o.y - y;
		}
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
