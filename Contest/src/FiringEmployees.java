
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class FiringEmployees {

    public int fire(int[] m, int[] s, int[] p) {
        int n = m.length + 1;
        ArrayList<Integer>[] map = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            map[i] = new ArrayList();
        }
        for (int i = 0; i < m.length; i++) {
            map[m[i]].add(i + 1);
        }
        int v = cal(0, s, p, map);
        return v;
    }

    public int cal(int index, int[] s, int[] p, ArrayList<Integer>[] map) {
        int result = 0;
        if (index != 0) {
            result = p[index - 1] - s[index - 1];
        }
        for (int i : map[index]) {
            int v = cal(i, s, p, map);
            if (v > 0) {
                result += v;
            }
        }
        return result;
    }
}
