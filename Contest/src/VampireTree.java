
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class VampireTree {

    int max = 0;

    public int maxDistance(int[] num) {

        Arrays.sort(num);
        ArrayList<Integer>[] map = new ArrayList[num.length];
        for (int i = 0; i < num.length; i++) {
            map[i] = new ArrayList();
        }
        LinkedList<Integer> q = new LinkedList();
        int n = num.length;
        q.add(n - 1);
        int index = n - 2;
        while (!q.isEmpty()) {
            int v = q.poll();
            if (num[v] > 1 || v == n - 1) {
                int total = num[v] - (v == (n - 1) ? 0 : 1);
                for (int i = 0; i < total; i++) {
                    if (index < 0) {
                        return -1;
                    }
                    map[v].add(index);
                    q.add(index--);
                }

            }
        }
        if(index != -1){
            return -1;
        }
        if (n == 1) {
            return 0;
        }
        int max = 0;
        for (int i = 0; i < n; i++) {
            if (num[i] > 1) {
                max++;
            }
        }
        return max + 1;
    }

    public static void main(String[] args) {
        int[] data = {1, 1, 1};
        System.out.println(new VampireTree().maxDistance(data));
    }
}
