/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class DetectACycle {

    class Node {

        int data;
        Node next;
    }

    boolean hasCycle(Node head) {
        if (head == null) {
            // System.out.println(0);
            return false;
        }
        Node a = head;
        Node b = head;
        do {
            a = a.next;
            b = b.next;
            if (b != null) {
                b = b.next;
            }
        } while (a != b && b != null);
        if (b == null) {
            //System.out.println(0);
            return false;
        }
        //System.out.println(1);
        return true;
    }

}
