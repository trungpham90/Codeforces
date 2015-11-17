
import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class ApplesAndOrangesEasy {
    public int maximumApples(int n, int k, int[] info){
        
        boolean[]check = new boolean[n];
        ArrayList<Integer> list = new ArrayList();
        Arrays.sort(info);
        for(int i : info){
            check[i - 1] = true;
            list.add(i - 1);
        }
        int result = 0;
         
        for(int i = 0; i < n; i++){
            if(check[i]){
                result++;
            }else{
                boolean ok = true;
                check[i] = true;
                int index = 0;
                for(int j = 0; j < list.size(); j++){
                    if(list.get(j) < i){
                        index = j + 1;
                    }else{
                        break;
                    }
                }
                list.add(index, i);
                for(int j = 0; j < list.size() - k/2; j++){
                    int a = list.get(j);
                    int b = list.get(j + k/2 );                    
                    if(b - a + 1 <= k){
                        ok = false;
                        break;
                    }
                }
                
                if(ok){
                    //System.out.println(i);
                    result++;
                }else{
                    check[i] = false;
                    list.remove((Integer)i);
                }
            }
        }
        //System.out.println(list);
        return result;
    }
    public static void main(String[] args) {
        int n = 23;
        int k = 7;
        int[ ]info = {3, 2, 9, 1, 15, 23, 20, 19};
        System.out.println(new ApplesAndOrangesEasy().maximumApples(n, k, info));
    }
   
}
