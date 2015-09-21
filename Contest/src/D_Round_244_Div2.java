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
public class D_Round_244_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		String a = in.next();
		String b = in.next();
		Store[]data = new Store[a.length() + b.length()];
		int index = 0;
		for (int i = 0; i < a.length(); i++) {
			data[index++] = new Store(a.substring(i) , 0);
		}
		for (int i = 0; i < b.length(); i++) {
			data[index++] = new Store(b.substring(i), 1);
		}
		Arrays.sort(data);
		//System.out.println(Arrays.toString(data));
		int start = 0;
		int result = -1;
		while(start < data.length){
			int end = start;
			int one = 0;
			int two = 0;
			while(end < data.length && data[end].val.charAt(0) == data[start].val.charAt(0)){
				if(data[end].from == 0){
					one++;
				}else{
					two++;
				}
				end++;
			}
			if(one > 0 && two > 0){
				int v = cal(start, end - 1, 1, data);
				//System.out.println(v + " " + start + " " + (end - 1));
				if(v != -1 && (result == -1 || result > v + 1)){
					result = v + 1;
				}
			}
			start = end;
		}
		out.println(result);

		out.close();
	}
	
	static int cal(int from, int to, int index, Store[]data){
		//System.out.println(from + " " + to);
		if(to - from + 1 == 2){
			return 0;
		}
		while(from <= to && index == data[from].val.length()){
			from++;
		}
		int result = -1;
		int start = from;
		while(start <= to){
			int end = start;
			int one = 0;
			int two = 0;
			while(end <= to && data[end].val.charAt(index) == data[start].val.charAt(index)){
				if(data[end].from == 0){
					one++;
				}else{
					two++;
				}
				end++;
			}
			if(one > 0 && two > 0){
				int v = cal(start , end - 1, index + 1, data);
				if(v != -1){
					if(result == -1 || result > v + 1){
						result = v + 1;
					}
				}
			}
			start = end;
		}
		//System.out.println(from + " " + to + " " + result);
		return result;
				
		
	}

	static class Store implements Comparable<Store> {
		String val;
		int from;

		public Store(String val, int from) {
			super();
			this.val = val;
			this.from = from;
		}

		@Override
		public String toString() {
			return "Store [val=" + val + ", from=" + from + "]";
		}

		@Override
		public int compareTo(Store o) {
		
			return val.compareTo(o.val);
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
