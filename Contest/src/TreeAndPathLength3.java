
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class TreeAndPathLength3 {

    public int[] construct(int s) {
        ArrayList<Point> list = new ArrayList();
        list.add(new Point(0, 1));
        list.add(new Point(1, 2));
        list.add(new Point(2, 3));
        if (s <= 497) {
            int start = 4;
            for (int i = 0; i < s - 1; i++) {
                list.add(new Point(2, start++));
            }
        } else {
            int need = s / 100;
            int start = 4;
            for (int i = 0; i < 98; i++) {
                list.add(new Point(1, start++));
            }
            
           // System.out.println(need);
            for (int i = 0; i < need - 1; i++) {
                list.add(new Point(2, start++));
            }
            list.add(new Point(3, start));
            int v = start;
            start++;
            need = (s % 100) ;

            for (int i = 0; i < need; i++) {
                list.add(new Point(v, start++));
            }
           // System.out.println(list.size() + " " + start);
        }
        int[] result = new int[list.size() * 2];
        int index = 0;
        for (Point p : list) {
            result[index++] = p.x;
            result[index++] = p.y;
        }
        
        return result;

    }

    class Point {

        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public String toString(){
            return x + " " + y;
        }
    }
    public static void main(String[] args) {
        System.out.println(new TreeAndPathLength3().construct(173));
    }
}
