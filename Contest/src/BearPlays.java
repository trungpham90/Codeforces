
import java.util.HashMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class BearPlays {

    HashMap<Long, Integer> map = new HashMap();
    HashMap<Integer, Long> rev = new HashMap();

    public int pileSize(int a, int b, int k) {
        return (int) (cal(Math.min(a, b), Math.max(a, b), 0, k));
    }

    long cal(long a, long b, int index, int k) {
        long mod = a + b;
        long tmp = (pow(2, k, mod)*a)%mod;
        if(tmp < mod - tmp){
            return tmp;
        }
        return mod - tmp;
    }

    long pow(long a, long b, long mod) {
        if (b == 0) {
            return 1;
        }
        long v = pow(a, b / 2, mod);
        if (b % 2 == 0) {
            return v * v % mod;
        } else {
            return ((v * a) % mod) * v % mod;
        }
    }

    public static void main(String[] args) {
        int[] data = {490044323, 980088648, 1982385059};
        System.out.println(new BearPlays().pileSize(data[0], data[1], data[2]));
    }
}
