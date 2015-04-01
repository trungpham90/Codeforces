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

import javax.swing.JOptionPane;

public class DSD_Numbers {
	

	static long Mod = 1000000007;

	static int[] X = { 0, 1, 0, -1 };
	static int[] Y = { 1, 0, -1, 0 };

	static int[][][][][] dp;
	static int[][][][][] check;
	static int cur = 0;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		
		
		int n = in.nextInt();
		dp = new int[11][2][2][82][82];
		check = new int[11][2][2][82][82];
		for (int i = 0; i < n; i++) {
			
			int l = in.nextInt();
			int r = in.nextInt();
			String L = "" + l;
			String R = "" + r;
			while (L.length() < R.length()) {
				L = "0" + L;
			}
			int result = 0;
			for (int j = 1; j <= 81; j++) {
				
				cur = cur + 1;
				result += count(0, 0, 0, j, 0, j, L, R);
			}
			out.println(result);
		}
		out.close();
	}

	public static int count(int index, int larger, int smaller, int left,
			int mod, int sum, String L, String R) {
		if (index == L.length()) {
			if (left == 0 && mod == 0) {
				return 1;
			}
			return 0;
		}
		if((L.length() - index) * 9 < left){
			return 0;
		}
		if (check[index][larger][smaller][left][mod] == cur) {
			return dp[index][larger][smaller][left][mod];
		}
		//System.out.println(cur);
		check[index][larger][smaller][left][mod] = cur;
		int x = L.charAt(index) - '0';
		int y = R.charAt(index) - '0';
		int result = 0;
		for (int i = 0; i < 10 && i <= left; i++) {
		
			if (x > i && larger == 0) {
				continue;
			}
			if (y < i && smaller == 0) {
				continue;
			}
			int nxtLarger = larger;
			int nxtSmaller = smaller;
			if (x < i) {
				nxtLarger = 1;
			}
			if (y > i) {
				nxtSmaller = 1;
			}
			int nxtMod = (mod * 10 + i) % sum;
			result += count(index + 1, nxtLarger, nxtSmaller, left - i, nxtMod,
					sum, L, R);
		}
		return dp[index][larger][smaller][left][mod] = result;
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
			// new File("map.txt"))));
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