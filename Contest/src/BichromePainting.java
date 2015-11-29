
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class BichromePainting {

    public int[][][][] dp;

    public String isThatPossible(String[] bo, int k) {
        if (k == 1) {
            return "Possible";
        }
        int[][] data = new int[bo.length][bo[0].length()];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = bo[i].charAt(j) == 'B' ? 1 : 0;
            }
        }

        int n = bo.length;
        int m = bo[0].length();
        boolean found = true;
        while (found) {
            found = false;
            for (int i = 0; i < n && !found; i++) {
                for (int j = 0; j < m; j++) {
                    if (data[i][j] == -1) {
                        continue;
                    }
                    found = false;
                    int sX = -1;
                    int sY = -1;
                    for (int a = 0; a < n && sX == -1; a++) {
                        for (int b = 0; b < m; b++) {
                            if (a + k <= n && b + k <= m && a <= i && a + k > i && b <= j && b + k > j) {
                                found = true;
                                for (int g = 0; g < k && found; g++) {
                                    for (int h = 0; h < k; h++) {
                                        if (data[a + g][b + h] != -1 && data[i][j] != data[a + g][b + h]) {
                                            found = false;
                                            break;
                                        }
                                    }
                                }
                                if(found){
                                    sX = a;
                                    sY = b;
                                    break;
                                }
                            }
                        }
                    }

                    if (found) {
                        for (int g = 0; g < k && found; g++) {
                            for (int h = 0; h < k; h++) {


                                data[sX + g][sY + h] = -1;

                            }
                        }
                        break;
                    }
                }
            }
        }

        for(int []a : data){
            for(int i : a){
                if(i != -1){
                    return "Impossible";
                }
            }
        }

        return "Possible";


    }

    public static void main(String[] args) {
        String[] b = {"BW",
 "WB"};


        System.out.println(new BichromePainting().isThatPossible(b, 2));
    }
}
