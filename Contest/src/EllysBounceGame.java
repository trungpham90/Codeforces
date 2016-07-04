
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class EllysBounceGame {

    public long getScore(int[] tiles) {
        long result = 0;
        int n = tiles.length;
        long[] pre = new long[n];
        TreeSet<Integer> set = new TreeSet();
        for (int i = 0; i < n; i++) {
            pre[i] = tiles[i];
            if (i > 0) {
                pre[i] += pre[i - 1];
            }
            if (tiles[i] % 2 != 0) {
                set.add(i);
            }
        }
        for (int i = 0; i < tiles.length; i++) {
            result = Long.max(result, cal(i, 1, n, tiles, pre, new TreeSet<>(set)));
            result = Long.max(result, cal(i, -1, n, tiles, pre, new TreeSet<>(set)));
        }
        return result;
    }

    public long cal(int index, int dir, int n, int[] data, long[] pre, TreeSet<Integer> set) {
        long result = 0;
        int count = 0;
        while (true) {
            result += data[index];
            if (data[index] % 2 != 0) {
                set.remove(index);
                dir = -dir;
                count++;
            }
            if (dir == 1) {
                if (set.higher(index) != null) {
                    int v = set.higher(index);
                    result += pre[v - 1] - pre[index] + count - (data[index] % 2 != 0 ? 1 : 0);
                    index = v;
                } else {
                    result += pre[n - 1] - pre[index] + count - (data[index] % 2 != 0 ? 1 : 0);
                    break;
                }
            } else if (set.lower(index) != null) {
                int v = set.lower(index);
                result += pre[index - 1] - pre[v] + count - (data[index] % 2 != 0 ? 1 : 0);
                index = v;
            } else {
                result += (index > 0 ? pre[index - 1] : 0) + count - (data[index] % 2 != 0 ? 1 : 0);
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] data = {42, 13, 17, 17};
        System.out.println(new EllysBounceGame().getScore(data));
    }
}
