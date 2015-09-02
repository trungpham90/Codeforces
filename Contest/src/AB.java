/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class AB {

    public String createString(int n, int k) {
        for(int i = 0; i <= n; i++){
            int left = n - i;
            if(left*i >= k){
                int tmp = k;
                int start = i;
                StringBuilder builder = new StringBuilder();
                int a = i;
                int b = left;
                while(tmp > 0){
                    if(tmp >= start){
                        tmp -= start;
                        builder.append("B");
                        b--;
                    }else{
                        start--;
                        builder.append("A");
                        a--;
                    }
                }
                while(a > 0){
                    builder.append("A");
                    a--;
                }
                while(b > 0){
                    builder.append("B");
                    b--;
                }
                return builder.reverse().toString();
            }
        }
        return "";
    }
    public static void main(String[] args) {
        System.out.println(new AB().createString(10, 12));
    }

   
}
