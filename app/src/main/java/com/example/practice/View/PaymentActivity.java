package com.example.practice.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practice.Database.ExceptionCallback;
import com.example.practice.Database.FiscalCoreServiceConnection;
import com.multisoft.drivers.fiscalcore.IFiscalCore;
import com.example.practice.Model.IToast;
import com.example.practice.Model.Payment;
import com.example.practice.Presenter.PaymentPresenter;
import com.example.practice.R;

import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextCount;
    private Button buttonPay;
    private Button buttonAdd;
    private Button buttonOpen;
    private RecyclerView recyclerView;
    private PaymentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);

        presenter = new PaymentPresenter(this);
        presenter.initialize();

        editTextCount = (EditText) findViewById(R.id.editTextCount);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PaymentListAdapter(presenter.getPayments()));



        boolean isPayBack = getIntent().getBooleanExtra("IS_PAY_BACK", false);
        buttonPay = (Button) findViewById(R.id.buttonPay);
        if (!isPayBack) {
            buttonPay.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                     presenter.pay();
                }
            });
        } else {
            buttonPay.setText("Вернуть");
            buttonPay.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    presenter.payBack();
                }
            });
        }
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            presenter.add();
            }
        });
        buttonOpen = (Button)findViewById(R.id.buttonOpen);
        buttonOpen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                 presenter.open();
            }
        });
    }
}

