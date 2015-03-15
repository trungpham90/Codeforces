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
 * 
 * 
 * 3 2 3 5 -2 -1 4 -1 2 7 3
 * 
 * 10 1 -10617 30886 -7223 -63085 47793 -61665 -14614 60492 16649 -58579 3 8 1
 * 10 4 7 1 7 3 7
 * 
 * 22862 -34877
 * 
 * @author pttrung
 */
public class E_Round_63_Div2 {

	public static long MOD = 1000000007;
	static int[] trace, last, first, sum;
	static int index = 0;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int k = in.nextInt();

		Node[] data = new Node[n];
		for (int i = 0; i < n; i++) {
			data[i] = new Node(in.nextInt(), i);
		}
		Arrays.sort(data, new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				if (o1.val != o2.val) {
					return Long.compare(o1.val, o2.val);
				}
				return o1.index - o2.index;
			}
		});
		Segment last = null;
		ArrayList<Segment> list = new ArrayList();

		for (int i = 0; i < n; i++) {
			int s = Math.max(0, data[i].index - k + 1);
			int e = data[i].index;

			if (last != null && last.v == data[i].val) {

				if (last.s <= s && last.e >= s) {
					if (list.size() > 0) {
						Segment tmp = list.get(list.size() - 1);
						if (tmp.s <= s && tmp.e >= s) {
							if (tmp.s < s) {
								tmp.e = s - 1;
							} else {
								list.remove(list.size() - 1);
							}
						} else if (s <= tmp.s) {
							list.remove(list.size() - 1);
						}
					}

					if (last.e < e) {
						list.add(new Segment(last.e + 1, e, data[i].val));
					}

				} else {
					list.add(new Segment(s, e, data[i].val));
				}
			} else {
				list.add(new Segment(s, e, data[i].val));
			}

			last = new Segment(s, e, data[i].val);

		}
		TreeSet<Node> set = new TreeSet();
		ArrayList<End> points = new ArrayList();
		for (Segment s : list) {
			points.add(new End(s.s, s.v, true));
			points.add(new End(s.e, s.v, false));
		}

		Collections.sort(points);

		ArrayList<Segment> result = new ArrayList();
		int start = 0;
		for (End end : points) {
			Node node = new Node(end.value, end.index);
			// System.out.println(node + " " + set + " " + start);
			if (set.isEmpty()) {
				set.add(node);
				start = Math.max(start, node.index);
			} else if (set.contains(node)) {
				// System.out.println("Contains " + set.last().val);
				if (set.last().val == node.val && node.index >= start) {
					result.add(new Segment(start, node.index, node.val));
					start = node.index + 1;
				}

				set.remove(node);
			} else {
				if (set.last().val < node.val) {
					int e = node.index - 1;
					if (start <= e) {
						result.add(new Segment(start, e, set.last().val));
					}
					start = node.index;
				}
				set.add(node);
			}
		}
		// System.out.println(result);
		// System.out.println(list);
		int index = 0;
		for (int i = 0; i < n - k + 1; i++) {
			if (index < result.size()) {
				if (result.get(index).s <= i && result.get(index).e >= i) {
					out.println(result.get(index).v);
				} else if (result.get(index).e < i) {
					index++;
					if (index < result.size() && result.get(index).s <= i
							&& result.get(index).e >= i) {
						out.println(result.get(index).v);
					} else {
						out.println("Nothing");
					}
				} else {
					out.println("Nothing");
				}
			} else {
				out.println("Nothing");
			}
		}

		out.close();
	}

	static class End implements Comparable<End> {
		int index;
		long value;
		boolean start;

		public End(int index, long value, boolean start) {
			super();
			this.index = index;
			this.value = value;
			this.start = start;
		}

		@Override
		public int compareTo(End o) {
			if (index != o.index) {
				return index - o.index;
			}
			if (start == o.start) {
				return Long.compare(o.value, value);
			} else {
				if (start) {
					return -1;
				} else {
					return 1;
				}
			}

		}

	}

	static class Segment {

		int s, e;
		long v;

		public Segment(int s, int e, long v) {
			this.s = s;
			this.e = e;
			this.v = v;
		}

		public String toString() {
			return s + " " + e + " " + v;
		}
	}

	static class Node implements Comparable<Node> {

		long val;
		int index;

		public Node(long val, int index) {
			this.val = val;
			this.index = index;
		}

		@Override
		public int compareTo(Node o) {
			if (val != o.val) {
				return Long.compare(val, o.val);
			}
			return 0;
		}

		public String toString() {
			return index + " " + val;
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
