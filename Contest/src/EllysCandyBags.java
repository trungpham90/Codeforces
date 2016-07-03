/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class EllysCandyBags {
    public int getSize(String[] packets){
        int[]count = new int[26];
        for(String v : packets){
            for(int i = 0; i < v.length(); i++){
                count[v.charAt(i) - 'A']++;
            }
        }
        int result = 0;
        for(int i : count){
            result += (i - (i % 2));
        }
        return result/2;
    }
}
