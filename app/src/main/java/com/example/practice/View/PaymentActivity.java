package com.example.practice.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practice.Database.DbHelper;
import com.example.practice.Presenter.DBModel;
import com.example.practice.Model.Payment;
import com.example.practice.Presenter.PaymentListAdapter;
import com.example.practice.Presenter.PaymentPresenter;
import com.example.practice.R;

import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private PaymentListAdapter paymentListAdapter;
    private Button buttonPay;
    private Button buttonAdd;
    private RecyclerView recyclerView;
    private EditText textName;
    private EditText textSum;
    private ProgressDialog progressDialog;
    private PaymentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();
    }
    public void init(){
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonPay=(Button)findViewById(R.id.buttonPay);
        buttonPay.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });


        textName = (EditText) findViewById(R.id.textName);
        textSum = (EditText) findViewById(R.id.textSum);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.add();
            }
        });



        paymentListAdapter = new PaymentListAdapter();

        RecyclerView paymentList = (RecyclerView) findViewById(R.id.recyclerView);
        paymentList.setAdapter(paymentListAdapter);


        DbHelper dbHelper = new DbHelper(this);
        DBModel paymentModel = new DBModel(dbHelper);
        presenter = new PaymentPresenter(paymentModel);
        presenter.viewIsReady();
    }

    public Payment getPayment() {
        Payment payment = new Payment();
        payment.setName(textName.getText().toString());
        payment.setPay(textSum.getText().toString());
        return payment;
    }

    public void showPayments(List<Payment> payments) {
        paymentListAdapter.setData(payments);
    }

    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }
}

