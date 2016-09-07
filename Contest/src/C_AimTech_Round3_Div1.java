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
public class C_AimTech_Round3_Div1 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		ArrayList<Integer>[] map = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			map[i] = new ArrayList();

		}
		for (int i = 0; i < n - 1; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			map[a].add(b);
			map[b].add(a);
		}
		int[] pa = new int[n];
		int[] count = new int[n];
		int[][] max = new int[n][2];
		for (int[] a : max) {
			Arrays.fill(a, -1);
		}
		cal(0, -1, pa, count, max, map);
		boolean[] check = new boolean[n];
		//System.out.println(Arrays.toString(count));
		cal2(0, 0, check, pa, count,max, map);
		for (boolean v : check) {
			if (v) {
				out.print(1 + " ");
			} else {
				out.print(0 + " ");
			}
		}
		out.close();
	}

	static void cal(int index, int p, int[] pa, int[] count, int[][] max,
			ArrayList<Integer>[] map) {
		count[index] = 1;
		pa[index] = p;
		int n = map.length;
		for (int i : map[index]) {
			if (i != p) {
				cal(i, index, pa, count, max, map);
				count[index] += count[i];
				if (count[i]*2 <= n ) {
					insert(max[index], index, i, count);
				}
				for (int j = 0; j < 2; j++) {
					insert(max[index], index, max[i][j], count);
				}
			}
		}

	}

	static void insert(int[] max, int index, int other, int[] count) {
		int cur = other;
		for (int i = 0; i < 2 && cur != -1; i++) {
			if (max[i] == -1 || count[max[i]] < count[cur]) {
				int tmp = max[i];
				max[i] = cur;
				cur = tmp;

			}
		}
	}

	static void cal2(int index, int maxPa, boolean[] check, int[] pa,
			int[] count, int[][] max, ArrayList<Integer>[] map) {
		int cur = -1;
		int n = map.length;
		int[] tmp = new int[2];
		Arrays.fill(tmp, -1);
		for (int i : map[index]) {
			int v = count[i];
			if (i == pa[index]) {
				v = n - count[index];
			}
			if (v * 2 > n) {
				cur = i;
				if (i != pa[index]) {
					insert(tmp, index, max[i][0], count);
				}
			} else {
				if (i != pa[index]) {
					insert(tmp, index, i, count);
				}
			}
		}
		//System.out.println(index + " " + cur + " " + pa[index] + " " + maxPa + " " + Arrays.toString(tmp) + " " + (cur!= -1? Arrays.toString(max[cur]) : ""));
		if (cur != -1) {
			if (cur == pa[index]) {
				int v = n - count[index];
				if (maxPa != -1 && (v - maxPa) * 2 <= n) {
					check[index] = true;
				}
			} else {
				if (max[cur][0] != -1
						&& (count[cur] - count[max[cur][0]]) * 2 <= n) {
					check[index] = true;
				}
			}
		}else{
			check[index] = true;
		}
		for(int i : map[index]){
			if(i != pa[index]){
				int v = n - count[i];
				if(v <= n/2){
					cal2(i, v , check, pa, count, max, map);
				}else{
					if(tmp[0] != -1 && tmp[0] != i && tmp[0] != max[i][0]){
						cal2(i,  Math.max(maxPa, count[tmp[0]]) , check, pa, count, max, map);
					}else if(tmp[1] != -1){
						cal2(i,  Math.max(maxPa, count[tmp[1]]) , check, pa, count, max, map);
					}else{
						cal2(i, maxPa , check, pa, count, max, map);
					}
				}
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
