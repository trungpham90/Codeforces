/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class Drbalance {

    public int lesscng(String s, int k) {
        int result = 0;
        char[] test = s.toCharArray();
        int n = s.length();
        int total = 0;
        for (int i = n - 1; i >= 0; i--) {
            int c = 0;
            for (int j = 0; j <= i; j++) {
                if (test[j] == '+') {
                    c++;
                } else {
                    c--;
                }
            }
            if (c < 0) {
                total++;
            }
        }
        if (total <= k) {

        } else {
            for (int z = 0; z < s.length() && total > k; z++) {
                if (test[z] == '-') {
                    test[z] = '+';
                    result++;
                    total = 0;
                    for (int i = n - 1; i >= 0; i--) {
                        int c = 0;
                        for (int j = 0; j <= i; j++) {
                            if (test[j] == '+') {
                                c++;
                            } else {
                                c--;
                            }
                        }
                        if (c < 0) {
                            total++;
                        }
                    }
                }
            }
        }
        return result;
    }
}
