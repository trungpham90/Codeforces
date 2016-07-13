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
public class D_Round_260_Div1 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int size = (int) Math.max(1, Math.ceil(Math.sqrt(n)));
		
		ArrayList<Integer>[] list = new ArrayList[size];
		HashMap<Integer, Integer>[] map = new HashMap[size];
		for (int i = 0; i < size; i++) {
			list[i] = new ArrayList(n/size);
			map[i] = new HashMap();
		}
		for (int i = 0; i < n; i++) {
			int v = in.nextInt();
			int index = i / size;
			list[index].add(v);
			if (map[index].containsKey(v)) {
				map[index].put(v, map[index].get(v) + 1);
			} else {
				map[index].put(v, 1);
			}
		}

		int q = in.nextInt();
		int last = 0;
		for (int i = 0; i < q; i++) {
			int t = in.nextInt();
			int l = (in.nextInt() - 1 + last ) % n;
			int r = (in.nextInt() - 1 + last ) % n;
			if (l > r) {
				int tmp = l;
				l = r;
				r = tmp;
			}
			
			if (t == 1) {
				if(l == r){
					continue;
				}
				int v = remove(r, list, map);
				insert(l, v, list, map);

			} else {
				int v = (in.nextInt() - 1 + last ) % n + 1;
				v = count(l, r, v, list, map);
				out.println(v);
				last = v;
			}
			
		}
		out.close();
	}

	public static int count(int l, int r, int v, ArrayList<Integer>[] list,
			HashMap<Integer, Integer>[] map) {
		int result = 0;
		int total = 0;
		for (int i = 0; i < list.length && total <= r; i++) {
			if (l <= total && total + list[i].size() - 1 <= r) {
				result += map[i].containsKey(v) ? map[i].get(v) : 0;
			} else if (l <= total && total <= r) {
				for (int j = 0; j < list[i].size() && j + total <= r; j++) {
					if (list[i].get(j) == v) {
						result++;
					}
				}
			} else if (l <= total + list[i].size() - 1
					&& total + list[i].size() - 1 <= r) {
				for (int j = l - total; j < list[i].size(); j++) {
					if ( list[i].get(j) == v) {
						result++;
					}
				}
			}else if(total <= l && r <= total + list[i].size() - 1){
				for(int j = 0; j < list[i].size(); j++){
					if(l <= total + j && total + j <= r){
						if(list[i].get(j) == v){
							result++;
						}
					}
				}
			}
			total += list[i].size();
		}
		return result;
	}

	public static int remove(int index, ArrayList<Integer>[] list,
			HashMap<Integer, Integer>[] map) {
		int total = 0;
		int result = -1;
		for (int i = 0; i < list.length; i++) {
			if (total + list[i].size() > index) {
				int pos = index - total;
				int v = list[i].get(pos);
				list[i].remove((int) pos);
				map[i].put(v, map[i].get(v) - 1);
				result = v;
				break;
			} else {
				total += list[i].size();
			}
		}
		return result;
	}

	public static void insert(int index, int v, ArrayList<Integer>[] list,
			HashMap<Integer, Integer>[] map) {
		int total = 0;

		for (int i = 0; i < list.length; i++) {
			if (total + list[i].size() > index) {
				int pos = index - total;
			
				list[i].add(pos, v);
				if (!map[i].containsKey(v)) {
					map[i].put(v, 1);
				} else {
					map[i].put(v, map[i].get(v) + 1);
				}
				break;
			} else {
				total += list[i].size();
			}
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