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
import com.example.practice.IFiscalCore;
import com.example.practice.Model.IToast;
import com.example.practice.Model.Payment;
import com.example.practice.Presenter.PaymentPresenter;
import com.example.practice.R;

import java.util.List;

public class PaymentActivity extends AppCompatActivity implements IToast {

    private static final int RECTYPE_UNFISCAL = 9;
    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextCount;
    private Button buttonPay;
    private Button buttonAdd;
    private Button buttonOpen;
    private static final int PAY_TYPE_CASH = 0;
    private RecyclerView recyclerView;
    private PaymentPresenter presenter;
    private static final String TEST_ITEM_ARTICLE = "";
    private static final String LANG_DEFAULT = "Ru-ru";
    private static final String ENVIRONMENT = "";
    private final String TAG = "PaymentActivity";


    private FiscalCoreServiceConnection _connection;
    ExceptionCallback _callback = new ExceptionCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _connection = new FiscalCoreServiceConnection(this);
        _connection.Initialize(LANG_DEFAULT, ENVIRONMENT, this);

        setContentView(R.layout.activity_payment);

        presenter = new PaymentPresenter(this);

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
                    try
                    {
                        IFiscalCore core = getCore();
                        core.PrintRecTotal(_callback);
                        _callback.Complete();
                        String total = core.GetRecTotal(_callback);
                        _callback.Complete();
                        core.PrintRecItemPay(PAY_TYPE_CASH, total, getString(R.string.cash), _callback);
                        _callback.Complete();
                        core.CloseRec(_callback);
                        _callback.Complete();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
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
                try
                {
                    IFiscalCore core = getCore();
                    core.PrintRecItem(editTextPrice.getText().toString(),editTextCount.getText().toString() , editTextName.getText().toString(),TEST_ITEM_ARTICLE, _callback);
                    _callback.Complete();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonOpen = (Button)findViewById(R.id.buttonOpen);
        buttonOpen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try
                {
                    IFiscalCore core = getCore();
                    core.OpenRec(RECTYPE_UNFISCAL, _callback);
                    _callback.Complete();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void Toast(int messageId, boolean isLong)
    {
        try
        {
            String message = getString(messageId);
            Toast.makeText(this, message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
        }
        catch (Resources.NotFoundException e)
        {
            String report = e.getMessage() + "\n";
            report += e.getStackTrace();
            Log.e(TAG, report);
        }
    }

    private IFiscalCore getCore() throws Exception
    {
        IFiscalCore core = _connection.GetCore();
        if (null == core)
            throw new Exception(getString(R.string.service_not_connected));
        return core;
    }
}

