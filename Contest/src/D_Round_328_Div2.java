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
public class D_Round_328_Div2 {

	public static long MOD = 1000000007;
	static int max = 0;
	static int min = Integer.MAX_VALUE;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		ArrayList<Integer>[] map = new ArrayList[n];
		ArrayList<Integer>[] nxt = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			map[i] = new ArrayList();
			nxt[i] = new ArrayList();
		}
		for (int i = 0; i < n - 1; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			map[a].add(b);
			map[b].add(a);
		}
		HashSet<Integer> set = new HashSet();
		int start = 0;
		for (int i = 0; i < m; i++) {
			int v = in.nextInt() - 1;
			set.add(v);
			start = v;
		}
		if (m > 1) {
			buildMap(start, start, false, set, map, nxt);
			int size = 0;
			for(ArrayList<Integer> list : nxt){
				if(!list.isEmpty()){
					size++;
				}
			}
			cal(start, start, nxt);
			//System.out.println(min + " " + max + " " + size);
			out.println((min + 1));
			out.println(((2*(size - 1) - max)));
		} else {
			out.println(start + 1);
			out.println(0);
		}
		out.close();
	}

	static Point cal(int index, int pa, ArrayList<Integer>[] map) {
		Point child = null;
		Point result = new Point(index, 0);
		for (int i : map[index]) {
			if (i != pa) {
				Point x = cal(i, index, map);
				x.y += 1;
				if (child == null) {
					child = x;

				} else {
					int v = child.y + x.y;
					if (v > max) {
						max = v;
						min = Math.min(x.x, child.x);
					} else if (v == max && min > Math.min(x.x, child.x)) {
						min = Math.min(x.x, child.x);
					}
					if (child.y < x.y) {
						child = x;
					} else if (child.y == x.y && child.x > x.x) {
						child = x;
					}
				}
				if (x.y > max) {
					max = x.y;
					min = Math.min(x.x, index);
				} else if (max == x.y && min > Math.min(x.x, index)) {
					min = Math.min(x.x, index);
				}
				if (result.y < x.y) {
					result = x;
				} else if (result.y == x.y && result.x > x.x) {
					result = x;
				}
			}
		}
		if (result.y > max) {
			max = result.y;
			min = Math.min(result.x, index);
		} else if (max == result.y && min > Math.min(result.x, index)) {
			min = Math.min(result.x, index);
		}
		return result;
	}

	public static boolean buildMap(int index, int pa, boolean start,
			HashSet<Integer> set, ArrayList<Integer>[] map,
			ArrayList<Integer>[] nxt) {
		//System.out.println(index + " " + pa + " " + start);
		boolean result = set.contains(index);
		start |= set.contains(index);
		for (int i : map[index]) {
			if (i != pa) {
				boolean v = buildMap(i, index, start || set.contains(index),
						set, map, nxt);
				if (v && start) {
					nxt[index].add(i);
					nxt[i].add(index);
					
				}
				result |= v;
			}
		}
		return result;
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
