import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.lang.Long;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Problem statement: http://codeforces.com/problemset/problem/431/D
 * 
 * @author thepham
 * 
 */

public class E_Round_294_Div2 {
	static long Mod = 1000000007;
	static int index = 0;
	static int[] num, stat;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();

		int n = in.nextInt();
	
		ArrayList<Integer>[] map = new ArrayList[n];
		ArrayList<Integer>[] q = new ArrayList[n];
		ArrayList<Integer>[] replace = new ArrayList[n];
		int[] pa = new int[n];
		num = new int[n];
		for (int i = 0; i < n; i++) {
			map[i] = new ArrayList();
			q[i] = new ArrayList();
			replace[i] = new ArrayList();
			pa[i] = i;
		}
		for (int i = 0; i < n - 1; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			map[a].add(b);
			map[b].add(a);
		}

		int m = in.nextInt();
		stat = new int[m];
		int[][] query = new int[m][2];
		for (int i = 0; i < m; i++) {
			query[i][0] = in.nextInt() - 1;
			query[i][1] = in.nextInt() - 1;
			q[query[i][0]].add(i);
			q[query[i][1]].add(i);
		}
		int[] dist = new int[n];
		Arrays.fill(dist, -1);
		LinkedList<Integer> list = new LinkedList();
		list.add(0);
		dist[0] = 0;
		while (!list.isEmpty()) {
			int node = list.removeFirst();
			for (int i : map[node]) {
				if (dist[i] == -1) {
					dist[i] = 1 + dist[node];
					list.add(i);
				}
			}
		}
		int[] result = new int[m];
		Arrays.fill(result, -2);
		visit(0, pa, dist, result, new boolean[n], replace, map, q, query);
		visit2(0, new boolean[n], new int[n + 1], dist, result, map, replace);
		for (int i = 0; i < m; i++) {
			out.println(stat[i]);
		}

		out.close();
	}

	public static int visit(int node, int[] union, int[] dist, int[] result,
			boolean[] visit, ArrayList<Integer>[] replace,
			ArrayList<Integer>[] map, ArrayList<Integer>[] q, int[][] qu) {
		visit[node] = true;
		num[node] = 1;
		for (int j : map[node]) {
			if (!visit[j]) {
				num[node] += visit(j, union, dist, result, visit, replace, map,
						q, qu);
				union[j] = node;
			}
		}
		for (int j : q[node]) {
			int other = qu[j][0] == node ? qu[j][1] : qu[j][0];
			if (visit[other] && result[j] == -2) {
				int ans = get(other, union);
				int d = dist[other] - 2 * dist[ans] + dist[node];

				if (d % 2 == 0) {
					result[j] = Math.max(dist[other], dist[node]) - d / 2;
					if (dist[other] > dist[node]) {
						replace[other].add(j);
					} else if (dist[other] < dist[node]) {
						replace[node].add(j);
					} else {
						replace[node].add(j);
						replace[other].add(j);
					}
				} else {
					result[j] = -1;
				}

			}
		}
		return num[node];
	}

	public static void visit2(int node, boolean[] visit, int[] table,
			int[] dist, int[] result, ArrayList<Integer>[] map,
			ArrayList<Integer>[] q) {
		visit[node] = true;
		table[dist[node]] = node;
		for (int i : map[node]) {

			if (!visit[i]) {

				visit2(i, visit, table, dist, result, map, q);

			}
		}
		for (int i : q[node]) {
			if (result[i] != -1) {
				if (result[i] < dist[node]) {
					
					if(stat[i] == 0){
						stat[i] = num[table[result[i]]] - num[table[result[i] + 1]]; 
					}else{
						stat[i] += map.length - num[table[result[i]]] - num[table[result[i] + 1]];
					}
				} else {
					stat[i] = map.length;
				}
			}
		}

	}

	public static int get(int node, int[] union) {
		if (node != union[node]) {
			return union[node] = get(union[node], union);
		}
		return node;
	}

	public static int[] buildKMP(String val) {
		int[] data = new int[val.length() + 1];
		int i = 0;
		int j = -1;
		data[0] = -1;
		while (i < val.length()) {
			while (j >= 0 && val.charAt(i) != val.charAt(j)) {
				j = data[j];
			}
			i++;
			j++;

			data[i] = j;
			// System.out.println(val + " " + i + " " + data[i]);
		}
		return data;
	}

	static int find(int index, int[] u) {
		if (u[index] != index) {
			return u[index] = find(u[index], u);
		}
		return index;
	}

	static int crossProduct(Point a, Point b) {
		return a.x * b.y + a.y * b.x;
	}

	static long squareDist(Point a) {
		long x = a.x;
		long y = a.y;
		return x * x + y * y;
	}

	static Point minus(Point a, Point b) {
		return new Point(a.x - b.x, a.y - b.y);
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

	static class Point {
		int x, y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public String toString() {
			return "{" + x + " " + y + "}";
		}

	}

	static long gcd(long a, long b) {
		if (b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}

	public static class FT {

		int[] data;

		FT(int n) {
			data = new int[n];
		}

		public void update(int index, int value) {
			while (index < data.length) {
				data[index] += value;
				index += (index & (-index));
			}
		}

		public int get(int index) {
			int result = 0;
			while (index > 0) {
				result += data[index];
				index -= (index & (-index));
			}
			return result;

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
			// FileInputStream(
			// new File("input.txt"))));
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
