
import java.math.BigInteger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class ThePermutationGame {
    public int findMin(int N){
        long mod = 1000000007;
        long result = 1;
        int[]count = new int[N + 1];
        for(int i = 2; i <= N; i++){
            int start =  i;
            for(int j = 2; j*j <= start; j++){
                int c = 0;
                while(start % j == 0){
                    c++;
                    start /= j;
                }
                count[j] = Math.max(count[j], c);
            }
            if(start != 1){
                count[start] = Math.max(count[start], 1);
            }
        }
        for(int i = 2; i <= N; i++){
            if(count[i] > 0){
                long v = pow(i,count[i],mod);
                result *=v;
                result %=mod;
            }
        }
        return (int)result;
    }
    
    public long pow(long v, int p, long mod){
        if(p ==  0){
            return 1;
        }
        long x = pow(v, p/2, mod);
        if(p % 2 == 0){
            return (x*x)%mod;
        }else{
            return (((v*x)%mod)*x)%mod;
        }
    }
    
   
    public static void main(String[] args) {
        System.out.println(new ThePermutationGame().findMin(11));
    }
}
