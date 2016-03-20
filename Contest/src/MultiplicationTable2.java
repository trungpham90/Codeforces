/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class MultiplicationTable2 {

    public int minimalGoodSet(int[] table) {
        int n = (int) Math.sqrt(table.length);
        int result = n;
        for (int i = 0; i < n; i++) {
            result = Integer.min(result, cal(i, new boolean[n], n, table));
        }
        return result;
    }

    public int cal(int index, boolean[] check, int n, int[] table) {
        check[index] = true;
        int result = 1;

        for (int i = 0; i < n; i++) {
            if (check[i]) {
                int v = table[i * n + index];
                if (!check[v]) {
                    result += cal(v, check, n, table);
                }
                v = table[index * n + i];
                if (!check[v]) {
                    result += cal(v, check, n, table);
                }
            }
        }
        return result;
    }
}
