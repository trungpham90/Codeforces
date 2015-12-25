/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class SubstitutionCipher {

    public String decode(String a, String b, String y) {
        char[] encrypt = new char[26];
        int count = 0;
        int mask = 0;
        for (int i = 0; i < b.length(); i++) {
            int index = b.charAt(i) - 'A';
            int other = a.charAt(i) - 'A';

            if (encrypt[index] == 0) {
                if ((((1 << other) & mask)) != 0) {
                    return "";
                }
                encrypt[index] = a.charAt(i);
                mask |= 1 << (a.charAt(i) - 'A');
                count++;
            } else if (encrypt[index] != a.charAt(i)) {
                return "";
            }
        }
        if (count == 25) {
            for (int i = 0; i < 26; i++) {
                if (encrypt[i] == 0) {
                    for (int j = 0; j < 26; j++) {
                        if (((1 << j) & mask) == 0) {
                            encrypt[i] = (char) ('A' + j);
                        }
                    }
                }
            }

        }
        char[] result = new char[y.length()];
        for (int i = 0; i < y.length(); i++) {
            int index = y.charAt(i) - 'A';
            if (encrypt[index] == 0) {
                return "";
            }
            result[i] = encrypt[index];
        }
        return new String(result);

    }
}
