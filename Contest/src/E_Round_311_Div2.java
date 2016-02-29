import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
public class E_Round_311_Div2 {

	public static long MOD = 1000000007;
	static boolean[][] dp;
	static int[] max, total;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		FastReader in = new FastReader(System.in);
		char[] line = in.nextCharArray();
		int h = in.nextInt();
		int n = line.length;

		dp = new boolean[n][n];
		max = new int[n];
		total = new int[n];
		max[0] = -1;
		for (int i = n - 1; i >= 0; i--) {
			for (int j = i; j < n; j++) {
				if (line[i] == line[j] && (j - i + 1 < 6 || dp[i + 2][j - 2])) {
					dp[i][j] = true;
					max[i] = j;
					total[i]++;

				}
			}

		}
		Node root = new Node();
		root.nxt[0] = new Node();
		root.nxt[1] = new Node();
		// System.out.println(Arrays.toString(max));
		for (int i = 0; i < n; i++) {
			if (max[i] >= i) {
				add(i, i, max[i], line, root.nxt[line[i] - 'a']);
			}
		}
		// System.out.println(root.count);
		get(h, root, out);

		out.close();
	}

	static class Node {

		Node[] nxt = new Node[2];
		int count;
		int end;

	}

	static void get(int k, Node node, PrintWriter out) {
		while (k > 0) {
			k -= node.end;
			if (k <= 0) {
				return;
			}

			if (node.nxt[0] != null && k <= node.nxt[0].count) {
				out.print('a');
				node = node.nxt[0];
			} else {
				out.print('b');
				k -= node.nxt[0] != null ? node.nxt[0].count : 0;
				node = node.nxt[1];
			}
		}

	}

	static void add(int index, int i, int j, char[] line, Node node) {

		while (index <= j) {
			node.count += total[i];
			if (dp[i][index]) {
				node.end++;
				total[i]--;
			}
			if (index + 1 <= j) {
				int nxt = line[index + 1] - 'a';
				if (node.nxt[nxt] == null) {
					node.nxt[nxt] = new Node();
				}
				node = node.nxt[nxt];
			}
			index++;
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

	static class FastReader extends BufferedReader {
		private StringTokenizer stringTokenizer;

		public FastReader(InputStream inputStream) {
			super(new InputStreamReader(inputStream));
		}

		public String nextToken() {
			try {
				while (stringTokenizer == null
						|| !stringTokenizer.hasMoreElements()) {
					stringTokenizer = new StringTokenizer(readLine());
				}
				return stringTokenizer.nextToken();
			} catch (IOException e) {
				return null;
			}
		}

		public int nextInt() {
			return Integer.parseInt(nextToken());
		}

		public char[] nextCharArray() {
			String next = nextToken();
			if (next == null) {
				return null;
			}
			return next.toCharArray();
		}

	}

}
