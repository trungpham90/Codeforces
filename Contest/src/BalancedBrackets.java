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

public class BalancedBrackets {

    public class Solution {

        public static boolean isBalanced(String expression) {
            Stack<Character> s = new Stack();
            String open = "({[";
            String close = ")}]";
            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);
                if (open.indexOf(c) >= 0) {
                    s.add(c);
                } else {
                    int index = close.indexOf(c);
                    if (s.isEmpty() || s.peek() != open.charAt(index)) {
                        return false;
                    }
                    s.pop();
                }
            }
            return s.isEmpty();
        }

        public static void main(String[] args) {
            Scanner in = new Scanner(System.in);
            int t = in.nextInt();
            for (int a0 = 0; a0 < t; a0++) {
                String expression = in.next();
                boolean answer = isBalanced(expression);
                if (answer) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
            }
        }
    }

}
