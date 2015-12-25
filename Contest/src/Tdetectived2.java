
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
public class Tdetectived2 {

    boolean[] check;

    public int reveal(String[] s) {
        int[] re = new int[s.length];
        Arrays.fill(re, s.length - 1);
        check = new boolean[1 << s.length];
        cal(1, re, s);
        System.out.println(Arrays.toString(re));
        int result = 0;
        for (int i = 0; i < s.length; i++) {
            result += i * re[i];
        }
        return result;
    }

    public void cal(int mask, int[] result, String[] s) {
      //  System.out.println(Integer.toBinaryString(mask));
        if (check[mask]) {
            return;
        }
        check[mask] = true;
        int[] cur = new int[s.length];
        int max = 0;
        for (int i = 0; i < s.length; i++) {
            if (((1 << i) & mask) != 0) {
                result[i] = Math.min(result[i], Integer.bitCount(mask) - 1);
                for (int j = 0; j < s[i].length(); j++) {
                    cur[j] = Math.max(cur[j], s[i].charAt(j) - '0');
                    if (((1 << j) & mask) == 0) {
                        max = Math.max(cur[j], max);
                    }
                }
            }
        }
        //  System.out.println(max + " " + Arrays.toString(cur));

        for (int i = 0; i < s.length; i++) {
            if (((1 << i) & mask) == 0 && cur[i] == max) {
                cal(mask | (1 << i), result, s);
            }
        }

    }

    public static void main(String[] args) {
        String[] data = {"064675511", "603525154", "430262731", "652030511", "726302420", "552020464", "517544052", "153126500", "141104200"};
        System.out.println(new Tdetectived2().reveal(data));
    }
}
