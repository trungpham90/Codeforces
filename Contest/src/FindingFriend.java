/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
import java.util.*;

public class FindingFriend {

    public int find(int r, int[] l, int f) {
        Arrays.sort(l);
        int result = 0;
        int other = 0;
        long total = (long) r * l.length;
        int c = 0;
        boolean stop = false;
        for (int i = l.length - 1; i >= 0 && !stop; i--) {
            if (l[i] == f) {
                return 1;
            } else if (l[i] < f) {
                result++;
                long used = r * c;
                long left = total - l[i] - used + 1;

                stop = left == r;

            }
            c++;
        }

        return result;

    }
}
