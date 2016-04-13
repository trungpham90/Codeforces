
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class BracketSequenceDiv1 {

    public long count(String s) {
        int n = s.length() / 2;
        if (n == 0) {
            return 0;
        }

        String b = s.substring(n);
        HashMap<String, Integer> map = new HashMap();
        long result = 0;
        a:
        for (int i = 1; i < (1 << n); i++) {
            Stack<Character> st = new Stack();
            for (int j = 0; j < n; j++) {
                if (((1 << j) & i) != 0) {
                    if (s.charAt(j) == '(' || s.charAt(j) == '[') {
                        st.push(s.charAt(j));
                    } else if (st.isEmpty()) {
                        continue a;

                    } else if (s.charAt(j) == ')') {
                        if (st.peek() == '(') {
                            st.pop();
                        } else {
                            continue a;
                        }
                    }else {
                        if(st.peek() == '['){
                            st.pop();
                        }else{
                            continue a;
                        }
                    }
                }
            }
            if(st.isEmpty()){
                result++;                
            }
            StringBuilder builder = new StringBuilder();
            
            while(!st.isEmpty()){
                builder.append(getReverse(st.pop()));
            }
            //System.out.println(builder);
            String v = builder.toString();
            if(map.containsKey(v)){
                map.put(v, map.get(v) + 1);
            }else{
                map.put(v, 1);
            }

        }
        //System.out.println(map);
        //System.out.println(b);
        a:
        for (int i = 1; i < (1 << b.length()); i++) {
            Stack<Character> st = new Stack();
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < b.length(); j++) {
                if (((1 << j) & i) != 0) {
                    if (b.charAt(j) == '(' || b.charAt(j) == '[') {
                        st.push(b.charAt(j));
                    } else if (st.isEmpty()) {
                        builder.append(b.charAt(j));

                    } else if (b.charAt(j) == ')') {
                        if (st.peek() == '(') {
                            st.pop();
                        } else {
                            continue a;
                        }
                    }else {
                        if(st.peek() == '['){
                            st.pop();
                        }else{
                            continue a;
                        }
                    }
                }
            }
            //System.out.println(builder.toString() + "  " + Integer.toBinaryString(i));
            if(st.isEmpty()){
                if(map.containsKey(builder.toString())){
                    result += map.get(builder.toString());
                }
                if(builder.length() == 0){                    
                    result++;
                }
            }
        }
        return result;
    }
    
    public char getReverse(char c){
        if(c == '('){
            return ')';
        }
        return ']';
    }
    public static void main(String[] args) {
        String v = "())";
        System.out.println(new BracketSequenceDiv1().count(v));
    }

}
