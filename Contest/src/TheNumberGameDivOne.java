
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class TheNumberGameDivOne {

    public String find(long n) {
        if (n % 2 != 0) {
            return "Brus";
        } else if (Long.bitCount(n) == 1) {
            for (long i = 0; i < 64; i++) {
                if ((1L << i) == n) {
                    if (i % 2 == 0) {
                        return "John";
                    } else {
                        return "Brus";
                    }
                }
            }
            return "John";
        } else {
            return "John";
        }

    }

    public static void main(String[] args) {
        for (int i = 2; i < 200; i++) {
            System.out.println(new TheNumberGameDivOne().find(i) + " " + i);
        }
    }
}
