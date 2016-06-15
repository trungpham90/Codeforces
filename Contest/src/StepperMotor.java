/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class StepperMotor {

    public int rotateToNearest(int n, int current, int[] target) {
        int result = cal(current, target[0], n);
        for (int i = 1; i < target.length; i++) {
            result = min(result, cal(current, target[i], n));
        }
        return result;
    }

    int min(int a, int b) {
        if (Math.abs(a) != Math.abs(b)) {
            if (Math.abs(a) > Math.abs(b)) {
                return b;
            }
            return a;
        }
        return a >= 0 ? a : b;
    }

    int cal(long cur, long tar, long n) {
        //   System.out.println("Start " + cur + " " + tar);
        cur %= n;
        tar %= n;
        while (cur < 0) {
            cur += n;
        }
        while (tar < 0) {
            tar += n;
        }
        cur %= n;
        tar %= n;
        int a = (int) ((cur - tar + n) % n);

        a = -a;

        int b = (int) ((tar - cur + n) % n);

     //   System.out.println(cur + " " + tar + "  " + a + " " + b);
        return min(a, b);
    }

    public static void main(String[] args) {
        int[] t = {64077, -567273, 105845, -279980, 35557,
            -286190, -652879, -431665, -634870, -454044};
        int n = 23;
        int c = 10;
        System.out.println(new StepperMotor().rotateToNearest(n, c, t));
    }

}
