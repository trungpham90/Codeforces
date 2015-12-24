/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class BearSlowlySorts {

    public int minMoves(int[] w) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        boolean sorted = true;
        for (int i = 0; i < w.length; i++) {
            min = Integer.min(min, w[i]);
            max = Integer.max(max, w[i]);
            if (i > 0) {
                sorted &= w[i] >= w[i - 1];
            }
        }
        if (sorted) {
            return 0;
        }
        if (min == w[0]) {
            return 1;
        }
        if (max == w[w.length - 1]) {
            return 1;
        }
        if (max == w[0] && min == w[w.length - 1]) {
            return 3;
        }
        return 2;
    }

    public static void main(String[] args) {
        int[] data = {4, 3, 1, 6, 2, 5};
        System.out.println(new BearSlowlySorts().minMoves(data));
    }
}
