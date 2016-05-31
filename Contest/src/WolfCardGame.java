
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class WolfCardGame {

    public int[] createAnswer(int n, int k) {
        if (n % 2 != 0) {
            int[] result = new int[k];
            int start = 2;
            for (int i = 0; i < k; i++) {
                result[i] = start;
                start += 2;
            }
            return result;
        } else {
            int[] result = new int[k];

            int cur = 3;
            if (n != 60) {
                while (n % cur == 0) {
                    cur++;
                }
            } else {
                cur = 7;
            }
            int start = cur;
            System.out.println(cur);
            for (int i = 0; i < k; i++) {
                result[i] = start % 100;
                start += cur;

            }
            return result;
        }
    }

    int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        int[] data = {30, 15};
        int[] tmp = {33, 69, 42, 45, 96, 15, 57, 12, 93, 9, 54, 99};
        Arrays.sort(tmp);
        System.out.println(Arrays.toString(tmp));
        System.out.println(Arrays.toString(new WolfCardGame().createAnswer(data[0], data[1])));
    }
}
