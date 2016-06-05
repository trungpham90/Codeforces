
import java.util.ArrayList;
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
public class FractionSplit {

    public String[] getSum(int n, int d) {
        ArrayList<String> result = new ArrayList();
        while (n != 0) {
            int x = (int) Math.ceil((double) d / n);
            result.add("1/" + x);
            n = n * x - d;
            d = d*x;
            int g = gcd(n, d);
            n /= g;
            d /= g;
          //  System.out.println(n + " " + d);
        }
        String[] re = new String[result.size()];
        for (int i = 0; i < re.length; i++) {
            re[i] = result.get(i);
        }
        return re;
    }

    int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        int[] data = {4,5};
        System.out.println(Arrays.toString(new FractionSplit().getSum(data[0], data[1])));
    }
}
