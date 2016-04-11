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
public class E_Zepto2015 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int q = in.nextInt();
		long[] data = new long[n];
		long sum = 0;
		for (int i = 0; i < n; i++) {
			data[i] = in.nextLong();
			sum += data[i];
		}

		long[] post = new long[2 * n];
		for (int i = 0; i < n; i++) {
			post[i + n] = data[i];
			if (i > 0) {
				post[i + n] += post[i + n - 1];
			}
		}
		for (int i = n - 1; i >= 0; i--) {
			post[i] = data[i];
			if (i + 1 < n) {
				post[i] += post[i + 1];
			}
		}
		//System.out.println(Arrays.toString(post));
		int[] hold = new int[n];
		
		HashMap<Long, Integer> map = new HashMap();
		for (int z = 0; z < q; z++) {

			long v = in.nextLong();
			if (map.containsKey(v)) {
				out.println(map.get(v));
			} else {
				if (sum <= v) {
					out.println(1);
				} else {
					int result = n;
					int a = -1, b = -1;
					int last = 0;
					for (int i = 0; i < n; i++) {
						if(last < i){
							last++;
						}
						//System.out.println("Start " + i + " " + last + " " + get(i, last, n, post) + " " + v);
						while(last + 1 < 2*n && get(i, last + 1, n, post) <= v){
							last++;
						}
						hold[i] = (last + 1) % n;
						//System.out.println(i + " " + hold[i] + " " + last  + " " + v + " " + get(i, last, n, post));
						if(a == -1 || (b - a) > last - i){
							a = i;
							b = last;
						}
					}
					
					b = (b + 1) % n;
					for (int i = a;; i = (i + 1) % n) {

						result = Math.min(result, cal(i, n, v, hold));
						if (i == b) {
							break;
						}
					}
					map.put(v, result);
					out.println(result);
				}
			}
			// System.out.println(a + " " + b + " " + max + " " + v);
		}
		out.close();
	}
	
	static long get(int index, int cur, int n, long[]post){
		if(cur >= n){
			return post[index] + post[cur];
		}else{
			return post[index] - (cur + 1 < n ? post[cur + 1] : 0);
		}
	}

	public static int cal(int start, int n, long v, int[] hold) {
		// System.out.println(start + " " + check[index] + " " + index);
		int result = 0;
		int index = start;
		while (true) {
			result++;

			int nxt = hold[index];
			// System.out.println(nxt + " " + index);
			if (isCross(index, nxt, start, n)) {
				break;
			}
			index = nxt;

		}
		// System.out.println(v + " " + start + " " + result);
		return result;

	}

	static boolean isCross(int s, int e, int x, int n) {
		if (s < x && x <= e) {
			return true;
		} else if (s > e) {
			if (e >= x) {
				return true;
			}
		}
		return false;
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
