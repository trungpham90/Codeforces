/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class ATaleOfTwoStacks {

    public class Solution {

        public static class MyQueue<T> {

            Stack<T> stackNewestOnTop = new Stack<T>();
            Stack<T> stackOldestOnTop = new Stack<T>();

            public void enqueue(T value) { // Push onto newest stack
                stackNewestOnTop.add(value);
            }

            public T peek() {
                if (stackOldestOnTop.isEmpty()) {
                    while (!stackNewestOnTop.isEmpty()) {
                        stackOldestOnTop.add(stackNewestOnTop.pop());
                    }
                }
                return stackOldestOnTop.peek();

            }

            public T dequeue() {
                if (stackOldestOnTop.isEmpty()) {
                    while (!stackNewestOnTop.isEmpty()) {
                        stackOldestOnTop.add(stackNewestOnTop.pop());
                    }
                }
                return stackOldestOnTop.pop();
            }
        }

        public static void main(String[] args) {
            MyQueue<Integer> queue = new MyQueue<Integer>();

            Scanner scan = new Scanner(System.in);
            int n = scan.nextInt();

            for (int i = 0; i < n; i++) {
                int operation = scan.nextInt();
                if (operation == 1) { // enqueue
                    queue.enqueue(scan.nextInt());
                } else if (operation == 2) { // dequeue
                    queue.dequeue();
                } else if (operation == 3) { // print/peek
                    System.out.println(queue.peek());
                }
            }
            scan.close();
        }
    }

}
