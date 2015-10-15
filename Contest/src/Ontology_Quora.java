import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * #
 * 
 * @author pttrung
 */
public class Ontology_Quora {

	public static long MOD = 1000000007;
	static int count = 0;

	public static void main(String[] args) throws FileNotFoundException {
		// PrintWriter out = new PrintWriter(new FileOutputStream(new File(
		// "output.txt")));
		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner();
		int n = in.nextInt();
		String[] tmp = in.nextLine().split(" ");
		// System.out.println(Arrays.toString(tmp));
		Node[] data = new Node[n];
		HashMap<String, Node> map = new HashMap();
		Stack<Node> s = new Stack();
		Node cur = null;
		Node last = null;
		int index = 0;
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].equals("(")) {
				if (cur != null) {
					s.push(cur);
					cur = last;
				}
			} else if (tmp[i].equals(")")) {
				if (!s.isEmpty()) {
					cur = s.pop();
				}
			} else {
				data[index] = new Node(tmp[i]);
				map.put(tmp[i], data[index]);
				if (cur != null) {
					if (cur.child == null) {
						cur.child = new ArrayList();
					}
					cur.child.add(data[index]);
				} else {
					cur = data[index];
				}
				last = data[index];
				index++;
			}
		}
		dfs(data[0]);
		// System.out.println(Arrays.toString(data));
		int m = in.nextInt();
		Q[] q = new Q[m];
		for (int i = 0; i < m; i++) {
			String[] line = in.nextLine().split(": ");
			q[i] = new Q(line[1], map.get(line[0]));
		}
		Arrays.sort(q);
		int k = in.nextInt();
		P[] p = new P[2 * k];
		index = 0;
		for (int i = 0; i < k; i++) {
			String line = in.nextLine();
			int first = line.indexOf(" ");
			Node node = map.get(line.substring(0, first));
			String query = line.substring(first + 1);
			p[index] = new P(i, node.s, 0);
			p[index + 1] = new P(i, node.e, 1);
			p[index].query = query;
			p[index + 1].query = query;

			index += 2;
		}
		Arrays.sort(p);
		int[] result = new int[k];

		index = 0;
		int other = 0;
		Prefix root = new Prefix();
		for (int i = 0; i < count; i++) {
			while (index < 2 * k && p[index].pos == i && p[index].start == 0) {
				addQuery(0, p[index++], result, root);
			}
			while (other < m && q[other].node.s == i) {
				addQuestion(0, q[other++], root);
			}
			while (index < 2 * k && p[index].pos == i && p[index].start == 1) {
				deleteQuery(0, p[index++], result, root);
			}
		}
		for (int i : result) {
			out.println(i);
		}

		out.close();
	}

	static void addQuestion(int index, Q node, Prefix tmp) {
		while (index < node.question.length()) {

			char c = node.question.charAt(index);
			if (tmp.nxt == null || !tmp.nxt.containsKey(c)
					|| tmp.nxt.get(c).q == 0) {
				return;
			}

			tmp = tmp.nxt.get(c);
			tmp.count++;
			index++;
		}
	}

	static void deleteQuery(int index, P q, int[] result, Prefix tmp) {
		while (index < q.query.length()) {
			char c = q.query.charAt(index);

			tmp = tmp.nxt.get(c);
			tmp.q--;
			index++;
		}
		result[q.index] += tmp.count;

	}

	static void addQuery(int index, P q, int[] result, Prefix tmp) {
		while (index < q.query.length()) {
			char c = q.query.charAt(index);
			if (tmp.nxt == null) {
				tmp.nxt = new HashMap();
			}
			if (!tmp.nxt.containsKey(c)) {
				tmp.nxt.put(c, new Prefix());
			}

			tmp = tmp.nxt.get(c);
			tmp.q++;
			index++;
		}
		result[q.index] -= tmp.count;

	}

	static class Prefix {
		int q = 0, count = 0;
		HashMap<Character, Prefix> nxt;

	}

	static class P implements Comparable<P> {
		int index, pos, start;
		String query;

		public P(int index, int pos, int start) {
			super();
			this.index = index;
			this.pos = pos;
			this.start = start;
		}

		@Override
		public int compareTo(P o) {
			if (pos != o.pos)
				return pos - o.pos;
			return start - o.start;
		}

	}

	static class Q implements Comparable<Q> {
		String question;
		Node node;

		public Q(String question, Node node) {
			super();
			this.question = question;
			this.node = node;
		}

		@Override
		public int compareTo(Q o) {
			return node.s - o.node.s;

		}

	}

	static void dfs(Node cur) {
		cur.s = count++;

		if (cur.child == null) {
			cur.e = count - 1;
			return;
		}
		for (Node c : cur.child) {
			dfs(c);

		}
		cur.e = count - 1;
	}

	static class Node implements Comparable<Node> {
		String name;
		int s, e;
		ArrayList<Node> child;

		public Node(String name) {
			super();
			this.name = name;
		}

		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return s - o.s;
		}

		@Override
		public String toString() {
			return "Node [name=" + name + ", s=" + s + ", e=" + e + "]";
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
