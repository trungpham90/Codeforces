
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
public class ThreeProgrammers {

    char[] result;
    boolean [][][][][]dp;
    public String validCodeHistory(String code) {
        int[] count = new int[3];
        for (int i = 0; i < code.length(); i++) {
            count[code.charAt(i) - 'A']++;
        }
        result = new char[code.length()];
        dp = new boolean[code.length()][2][3][count[1] + 1][count[2] + 1];
        if(!cal(0, 0, 0, count[1], count[2], code.length())){
            return "impossible";
        }
        return new String(result);

    }

    public boolean cal(int index, int b, int c, int numB, int numC, int n) {
        if (index == n) {
            return true;
        }
        if(dp[index][b][c][numB][numC]){
            return false;
        }
        dp[index][b][c][numB][numC] = true;
        if(b == 0 && numB > 0){
            if(cal(index + 1, 1 , Integer.max(0, c - 1), numB - 1, numC, n)){
                result[index] = 'B';
                return true;
            }
        }
        if(c == 0 && numC > 0){
            if(cal(index + 1,  Integer.max(0, b - 1), 2, numB , numC - 1, n)){
                result[index] = 'C';
                return true;
            }
        }
        int numA = n - index - numB - numC;
        if(numA > 0){
            if(cal(index + 1,  Integer.max(0, b - 1), Integer.max(0, c - 1), numB , numC, n)){
                result[index] = 'A';
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String v = "CAC";
        System.out.println(new ThreeProgrammers().validCodeHistory(v));
    }
}
