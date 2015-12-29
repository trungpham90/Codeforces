
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class FourStrings {

    int[][][][] dp;

    public int shortestLength(String a, String b, String c, String d) {

        String[] data = new String[]{a, b, c, d};
        int[][] k = new int[4][];
        for (int i = 0; i < 4; i++) {
            k[i] = KMP(data[i]);
        }
        int result = Integer.MAX_VALUE;
        int[] per = {0, 1, 2, 3};
        do {
            int[] p = {0, 0, 0, 0};
            int count = 0;
            for (int i = 0; i < 4; i++) {
                while (p[per[i]] < data[per[i]].length()) {
                    char x = data[per[i]].charAt(p[per[i]]);
                    for (int j = i; j < 4; j++) {
                        p[per[j]] = determine(p[per[j]], k[per[j]], x, data[per[j]]);
                    }
                    count++;

                }
            }
            result = Integer.min(result, count);
        } while (nextPer(per));
        return result;
    }

    public static int[] KMP(String val) {
        int i = 0;
        int j = -1;
        int[] result = new int[val.length() + 1];
        result[0] = -1;
        while (i < val.length()) {
            while (j >= 0 && val.charAt(j) != val.charAt(i)) {
                j = result[j];
            }
            j++;
            i++;
            result[i] = j;
        }
        return result;

    }

    int determine(int i, int[] k, char c, String data) {
        if (i == data.length()) {
            return i;
        }
        while (i >= 0 && data.charAt(i) != c) {
            i = k[i];
        }
        i++;
        return i;
    }

    public static boolean nextPer(int[] data) {
        int i = data.length - 1;
        while (i > 0 && data[i] <= data[i - 1]) {
            i--;
        }
        if (i == 0) {
            return false;
        }
        int j = data.length - 1;
        while (data[j] <= data[i - 1]) {
            j--;
        }
        int temp = data[i - 1];
        data[i - 1] = data[j];
        data[j] = temp;
        Arrays.sort(data, i, data.length);
        return true;
    }

    public static void main(String[] args) {
        String[] data = {"a",
            "bc",
            "def",
            "ghij"};
        System.out.println(new FourStrings().shortestLength(data[0], data[1], data[2], data[3]));
    }
}
