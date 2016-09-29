/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class RansomNote {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        int n = in.nextInt();
        HashMap<String, Integer> map = new HashMap<>();
        String magazine[] = new String[m];
        for (int magazine_i = 0; magazine_i < m; magazine_i++) {
            magazine[magazine_i] = in.next();
            if (!map.containsKey(magazine[magazine_i])) {
                map.put(magazine[magazine_i], 1);
            } else {
                map.put(magazine[magazine_i], map.get(magazine[magazine_i]) + 1);
            }
        }
        String ransom[] = new String[n];
        boolean ok = true;
        for (int ransom_i = 0; ransom_i < n && ok; ransom_i++) {
            ransom[ransom_i] = in.next();
            if (!map.containsKey(ransom[ransom_i])) {
                ok = false;
            } else {
                map.put(ransom[ransom_i], map.get(ransom[ransom_i]) - 1);
                if (map.get(ransom[ransom_i]) < 0) {
                    ok = false;
                }
            }
        }
        if (ok) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}
