/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class DoubleOrOneEasy {

    public int minimalSteps(int a, int b, int newA, int newB) {
        int result = -1;
        long A = a;
        long B = b;
        long nA = newA;
        long nB = newB;
        for (int i = 0; i < 31 && A <= nA && B <= nB; i++) {
            long total = i;

            long x = nA - A;
            long y = nB - B;
            A *= 2;
            B *= 2;
            
            if (x != y || x < 0) {

                continue;
            }
            
            
            long other = 0;
            for (int j = 0; j < total; j++) {
                if (x % 2 != 0) {
                    other++;
                    x--;
                }
                x /= 2;
            }
            other += x;

            total += other;
           // System.out.println(x +  " " + y + " " + A/2 + " " + B/2 + " " + total +  " " + i );
            result = (result == -1 || result > total) ? (int)total : result;
        }

        return result;

    }

    public static void main(String[] args) {
        int[] data = {30224, 30224, 990583493, 990583493};
        System.out.println(new DoubleOrOneEasy().minimalSteps(data[0], data[1],
                data[2], data[3]));
    }
}
