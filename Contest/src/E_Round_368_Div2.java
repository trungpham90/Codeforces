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
public class E_Round_368_Div2 {

	public static long MOD = 1000000007;
	static FT root;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		int k = in.nextInt();
		ArrayList<Rec>[] list = new ArrayList[k];
		int[][] data = new int[k][];
		for (int i = 0; i < k; i++) {
			list[i] = new ArrayList();
			int l = in.nextInt();
			data[i] = new int[l];
			for (int j = 0; j < l; j++) {
				int x = in.nextInt();
				int y = in.nextInt();
				data[i][j] = in.nextInt();
				list[i].add(new Rec(x, x, y, j, Rec.Type.BULB));
			}
			Collections.sort(list[i]);
		}
		int q = in.nextInt();
		ArrayList<Rec> rec = new ArrayList<>();
		String[] query = new String[q];
		int num = 0;
		for (int i = 0; i < q; i++) {
			query[i] = in.nextLine();
			String[] tmp = query[i].split(" ");
			if ("ASK".equals(tmp[0])) {
				int x1 = Integer.parseInt(tmp[1]);
				int y1 = Integer.parseInt(tmp[2]);
				int x2 = Integer.parseInt(tmp[3]);
				int y2 = Integer.parseInt(tmp[4]);
				rec.add(new Rec(x1, x2, y1, num, Rec.Type.OPEN));
				rec.add(new Rec(x1, x2, y2, num, Rec.Type.CLOSE));
				num++;
			}
		}
		long[][] hold = new long[num][k];

		Collections.sort(rec);
		if (!rec.isEmpty()) {
			root = new FT(n + 1);

			for (int i = 0; i < k; i++) {
				// System.out.println("Start");

				for (int x = 0, y = 0; y < rec.size();) {
					Rec r;

					if (x == list[i].size()) {
						r = rec.get(y++);
					} else if (list[i].get(x).compareTo(rec.get(y)) < 0) {
						r = list[i].get(x++);
					} else {
						r = rec.get(y++);
					}
					// System.out.println(r);
					if (r.type == Rec.Type.BULB) {
						update(r.x1, data[i][r.index]);
					} else if (r.type == Rec.Type.OPEN) {
						hold[r.index][i] -= get(r.x1, r.x2);
					} else {
						hold[r.index][i] += get(r.x1, r.x2);

					}
				}

			}

			boolean[] off = new boolean[k];
			int index = 0;
			for (int i = 0; i < q; i++) {
				if (query[i].startsWith("SWITCH")) {
					int v = Integer.parseInt(query[i].split(" ")[1]) - 1;
					off[v] = !off[v];
				} else {
					long result = 0;
					// System.out.println(hold[index].size());
					for (int j = 0; j < k; j++) {
						if (!off[j]) {

							result += hold[index][j];
						}
					}
					index++;
					out.println(result);
				}
			}
		}

		out.close();
	}

	static void update(int pos, int v) {
		root.update(pos, v);
	}

	static long get(int l, int r) {
		long re = root.get(r);
		if (l > 1) {
			re -= root.get(l - 1);
		}
		return re;

	}

	static class Rec implements Comparable<Rec> {
		enum Type {
			BULB, OPEN, CLOSE;
		}

		int x1, x2, y, index;
		Type type;

		public Rec(int x1, int x2, int y, int index, Type type) {
			super();
			this.x1 = x1;

			this.x2 = x2;
			this.y = y;
			this.index = index;
			this.type = type;
		}

		@Override
		public int compareTo(Rec o) {
			if (y != o.y) {
				return Integer.compare(y, o.y);
			} else if (type != o.type) {
				if (type == Rec.Type.OPEN) {
					return -1;
				} else if (type == Rec.Type.CLOSE) {
					return 1;
				} else {
					return o.type == Rec.Type.OPEN ? 1 : -1;
				}
			}
			return 0;
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

	public static class Point {

		long x, y;

		public Point(long start, long end) {
			this.x = start;
			this.y = end;
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
