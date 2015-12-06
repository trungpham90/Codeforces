
import java.util.Arrays;
import java.util.PriorityQueue;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class ShoppingSurveyDiv1 {

    public int minValue(int n, int k, int[] s) {

        int result = n;
        int start = 0;
        int end = n;
        while (start <= end) {
            int mid = (start + end) >> 1;
            int[] data = new int[n];
            for (int i : s) {
                if (i <= mid) {
                    for (int j = 0; j < i; j++) {
                        data[j]++;
                    }
                } else {
                    int left = i - mid;
                    for (int j = 0; j < mid; j++) {
                        data[j]++;
                    }
                    boolean[] check = new boolean[n];
                    for (int j = 0; j < left; j++) {
                        int min = -1;
                        for (int h = mid; h < n; h++) {
                            if ((min == -1 || data[min] > data[h]) && !check[h]) {
                                min = h;
                            }
                        }
                        check[min] = true;
                        data[min]++;
                    }
                }
            }

            int c = 0;
            for (int i : data) {
                if (i >= k) {
                    c++;
                }
            }
            if (c <= mid) {
                result = Math.min(c, result);
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int n = 20;
        int k = 3;
        int[] s = {1, 10, 3, 4, 8, 15, 3, 16, 18, 2, 7, 3};
        System.out.println(new ShoppingSurveyDiv1().minValue(n, k, s));
    }
}
