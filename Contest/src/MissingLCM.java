
import java.math.BigInteger;
import java.util.HashMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class MissingLCM {

    public int getMin(int N) {
        int[] count = new int[N + 1];
        int total = 0;
        for (int i = 1; i <= N; i++) {
            int cur = i;
            int c = 0;
            while (cur % 2 == 0) {
                c++;
                cur /= 2;
            }
            if (c > 0) {
                if (count[2] == 0) {
                    total++;
                }
                count[2] = Math.max(count[2], c);
            }
            for (int j = 3; j * j <= cur; j += 2) {

                if (cur % j == 0) {
                    c = 0;
                    while (cur % j == 0) {
                        cur /= j;
                        c++;
                    }
                    if (count[j] == 0) {
                        total++;
                        count[j] = c;
                    } else if (count[j] < c) {
                        count[j] = c;
                    }
                }
            }
            if (cur != 1) {
                if (count[cur] == 0) {
                    total++;
                    count[cur] = 1;
                }
            }
        }
        // System.out.println(total);
        long other = 1;
        for (int i = N + 1; i <= 2 * N; i++) {

            int cur = i;
            int c = 0;
            while (cur % 2 == 0) {
                c++;
                cur /= 2;
            }
            if (c > 0 && 2 <= N) {
                if (count[2] > 0 && count[2] < c) {
                    total--;
                    count[2] = 0;
                }
            }
            for (int j = 3; j * j <= cur; j += 2) {

                if (cur % j == 0) {
                    c = 0;
                    while (cur % j == 0) {
                        cur /= j;
                        c++;
                    }
                    if (j <= N && count[j] > 0 && count[j] <= c) {
                        total--;
                        count[j] = 0;
                    }
                }
            }
            if (cur != 1) {
                if (cur <= N && count[cur] > 0 && count[cur] == 1) {
                    count[cur] = 0;
                    total--;
                }
            }
            if (total == 0) {
                return i;
            }
        }
        return 0;
    }

    public long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static void main(String[] args) {
        System.out.println(new MissingLCM().getMin(999999));
    }
}
