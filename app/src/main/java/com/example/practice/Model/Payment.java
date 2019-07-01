package com.example.practice.Model;

import java.text.DecimalFormat;

public class Payment {

    private int id;

    private String Name;

    private String Pay;

    private String Count;

    private String Article;

    public Payment(){}

    public Payment(int id, String Name, String Pay, String Count, String Article) {
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

    public String getPay(){

        return Pay;
    }

    public void setPay(String Pay){

        this.Pay = Pay;
    }

    public String getCount(){

        return Count;
    }

    public void setCount(String Count){

        this.Count = Count;
    }

    public String getArticle() {
        return Article;
    }

    public void setArticle(String Article) {
        this.Article = Article;
    }
}
