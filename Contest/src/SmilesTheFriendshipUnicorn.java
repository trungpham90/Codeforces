
import java.util.ArrayList;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class SmilesTheFriendshipUnicorn {

    public String hasFriendshipChain(int n, int[] a, int[] b) {
        HashSet<Integer>[] map = new HashSet[n];

        TreeSet<Edge> set = new TreeSet();
        for (int i = 0; i < n; i++) {
            map[i] = new HashSet();

        }
        for (int i = 0; i < a.length; i++) {
            int min = Integer.min(a[i], b[i]);
            int max = Integer.max(a[i], b[i]);

            set.add(new Edge(min, max));
            map[a[i]].add(b[i]);
            map[b[i]].add(a[i]);
        }

        for (int i = 0; i < a.length; i++) {

            int x = Integer.min(a[i], b[i]);
            int y = Integer.max(a[i], b[i]);
            for (int j = i + 1; j < a.length; j++) {
                int h = Integer.min(a[j], b[j]);
                int k = Integer.max(a[j], b[j]);
                if (y != h && y != k && x != h && x != k) {
                    if (set.contains(new Edge(Integer.min(x, h), Integer.max(x, h)))) {
                        int v = map[y].size() - 1 - (map[y].contains(h) ? 1 : 0) - (map[y].contains(k) ? 1 : 0);
                        int u = map[k].size() - 1 - (map[k].contains(y) ? 1 : 0) - (map[k].contains(x) ? 1 : 0);
                        if (v > 0 || u > 0) {
                            return "Yay!";
                        }
                    }
                    if (set.contains(new Edge(Integer.min(x, k), Integer.max(x, k)))) {
                        int v = map[y].size() - 1 - (map[y].contains(h) ? 1 : 0) - (map[y].contains(k) ? 1 : 0);
                        int u = map[h].size() - 1 - (map[h].contains(y) ? 1 : 0) - (map[h].contains(x) ? 1 : 0);
                        if (v > 0 || u > 0) {
                            return "Yay!";
                        }
                    }
                    if (set.contains(new Edge(Integer.min(y, h), Integer.max(y, h)))) {
                        int v = map[x].size() - 1 - (map[x].contains(h) ? 1 : 0) - (map[x].contains(k) ? 1 : 0);
                        int u = map[k].size() - 1 - (map[k].contains(y) ? 1 : 0) - (map[k].contains(x) ? 1 : 0);
                        if (v > 0 || u > 0) {
                            return "Yay!";
                        }
                    }
                    if (set.contains(new Edge(Integer.min(y, k), Integer.max(y, k)))) {
                        int v = map[x].size() - 1 - (map[x].contains(h) ? 1 : 0) - (map[x].contains(k) ? 1 : 0);
                        int u = map[h].size() - 1 - (map[h].contains(y) ? 1 : 0) - (map[h].contains(x) ? 1 : 0);
                        if (v > 0 || u > 0) {
                            return "Yay!";
                        }
                    }
                }
            }
        }

        return ":(";
    }

    class Edge implements Comparable<Edge> {

        int u, v;

        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }

        @Override
        public int compareTo(Edge o) {
            if (u != o.u) {
                return Integer.compare(u, o.u);
            }
            return Integer.compare(v, o.v);
        }

    }

}
