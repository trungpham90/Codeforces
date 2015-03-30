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

public class C_Round_91_Div1 {
	static long Mod = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();

	
		int k = in.nextInt();
		boolean ok = true;
		if (n <= 14) {
			long max = 1;
			for (int i = 1; i <= n; i++) {
				max *= i;
			}
			if (max < k) {
				ok = false;
				out.println(-1);
			}
		}
		if (ok) {
			PriorityQueue<Long> q = new PriorityQueue();
			TreeSet<Integer> set = new TreeSet();
			q.add(4L);
			q.add(7L);
			
			while (!q.isEmpty()) {
				long v = q.poll();
				if (v > n) {
					break;
				}
				set.add((int)v);
				q.add(v * 10 + 4);
				q.add(v * 10 + 7);
			}
			//out.println(set);
			int left = 0;
			long cur = 1;
			for(int i = 1; i <= 14 ; i++){
				cur *= i;
				if(cur >= k){
					left = i ;
					break;
				}
			}
			int other = n - left;
			int result = set.headSet(other, true).size();
			int start = other + 1;
			ArrayList<Integer> list = new ArrayList();
			for(int i = 0; i < left; i++){
				list.add(start + i);
			}
			for(int i = 0; i < left; i++){
				int min = 0;
				int max = list.size() - 1;
				long re= -1;
				int id = -1;
				while(min <= max){
					int mid = (min + max)/2;
					long large = mid;
					for(int j = 1; j < left - i; j++ ){
						large *= j;
					}
					if(large < k){
						if(large > re){
							re = large;
							id = mid;
						}
						min = mid + 1;
					}else{
						max = mid - 1;
					}
				}
				k -= re;
				int v = list.remove((int)id);
				//System.out.println(v + " " + id + " " + start + " " + re);
				if(set.contains(v) && set.contains(start)){
					result++;
				}
				start++;
			}
			out.println(result);
		}
		out.close();
	}

	
	
	static class Segment implements Comparable<Segment> {
		int x, y;

		public Segment(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public String toString() {
			return x + " " + y;
		}

		@Override
		public int compareTo(Segment o) {
			if (x != o.x) {
				return x - o.x;
			}
			return o.y - y;
		}
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
