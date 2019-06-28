package com.example.practice.Model;

import java.text.DecimalFormat;

public class Payment {

    private int id;

    private String Name;

    private double Pay;

    private int Count;

    private String Article;

    public Payment(int id, String Name, double Pay, int Count, String Article) {
        this.id = id;
        this.Name = Name;
        this.Pay = Pay;
        this.Count = Count;
        this.Article = Article;
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

    public double getPay(){

        return Pay;
    }

    public void setPay(double Pay){

        this.Pay = Pay;
    }

    public int getCount(){

        return Count;
    }

    public void setCount(int Count){

        this.Count = Count;
    }

    public String getArticle() {
        return Article;
    }

    public void setArticle(String Article) {
        this.Article = Article;
    }
}
