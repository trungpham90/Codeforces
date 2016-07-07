
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class BeautifulBanner {

    public int maximumBeauty(String letters, int[] beauty) {
        String need = "TechOlympics";
        int[] max = new int[need.length()];
        Arrays.fill(max, -1);
        for (int i = 0; i < letters.length(); i++) {
            int index = need.indexOf(letters.charAt(i));
            if (index >= 0) {
                if (letters.charAt(i) != 'c') {
                    max[index] = Integer.max(max[index], beauty[i]);
                } else if (max[index] == -1) {
                    max[index] = beauty[i];
                } else {
                    int other = need.lastIndexOf('c');
                    if (max[other] == -1) {
                        max[other] = beauty[i];
                    } else if (max[index] >= max[other]) {
                        if (beauty[i] >= max[index]) {
                            max[other] = max[index];
                            max[index] = beauty[i];
                        } else if (beauty[i] > max[other]) {
                            max[other] = beauty[i];
                        }
                    } else if (beauty[i] >= max[other]) {
                        max[index] = max[other];
                        max[other] = beauty[i];
                    } else if (beauty[i] > max[index]) {
                        max[index] = beauty[i];
                    }
                }
            }
        }
        int total = 0;
        for (int i : max) {
            if (i == -1) {
                return -1;
            }
            total += i;
        }
        return total;
    }
}
