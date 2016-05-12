
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
public class LCMGCD {

    public String isPossible(int[] x, int t) {

        int[] re = parse(t);

        int[][] data = new int[x.length][2];
        for (int i = 0; i < x.length; i++) {
            data[i] = parse(x[i]);

        }
        if (data.length == 2) {
            if (Arrays.equals(lcm(data[0], data[1]), re) || Arrays.equals(gcd(data[0], data[1]), re)) {
                return "Possible";
            } else {
                return "Impossible";
            }
        }

        for (int i = 0; i < data.length; i++) {
            if (Arrays.equals(re, data[i])) {

                boolean[] check = new boolean[data.length];
                check[i] = true;
                if (cal(check, data, re)) {
                    return "Possible";
                }
            }
        }
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                int[] a = lcm(data[i], data[j]);
                int[] b = gcd(data[i], data[j]);
                if (!Arrays.equals(data[i], re) && !Arrays.equals(data[j], re) && (Arrays.equals(re, a) || Arrays.equals(re, b))) {
                    return "Possible";
                }
            }
        }
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                int[] a = lcm(data[i], data[j]);
                int[] b = gcd(data[i], data[j]);
                for (int h = 0; h < data.length; h++) {
                    if ((i != j && j != h && h != i) && (Arrays.equals(re, lcm(a, data[h])) || Arrays.equals(re, gcd(a, data[h])) || Arrays.equals(re, lcm(b, data[h])) || Arrays.equals(re, gcd(b, data[h])))) {
                        return "Possible";

                    }
                }
            }
        }
        return "Impossible";
    }

    public boolean cal(boolean[] check, int[][] data, int[] re) {
        if (data.length == 1) {
            return true;
        }
        for (int i = 0; i < data.length; i++) {
            if (!check[i]) {

                for (int j = i + 1; j < data.length; j++) {
                    if (!check[j]) {
                        int[] a = lcm(data[i], data[j]);
                        int[] b = gcd(data[i], data[j]);
                        if (Arrays.equals(re, lcm(a, re)) || Arrays.equals(re, gcd(a, re)) || Arrays.equals(re, lcm(b, re)) || Arrays.equals(re, gcd(b, re))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    int[] lcm(int[] a, int[] b) {
        return new int[]{Math.max(a[0], b[0]), Math.max(a[1], b[1])};
    }

    int[] gcd(int[] a, int[] b) {
        return new int[]{Math.min(a[0], b[0]), Math.min(a[1], b[1])};
    }

    int[] parse(int v) {
        int[] re = new int[2];
        while (v % 2 == 0) {
            re[0]++;
            v /= 2;
        }
        while (v % 3 == 0) {
            re[1]++;
            v /= 3;
        }
        return re;
    }

    public static void main(String[] args) {
        int[] data = {6, 36};
        int t = 36;
        System.out.println(new LCMGCD().isPossible(data, t));
    }
}
