/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class EllysTimeMachine {

    public String getTime(String time) {
        String[] tmp = time.split(":");
        int h = Integer.parseInt(tmp[0]);
        int m = Integer.parseInt(tmp[1]);
        m += (5 - (m % 5)) % 5;
        boolean more = false;
        if(m == 60){
            m = 0;
            more = true;
        }
        
        
        String v = getMinute(m) + ":" + getHour(h, more);
        return v;
    }
    
    public String getHour(int h, boolean more){
        if(more){
            h++;
        }
        if(h == 13){
            h = 1;
        }
        h*= 5;
        h %= 60;
        return  (h < 10 ? "0" : "") + h;
    }
    
    public String getMinute(int m){
        if(m/5 == 0){
            return "12";
        }else{
            return (m / 5 < 10 ? "0" : "") + m/5;
        }
    }
    
    public static void main(String[] args) {
        String v = "01:00";
        System.out.println(new EllysTimeMachine().getTime(v));
    }
    
}
