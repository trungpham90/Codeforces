
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class OddEvenTree {

    public int[] getTree(String[] x) {
        int n = x.length;
        ArrayList<Integer>[] map = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            map[i] = new ArrayList();
        }
        int[] result = new int[2 * (n - 1)];
        int index = 0;


        Stack<Integer> s = new Stack();
        int odd = -1;
        for (int i = 0; i < n; i++) {
            if (x[0].charAt(i) == 'E') {
                if (i != 0) {
                    if (odd != -1) {
                        map[odd].add(i);
                        map[i].add(odd);
                        result[index++] = odd;
                        result[index++] = i;
                    } else {
                        s.push(i);
                    }
                }
            } else {
                if (i == 0) {
                  
                    return new int[]{-1};
                } else {
                    odd = i;
                    map[0].add(i);
                    map[i].add(0);
                    result[index++] = 0;
                    result[index++] = i;
                    while (!s.isEmpty()) {
                        int v = s.pop();
                        map[odd].add(v);
                        map[v].add(odd);
                        result[index++] = odd;
                        result[index++] = v;
                    }
                }
            }
        }
        for (int i = 1; i < n; i++) {
            int[] od = new int[n];
            Arrays.fill(od, -1);
            od[i] = 0;
            LinkedList<Integer> q = new LinkedList();
            q.add(i);
            while (!q.isEmpty()) {
                int node = q.removeFirst();
                for (int j : map[node]) {
                    if (od[j] == -1) {
                        od[j] = 1 + od[node];
                        q.add(j);
                    }
                }
            }
          //  System.out.println(i + " " + Arrays.toString(od));
            for (int j = 0; j < n; j++) {
                if (x[i].charAt(j) == 'E' && od[j] % 2 != 0) {
                    return new int[]{-1};
                } else if (x[i].charAt(j) == 'O' && od[j] % 2 == 0) {
                    return new int[]{-1};
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String[] x = {"EOE",
            "OEO",
            "EOE"};
        System.out.println(Arrays.toString(new OddEvenTree().getTree(x)));
    }
}
