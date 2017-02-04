
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * #
 * @author pttrung
 */
public class AlphabetOrderDiv1 {

    public String isOrdered(String[] words) {
        HashSet<Integer>[] map = new HashSet[26];
        HashSet<Integer>[] revert = new HashSet[26];
        for (int i = 0; i < 26; i++) {
            map[i] = new HashSet();
            revert[i] = new HashSet();
        }
        for (String v : words) {
            for (int i = 1; i < v.length(); i++) {
                int a = v.charAt(i - 1) - 'a';
                int b = v.charAt(i) - 'a';
                if (a != b) {
                    map[a].add(b);
                    revert[b].add(a);
                }
            }
        }
        int c = 0;
        boolean[] check = new boolean[26];
        boolean found = true;
        while (found) {
            found = false;
            for (int i = 0; i < 26; i++) {
                if (!check[i] && revert[i].isEmpty()) {
                    found = true;
                    check[i] = true;
                    c++;
                    for (int j : map[i]) {
                        revert[j].remove(i);
                    }
                }
            }
        }
      //  System.out.println(Arrays.toString(map));
        if (c == 26) {
            return "Possible";
        }
        return "Impossible";
    }

    public static void main(String[] args) {
        String[] w = {"nnnppppooommmmghhssrwwwwwddddvuuuujkkkkttffffiibbqqqaaeeelllxxcccyyyyy", "nnnnppooghsssrrwduzzzzkktfiibqqaelcyy", "nnpppommgghhssrrvvvvuzjkkfqaaaaaexccccy", "nppppooogghhssssrrwdduuuuuuzzzjjjkktttffiiibbqqaeeeeelxccyyyy", "nnppoooommghsssrwdddzzjjffiiiibbqaaaaxcccyyyy", "nppooommgghhswwddduzjjkttffiibbqqqaaeeellxxxcyy", "nnnnommgrrwwddvuuuuzzzjjjjjtttttffffiibaeelllxc", "pmrtfibaey", "nnnppooooommgghhhrrrrwwwwdvvvvuuuuzzzzzjjjjjkttttffffibqqqaaeeelxxxxccccyyy", "nnpommhsssrrvvvzzzjjjjjktfffffbqaaaaeelllxxxccccy", "ppoomssrwdddduuuzzjtibqqqellxcy", "nowuzzzzjjfbelx", "pmmhhsrwdzkfbaexccyy"};
        System.out.println(new AlphabetOrderDiv1().isOrdered(w));
    }
}
