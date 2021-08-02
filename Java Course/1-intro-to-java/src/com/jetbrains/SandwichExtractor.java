package com.jetbrains;

import java.util.Arrays;

//zad2
public class SandwichExtractor {
    public static String[] extractIngredients(String sandwich){
        int firstBreadIndex = sandwich.indexOf("bread");
        int secondBreadIndex =sandwich.indexOf("bread", firstBreadIndex+5);
        if(firstBreadIndex == -1 || secondBreadIndex == -1){
            return new String[]{};
        }
        String sandwichWithoutBread = sandwich.substring(firstBreadIndex+5,secondBreadIndex);
        int firstIndexOfOlives = sandwichWithoutBread.indexOf("olives-");
        if(sandwichWithoutBread.indexOf("olives-") == 0){
            sandwichWithoutBread = sandwichWithoutBread.substring(firstIndexOfOlives + 7);
        }
        if(sandwichWithoutBread.lastIndexOf("-olives") == sandwichWithoutBread.length()-7){
            sandwichWithoutBread = sandwichWithoutBread.substring(0,sandwichWithoutBread.length()-7);
        }
        String sandwichWithoutOlives = sandwichWithoutBread.replaceAll("-olives-", "-");
        String[] ingredients = sandwichWithoutOlives.split("-");
        Arrays.sort(ingredients);
        /*
        for(int i = 0; i<ingredients.length-1; i++){
            int indexOfMinEl = i;
            for(int j = i+1; j<ingredients.length;j++){
                if(ingredients[indexOfMinEl].charAt(0) >= ingredients[j].charAt(0)){
                    int lengthOfI = ingredients[indexOfMinEl].length();
                    int lengthOfJ = ingredients[j].length();
                    for(int k = 0; k<lengthOfI && k < lengthOfJ; k++){
                        if(ingredients[indexOfMinEl].charAt(k) > ingredients[j].charAt(k)){
                            indexOfMinEl = j;
                            break;
                        }
                        else if(ingredients[indexOfMinEl].charAt(k) < ingredients[j].charAt(k)) {
                            break;
                        }
                    }
                }
            }
            if( i != indexOfMinEl){
                String tmp = ingredients[i];
                ingredients[i] = ingredients[indexOfMinEl];
                ingredients[indexOfMinEl] = tmp;
            }
        }
         */
        return ingredients;
    }
}
