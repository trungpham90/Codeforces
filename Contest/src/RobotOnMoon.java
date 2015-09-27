/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class RobotOnMoon {

    public int longestSafeCommand(String[] board) {
        int x = -1;
        int y = -1;
        int n = board.length;
        int m = board[0].length();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i].charAt(j) == 'S') {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        int result = 0;
        int start = x - 1;
        int a = 0, b = 0, c = 0, d = 0;
        while (start >= 0) {
            if (board[start].charAt(y) == '.') {
                a = x - start;
            } else if (board[start].charAt(y) == '#') {
                return -1;
            }
            start--;
        }
        start = x + 1;
        while (start < n) {
            if (board[start].charAt(y) == '.') {
                b = start - x;
            } else if (board[start].charAt(y) == '#') {
                return -1;
            }
            start++;
        }
        start = y - 1;
        while (start >= 0) {
            if (board[x].charAt(start) == '.') {
                c = y - start;
            } else if (board[x].charAt(start) == '#') {
                return -1;
            }
            start--;
        }
        start = y + 1;
        while (start < m) {
            if (board[x].charAt(start) == '.') {
                d = start - y;
            } else if (board[x].charAt(start) == '#') {
                return -1;
            }
            start++;
        }
        result = a + b + c + d;
        return result;

    }
}
