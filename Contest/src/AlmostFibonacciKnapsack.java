
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class AlmostFibonacciKnapsack {

    HashSet<Long> set = new HashSet();
    int[] store;

    public int[] getIndices(long x) {
        ArrayList<Long> list = new ArrayList();
        list.add(2L);
        list.add(3L);
        for (int i = 0; i < 100; i++) {
            long v = list.get(list.size() - 1) + list.get(list.size() - 2) - 1;
            if (v > x) {

                break;
            }
            list.add(v);
        }
        store = new int[list.size()];
        Arrays.fill(store, -1);
        int v = cal(x, 0, list.size(), list);
        if (v != -1) {
            int[]re = Arrays.copyOf(store, v);
            return re;
        } else {

            int[] re = {-1};
            return re;
        }
    }

    int cal(long left, int index, int last, ArrayList<Long> list) {
        if (left == 0) {
            return index;
        }
        if (set.contains(left)) {
            return -1;
        }
        set.add(left);
        for (int i = last - 1; i >= 0; i--) {
            if (list.get(i) <= left) {
                int v  = cal(left - list.get(i), index + 1, i, list);
                if (v != -1) {
                    store[index] = i + 1;
                    return v;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new AlmostFibonacciKnapsack().getIndices(86267769395L)));
    }
}
