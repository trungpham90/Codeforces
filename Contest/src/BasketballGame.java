/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class BasketballGame {

    public String getScore(int[] t, int[] b, int[] p) {
        int sA = 0, sB = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i] <= 1440) {
                if (b[i] == 1) {
                    sA += p[i];
                } else {
                    sB += p[i];
                }
            } else if (b[i] == 1) {
                sB += p[i];
            } else {
                sA += p[i];
            }
        }
        return sA + ":" + sB;
    }
}
