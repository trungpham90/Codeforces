
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class Autogame {
    public int wayscnt(int[] a, int K){
        int n = a.length;
        boolean[][]map = new boolean[n][n];
        int s = Math.min(K , n) + 1;
        int[][]step = new int[n][s];
        for(int []x : step){
            Arrays.fill(x,-1);
        }
        for(int i = 0; i < n; i++){
            int cur = i;
            for(int j = 0; j < s; j++){
                step[i][j] = cur;
                cur = a[cur] - 1;
            }
        }
        for(int i = 0; i < n; i++){
            for(int j = i + 1; j < n; j++){
                for(int k = 0; k < s; k++){
                    if(step[i][k] == step[j][k]){
                        map[i][j] = true;
                        map[j][i] = true;
                        break;                                
                    }
                }
            }
        }
        boolean[]check = new boolean[n];
        long result = 1;
        long mod = 1000000007;
        for(int i = 0; i < n; i++){
            if(!check[i]){
                int v = dfs(i, check, map);
                result *= (v + 1);
                result %= mod;
            }
        }
        return (int)result;
    }
    
    public int dfs(int node,boolean[]check, boolean[][]map){
        
        check[node] = true;
        int result = 1;
        for(int i = 0; i < map.length; i++){
            if(map[node][i] && !check[i]){
                result += dfs(i, check, map);
            }
        }
        return result;
    }
    public static void main(String[] args) {
        int[]a = {2,1}	;
        int K = 42;
        System.out.println(new Autogame().wayscnt(a, K));
    }
}
