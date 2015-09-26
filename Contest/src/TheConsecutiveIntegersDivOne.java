
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class TheConsecutiveIntegersDivOne {

    public int find(int[] num, int k) {
        if (k == 1) {
            return 0;
        }
        Arrays.sort(num);
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < num.length - 1; i++) {
            int start = num[i];
            int end = num[i + 1];
            while (start <= end) {
                int mid = (start + end) >> 1;
                int a = cal(mid, k, num);
                int b = cal(mid + 1, k, num);
                if (a > b) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
                result = Math.min(result, Math.min(a, b));

            }
        }
        return result;
    }

    public int cal(int i, int k, int[] num) {
        int result = Integer.MAX_VALUE;
        for (int j = 0; j + k <= num.length; j++) {
            int tmp = 0;
            for (int h = 0; h < k; h++) {
                tmp += Math.abs(i + h - num[j + h]);
            }
            result = Math.min(result, tmp);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] num = {-4845232, -5077278, -1696332, 9685053, -8008906, -2041580, -2825611, 7330935, -1994531, -5206896, -202206, -1506777, 8005773};
        int k = 9;
        System.out.println(new TheConsecutiveIntegersDivOne().find(num, k));


    }
}
