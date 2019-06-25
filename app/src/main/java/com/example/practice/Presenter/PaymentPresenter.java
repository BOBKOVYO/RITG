package com.example.practice.Presenter;

import android.content.ContentValues;
import android.text.TextUtils;

import com.example.practice.Database.PaymentTable;
import com.example.practice.Model.Payment;
import com.example.practice.R;
import com.example.practice.View.PaymentActivity;


import java.util.List;

public class PaymentPresenter {

    private PaymentActivity view;

    private final DBModel model;

    public PaymentPresenter(DBModel model) {
        this.model = model;
    }


    public void viewIsReady() {
        loadPayments();
    }

    public void loadPayments() {
        model.loadPayments(new DBModel.LoadPaymentCallback() {
            @Override
            public void onLoad(List<Payment> payments) {
                view.showPayments(payments);
            }
        });
    }


    public void add() {
        Payment payment = view.getPayment();
        if (TextUtils.isEmpty(payment.getName()) || TextUtils.isEmpty(payment.getPay())) {
            view.showToast(R.string.empty_values);
            return;
        }

        ContentValues cv = new ContentValues(2);
        cv.put(PaymentTable.COLUMN.NAME, payment.getName());
        cv.put(PaymentTable.COLUMN.PAY, payment.getPay());
        model.addPayment(cv, new DBModel.CompleteCallback() {
            @Override
            public void onComplete() {
                loadPayments();
            }
        });
    }

}
