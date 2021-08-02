package com.jetbrains;
//zad1
public class SocialDistanceMaximizer {
    public static int maxDistance(int[] seats){
        int currDist = 0;
        int maxDist = 0;
        int index = 0;
        if(seats[index] == 0){
            while(seats[index] == 0){
                maxDist++;
                index++;
            }
        }
        for(int i = index; i<seats.length; i++){
            if(seats[i] == 0){
                currDist++;
            }
            if(seats[i] == 1){
                int distBeforeReset = (currDist+1)/2;
                if(maxDist < distBeforeReset){
                    maxDist = distBeforeReset;
                }
                currDist = 0;
            }
        }
        if(currDist > maxDist){
            maxDist = currDist;
        }
        return maxDist;
    }
}
