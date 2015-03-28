
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
 TASK: wormhole
 */
public class wormhole {

    static int visit = 0;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner();
        PrintWriter out = new PrintWriter(new FileOutputStream(new File("wormhole.out")));
        //PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = in.nextInt();
            y[i] = in.nextInt();
        }
        int result = cal(0, new int[n], x, y);
        out.println(result);
        out.close();
    }

    static int cal(int mask, int[] map, int[] x, int[] y) {
        if (Integer.bitCount(mask) == map.length) {
            int v = 0;
            int result = 0;
            for (int i = 0; i < map.length; i++) {
                visit = 0;
                visit |= (1 << i);
                if (isLoop(map[i], map, x, y)) {
                    result = 1;
                    break;
                }
            }
            return result;
        }
        int result = 0;
        for (int i = 0; i < map.length; i++) {
            if (((1 << i) & mask) == 0) {
                for (int j = i + 1; j < map.length; j++) {
                    if (((1 << j) & mask) == 0) {
                        map[i] = j;
                        map[j] = i;
                        result += cal(mask | (1 << i) | (1 << j), map, x, y);

                    }
                }
                break;
            }
        }
        return result;
    }

    static boolean isLoop(int index, int[] map, int[] x, int[] y) {        
        int nxt = -1;
        for (int i = 0; i < x.length; i++) {
            if (y[index] == y[i] && x[index] < x[i]) {
                if (nxt == -1 || x[nxt] > x[i]) {
                    nxt = i;
                }
            }
        }
        if (nxt == -1) {
            return false;
        }
        if ((visit & (1 << nxt)) != 0) {
            return true;
        }
        visit |= (1 << nxt);
        return isLoop(map[nxt], map, x, y);
    }

    static class Scanner {

        BufferedReader br;
        StringTokenizer st;

        public Scanner() throws FileNotFoundException {
            //System.setOut(new PrintStream(new BufferedOutputStream(System.out), true));
            //br = new BufferedReader(new InputStreamReader(System.in));
             br = new BufferedReader(new FileReader(new File("wormhole.in")));
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
