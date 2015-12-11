
import java.util.LinkedList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class ShadowSculpture {

    public String possible(String[] XY, String[] YZ, String[] ZX) {
        int a = 0, b = 0, c = 0;
        for (String x : XY) {
            for (int i = 0; i < x.length(); i++) {
                if (x.charAt(i) == 'Y') {
                    a++;
                }
            }
        }
        for (String x : YZ) {
            for (int i = 0; i < x.length(); i++) {
                if (x.charAt(i) == 'Y') {
                    b++;
                }
            }
        }
        for (String x : ZX) {
            for (int i = 0; i < x.length(); i++) {
                if (x.charAt(i) == 'Y') {
                    c++;
                }
            }
        }
        if (a == 0 && b == 0 && c == 0) {
            return "Possible";
        }
        int n = YZ.length;
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                if (XY[x].charAt(y) == 'Y') {
                    for (int z = 0; z < n; z++) {
                        if (YZ[y].charAt(z) == 'Y' && ZX[z].charAt(x) == 'Y') {
                            LinkedList<Point> q = new LinkedList();
                            q.add(new Point(x, y, z));


                            boolean[][][] check = new boolean[n][n][n];
                            check[x][y][z] = true;
                            while (!q.isEmpty()) {
                                Point p = q.poll();
                                for (int i = -1; i < 2; i++) {
                                    for (int j = -1; j < 2; j++) {
                                        for (int k = -1; k < 2; k++) {
                                            int v = Math.abs(i) + Math.abs(j) + Math.abs(k);
                                            if (v != 1) {
                                                continue;
                                            }
                                            int d = p.x + i;
                                            int e = p.y + j;
                                            int f = p.z + k;
                                            if (d >= 0 && e >= 0 && f >= 0 && d < n && e < n && f < n && !check[d][e][f] && XY[d].charAt(e) == 'Y' && YZ[e].charAt(f) == 'Y' && ZX[f].charAt(d) == 'Y') {

                                                check[d][e][f] = true;
                                                q.add(new Point(d, e, f));
                                            }
                                        }
                                    }
                                }
                            }
                            boolean ok = true;
                            for (int i = 0; i < n && ok; i++) {
                                for (int j = 0; j < n && ok; j++) {

                                    boolean v = false;
                                    for (int k = 0; k < n; k++) {
                                        if (check[i][j][k]) {
                                            v = true;
                                            break;
                                        }
                                    }
                                    if ((XY[i].charAt(j) == 'Y') != v) {
                                        ok = false;
                                    }
                                }
                            }
                            for (int j = 0; j < n && ok; j++) {
                                for (int k = 0; k < n && ok; k++) {

                                    boolean v = false;
                                    for (int i = 0; i < n; i++) {
                                        if (check[i][j][k]) {
                                            v = true;
                                            break;
                                        }
                                    }
                                    if ((YZ[j].charAt(k) == 'Y') != v) {
                                        ok = false;
                                    }
                                }
                            }

                            for (int k = 0; k < n && ok; k++) {
                                for (int i = 0; i < n && ok; i++) {

                                    boolean v = false;
                                    for (int j = 0; j < n; j++) {
                                        if (check[i][j][k]) {
                                            v = true;
                                            break;
                                        }
                                    }
                                    if ((ZX[k].charAt(i) == 'Y') != v) {
                                        ok = false;
                                        break;
                                    }
                                }
                            }
//                            for(int i = 0; i < n; i++){
//                                for(int j = 0; j < n;j++){
//                                    for(int k = 0; k < n; k++){
//                                        if(check[i][j][k]){
//                                            System.out.println(i + " " + j + " " + k);
//                                        }
//                                    }
//                                }
//                            }
                            if (ok) {
                                return "Possible";
                            }

                        }
                    }
                    break;
                }
            }
        }
        return "Impossible";
    }

    class Point {

        int x, y, z;

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static void main(String[] args) {

        String[][] data = {{"NNN", "NNN", "NNN"}, {"NNY", "YNN", "YYY"}, {"NNN", "NNY", "NYN"}};
        System.out.println(new ShadowSculpture().possible(data[0], data[1], data[2]));
    }
}
