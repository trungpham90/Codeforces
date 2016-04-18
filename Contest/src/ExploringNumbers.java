/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class ExploringNumbers {

    public int numberOfSteps(int n) {
        int start = n;
        for (int i = 1; i <= n; i++) {
            if (isPrime(start)) {            
                return i;
            }
            start = nxt(start);
            if(start == 1){
                return -1;
            }
        }
        return -1;
    }

    public int nxt(int v) {
        int result = 0;
        while (v > 0) {
            result += (v % 10) * (v % 10);
            v /= 10;
        }
        return result;
    }

    public boolean isPrime(int v) {
        if(v == 1){
            return false;
        }
        for (int i = 2; i * i <= v; i++) {
            if (v % i == 0) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        System.out.println(new ExploringNumbers().numberOfSteps(12366));
    }
}
