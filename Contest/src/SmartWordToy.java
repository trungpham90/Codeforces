
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class SmartWordToy {

    public int minPresses(String start, String finish, String[] forbid) {
        int result = -1;
        String[][] data = new String[forbid.length][4];
        for (int i = 0; i < forbid.length; i++) {
            data[i] = forbid[i].split(" ");
        }
        LinkedList<String> q = new LinkedList();
        HashMap<String, Integer> set = new HashMap();
        q.add(start);
        set.put(start, 0);
        while (!q.isEmpty() && !set.containsKey(finish)) {
            String v = q.poll();
            char[] tmp = v.toCharArray();
            for (int i = 0; i < 4; i++) {
                int index = v.charAt(i) - 'a';
                for (int j = -1; j < 2; j += 2) {
                    int nxt = (index + j + 26) % 26;
                    char c = (char)(nxt + 'a');
                    tmp[i] = c;
                    String cur = new String(tmp);
                    if(!set.containsKey(cur) && canUse(cur, data)){
                        set.put(cur, set.get(v) + 1);
                        q.add(cur);
                    }
                    tmp[i] = v.charAt(i);
                }
            }
        }
        result = set.containsKey(finish) ? set.get(finish) : -1;
        return result;
    }

    boolean canUse(String v, String[][] data) {
        for (int i = 0; i < data.length; i++) {
            int c = 0;
            for (int j = 0; j < 4; j++) {
                if (data[i][j].contains("" + v.charAt(j))) {
                    c++;
                }else{
                    break;
                }
            }
            if (c == 4) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        String[]a = {"aaaa","zzzz"};
        String []f = {"a a a z", "a a z a", "a z a a", "z a a a", "a z z z", "z a z z", "z z a z", "z z z a"};
        System.out.println(new SmartWordToy().minPresses(a[0], a[1], f));
    }
}
