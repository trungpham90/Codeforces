
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class Sunnygraphs {

    public long count(int[] a) {
        int n = a.length;
        boolean[] check = new boolean[n];
        LinkedList<Integer> q = new LinkedList();
        q.add(0);
        check[0] = true;
        int c = 1;
        while (!q.isEmpty()) {
            int node = q.poll();
            if (!check[a[node]]) {
                c++;
                q.add(a[node]);
                check[a[node]] = true;
            }
        }

        boolean[] other = new boolean[n];
        q = new LinkedList();
        q.add(1);
        other[1] = true;
        int d = 1;
        while (!q.isEmpty()) {
            int node = q.poll();
            if (!other[a[node]]) {
                d++;
                q.add(a[node]);
                other[a[node]] = true;
            }
        }

        int same = 0;
        for (int i = 0; i < n; i++) {
            if (check[i] && other[i]) {
                same++;
            }
        }
        System.out.println(c + " " + d + " " + same);
        long result = ((1L << same) - 1L) * (1L << (n - same));
        //System.out.println(result);
        result += ((1L << (c - same)) - 1L) * ((1L << (d - same)) - 1L) * ((1L << (n - c - d + same)));
        return result + (same != 0 ? (1L << (n - c - d + same)) : 0);

    }

    public static void main(String[] args) {
        int[] data = {3, 2, 1, 2, 2, 6, 0};
        System.out.println(new Sunnygraphs().count(data));
    }
}
