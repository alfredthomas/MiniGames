package com.aj.minigames;

/**
 * Created by AJ on 9/6/2017.
 */

public class Helper {

    public static boolean isPointInCircle(int x, int y, int circlex, int circley, int radius)
    {
        double d = Math.sqrt(Math.pow(x-circlex,2)+Math.pow(y-circley,2));
        return d<radius;
    }
}
