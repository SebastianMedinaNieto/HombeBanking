package com.mindhub.homebanking.Utils;



public final class Utility {


    public Utility() {
    };

    public static String getCardNumber(){
        String cardNumber = (int) ((Math.random() * (9999 - 1000)) + 1000)
                + "-" + (int) ((Math.random() * (9999 - 1000)) + 1000)
                + "-" + (int) ((Math.random() * (9999 - 1000)) + 1000)
                + "-" + (int) ((Math.random() * (9999 - 1000)) + 1000);
        return cardNumber;
    }

    public static int getRandomNumber(int min,int max){return (int) ((Math.random() * (max - min)) + min);}




}
