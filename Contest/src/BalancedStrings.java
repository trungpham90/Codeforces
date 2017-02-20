/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class BalancedStrings {

    public String[] findAny(int N) {
        String[] data = new String[N];
        if (N <= 26) {

            for (int i = 0; i < N; i++) {
                data[i] = "" + (char) (i + 'a');
            }
            return data;
        } else {
            int similar = 0;
            int[] count = new int[26];
            int st = 6;
            for (int i = 3; i < N; i++) {
                similar += count[st];
                count[st]++;
                data[i] = "" + (char) ('a' + st);
                st = (st + 1) % 26;
                if (st < 6) {
                    st = 6;
                }
            }
            data[0] = "a";
            data[1] = "c";
            data[2] = "e";
            while (similar > 0) {
                if (data[0].length() < 100) {
                    data[0] += (data[0].length() % 2 == 0 ? "a" : "b");
                } else if (data[1].length() < 100) {
                    data[1] += (data[1].length() % 2 == 0 ? "c" : "d");
                } else {
                    data[2] += (data[2].length() % 2 == 0 ? "e" : "f");
                }
                similar--;
            }
            return data;
        }
    }
}
