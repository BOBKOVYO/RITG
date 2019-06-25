package com.example.practice.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.practice.R;

public class MainActivity extends AppCompatActivity {

    private Button buttonAddPay;
    private Button buttonCloseCasting;
    private Button buttonBackPay;
    private Button buttonOpenCasting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAddPay = (Button) findViewById(R.id.buttonAddPay);
        buttonAddPay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
               startActivity(intent);
           }
        });
        buttonBackPay = (Button) findViewById(R.id.buttonBackPay);
        buttonBackPay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
            startActivity(intent);
        }
        });

        buttonCloseCasting = (Button) findViewById(R.id.buttonCloseCasting);
        buttonCloseCasting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {

            }
        });
        buttonOpenCasting = (Button) findViewById(R.id.buttonOpenCasting);
        buttonOpenCasting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {

            }
        });
    }
}
