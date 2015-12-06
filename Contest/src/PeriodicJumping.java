/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class PeriodicJumping {

    public int minimalTime(int x, int[] jump) {
        if(x == 0){
            return 0;
        }
        long v = x < 0 ? -x : x;
        int c = 0;
        long sum = 0;
        for(int i = 0; i < jump.length; i++){
            sum += jump[i];
            c++;
            if(sum == v){
                return c;
            }
        }
        int result = 0;
        long cur = v;
        long max = v;
        if(v > sum){
            cur += (v/sum)*sum;
            result = (int)(v/sum)*jump.length;
            if(v % sum == 0){
                return result;
            }
        }
        System.out.println(cur + " " + max + " " + result);
        
        int i = 0;
        while(cur - max < max){
            cur += jump[i];
            max = Math.max(max, jump[i]);
            result++;
            i = (i + 1) % jump.length;
        }
        return result;
        
    }

    public static void main(String[] args) {
        int x = -1000000;
        int[] j = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1000013};
        System.out.println(new PeriodicJumping().minimalTime(x, j));
    }
}
