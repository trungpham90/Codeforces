
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 ID: trungph2
 LANG: JAVA
 TASK: skidesign
 */
public class skidesign {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner();
        PrintWriter out = new PrintWriter(new FileOutputStream(new File("skidesign.out")));
        //PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = in.nextInt();
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 100; i++) {
            int j = i + 17;
            int tmp = 0;
            for (int k = 0; k < n; k++) {
                if (data[k] < i) {
                    tmp += (i - data[k]) * (i - data[k]);
                } else if (j < data[k]) {
                    tmp += (data[k] - j) * (data[k] - j);
                }
            }
            if (min > tmp) {
                min = tmp;
            }

        }
        out.println(min);
        out.close();
    }

    static class Scanner {

        BufferedReader br;
        StringTokenizer st;

        public Scanner() throws FileNotFoundException {
            //System.setOut(new PrintStream(new BufferedOutputStream(System.out), true));
            //   br = new BufferedReader(new InputStreamReader(System.in));
            br = new BufferedReader(new FileReader(new File("skidesign.in")));
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
