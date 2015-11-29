/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class ProblemSets {

    public long maxSets(long E, long EM, long M, long MH, long H) {
        long result = 0;
        long start = 0;
        long end = max(E + EM, max(EM + M + MH, MH + H));
        while (start <= end) {
            long mid = (start + end) >> 1;
            boolean ok = true;
            long a = 0;
            long b = 0;
            if (E + EM >= mid) {
                if (E < mid) {
                    a = mid - E;
                }
                if (EM - a + M + MH >= mid) {
                    if (EM - a + M < mid) {
                        b = mid - (EM - a) - M;
                    }
                    if (MH - b + H >= mid) {
                    } else {
                        ok = false;
                    }
                } else {
                    ok = false;
                }
            } else {
                ok = false;
            }
          //  System.out.println(ok + " " + mid + " " + a + " " + b);
            if (ok) {
                result = max(result, mid);
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return result;

    }

    long min(long a, long b) {
        return a < b ? a : b;
    }

    long max(long a, long b) {
        return a > b ? a : b;
    }

    public static void main(String[] args) {
        long[] data = {2, 2, 1, 2, 2};
        System.out.println(new ProblemSets().maxSets(data[0], data[1], data[2], data[3], data[4]));
    }
}
