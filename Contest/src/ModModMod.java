
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class ModModMod {
    public long findSum(int[] m, int r){
        long result  = 0;
        ArrayList<Integer> list = new ArrayList();
        list.add(m[0]);
        int last = m[0];
        for(int i : m){
            if(i < last){
                list.add(i);
                last = i;
            }
        }
      //  System.out.println(list);
        if(list.get(list.size() - 1) == 1){
            return 0;
        }
        for(int i = 1; i <= r; i++){
            long v = cal(i, list);
            result += v;
        }
        return result;
    }
    
    public long cal(int v, ArrayList<Integer> m){
        long result = v;
        while(true){
            int start = 0;
            int end = m.size() - 1;
            int re = -1;
            while(start <= end){
                int mid = (start + end)>>1;
                if(m.get(mid) > result){
                    start = mid + 1;
                }else if(m.get(mid) == result){                    
                    return 0;
                }else{
                    if(re == -1 || re > mid){
                        re = mid;
                    }
                    end = mid - 1;                        
                        
                }
            }
            if(re == -1){
                break;
            }
            result %= m.get(re);
        }
        return result;
    }
    public static void main(String[] args) {
        int[] m = {995,149,28,265,275,107,555,241,702,462,519,212,362,478,783,381,602,546,183,886,59,317,977,612,328,91,771,131};

        System.out.println(new ModModMod().findSum(m, 992363));
    }
}
