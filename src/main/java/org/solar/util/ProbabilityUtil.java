package org.solar.util;

import java.util.Random;

public class ProbabilityUtil {

    public static boolean inProbability(int probability) {
        return inProbability(probability,10000);
    }
    public static boolean inProbability(int probability,int bound) {
        int randomInt=random.nextInt(bound);
        if (randomInt<probability){
            return true;
        }
        return false;
    }
    private final static  Random random=new Random();
}
