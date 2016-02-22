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
public class C_Round_119_Div1 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		int k = in.nextInt();
		boolean[] check = new boolean[n];
		for (int i = 0; i < k; i++) {
			check[in.nextInt() - 1] = true;
		}
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
		int s = in.nextInt() - 1;
		int t = in.nextInt() - 1;
		int st = 0;
		int ed = n;
		int result = -1;
		int[] dist = new int[n];
		PriorityQueue<Point> q = new PriorityQueue<>();
		while (st <= ed) {
			int mid = (st + ed) >> 1;
			
			if (cal(mid, s, t, dist, check, map)) {
				if (result == -1 || result > mid) {
					result = mid;
				}
				ed = 0;
				for (int i : dist) {
					ed = Math.max(i, ed);
				}
				ed = Math.min(mid - 1, ed);
				
				if(cal(ed,s,t,dist, check,map)){
					if (result == -1 || result > ed) {
						result = ed;
					}
					ed = ed - 1;
				}else{
					st = ed + 1;
					ed = mid - 1;
					
				}
			} else {
				st = mid + 1;
			}
		}
		out.println(result);

		out.close();
	}

	public static boolean cal(int mid, int s, int t, int[] dist,
			boolean[] check, ArrayList<Integer>[] map) {
		PriorityQueue<Point> q = new PriorityQueue<>();

		Arrays.fill(dist, -1);
		dist[s] = 0;
		q.add(new Point(0, s));
		while (!q.isEmpty()) {
			Point p = q.poll();
			if (dist[p.y] == p.x && p.x + 1 <= mid) {
				for (int i : map[p.y]) {
					if (dist[i] == -1 || dist[i] > 1 + p.x) {
						dist[i] = 1 + p.x;
						if (check[i]) {
							dist[i] = 0;
						}
						q.add(new Point(dist[i], i));
					}
				}
			}
		}
		return dist[t] != -1;
	}

	static class Dist implements Comparable<Dist> {

		int pos, max, nearest;

		public Dist(int pos, int max, int nearest) {
			this.pos = pos;
			this.max = max;
			this.nearest = nearest;
		}

		@Override
		public int compareTo(Dist o) {
			if (max != o.max) {
				return Integer.compare(max, o.max);
			}
			return Integer.compare(nearest, o.nearest);
		}

		@Override
		public String toString() {
			return "Dist{" + "pos=" + pos + ", max=" + max + ", nearest="
					+ nearest + '}';
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
