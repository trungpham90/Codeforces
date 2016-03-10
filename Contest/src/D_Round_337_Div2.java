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
public class D_Round_337_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		ArrayList<Rec> ver = new ArrayList();
		ArrayList<Rec> hoz = new ArrayList();
		TreeSet<Integer> set = new TreeSet();
		for (int i = 0; i < n; i++) {
			int[] data = new int[4];
			for (int j = 0; j < 4; j++) {
				data[j] = in.nextInt();
				set.add(data[j]);
			}
			if (data[0] == data[2]) {
				ver.add(new Rec(Math.min(data[0], data[2]), Math.min(data[3],
						data[1]), Math.max(data[0], data[2]), Math.max(data[3],
						data[1])));
			} else {
				hoz.add(new Rec(Math.min(data[0], data[2]), Math.min(data[3],
						data[1]), Math.max(data[0], data[2]), Math.max(data[3],
						data[1])));

			}
		}
		HashMap<Integer, Integer> map = new HashMap();
		ArrayList<Integer> list = new ArrayList();
		list.add(0);
		list.addAll(set);
		int index = 1;
		for (int i : set) {
			map.put(i, index++);
		}
		ArrayList<Edge> nxt = new ArrayList();
		for (Rec r : ver) {
			r.x1 = map.get(r.x1);
			r.y1 = map.get(r.y1);
			r.x2 = map.get(r.x2);
			r.y2 = map.get(r.y2);
			nxt.add(new Edge(r.x1, r.y1, 0, 2));
			nxt.add(new Edge(r.x1, r.y2, 0, 0));
		}
		ArrayList<Edge> tmp = new ArrayList();
		for (Rec r : hoz) {
			r.x1 = map.get(r.x1);
			r.y1 = map.get(r.y1);
			r.x2 = map.get(r.x2);
			r.y2 = map.get(r.y2);
			tmp.add(new Edge(r.x1, r.y1, 0, 2));
			tmp.add(new Edge(r.x2, r.y1, 0, 0));
		}
		int[] store = new int[index];
		int[] count = new int[index];
		Arrays.fill(store, -1);

		Collections.sort(tmp, new Comparator<Edge>() {

			@Override
			public int compare(Edge o1, Edge o2) {
				if (o1.x != o2.x) {
					return Integer.compare(o1.x, o2.x);
				}
				return Integer.compare(o2.o, o1.o);
			}
		});
		long result = 0;
		for (Edge e : tmp) {
			if (e.o == 2) {
				if (store[e.y] == -1) {
					store[e.y] = e.x;
				}
				count[e.y]++;

			} else {
				count[e.y]--;
				if (count[e.y] == 0) {
					result += list.get(e.x) - list.get(store[e.y]) + 1;
					nxt.add(new Edge(store[e.y], e.y, e.x, 1));
					store[e.y] = -1;
				}
			}
		}
		Collections.sort(nxt, new Comparator<Edge>() {

			@Override
			public int compare(Edge o1, Edge o2) {
				if (o1.y != o2.y) {
					return Integer.compare(o1.y, o2.y);
				}
				return Integer.compare(o2.o, o1.o);
			}
		});
		FT tree = new FT(index + 1);
		for (Edge e : nxt) {
			//System.out.println(e);
			if (e.o == 2) {
				if (store[e.x] == -1) {
					store[e.x] = e.y;
					tree.update(e.x, 1);
					
					
				}
				count[e.x]++;

			} else if (e.o == 0) {
				count[e.x]--;
				if (count[e.x] == 0) {
					result += list.get(e.y) - list.get(store[e.x]) + 1;
					store[e.x] = -1;
					tree.update(e.x, -1);
					
				}
			} else {
				int v = (int) (tree.get(e.z) - (e.x > 1 ? tree.get(e.x - 1) : 0));
				result -= v;
			}
		}
		out.println(result);
		out.close();
	}

	static class Edge {
		int x, y, z, o;

		public Edge(int x, int y, int z, int o) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
			this.o = o;
		}

		@Override
		public String toString() {
			return "Edge [x=" + x + ", y=" + y + ", z=" + z + ", o=" + o + "]";
		}

	}

	static class Rec {
		int x1, y1, x2, y2;

		public Rec(int x1, int y1, int x2, int y2) {
			super();
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
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
