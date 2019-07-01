package com.example.practice.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.practice.Presenter.MainPresenter;
import com.example.practice.R;

public class MainActivity extends AppCompatActivity {



    private Button buttonAddPay;
    private Button buttonCloseCasting;
    private Button buttonBackPay;
    private Button buttonOpenCasting;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter=new MainPresenter(this);
        presenter.initialize();

        buttonAddPay = (Button) findViewById(R.id.buttonAddPay);
        buttonAddPay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               presenter.openView(PaymentActivity.class, false);
           }
        });
        buttonBackPay = (Button) findViewById(R.id.buttonBackPay);
        buttonBackPay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.openView(PaymentActivity.class, true);
        }
        });

        buttonCloseCasting = (Button) findViewById(R.id.buttonCloseCasting);
        buttonCloseCasting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                presenter.closeCasting();
            }
        });
        buttonOpenCasting = (Button) findViewById(R.id.buttonOpenCasting);
        buttonOpenCasting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                presenter.openCasting();
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume();
    }
}
