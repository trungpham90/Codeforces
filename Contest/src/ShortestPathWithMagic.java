
import java.util.Arrays;
import java.util.PriorityQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class ShortestPathWithMagic {

    public double getTime(String[] dist, int k) {
        int n = dist.length;
        double[][] map = new double[n][k + 1];
        for (double[] a : map) {
            
            Arrays.fill(a, 100000);

        }
        map[0][k] = 0;
        PriorityQueue<State> q = new PriorityQueue<>((a, b) -> Double.compare(a.d, b.d));
        
        q.add(new State(0, k, 0));
        while (!q.isEmpty()) {
            State s = q.poll();
            if (map[s.x][s.k] == s.d) {
                for (int i = 0; i < n; i++) {
                    double v = dist[s.x].charAt(i) - '0';
                    if (map[s.x][s.k] + v < map[i][s.k]) {
                        map[i][s.k] = v + map[s.x][s.k];
                        q.add(new State(i, s.k, map[i][s.k]));
                    }
                    if (s.k > 0) {
                        if (map[s.x][s.k] + (v / 2) < map[i][s.k - 1]) {
                            map[i][s.k - 1] = (v / 2) + map[s.x][s.k];
                            q.add(new State(i, s.k - 1, map[i][s.k - 1]));
                        }
                    }
                }
            }
        }
        double result = 100000;
        for (int i = 0; i <= k; i++) {
            result = Math.min(map[1][i], result);
        }
        return result;
    }

    class State {

        int x, k;
        double d;

        public State(int x, int k, double d) {
            this.x = x;
            this.k = k;
            this.d = d;
        }

    }
}
