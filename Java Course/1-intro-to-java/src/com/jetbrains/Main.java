package com.jetbrains;

public class Main {
    public static void main(String[] args) {
        //zad1
       // System.out.println(SocialDistanceMaximizer.maxDistance(new int[]{0,1,0, 1}));

        //zad2
        String[] str = SandwichExtractor.extractIngredients("asdbreadmayo-olives-ham-olives-tomato-mayoo-molivesbreadblabla");
        for(int i =0; i<str.length; i++){
            System.out.println(str[i]);
        }

        //zad3
        //System.out.println(Remembrall.isPhoneNumberForgettable(""));

    }
}
