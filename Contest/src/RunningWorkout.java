/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class RunningWorkout {

    public double getTime(int L, int v1, int v2, int v3) {
        double l = L;
        double result = l / v1 + l / (v1 - v2) + l / (v1 - v2 - v3);
        return result;

    }
}
