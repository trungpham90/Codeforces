
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
 * @author pttrung
 */
public class D_Round_267_Div2 {

    public static long MOD = 1000000007;  
    static int cur = 0;
    static int[]step, min , dp;
    public static void main(String[] args) throws FileNotFoundException {
        // PrintWriter out = new PrintWriter(new FileOutputStream(new File(
        // "output.txt")));
        PrintWriter out = new PrintWriter(System.out);
        Scanner in = new Scanner();      
        int n = in.nextInt();
        String[] line = new String[n];
        for(int i = 0;i < n; i++){
        	line[i] = in.next().toLowerCase();
        }
        int m = in.nextInt();
        Pair[]data = new Pair[m];
        int index = 0;
        HashMap<String, Integer> map = new HashMap();
        HashMap<Integer, String> inverted = new HashMap();
        for(int i = 0; i < m; i++){
        	data[i] = new Pair(in.next().toLowerCase(), in.next().toLowerCase());
        	if(!map.containsKey(data[i].a)){
        		map.put(data[i].a, index);
        		inverted.put(index, data[i].a);
        		index++;
        	}
        	if(!map.containsKey(data[i].b)){
        		map.put(data[i].b, index);
        		inverted.put(index, data[i].b);
        		index++;
        	}
        }
        ArrayList<Integer>[]mp = new ArrayList[index];
        int[]count = new int[index];
        int[]length = new int[index];
        dp = new int[index];
        for(int i = 0; i < index; i++){
        	dp[i] = i;
        	mp[i] = new ArrayList();
        	String v =inverted.get(i);
        	length[i] = v.length();
        	for(int j = 0; j < v.length(); j++ ){
        		if(v.charAt(j) == 'r'){
        			count[i]++;
        		}
        	}
        }
        for(Pair p : data){
        	mp[map.get(p.a)].add(map.get(p.b));
        }
        int[]check = new int[index];
        step = new int[index];
        min = new int[index];
        for(int i = 0; i < index; i++){
        	if(check[i] == 0){
        		dfs(i, check, count, length, new Stack(), mp);
        	}
        }
       // System.out.println(Arrays.toString(dp) + " " + map + " " + Arrays.toString(step) + " " + Arrays.toString(min));
        long a = 0, b = 0;
        for(int i = 0; i < n; i++){
        	if(map.containsKey(line[i])){
        		a += count[dp[map.get(line[i])]];
        		b += length[dp[map.get(line[i])]];
        	}else{
        		for(int j = 0; j < line[i].length(); j++){
        			if(line[i].charAt(j) == 'r'){
        				a++;
        			}
        		}
        		b += line[i].length();
        	}
        }
        out.println(a + " " + b);
        out.close();
    }
    
    static int dfs(int node, int[]check, int[]count, int[]l, Stack<Integer> s, ArrayList<Integer>[]map){
    	int result = node;
    	step[node] = min[node] = cur++;
    	check[node] = 1;
    	s.add(node);
    	for(int i : map[node]){
    		if(check[i] == 0){
    			int v = dfs(i, check, count, l ,s , map);
    			if(count[v] < count[result] || (count[v] == count[result] && l[v] < l[result])){
    				result = v;
    			}
    			min[node] = Math.min(min[node], min[i]);
    		}else if(check[i] == 1){
    			int v = dp[i];
    			if(count[v] < count[result] || (count[v] == count[result] && l[v] < l[result])){
    				result = v;
    			}
    			min[node] = Math.min(min[node], min[i]);
    		}else{
    			int v = dp[i];
    			if(count[v] < count[result] || (count[v] == count[result] && l[v] < l[result])){
    				result = v;
    			}
    		}
    	}
    	if(min[node] == step[node]){
    		ArrayList<Integer> tmp = new ArrayList();
    		int cur = s.pop();
    		tmp.add(cur);
    		check[cur] = 2;
    		while(cur != node){
    			cur = s.pop();
    			if(count[result] > count[dp[cur]] || (count[result] == count[dp[cur]] && l[result] > l[dp[cur]])){
    				result = dp[cur];
    			}
    			tmp.add(cur);
    			check[cur] = 2;
    		}
    		for(int i : tmp){
    			dp[i] = result;
    		}
    		//System.out.println(tmp + " " + node + " " + result);
    	}
    	//System.out.println(node + " " + result );
    	return dp[node] = result;
    }
    
    static class Pair{
    	String a, b;

		public Pair(String a, String b) {
			super();
			this.a = a;
			this.b = b;
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
            // System.setOut(new PrintStream(new BufferedOutputStream(System.out), true));
            br = new BufferedReader(new InputStreamReader(System.in));
            //  br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("input.txt"))));
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
