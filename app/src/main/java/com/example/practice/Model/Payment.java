package com.example.practice.Model;

import java.text.DecimalFormat;

public class Payment {

    private int id;

    private String Name;

    private String Pay;

    public Payment(int i, String s, String s1) {
    }

    public int getId(){

        return id;
    }

    public void setId(int id){

        this.id = id;
    }

    public String getName(){

        return Name;
    }

    public void setName(String Name){

        this.Name = Name;
    }

    public String getPay(){

        return Pay;
    }

    public void setPay(String Pay){

        this.Pay = Pay;
    }
}
