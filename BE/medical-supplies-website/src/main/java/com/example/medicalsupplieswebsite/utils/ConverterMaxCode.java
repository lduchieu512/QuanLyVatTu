package com.example.medicalsupplieswebsite.utils;

public class ConverterMaxCode {
    public  static  String converterCodeToAdd(String code){
        //VT-100
        int number = Integer.parseInt(code.split("VT-")[1]);
        if (number == 0 ){
            return String.format("VT-%.3s",1);
        } else
            return String.format("VT-%.3s",++number);
    }

    public static String generateNextId(String currentId) {
        String prefix = currentId.split("-")[0];
        int number = Integer.parseInt(currentId.split("-")[1]);
        int nextNumber = number + 1;
        return String.format("%s-%03d", prefix, nextNumber);
    }
}
