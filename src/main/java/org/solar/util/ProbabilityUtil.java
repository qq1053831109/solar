package org.solar.util;

import java.util.Random;

public class ProbabilityUtil {

    public static boolean inProbability(Integer probability) {
        return inProbability(probability,100);
    }
    public static boolean inProbability(Integer probability,int bound) {
        if (probability==null){
            return false;
        }
        int randomInt=random.nextInt(bound);
        if (randomInt<probability){
            return true;
        }
        return false;
    }
    private final static  Random random=new Random();
}
