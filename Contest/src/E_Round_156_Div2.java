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
public class E_Round_156_Div2 {

	public static long MOD = 1000000007;
	static int[] dp;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		long[] data = new long[n];
		Point[] p = new Point[n];
		for (int i = 0; i < n; i++) {
			data[i] = in.nextLong();
		}
		dp = new int[1000000];
		Arrays.fill(dp, -1);

		long re = 0;
		int lastStart = 0, lastEnd = 0;
		int[] count = new int[10000];
		count[0]++;
		FT tree = new FT(10001);
		tree.update(1, 1);
		dp[0] = 0;
		for (int i = 1; i < dp.length; i++) {
			int min = lastStart;
			int max = lastEnd;
			while (pow(min, 4) < i) {
				min++;
			}
			while (max*max < i && (max + 1)*(max + 1) <= i) {
				max++;
			}
			if(max == i){
				max--;
			}
			//System.out.println(i + " " + min + " " + max);
			if (min > max) {
				dp[i] = 0;
			} else if (min == max) {
				dp[i] = dp[max] == 0 ? 1 : 0;
			} else {
				while (min > lastStart) {
					count[dp[lastStart]]--;
					if (count[dp[lastStart]] == 0) {
						tree.update(dp[lastStart] + 1, -1);
					}
					lastStart++;
				}
				while (lastEnd < max) {
					lastEnd++;
					count[dp[lastEnd]]++;
					if (count[dp[lastEnd]] == 1) {
						tree.update(dp[lastEnd] + 1, 1);
					}
				}
				int result = count.length - 1;
				int st = 1;
				int ed = count.length - 1;
				while (st <= ed) {
					int mid = (st + ed) >> 1;
					if (tree.get(mid) < mid) {
						ed = mid - 1;
						result = Math.min(result, mid - 1);
					} else {
						st = mid + 1;
					}
				}
				//System.out.println(result + " " + i);
				dp[i] = result;
				
			}
			
			
		}
		Arrays.sort(data);
		for(long v : data){
			if(v < dp.length){
				re ^= dp[(int)v];
			}else{
				int min = (int) Math.pow(v, 0.25);
				int max = (int) Math.pow(v, 0.5);
				if(pow(min, 4) < v){
					min++;
				}
				
				while (lastEnd < max) {
					lastEnd++;
					count[dp[lastEnd]]++;
					if (count[dp[lastEnd]] == 1) {
						tree.update(dp[lastEnd] + 1, 1);
					}
				}
				while (min > lastStart) {
					count[dp[lastStart]]--;
					if (count[dp[lastStart]] == 0) {
						tree.update(dp[lastStart] + 1, -1);
					}
					lastStart++;
				}
				int result = count.length - 1;
				int st = 1;
				int ed = count.length - 1;
				while (st <= ed) {
					int mid = (st + ed) >> 1;
					if (tree.get(mid) < mid) {
						ed = mid - 1;
						result = Math.min(result, mid - 1);
					} else {
						st = mid + 1;
					}
				}
				re ^= result;
			}
		}
		
		//System.out.println("HE HE");
		if (re == 0) {
			out.println("Rublo");
		} else {
			out.println("Furlo");
		}

		out.close();
	}

	static int cal(int v) {
		if (v == 0) {
			return 0;
		}
		if(dp[v] != -1){
			return dp[v];
		}
		int min = (int) Math.pow(v, 0.25);
		int max = (int) Math.pow(v, 0.5);
		if (min * min * min * min < v) {
			min++;
		}
		if (max == v) {
			max--;
		}
		TreeSet<Integer> set = new TreeSet();
		for (int i = min; i <= max; i++) {
			set.add(cal(i));
		}
		int result = 0;
		while (set.contains(result)) {
			result++;
		}
		return result;
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
