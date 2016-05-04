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
public class B_CROC2016_Final {

	public static long MOD = 1000000007;
	static int cur = 0;
	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		ArrayList<Integer>[] map = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			map[i] = new ArrayList();
		}
		Edge[] data = new Edge[m];
		for (int i = 0; i < m; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			int c = in.next().charAt(0) == 'B' ? 0 : 1;
			data[i] = new Edge(a, b, c);
			map[a].add(i);
			map[b].add(i);

		}
		//Red
		int[]check = new int[n];
		HashSet<Integer>red = new HashSet();
		boolean ok = true;
		for(int i = 0;i < n; i++){
			//System.out.println(Arrays.toString(check) + " " + i + " " + check[i]);
			if(check[i] == 0){
				
				cur++;
				HashSet<Integer> set = new HashSet();
				int a = cal(i, 1, check, set, 1, data, map);
				cur++;
				HashSet<Integer> other = new HashSet();
				int b = cal(i, -1, check, other, 1, data, map);
				//System.out.println(a + " " + b);
				if(a != -1 && b != -1){
					if(a < b){
						red.addAll(set);
					}else{
						red.addAll(other);
					}
				}else if(a != -1){
					red.addAll(set);
				}else if(b != -1){
					red.addAll(other);
				}else{
					ok = false;
					break;
				}
			}
		}
		//System.out.println(ok);
		
		//Blue
		check = new int[n];
		HashSet<Integer>blue = new HashSet();
		boolean ok1 = true;
		for(int i = 0;i < n; i++){
			if(check[i] == 0){
				
				cur++;
				HashSet<Integer> set = new HashSet();
				int a = cal(i, 1, check, set, 0, data, map);
				cur++;
				HashSet<Integer> other = new HashSet();
				int b = cal(i, -1, check, other, 0, data, map);
				//System.out.println(a + " " + b);
				if(a != -1 && b != -1){
					if(a < b){
						blue.addAll(set);
					}else{
						blue.addAll(other);
					}
				}else if(a != -1){
					blue.addAll(set);
				}else if(b != -1){
					blue.addAll(other);
				}else{
					ok1 = false;
					break;
				}
			}
		}
		if(ok && ok1){
			out.println(Math.min(red.size(), blue.size()));
			if(red.size() < blue.size()){
				for(int i : red){
					out.print((i + 1) + " ");
				}
			}else{
				for(int i : blue){
					out.print((i + 1) + " ");
				}
			}
		}else if(ok){
			out.println(red.size());
			for(int i : red){
				out.print((i + 1) + " ");
			}
		}else if(ok1){
			out.println(blue.size());
			for(int i : blue){
				out.print((i + 1) + " ");
			}
		}else{
			out.println(-1);
		}

		out.close();
	}

	public static int cal(int node, int reverse, int[] status, HashSet<Integer> set, int needColor,
			Edge[] data, ArrayList<Integer>[] map) {
		status[node] = cur;
		if(reverse == 1){
			set.add(node);
		}
		int result = reverse == 1 ? 1 : 0;
		for (int i : map[node]) {
			int nxt = data[i].u == node ? data[i].v : data[i].u;
			if (reverse == 1) {
				if (data[i].c == needColor) {
					if (status[nxt] == cur && !set.contains(nxt)) {
						return -1;
					} else if (status[nxt] != cur) {
						int v = cal(nxt, 1, status, set,needColor, data, map);
						if (v != -1) {
							result += v;
						} else {
							return -1;
						}
					}
				} else {
					if (status[nxt] == cur && set.contains(nxt)) {
						return -1;
					} else if (status[nxt] != cur) {
						int v = cal(nxt, -1, status,set, needColor, data, map);
						if (v != -1) {
							result += v;
						} else {
							return -1;
						}
					}
				}
			} else {
				if (data[i].c == needColor) {
					if (status[nxt] == cur&& set.contains(nxt)) {
						return -1;
					} else if (status[nxt] != cur) {
						int v = cal(nxt, -1, status, set,needColor, data, map);
						if (v != -1) {
							result += v;
						} else {
							return -1;
						}
					}
				} else {
					if (status[nxt] == cur && !set.contains(nxt)) {
						return -1;
					} else if (status[nxt] != cur ) {
						int v = cal(nxt, 1, status,set, needColor, data, map);
						if (v != -1) {
							result += v;
						} else {
							return -1;
						}
					}
				}
			}
		}
		return result;
	}

	static class Edge {
		int u, v, c;

		public Edge(int u, int v, int c) {
			super();
			this.u = u;
			this.v = v;
			this.c = c;
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
			return Integer.compare(x, o.x);
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
