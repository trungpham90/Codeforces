
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
public class ANewHope {

    public int count(int[] f, int[] l, int d) {
        int n = f.length;
        int[] pos = new int[n];
        for (int i = 0; i < n; i++) {
            pos[l[i] - 1] = i;
        }
        int[] other = new int[n + 1];
        for (int i = 0; i < n; i++) {
            other[f[i] - 1] = i;
        }
        if (Arrays.equals(f, l)) {
            return 1;
        }
        int result = 0;
        int c = n - d;
        for(int i = 0; i < n; i++){
            if(other[i] > pos[i]){
                int v = 0;
                while(other[i] > pos[i]){
                    other[i] -= c;
                    v++;
                }
                result = Integer.max(result, v);
            }
        }
        return result + 1;
    }

    public static void main(String[] args) {
        int[][] data = {{1, 2, 3, 4},
        {4, 3, 2, 1}};
        System.out.println(new ANewHope().count(data[0], data[1], 3));
    }
}
