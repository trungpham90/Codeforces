/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class IsThisABinarySearchTree {

    class Node {

        int data;
        Node left;
        Node right;
    }

    boolean checkBST(Node root) {
        return check(root, -1, -1);
    }

    boolean check(Node node, int min, int max) {
        if (node == null) {
            return true;
        }
        if (min != -1 && max != -1 && min >= max) {
            return false;
        }
        if ((min != -1 && node.data <= min) || (max != -1 && node.data >= max)) {
            return false;
        }

        return check(node.left, min, node.data) && check(node.right, node.data, max);
    }

}
