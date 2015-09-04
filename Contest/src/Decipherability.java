/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class Decipherability {
    public String check(String s, int k){
        int n = s.length();
        if(n == k){
            return "Certain";
        }
        int[]count = new int[26];
        int greater = 0;
        for(int i = 0; i < n; i++){
            int index = s.charAt(i) - 'a';
            count[index]++;
            if(count[index] > 1){
                greater++;
            }
            if(i > k){
                int other = s.charAt(i - k - 1) - 'a';
                count[other]--;
                if(count[other] == 1){
                    greater--;
                }
            }
            if(i >= k){
                if(greater != 0){
                    return "Uncertain";
                }
            }
        }
        return "Certain";
    }
    public static void main(String[] args) {
        System.out.println(new Decipherability().check("aba", 2));
    }
}
