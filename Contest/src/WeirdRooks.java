
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class WeirdRooks {
    TreeSet<String> set;
    boolean[][][]dp;
    public String describe(int[] cols) {
        set = new TreeSet<>((a,b)-> {
            String[]x = a.split(",");
            String[]y = b.split(",");
            if(!x[0].equals(y[0])){
                return x[0].compareTo(y[0]);
            }else{
                int c = Integer.parseInt(x[1]);
                int d = Integer.parseInt(y[1]);
                return Integer.compare(c, d);
            }
        });
        dp = new boolean[cols.length][1 << 10][cols.length * 10 + 1];
        cal(cols.length - 1, 0,0,cols);
        StringBuilder builder = new StringBuilder();
        for(String v : set){
            if(builder.length() != 0){
                builder.append(" ");
            }
            builder.append(v);
        }
        return builder.toString();
    }
    
    public void cal(int row, int colMask, int total , int[]cols){
        if(row == -1){
            if(total == 1 && Integer.bitCount(colMask) == 1){
                System.out.println(Integer.toBinaryString(colMask));
            }
            set.add(Integer.bitCount(colMask) + "," + total);
            return;
        }
        if(dp[row][colMask][total]){
            return;
        }
        dp[row][colMask][total] = true;
        
        int spec = 0;
        for(int i = cols[row] - 1; i >= 0; i--){
            if(((1<<i) & colMask) == 0){
                cal(row - 1, colMask | (1<<i) , total + spec, cols);
                spec++;
            }
        }
        cal(row - 1, colMask, total + spec, cols);
        
    }
    
    public static void main(String[] args) {
        int[]cols = {1,2,3};
        System.out.println(new WeirdRooks().describe(cols));
    }
}
