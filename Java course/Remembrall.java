package com.jetbrains;
//zad3
public class Remembrall {
    public static boolean isPhoneNumberForgettable(String phoneNumber){
        if(phoneNumber == null || phoneNumber.isEmpty()){
            return false;
        }
        char[] formatedNumber = phoneNumber.replaceAll(" ","")
                .replaceAll("-","")
                .toCharArray();
        boolean hasRepeatedNum = false;
        for(int i = 0; i<formatedNumber.length; i++){
            for(int j = i+1; j<formatedNumber.length;j++){
                if((formatedNumber[i] >= 'A' && formatedNumber[i] <= 'Z') ||           // there is letter in num
                        (formatedNumber[i] >= 'a' && formatedNumber[i] <= 'z')){
                    return true;
                }
                if(formatedNumber[i]==formatedNumber[j]){                            // there is repeated number in num
                    hasRepeatedNum = true;
                }
            }
        }
        return !hasRepeatedNum;
    }
}
