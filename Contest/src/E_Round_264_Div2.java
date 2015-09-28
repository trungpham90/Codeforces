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
public class E_Round_264_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int q = in.nextInt();
		int[] data = new int[n];
		ArrayList<Integer>[] map = new ArrayList[n];
		ArrayList<Integer> pr = new ArrayList();
		boolean[] non = new boolean[2000001];
		for (int i = 2; i < non.length; i++) {
			if (!non[i]) {
				pr.add(i);
				for (int j = i + i; j < non.length; j += i) {
					non[j] = true;
				}
			}
		}
		for (int i = 0; i < n; i++) {
			map[i] = new ArrayList();
			data[i] = in.nextInt();
		}
		for (int i = 0; i < n - 1; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			map[a].add(b);
			map[b].add(a);
		}
		int[] result = new int[n];
		int[] p = new int[n];
		int[] d = new int[n];
		initialize(0, 0, 0, new HashMap(), result, data, p, d, pr, map);

		for (int i = 0; i < q; i++) {
			// System.out.println(Arrays.toString(result));
			int t = in.nextInt();
			int v = in.nextInt() - 1;
			if (t == 1) {

				out.println(result[v] + 1);
			} else {
				int w = in.nextInt();
				data[v] = w;
				update(v, w, result, data, p, d, pr, map);
			}
		}

		out.close();
	}

	public static void update(int node, int val, int[] result, int[] data,
			int[] p, int[] d, ArrayList<Integer> pr, ArrayList<Integer>[] map) {
		HashMap<Integer, TreeSet<Point>> list = new HashMap();
		int start = node;
		data[node] = val;
		result[node] = -2;
		while (start != p[start]) {
			int v = data[p[start]];
			for (int i : pr) {
				if (i * i > v) {
					break;

				}
				if (v % i == 0) {
					while (v % i == 0) {
						v /= i;
					}
					if (!list.containsKey(i)) {
						list.put(i, new TreeSet());
					}
					list.get(i).add(new Point(d[p[start]], p[start]));
				}
			}
			if (v != 1) {
				int i = v;
				if (!list.containsKey(i)) {
					list.put(i, new TreeSet());
				}
				list.get(i).add(new Point(d[p[start]], p[start]));
			}
			start = p[start];
		}
		int cur = -1;
		int tmp = -1;
		int v = data[node];
		for (int i : pr) {
			if (i * i > v) {
				break;

			}
			if (v % i == 0) {
				while (v % i == 0) {
					v /= i;
				}
				if (!list.containsKey(i)) {
					list.put(i, new TreeSet());
				} else if (!list.get(i).isEmpty()) {
					Point q = list.get(i).last();
					if (q.x > tmp) {
						tmp = q.x;
						cur = q.y;
					}
				}
				list.get(i).add(new Point(d[node], node));
			}
		}
		if (v != 1) {
			int i = v;
			if (!list.containsKey(i)) {
				list.put(i, new TreeSet());
			} else if (!list.get(i).isEmpty()) {
				Point q = list.get(i).last();
				if (q.x > tmp) {
					tmp = q.x;
					cur = q.y;
				}
			}
			list.get(i).add(new Point(d[node], node));
		}
		if (cur != -1) {
			result[node] = cur;
		}
		for (int i : map[node]) {
			if (i != p[node]) {
				initialize(i, node, d[i], list, result, data, p, d, pr, map);
			}
			
		}
	}

	public static void initialize(int node, int pa, int dist,
			HashMap<Integer, TreeSet<Point>> list, int[] result, int[] data,
			int[] p, int[] d, ArrayList<Integer> pr, ArrayList<Integer>[] map) {
		// System.out.println(node + " " + list);
		
		p[node] = pa;
		result[node] = -2;
		d[node] = dist;
		int cur = -1;
		int tmp = -1;
		int start = data[node];

		for (int i : pr) {
			if (i * i > start) {
				break;
			}
			if (start % i == 0) {
				while (start % i == 0) {
					start /= i;
				}
				if (!list.containsKey(i)) {
					list.put(i, new TreeSet());
				} else if (!list.get(i).isEmpty()) {
					Point q = list.get(i).last();
					if (q.x > tmp) {
						tmp = q.x;
						cur = q.y;
					}
				}
				list.get(i).add(new Point(dist, node));
			}
		}
		if (start != 1) {
			int i = start;
			if (!list.containsKey(i)) {
				list.put(i, new TreeSet());
			} else if (!list.get(i).isEmpty()) {
				Point q = list.get(i).last();
				if (q.x > tmp) {
					tmp = q.x;
					cur = q.y;
				}
			}
			list.get(i).add(new Point(dist, node));
		}
		if (cur != -1) {
			result[node] = cur;
		}
		for (int i : map[node]) {
			if (i != pa) {
				initialize(i, node, dist + 1, list, result, data, p, d, pr, map);
			}
		}
		start = data[node];
		for (int i : pr) {
			if (i * i > start) {
				break;
			}
			if (start % i == 0) {
				while (start % i == 0) {
					start /= i;
				}

				list.get(i).remove(new Point(dist, node));
			}
		}
		if (start != 1) {
			int i = start;

			list.get(i).remove(new Point(dist, node));
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
			if (x != o.x) {
				return x - o.x;
			}
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
