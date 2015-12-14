

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class WaitingForBus {

    public double whenWillBusArrive(int[] time, int[] prob, int s) {

        double[] dp = new double[s + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < time.length; j++) {
                double p = (double) prob[j] / 100;
                dp[i] += p * (i - time[j] > 0 ? dp[i - time[j]] : time[j] - i);
            }
        }
        return dp[s];
    }

    public static void main(String[] args) {
        int[] time = {5, 100};
        int[] prob = {90, 10};
        System.out.println(new WaitingForBus().whenWillBusArrive(time, prob, 5));
    }
}
