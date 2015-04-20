
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class Revmatching {

    public int smallest(String[] A) {
        int n = A.length;
        int[][]map = new int[n][n];
        for(int i = 0; i< n; i++){
            for(int j = 0; j< n; j++){
                map[i][j] = A[i].charAt(j) - '0';
            }
        }
       
        int result = Integer.MAX_VALUE;
        int[]cost = new int[n];
        for (int i = 1; i < 1 << n; i++) {
            
            int left = n - Integer.bitCount(i) + 1;
            for (int j = 0; j < n; j++) {
                int total = 0;
                for (int k = 0; k < n; k++) {
                    if (((1 << k) & i) != 0) {
                        total += map[k][j];
                    }
                }

                cost[j] = total;
            }
            Arrays.sort(cost);
            int total = 0;
            for (int j = 0; j < left; j++) {
                total += cost[j];
            }           
            result = Math.min(result, total);
        }
        return result;
    }

    public static void main(String[] args) {

        String[] data = {"65441060903302167490", "54584129060537207056", "04063109223863893065", "32095021935342825271", "86692607600049489234", "60550452828998606283", "93121304050306344633", "49755871330285779159", "01618175005086510403", "31120822471060153329", "58647094718671708562", "32671323792255313133", "75428263005278979250", "57998998995496960076", "58856145942496846637", "20759381082208239849", "94280256668006492067", "28787496941560369415", "33300662832690339459", "23731251006225109477"};
        System.out.println(new Revmatching().smallest(data));
    }
}
