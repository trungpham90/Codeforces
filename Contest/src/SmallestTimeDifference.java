/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
public class SmallestTimeDifference {

    static final int WHOLE_DAY = 24 * 60;

    static int getMinTimeDifference(String[] times) {
        int result = Integer.MAX_VALUE;
        int[]timeInMin = new int[times.length];
        for(int i = 0; i < times.length; i++){
            timeInMin[i] = getTimeInMinutes(times[i]);
        }
        for (int i = 0; i < times.length; i++) {
            int timeA = timeInMin[i];
            for (int j = i + 1; j < times.length; j++) {
                int timeB = timeInMin[j];
                int minDistance = Math.min(getDistance(timeA, timeB), getDistance(timeB, timeA));
                result = Math.min(result, minDistance);
            }
        }
        return result;
    }

    static int getDistance(int timeA, int timeB) {
        if (timeA > timeB) {
            timeB += WHOLE_DAY;
        }
        return timeB - timeA;
    }

    static int getTimeInMinutes(String time) {
        String[] tmp = time.split(":");
        int hh = Integer.parseInt(tmp[0]);
        int mm = Integer.parseInt(tmp[1]);
        return hh * 60 + mm;
    }

}
