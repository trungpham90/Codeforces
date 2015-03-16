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

public class B_Round_138_Div1 {
	static long Mod = 1000000007;
	static int[][][] dp;
	static int max = 100000;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		
		String s = in.next();
		String t = in.next();
		int n = s.length();
		int m = t.length();
		int[] pos = new int[n];
		Arrays.fill(pos, -1);
		ArrayList<Integer>[] map = new ArrayList[26];
		boolean[] check = new boolean[m];
		for (int i = 0; i < 26; i++) {
			map[i] = new ArrayList();
		}
		for (int i = 0; i < m; i++) {
			int index = t.charAt(i) - 'a';
			map[index].add(i);
		}
		//System.out.println("HE HE");
		boolean ok = true;
		for (int i = n - 1; i >= 0; i--) {
			int index = s.charAt(i) - 'a';
			int start = 0;
			int end = map[index].size() - 1;
			
			int result = -1;
			while (start <= end) {
				int mid = (start + end) >> 1;
				int tmp = map[index].get(mid);
				if (tmp + 1 == m || check[tmp + 1]) {
					if (result == -1 || result > tmp) {
						result = tmp;
					}
					end = mid - 1;
				}else{
					start = mid + 1;
				}
				
			}
			if (result == -1) {
				ok = false;
				break;
			}
			check[result] = true;
			pos[i] = result;
		}
		
		if (ok) {
			check = new boolean[m];
			for(int i = 0; i < n; i++){
				int index = s.charAt(i) - 'a';
				int start = 0;
				int end = map[index].size() - 1;
				int result = -1;
				while (start <= end) {
					int mid = (start + end) >> 1;
					int tmp = map[index].get(mid);
					if (tmp == 0 || check[tmp - 1]) {
						if (result < tmp) {
							result = tmp;
						}
						start = mid + 1;
					}else{
						end = mid - 1;
					}
				}
				//System.out.println(result + " " + i);
				if (result == -1 || result < pos[i]) {
					ok = false;
					break;
				}
				check[result] = true;
			}
			if(ok){
				out.println("Yes");
			}else{
				out.println("No");
			}
		} else {
			out.println("No");
		}

		out.close();
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
