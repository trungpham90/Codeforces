/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class EllysPrimesMatrix {

    public int getCount(String[] matrix) {
        boolean[] p = new boolean[10000000];
        p[0] = true;
        p[1] = true;
        for (int i = 2; i < p.length; i++) {
            if (!p[i]) {
                for (int j = i + i; j < p.length; j += i) {
                    p[j] = true;
                }
            }
        }
        return count(0, 0, matrix, p);

    }

    public int count(int index, int cur, String[] matrix, boolean[] p) {
       // System.out.println(index);
        if (index == matrix.length) {
            if (!p[cur]) {
                System.out.println(cur);
                return 1;
            }
            return 0;
        }
        boolean[] digit = new boolean[10];
        int result = 0;
        for (int i = 0; i < matrix[index].length(); i++) {
            int nxt = matrix[index].charAt(i) - '0';
            if (!digit[nxt]) {
                digit[nxt] = true;
                result += count(index + 1, cur * 10 + nxt, matrix, p);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String[] data = {"90973", "70663"};
        System.out.println(new EllysPrimesMatrix().getCount(data));
    }
}
