
import java.util.HashSet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class Similars {

    public int maxsim(int L, int R) {
        HashSet<Integer> set = new HashSet();
        int re = 0;
        for (int i = L; i <= R; i++) {
            String v = "" + i;
            int result = 0;
            for (int j = 0; j < v.length(); j++) {
                int index = v.charAt(j) - '0';
                result |= (1 << index);
            }
            if (!set.contains(result)) {
                set.add(result);
            }else{
                re = Math.max(Integer.bitCount(result), re);
            }
        }
        for(int i : set){
            for(int j : set){
                if(i != j){
                    int v = i & j;
                    re = Math.max(Integer.bitCount(v), re);
                }
            }
        }
        return re;
    }
}
