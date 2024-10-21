package com.example.medicalsupplieswebsite.error;

public class NotFoundById extends Exception{
    /**
     * A0722I1 - ThanhDT
     * @param error
     */
    public NotFoundById(String error){
        super(error);
    }
}
