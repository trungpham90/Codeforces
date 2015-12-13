
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
public class ChristmasTreeDecoration {

    public int solve(int[] col, int[] x, int[] y) {

        Edge[] data = new Edge[x.length];
        for (int i = 0; i < data.length; i++) {
            if (col[x[i] - 1] == col[y[i] - 1]) {
                data[i] = new Edge(x[i] - 1, y[i] - 1, 1);
            } else {
                data[i] = new Edge(x[i] - 1, y[i] - 1, 0);
            }
        }
        Arrays.sort(data, (o1, o2) -> Integer.compare(o1.c, o2.c));
        int n = col.length;
        int[] u = new int[n];
        for (int i = 0; i < n; i++) {
            u[i] = i;
        }
        int result = 0;
        for (Edge e : data) {
            if (find(e.x, u) != find(e.y, u)) {
                result += e.c;
                u[find(e.x, u)] = find(e.y, u);
            }
        }
        return result;
    }

    int find(int index, int[] u) {
        if (index != u[index]) {
            return u[index] = find(u[index], u);
        }
        return index;
    }

    class Edge {

        int x, y, c;

        public Edge(int x, int y, int c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }
    }
}
