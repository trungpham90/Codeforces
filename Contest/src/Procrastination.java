/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class Procrastination {
    public long findFinalAssignee(long n){
        if(n <= 3){
            return n;
        }
        for(long i = 2; i*i <= n; i++){
            if(n % i == 0){
                n = n + 1;
            }else if((n - 1) % i == 0){
                n = n - 1;
            }
        }
        //System.out.println(n);
        for(long i = (long) Math.sqrt(n); i>= 2; i--){
           // System.out.println((n % i) + " " + i + " " + n);
            if(n % i == 0 && n/i != i){
                n = n + 1;
            }else if((n - 1) % i == 0 && ((n - 1)/i != i)){
                n = (n - 1);
            }
        }
        return n;
    }
    public static void main(String[] args) {
        long n = 196248;
        System.out.println(new Procrastination().findFinalAssignee(n));
    }
}
