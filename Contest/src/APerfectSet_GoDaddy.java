
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class APerfectSet_GoDaddy {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int d = in.nextInt();
        int v = n / 2;
        if (n % 2 != 0) {
            v++;
        }
        v += d;
        System.out.println(v);
    }
}
