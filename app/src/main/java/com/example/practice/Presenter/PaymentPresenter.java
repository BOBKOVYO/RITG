package com.example.practice.Presenter;

import android.content.ContentValues;
import android.text.TextUtils;

import com.example.practice.Database.PaymentTable;
import com.example.practice.Model.Payment;
import com.example.practice.R;
import com.example.practice.View.PaymentActivity;


import java.util.ArrayList;
import java.util.List;

public class PaymentPresenter {

    private PaymentActivity view;
    private List<Payment> payments;
    //private DbHelper dbHelper;

    public PaymentPresenter(PaymentActivity view) {
        this.view = view;
        payments=new ArrayList<>();
        payments.add(new Payment(1,"1","100"));
        /*payments.add(new Payment(2,"2","1000"));
        payments.add(new Payment(3,"3","10000"));*/
    }

    public void add() {


    }

    public void pay() {

    }

    public void payBack() {

    }

    public List<Payment> getPayments() {
        return payments;
    }
}
