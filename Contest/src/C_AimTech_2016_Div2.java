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
public class C_AimTech_2016_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		ArrayList<Integer>[] map = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			map[i] = new ArrayList();
		}
		for (int i = 0; i < m; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			map[a].add(b);
			map[b].add(a);
		}
		TreeSet<Integer> set = new TreeSet();
		for (ArrayList<Integer> list : map) {
			set.add(list.size());
		}

		if (set.size() > 3) {
			out.println("No");
		} else {
			int a = 0, b = 0, c = 0;
			int na = 0, nb = 0, nc = 0;
			// System.out.println(set);
			if (set.size() == 1) {

				a = set.first();
				if (a == n - 1) {
					na = a + 1;
				} else {
					c = a;
					na = a + 1;
					nc = a + 1;
				}
			} else if (set.size() == 2) {
				a = set.pollFirst();
				c = set.pollFirst();
				na = a + 1;
				nc = c + 1;
				if (na + nc > n) {
					b = c;
					c = a;
					nb = a + c - b + 1;
					nc = c - nb + 1;
					na = a - nb + 1;
				}
			} else {
				a = set.pollFirst();
				c = set.pollFirst();
				b = set.pollFirst();
				nb = a + c - b + 1;
				nc = c - nb + 1;
				na = a - nb + 1;
			}

			if (nc >= 0 && nb >= 0 && na >= 0 && na + nb + nc == n) {
				int[] color = new int[n];

				boolean[] check = new boolean[n];
				int[] count = { na, nb, nc };
				for (int k = 0; k < n; k++) {
					if (check[k]) {
						continue;
					}
					LinkedList<Integer> q = new LinkedList();
					q.add(k);
					
					//System.out.println(Arrays.toString(count));
					while (!q.isEmpty()) {
						int node = q.pollFirst();
						if (!check[node]) {
							check[node] = true;
							if (map[node].size() == a && count[0] > 0) {
								color[node] = 0;
								count[0]--;

							} else if (map[node].size() == b && count[1] > 0) {
								color[node] = 1;
								count[1]--;
							} else {
								color[node] = 2;
								count[2]--;

							}
						}
						// System.out.println(node + " " +
						// Arrays.toString(count));
						for (int i : map[node]) {
							if (!check[i]) {
								if (map[node].size() == map[i].size()
										&& count[color[node]] > 0) {
									color[i] = color[node];
									count[color[i]]--;
								} else {
									if (map[i].size() == a && count[0] > 0) {
										color[i] = 0;
										count[0]--;

									} else if (map[i].size() == b
											&& count[1] > 0) {
										color[i] = 1;
										count[1]--;
									} else {
										color[i] = 2;
										count[2]--;

									}
								}

								check[i] = true;
								q.add(i);
							}
						}
					}
				}
				//System.out.println(Arrays.toString(color));
				if (check(color, na, nb, nc, map)) {
					out.println("Yes");
					for (int i : color) {
						out.print((char) ('a' + i));
					}
				} else {
					out.println("No");
				}
			} else {
				out.println("No");
			}
		}
		out.close();
	}

	static boolean check(int[] color, int na, int nb, int nc,
			ArrayList<Integer>[] map) {
		LinkedList<Integer> q = new LinkedList();
		q.add(0);
		boolean[] check = new boolean[map.length];
		check[0] = true;
		while (!q.isEmpty()) {
			int node = q.poll();
			int[] count = new int[3];
			for (int i : map[node]) {
				count[color[i]]++;
				if (Math.abs(color[i] - color[node]) > 1) {
					return false;
				}

				if (!check[i]) {
					check[i] = true;
					q.add(i);
				}
			}
			// System.out.println(Arrays.toString(color) + " "
			// + Arrays.toString(count) + " " + color[node] + " " + na
			// + " " + nb + " " + nc);
			if (color[node] == 0) {
				if (count[0] != na - 1 || count[1] != nb) {
					return false;
				}

			} else if (color[node] == 1) {
				if (count[0] != na || count[1] != nb - 1 || count[2] != nc) {
					return false;
				}
			} else {
				if (count[1] != nb || count[2] != nc - 1) {
					return false;
				}
			}
		}
		return true;

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
