/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class PaintTheRoom {
    public String canPaintEvenly(int r, int c, int k){
        if(r % 2 == 0 || c  % 2 == 0 || k == 1){
            return "Paint";
        }
        return "Cannot paint";
        
    }
}
