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
public class D_Round_312_Div2 {

	public static long MOD = 1000000007;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int h = in.nextInt();
		int q = in.nextInt();
		ArrayList<Point> yes = new ArrayList();
		ArrayList<Point> no = new ArrayList();
		for (int i = 0; i < q; i++) {
			int x = in.nextInt();
			long l = in.nextLong();
			long r = in.nextLong();
			int ans = in.nextInt();
			long dif = h - x;
			long L = (1L << (dif)) * l;
			long R = (1L << (dif)) * r + (1L << dif) - 1L;
			// System.out.println(L + " " +R + " " + ans + " " + dif);
			if (ans == 0) {
				no.add(new Point(L, 0));
				no.add(new Point(R, 1));

			} else {
				yes.add(new Point(L, R));
			}
		}
		Collections.sort(yes, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				long x = o1.y - o1.x;
				long y = o2.y - o1.x;
				return Long.compare(x, y);
			}
		});
		Collections.sort(no, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				if (o1.x != o2.x) {
					return Long.compare(o1.x, o2.x);
				} else {
					return Long.compare(o1.y, o2.y);
				}

			}
		});

		Point y = null;
		boolean ok = true;
		if (!yes.isEmpty()) {
			y = yes.get(0);
			for (int i = 1; i < yes.size(); i++) {
				Point cur = yes.get(i);
				if (cur.x <= y.x && y.y <= cur.y) {
					continue;
				} else if (cur.x <= y.x && y.x <= cur.y) {
					y.y = cur.y;
				} else if (cur.x <= y.y && y.y <= cur.y) {
					y.x = cur.x;
				} else {
					
					ok = false;
					break;
				}
			}
		}

		if (ok) {
			TreeSet<Point> neg = new TreeSet();
			long start = -1;
			int count = 0;
			for (Point p : no) {
				if (p.y == 0) {
					count++;
				} else {
					count--;
				}
				if (count > 0) {
					if (start == -1) {
						start = p.x;
					}
				} else if (count == 0) {
					neg.add(new Point(start, p.x));
					start = -1;
				}
			}

			if (y != null) {
				Point p = neg.floor(y);
				if (p != null) {
					if (p.y >= y.x) {
						y.x = p.y + 1;
						if (y.x > y.y) {
							ok = false;
						}
					}
				}
				if (ok) {
					p = neg.ceiling(y);
					ArrayList<Point>left= new ArrayList();
					if (p != null) {
						if (p.x <= y.y) {
							Point a = new Point(y.x, p.x - 1);
							if(a.x <= a.y){
								left.add(a);
							}
							Point b = new Point(p.y + 1, y.y);
							if(b.x <= b.y){
								while(true){
									p = neg.ceiling(b);
									if(p != null){
										if(p.x <= b.y){
											Point c = new Point(b.x, p.x - 1);
											Point d = new Point(p.y + 1, b.y);
											if(c.x <= c.y){
												left.add(c);
											}
											if(d.x <= d.y){
												b = d;
											}else{
												break;
											}
											
										}else{
											left.add(b);
											break;
										}
									}else{
										left.add(b);
										break;
									}
								}
							}
						}else{
							left.add(y);
						}
					}else{
						left.add(y);
					}
					if (ok && !left.isEmpty()) {
						if (left.size() == 1 && left.get(0).x == left.get(0).y) {
							out.println(left.get(0).x);
						} else {
							out.println("Data not sufficient!");
						}
					} else {
						out.println("Game cheated!");
					}
				} else {
					out.println("Game cheated!");
				}
			} else {
				long L = 1L << (h - 1);
				long R = (1L << h) - 1;
				ArrayList<Point> gap = new ArrayList();
				long s = L;
				for (Point p : neg) {
					if (p.x - 1 >= s) {
						gap.add(new Point(s, p.x - 1));
					}
					s = p.y + 1;
				}
				if (s <= R) {
					gap.add(new Point(s, R));
				}
				if (gap.size() == 1 && gap.get(0).x == gap.get(0).y) {
					out.println(gap.get(0).x);
				} else {
					if (gap.size() > 0)
						out.println("Data not sufficient!");
					else{
						out.println("Game cheated!");
					}
				}
			}
		} else {
			
			out.println("Game cheated!");
		}
		out.close();
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

		long x, y;

		public Point(long start, long end) {
			this.x = start;
			this.y = end;
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
			return Long.compare(x, o.x);
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
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
