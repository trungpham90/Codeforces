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

public class MakingAnagram {

    public static int numberNeeded(String first, String second) {
        int[] count = new int[26];
        for (int i = 0; i < first.length(); i++) {
            int index = first.charAt(i) - 'a';
            count[index]++;
        }
        for (int i = 0; i < second.length(); i++) {
            int index = second.charAt(i) - 'a';
            count[index]--;
        }
        int result = 0;
        for (int v : count) {
            result += Math.abs(v);
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String a = in.next();
        String b = in.next();
        System.out.println(numberNeeded(a, b));
    }
}
