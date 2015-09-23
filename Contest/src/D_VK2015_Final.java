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
public class D_VK2015_Final {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int q = in.nextInt();
		TreeSet<Group> tree = new TreeSet();
		int index = 0;
		int[] u = new int[q];
		for (int i = 0; i < q; i++) {
			u[i] = i;
		}
		for (int i = 0; i < q; i++) {

			int t = in.nextInt();

			int a = in.nextInt();
			int b = in.nextInt();
			if (a > b) {
				int tmp = a;
				a = b;
				b = tmp;
			}
			if (t == 1) {
				if (a == b) {
					continue;
				}

				int x = getGroup(a, tree);
				int y = getGroup(b, tree);
				if (x == -1 && y == -1) {
					int id = index++;
					if (a + 1 < b) {
						tree.add(new Group(a, a, id));
						tree.add(new Group(b, b, id));
					} else {
						tree.add(new Group(a, b, id));
					}
				} else if (x == -1) {
					Group right = tree.floor(new Group(b, b, b));
					if (right.l == a + 1) {
						tree.remove(right);
						right.l = a;
						tree.add(right);
					} else if (a < right.l) {
						tree.add(new Group(a, a, get(y, u)));
					}
				} else if (y == -1) {
					Group left = tree.floor(new Group(a, a, b));
					if (left.r + 1 == b) {
						tree.remove(left);
						left.r = b;
						tree.add(left);
					} else if (left.r < b) {
						tree.add(new Group(b, b, get(x, u)));
					}

				} else {
					x = get(x, u);
					y = get(y, u);

					if (x != y) {
						Group left = tree.floor(new Group(a, a, a));
						Group right = tree.floor(new Group(b, b, b));
						if (left.r + 1 == right.l) {
							tree.remove(left);
							tree.remove(right);
							left.r = right.r;
							tree.add(left);
						}
						u[y] = x;
					}
				}
			} else if (t == 2) {
				if (a == b) {
					continue;
				}
				int cur = a;
				int start = a;
				int end = b;
				int id = -1;
				Group test = tree.floor(new Group(cur, cur, cur));
				if (test != null && test.l <= a && b <= test.r) {
					continue;
				}
				while (true) {
					Group left = tree.floor(new Group(cur, cur, cur));

					if (left != null && (left.r >= a || (get(left.id, u) == id && left.r + 1 == cur))) {
						int tmp = get(left.id, u);
						if (id == -1) {
							id = tmp;
						} else {
							u[tmp] = id;
						}
						if (start > left.l) {
							start = left.l;

						}
						tree.remove(left);
						end = Math.max(left.r, end);
						cur = left.l;
					} else {
						break;
					}
				}
				cur = a;

				while (true) {

					Group right = tree.ceiling(new Group(cur, cur, cur));

					if (right != null
							&& (right.l <= b || (get(right.id, u) == id && cur + 1 == right.l))) {
						int tmp = get(right.id, u);
						if (id == -1) {
							id = tmp;
						} else {
							u[tmp] = id;
						}
						end = Math.max(right.r, end);
						cur = right.r;
						tree.remove(right);
					} else {
						break;
					}

				}
				if (id == -1) {
					id = index++;
				}
				tree.add(new Group(start, end, id));
			} else {

				int x = getGroup(a, tree);
				int y = getGroup(b, tree);

				if (a != b && (x == -1 || y == -1 || (get(x, u) != get(y, u)))) {
					out.println("NO");
				} else {
					out.println("YES");
				}
			}
			// System.out.println(tree);
		}

		out.close();
	}

	static int getGroup(int v, TreeSet<Group> set) {
		Group left = set.floor(new Group(v, v, v));
		if (left == null || left.r < v) {
			return -1;
		}
		return left.id;
	}

	static class Group implements Comparable<Group> {
		int l, r;
		int id;

		public Group(int l, int r, int id) {
			super();
			this.l = l;
			this.r = r;
			this.id = id;
		}

		@Override
		public int compareTo(Group o) {
			// TODO Auto-generated method stub
			return l - o.l;
		}

		@Override
		public String toString() {
			return "Group [l=" + l + ", r=" + r + ", id=" + id + "]";
		}
	}

	static int get(int index, int[] u) {
		if (index == u[index]) {
			return index;
		}
		return u[index] = get(u[index], u);
	}

	static class Slot implements Comparable<Slot> {
		int l, r;
		int g;

		public Slot(int l, int r, int g) {
			super();
			this.l = l;
			this.r = r;
			this.g = g;
		}

		@Override
		public int compareTo(Slot o) {
			// TODO Auto-generated method stub
			return l - o.l;
		}

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
			if (x != o.x)
				return x - o.x;
			return y - o.y;
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
