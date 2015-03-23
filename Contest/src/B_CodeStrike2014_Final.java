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

public class B_CodeStrike2014_Final {
	static long Mod = 1000000007;
	static int[][][] dp;
	static int max = 100000;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		int m = in.nextInt();
		int[] type = new int[m];
		int[] data = new int[m];
		int end = -1;
		boolean[] cur = new boolean[n];
		int c = 0;
		boolean ok = false;
		for (int i = 0; i < m; i++) {
			char op = in.next().charAt(0);
			data[i] = in.nextInt() - 1;
			if (op == '+') {
				type[i] = 0;
				c++;
				cur[data[i]] = true;
			} else {

				type[i] = 1;
				if (!cur[data[i]]) {
					if(c == 0){
						ok = true;
					}else{
						ok = false;
					}
					end = i;
				} else {
					c--;
				}
				cur[data[i]] = false;
			}
		}

		int[] result = new int[n];
		
		HashSet<Integer> occur = new HashSet();
		int last = 0;
		//System.out.println(c  + " " + end);
		int count = 0;
		int start = -1;
		for (int i = 0; i < m; i++) {
			if (i < end) {
				result[data[i]] = -1;
			} else if (i == end) {
				
				if (ok) {
					last++;
					occur.add(data[i]);
				} else {
					result[data[i]] = -1;
				}
			} else if (i > end) {
				
				if(!ok && i == end + 1){
					boolean[]check = new boolean[n];
					
					for(int j = 0; j < end; j++){
						if(type[j] == 0){
							check[data[j]] = true;
							count++;
						}else{
							if(check[data[j]]){
								check[data[j]] = false;
								count--;
							}
						}
					}
					if(count > 0){
						start = data[end];
					}
				}
				
				if (start == -1) {
					start = data[i];
				}
				if (last > 0 && !occur.contains(data[i])) {
					result[data[i]] = -1;
				}
				if (result[data[i]] != -1) {
					occur.add(data[i]);
				}
				if (type[i] == 1) {

					count--;
					if (count != 0 && data[i] == start) {
						result[start] = -1;
						if (occur.contains(start)) {
							occur.remove(start);
						}
					}
				} else {
					count++;
				}

				if (count == 0) {
					last++;
					HashSet<Integer> nxt = new HashSet();
					for (int v : occur) {
						if (v != start || (start != data[i])) {
							result[v] = -1;

						} else {
							nxt.add(v);
						}
					}
					start = -1;
					occur = nxt;
				}
				
			}
		}

		if (count > 0) {
			for (int v : occur) {
				if (v != start)
					result[v] = -1;
			}
		}
		count = 0;
		// System.out.println(Arrays.toString(result));
		for (int i = 0; i < n; i++) {
			if (result[i] != -1) {
				count++;
			}
		}
		out.println(count);
		for (int i = 0; i < n; i++) {
			if (result[i] != -1) {
				out.print((i + 1) + " ");
			}
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
