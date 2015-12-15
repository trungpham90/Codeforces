
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class TheKingsFactorization {

    public long[] getVector(long N, long[] primes) {
        int max = 2*primes.length;
        ArrayList<Long> list = new ArrayList();
        for (long i : primes) {
            while (N % i == 0) {
                N /= i;
                list.add(i);
            }
        }       
        for (long i = 2; i * i <= N && list.size() + 1 < max ; i++) {
            while (N % i == 0) {
                N /= i;
                list.add(i);
            }
        }
      //  System.out.println(list.size() + " " + max);
        if (N != 1) {
            list.add(N);
        }
        Collections.sort(list);
        long[] result = new long[list.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }
        return result;
    }
    public static void main(String[] args) {
        long []data = {2,5};
        System.out.println(Arrays.toString(new TheKingsFactorization().getVector(210, data)));
    }
}
