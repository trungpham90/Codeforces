
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
public class JanuszTheBusinessman {

    public int makeGuestsReturn(int[] a, int[] d) {
        Point[] data = new Point[a.length];
        for (int i = 0; i < data.length; i++) {
            data[i] = new Point(a[i], d[i]);
        }
        Arrays.sort(data, (e, f) -> {
            if (e.y != f.y) {
                return Integer.compare(e.y, f.y);
            }
            return Integer.compare(e.x, f.x);
        });
        int result = 0;
        int index = 0;
        while(index < a.length){
            int min = data[index].y;
            int max = -1;
            for(int i = 0; i < data.length; i++){
                if(data[i].x <= min && data[i].y >= min){
                    if(max == -1 || data[max].y < data[i].y){
                        max = i;
                    }
                }
            }
            result++;
            index = a.length;
            for(int i = 0; i < data.length; i++){
                if(data[i].x > data[max].y){
                    index = i;
                    break;
                }
            }
        }
        
        return result;
    }

    class Point {

        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
