/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
import java.util.*;

public class DAGConstruction {

    public int[] construct(int[] x) {
        Point[] data = new Point[x.length];
        for (int i = 0; i < x.length; i++) {
            data[i] = new Point(i, x[i]);
        }
        Arrays.sort(data, (a, b) -> Integer.compare(a.y, b.y));
        ArrayList<Point> result = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        for (Point p : data) {
          //  System.out.println(list.size() + " " + p.y);
            if (p.y - 1 > list.size()) {
                return new int[]{-1};
            }
            for (int i = 0; i < p.y - 1; i++) {
                result.add(new Point(p.x, list.get(i)));
            }
            list.add(p.x);
        }
        int[] re = new int[result.size() * 2];
        for (int i = 0; i < re.length; i += 2) {
            re[i] = result.get(i / 2).x;
            re[i + 1] = result.get(i / 2).y;
        }
        return re;
    }

    class Point {

        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public static void main(String[] args) {
        int[]x = {2,1};
        System.out.println(Arrays.toString(new DAGConstruction().construct(x)));
    }
}
