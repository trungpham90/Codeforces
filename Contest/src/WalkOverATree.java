
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
public class WalkOverATree {
    int[][]dp;
    public int maxNodesVisited(int[] parent, int l) {
        int n = parent.length + 1;
        ArrayList<Integer>[] map = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            map[i] = new ArrayList();
        }
        for (int i = 0; i < n - 1; i++) {
            map[parent[i]].add(i + 1);
        }
        int[] child = new int[n];
        dfs(0, child, map);
        dp = new int[n][l + 1];
        for(int []a : dp){
            Arrays.fill(a,-1);
        }
        return cal(0,l,child, map);
    }

    public int cal(int index, int left, int[] child, ArrayList<Integer>[] map) {
        if (left == 0) {
            return 1;
        }
        if (left >= 2 * (child[index] - 1)) {
            return child[index];
        }
        if(dp[index][left] != -1){
            return dp[index][left];
        }
        int result = 0;

        for (int i : map[index]) {
            for (int j = 1; j <= left; j++) {
                int v = cal(i, j - 1, child, map);
                int node = child[index] - 1 - child[i];
                v += Math.min((left - j) / 2, node);
                result = Math.max(result, v);
            }
        }

        return dp[index][left] = result + 1;
    }

    public int dfs(int index, int[] child, ArrayList<Integer>[] map) {
        int result = 1;
        for (int i : map[index]) {
            result += dfs(i, child, map);
        }
        return child[index] = result;

    }
    public static void main(String[] args) {
        
        int[]pa = {0, 1, 2, 3};
        int l = 2;
        System.out.println(new WalkOverATree().maxNodesVisited(pa, l));
    }
}
