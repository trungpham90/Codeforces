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

public class LeftRotation {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int a[] = new int[n];
        for (int a_i = 0; a_i < n; a_i++) {
            a[a_i] = in.nextInt();
        }
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            int index = (i - k + n) % n;
            data[index] = a[i];
        }
        for (int i = 0; i < n; i++) {
            System.out.print(data[i] + " ");
        }
    }
}
