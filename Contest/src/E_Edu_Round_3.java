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
public class E_Edu_Round_3 {

	public static long MOD = 1000000007;
	
	

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		Edge[] data = new Edge[m];
		for (int i = 0; i < m; i++) {
			data[i] = new Edge(i, in.nextInt() - 1, in.nextInt() - 1,
					in.nextInt());
		}
		Arrays.sort(data);
		boolean[] used = new boolean[m];
		int[] u = new int[n];
		ArrayList<Edge>[] map = new ArrayList[n];
		ArrayList<Edge>[]other = new ArrayList[n];
		int[]anc = new int[n];
		for (int i = 0; i < n; i++) {
			u[i] = i;
			other[i] =new ArrayList();
			map[i] = new ArrayList();
		}
		long total = 0;
		for (Edge e : data) {
			if (find(e.u, u) != find(e.v, u)) {
				used[e.index] = true;
				u[find(e.u, u)] = find(e.v, u);
				map[e.u].add(e);
				map[e.v].add(e);
				total += e.w;
			}else{
			//	System.out.println(e.index);
				other[e.u].add(e);
				other[e.v].add(e);
			}
		}

		
		int[] tree = new int[ 8* n + 1];
		int[]re = new int[m];
		tarjan(0, 0, new int[n], re, new boolean[n] , map, other);
		//.out.println(Arrays.toString(re));
		int[]max = new int[m];
		cal(0, 0 , 0, new int[n], re, max, map, other, tree);
		
		//System.out.println(Arrays.toString(first)  + " " + Arrays.toString(last));
		long[] result = new long[m];
		for(Edge e : data){
			if(used[e.index]){
				result[e.index] = total;
			}else{
				result[e.index] = total - max[e.index] + e.w;
			}
		}
		for (long i : result) {
			out.println(i);
		}
		out.close();
	}
	
	static void cal(int index, int pa, int cur, int[]level, int[]re,int[]max, ArrayList<Edge>[]map, ArrayList<Edge>[]other, int[]data){
		level[index] = cur;
		for(Edge e : map[index]){
			int nxt = e.u == index ? e.v : e.u;
			if(nxt != pa){
				update(0, cur + 1, (int)e.w, 0, 2*map.length , data);
				cal(nxt, index, cur + 1, level, re,max, map, other, data);
				update(0, cur + 1, 0, 0, 2*map.length, data);
			}
		}
		for(Edge e : other[index]){
			max[e.index] = Math.max(get(0, 0, 2* map.length , level[re[e.index]] + 1 , level[index] , data) , max[e.index]);
		}
	}
	
	static void tarjan(int index, int pa, int[]anc, int[]re, boolean[]color, ArrayList<Edge>[]map, ArrayList<Edge>[]other){
		anc[index] = index;
		for(Edge e : map[index]){
			int nxt = e.u == index ? e.v : e.u;
			if(nxt != pa){
				tarjan(nxt, index, anc, re,color, map, other);
				anc[find(nxt, anc)] = index; 
			}
		}
		color[index] = true;
		for(Edge e : other[index]){
			int nxt = e.u == index ? e.v : e.u;
			if(color[nxt]){
				//System.out.println(e.index + " " + nxt + " " + index);
				re[e.index] = find(nxt, anc);
			}
		}
	}

	

	static int get(int index, int l, int r, int l1, int r1, int[] data) {
		if (l > r1 || r < l1) {
			return 0;
		}
		if (l1 <= l && r <= r1) {
			return data[index];
		}
		int mid = (l + r) / 2;
		int a = get(left(index), l, mid, l1, r1, data);
		int b = get(right(index), mid + 1, r, l1, r1, data);
		return Math.max(a, b);
	}

	static void update(int index, int p, int v, int l, int r, int[] data) {
		//System.out.println(index + " " + l + " " + r);
		if (l > p || r < p) {
			return;
		}
		if (l == p && r == p) {
			data[index] = v;
			return;
		}
		int mid = (l + r) / 2;
		update(left(index), p, v, l, mid, data);
		update(right(index), p, v, mid + 1, r, data);
		data[index] = Math.max(data[left(index)], data[right(index)]);

	}

	static int left(int index) {
		return 2 * index + 1;
	}

	static int right(int index) {
		return 2 * index + 2;
	}

	static int find(int index, int[] u) {
		if (u[index] != index) {
			return u[index] = find(u[index], u);
		}
		return index;
	}

	static class Edge implements Comparable<Edge> {
		int index, u, v;
		long w;

		public Edge(int index, int u, int v, long w) {
			super();
			this.index = index;
			this.u = u;
			this.v = v;
			this.w = w;
		}

		@Override
		public int compareTo(Edge o) {
			// TODO Auto-generated method stub
			return Long.compare(w, o.w);
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
