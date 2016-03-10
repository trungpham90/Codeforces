
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
public class CliqueParty {
    public int maxsize(int[] a, int k){
        long rate = k;
        Arrays.sort(a);
        int result = 2;
        for(int i = 0; i < a.length; i++){
            for(int j = i + 2 ; j < a.length; j++){
                long max = a[j] - a[i];
                int start = i;                
                int c = 2;
                while(start < j){
                    boolean found = false;
                    for(int h = start + 1; h < j; h++ ){
                        long v = a[h] - a[start];
                        if(v*rate >= max){
                            long other = a[j] - a[h];
                            if(other*rate >= max){
                                found = true;
                                start = h;
                                c++;
                                break;
                            }
                        }
                    }
                    if(!found){
                        break;
                    }
                }
                result = Integer.max(c, result);
            }
        }
        return result;
    }
    public static void main(String[] args) {
        int[]data = {1,2,3};
        int k = 2;
        System.out.println(new CliqueParty().maxsize(data, k));
    }
}
