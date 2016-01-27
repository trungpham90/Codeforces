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
public class E_Round_147_Div2 {

	public static long MOD = 1000000007;
	static int min;
	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		String t = in.next();
		int n = in.nextInt();
		String[] data = new String[n];
		int[] lim = new int[n];
		for (int i = 0; i < n; i++) {
			data[i] = in.next();
			lim[i] = in.nextInt();
		}
		int total = 2 + n + 26 ;
		int first = 1 + n;
		
		int[][]flow = new int[total][total];
		for(int i = 0; i < n; i++){
			flow[0][i + 1] = lim[i];
			int[]count = new int[26];
			for(int j = 0; j < data[i].length(); j++){
				count[data[i].charAt(j) - 'a']++;
			}
			for(int j = 0; j < 26; j++){
				flow[i + 1][j + first] = count[j];
				
			}
			
		}
		int[]count = new int[26];
		for(int i = 0; i < t.length(); i++){
			count[t.charAt(i) - 'a']++;
			
		}
		for(int i = 0; i < 26; i++){
			flow[i + first][total - 1] = count[i];
		}
		int[]pa = new int[total];
		int[]dist = new int[total];
		int c = 0;
		while(true){
			PriorityQueue<Point> q=  new PriorityQueue<>();
			q.add(new Point(0,0));
			Arrays.fill(pa , -1);
			Arrays.fill(dist, Integer.MAX_VALUE);
			pa[0] = 0;
			dist[0] = 0;
			while(!q.isEmpty()){
				Point p = q.poll();
				if(dist[p.y] == p.x){
					for(int i = 0; i < total; i++){
						if(flow[p.y][i] > 0){
							int cst = cost(p.y, i, first,  total);
							if(dist[i] > cst + p.x){
								dist[i] = cst + p.x;
								q.add(new Point(dist[i], i));
								pa[i] = p.y;
							}
						}
					}
				}
			}
			//System.out.println(dist[total - 1]);
			min = Integer.MAX_VALUE;
			if(pa[total - 1] == -1){
				break;
			}
			update(total - 1, pa, flow);
			if(min == 0){
				break;
			}
			c += min; 
		}
		if(c < t.length()){
			out.println(-1);
		}else{
			int result = 0;
			for(int i = 0; i < n; i++){
				
				result += (i + 1)*(lim[i] - flow[0][i + 1]);
			}
			out.println(result);
		}
		
		out.close();
	}
	
	static void update(int index, int[]pa, int[][]flow){
		if(pa[index] != index){
			min = Math.min(min, flow[pa[index]][index]);
			update(pa[index], pa, flow);
			flow[pa[index]][index] -= min;
			flow[index][pa[index]] += min;
		}
	}
	
	public static int cost(int a, int b, int first, int total){
		
		if(a == 0 && b < first){
			return b;
		}
		else if(b == 0 && a < first){
			return -b;
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
