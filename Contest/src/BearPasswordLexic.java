
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class BearPasswordLexic {

    public String findPassword(int[] x) {
        int n = x.length;
       
        char[] result = new char[x.length];
        int start = 0;

        int[] count = new int[n];
        Deque<Integer> list = new LinkedList();

        for (int i = n - 1; i >= 0; i--) {
            if (x[i] < count[i]) {
                return "";
            }
            int need = x[i] - count[i];
            
            for (int j = 0; j < need; j++) {
                start += i + 1;
                list.add(i + 1);
                for(int k = i - 1; k >= 0;k--){
                    count[k] += i - k + 1;
                }
            }
            
        }        
        if (start != n) {
            return "";
        }        
        int cur = 0;

        start = 0;
        //System.out.println(list);
        while(!list.isEmpty()) {
            
            if (cur == 0) {
                int v = list.pollFirst();
                for (int j = 0; j < v; j++) {
                    result[start++] = 'a';
                }
            } else {
                int v = list.pollLast();
                for (int j = 0; j < v; j++) {
                    result[start++] = 'b';
                }
            }
            cur = 1 - cur;
        }
        
        return new String(result);
    }

    public static void main(String[] args) {
        int[] v = {0};
        System.out.println(new BearPasswordLexic().findPassword(v));
    }
}
