/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class RandomPancakeStack {

    double[][] dp;

    public double expectedDeliciousness(int[] d) {
        dp = new double[d.length][d.length];
        double result = 0;
        for (int i = 0; i < d.length; i++) {
            result += cal(i, d.length - 1, d) * 1.0 / d.length;
        }
        return result;
    }

    public double cal(int index, int left, int[] d) {
        if (index == 0) {
            return d[index];
        }
        if (dp[index][left] != 0) {
            return dp[index][left];
        }
        int l = index;
        int r = left - l;
        //  System.out.println(l + " " + r + " " + index + " " + l);
        double result = (double) (r * d[index]) / left;
        for (int i = 0; i < index; i++) {
            result += ((double) 1 / left) * (d[index] + cal(i, left - 1, d));
        }
        // System.out.println(result + " " + left + " " + index);
        return dp[index][left] = result;

    }

    public static void main(String[] args) {
        int[] d = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(new RandomPancakeStack().expectedDeliciousness(d));
    }
}
