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
public class D_FB_2016_Round2 {

	public static long MOD = 1000000007;
	static int min;
	static long[][][] dp;

	public static void main(String[] args) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(new FileOutputStream(new File(
				"output.txt")));
		// PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		long start = System.currentTimeMillis();
		int T = in.nextInt();
		for (int z = 0; z < T; z++) {
			int n = in.nextInt();
			int k = in.nextInt();
			int p = in.nextInt();
			dp = new long[k + 1][k + 1][n];
			int[][] cost = new int[n][k];
			ArrayList<Integer>[] map = new ArrayList[n];
			for (int i = 0; i < n; i++) {
				map[i] = new ArrayList();
				for (int j = 0; j < k; j++) {
					cost[i][j] = in.nextInt();
				}
			}
			for (int i = 0; i < n - 1; i++) {
				int a = in.nextInt() - 1;
				int b = in.nextInt() - 1;
				map[a].add(b);
				map[b].add(a);
			}
			long result = Long.MAX_VALUE;
			for (int i = 0; i < k; i++) {
				long v = cal(0, 0, i, k, p, k, cost, map);
				// System.out.println(v + " " + i);
				result = result < v ? result : v;
			}
			out.println("Case #" + (z + 1) + ": " + result);

		}
		System.out.println(System.currentTimeMillis() - start);
		out.close();
	}

	static long cal(int index, int pa, int color, int paColor, int p, int k,
			int[][] cost, ArrayList<Integer>[] map) {
		if (map[index].size() - (index == pa ? 0 : 1) == 0) {
			return cost[index][color];
		}
		if (dp[paColor][color][index] != 0) {
			return dp[paColor][color][index];
		}
		long[][] child = new long[map[index].size() - (index == pa ? 0 : 1)][k];
		int cur = 0;
		long result = cost[index][color];
		for (int i = 0; i < map[index].size(); i++) {
			if (map[index].get(i) != pa) {
				for (int j = 0; j < k; j++) {
					child[cur][j] = cal(map[index].get(i), index, j, color, p,
							k, cost, map);
				}
				cur++;
			}
		}
		// System.out.println("CUR " + index);
		// for(long[]a : child){
		// System.out.println(Arrays.toString(a));
		// }
		if (map[index].size() > k) {
			result += p;
			for (long[] a : child) {
				Arrays.sort(a);
				result += a[0];
			}
		} else {
			int total = k + map[index].size() + (index == pa ? 2 : 0);
			int first = map[index].size() + (index == pa ? 1 : 0);
			int[][] flow = new int[total][total];
			long[][] other = new long[map[index].size() - (index == pa ? 0 : 1)][k
					- (index == pa ? 0 : 1)];
			for (int i = 0; i < other.length; i++) {
				int tmp = 0;
				for (int j = 0; j < k; j++) {
					if (j == paColor) {
						continue;
					}
					other[i][tmp++] = child[i][j];
				}
			}
			for (int i = 0; i < map[index].size() - (index == pa ? 0 : 1); i++) {
				flow[0][i + 1] = 1;
				for (int j = 0; j < k - (index == pa ? 0 : 1); j++) {
					flow[i + 1][j + first] = 1;
					flow[j + first][total - 1] = 1;
				}
			}
			long tmp = p;
			for (long[] a : child) {
				Arrays.sort(a);
				tmp += a[0];
			}
			// System.out.println("Start " + index);
			long mf = MCMF(flow, other, map[index].size()
					- (index == pa ? 0 : 1), total, first);
			// System.out.println("MCMF " + mf + " " + index);
			result += tmp < mf ? tmp : mf;

		}
		// System.out.println(index + " " + color + " " + pa + " " + paColor +
		// " "
		// + result);
		return dp[paColor][color][index] = result;
	}

	static long MCMF(int[][] flow, long[][] cost, int numChild, int total,
			int first) {
		int[] p = new int[total];
		long[] dist = new long[total];
		long result = 0;
		int c = 0;
		while (true) {
			min = Integer.MAX_VALUE;
			Arrays.fill(p, -1);
			p[0] = 0;
			Arrays.fill(dist, Long.MAX_VALUE);
			dist[0] = 0;
			PriorityQueue<Point> q = new PriorityQueue<>();
			q.add(new Point(0, 0));

			while (!q.isEmpty()) {
				Point tmp = q.poll();
				if (tmp.x == dist[tmp.y]) {
					for (int i = 0; i < total; i++) {
						if (flow[tmp.y][i] > 0) {
							long v = calCost(tmp.y, i, cost, numChild, total,
									first);
							// System.out.println("COST " + v + " " + tmp.y +
							// " " + i);
							if (dist[tmp.y] + v < dist[i]) {
								p[i] = tmp.y;
								dist[i] = dist[tmp.y] + v;
								q.add(new Point(dist[i], i));
							}
						}
					}
				}
			}
			if (p[total - 1] == -1) {
				break;
			}
			update(total - 1, p, flow);
			if (min == 0) {
				break;
			}
			// System.out.println(dist[total - 1] + " " + Arrays.toString(p));
			result += dist[total - 1];
			c += min;
		}
		// System.out.println(numChild + " " + c + " " + result);
		return result;
	}

	static void update(int index, int[] pa, int[][] flow) {
		if (pa[index] != index) {
			min = Math.min(min, flow[pa[index]][index]);
			update(pa[index], pa, flow);
			flow[pa[index]][index] -= min;
			flow[index][pa[index]] += min;
		}
	}

	static long calCost(int a, int b, long[][] cost, int numChild, int total,
			int first) {
		if (a == 0 || b == total - 1) {
			return 0;
		}

		if (a > 0 && a <= numChild && b >= first) {
			return cost[a - 1][b - first];
		}
		if (a >= first && b > 0 && b <= numChild) {
			return -cost[b - 1][a - first];
		}
		return 0;
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

		long x;
		int y;

		public Point(long start, int end) {
			this.x = start;
			this.y = end;
		}

		@Override
		public int compareTo(Point o) {
			return Long.compare(x, o.x);
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
			// br = new BufferedReader(new InputStreamReader(System.in));
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					new File("costly_labels.txt"))));
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
