
public class SumFullSet {

    public String isSumFullSet(int[] e) {
        for (int i = 0; i < e.length; i++) {
            for (int j = i + 1; j < e.length; j++) {
                boolean found = false;
                for (int k = 0; k < e.length; k++) {
                    if (e[i] + e[j] == e[k]) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return "not closed";
                }
            }
        }
        return "closed";
    }
}
