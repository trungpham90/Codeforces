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
public class E_Round_296_Div2 {

	public static long MOD = 1000000007;
	static int count = 0;
	static int[][] mp, flat;
	static int[] list, progress;

	static boolean[] check;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		ArrayList<int[]> data = new ArrayList();
		long a = System.currentTimeMillis();
		int[] total = new int[n];
		mp = new int[n][];
		progress = new int[n];
		int[] edge = new int[n];
		for (int i = 0; i < m; i++) {
			data.add(new int[] { in.nextInt() - 1, in.nextInt() - 1 });

			total[data.get(i)[0]]++;
			total[data.get(i)[1]]++;
			edge[data.get(i)[0]]++;
			if (data.get(i)[1] != data.get(i)[0]) {
				edge[data.get(i)[1]]++;
			}

		}

		int last = -1;
		for (int i = 0; i < n; i++) {
			if (total[i] % 2 != 0) {
				if (last == -1) {
					last = i;
				} else {
					edge[last]++;

					edge[i]++;

					data.add(new int[] { last, i });
					last = -1;
				}
			}
		}

		if (data.size() % 2 != 0) {
			data.add(new int[] { 0, 0 });

			edge[0]++;
		}
		for (int i = 0; i < n; i++) {
			mp[i] = new int[edge[i]];

		}
		int[] track = new int[n];
		flat = new int[data.size()][2];
		for (int i = 0; i < data.size(); i++) {
			flat[i] = data.get(i);
			mp[flat[i][0]][track[flat[i][0]]++] = i;
			if (flat[i][0] != flat[i][1]) {
				mp[flat[i][1]][track[flat[i][1]]++] = i;
			}
		}
		
		list = new int[data.size() + 1];
		check = new boolean[data.size()];
		eulerCircuit(0);
		
		out.println(data.size());
		boolean rev = true;

		for (int i = 0; i < list.length - 1; i++) {
			if (rev) {
				out.println((list[i + 1] + 1) + " " + (list[i] + 1));
			} else {
				out.println((list[i] + 1) + " " + (list[i + 1] + 1));
			}
			rev = !rev;
		}
		out.close();
	}

	public static void eulerCircuit(int index) {
		int tmp = 1;

		while (progress[index] < mp[index].length) {
			int i = mp[index][progress[index]++];
			if (!check[i]) {
				check[i] = true;
				int nxt = flat[i][0] == index ? flat[i][1] : flat[i][0];
				if (nxt != index) {
					eulerCircuit(nxt);
				} else {
					tmp++;
				}
			}
		}
		for (int i = 0; i < tmp; i++) {
			list[count++] = index;
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
			return Integer.compare(o.x, x);
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
