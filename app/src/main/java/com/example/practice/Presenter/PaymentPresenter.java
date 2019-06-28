package com.example.practice.Presenter;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.Database.DBHelper;
import com.example.practice.Database.ExceptionCallback;
import com.example.practice.Database.FiscalCoreServiceConnection;
import com.example.practice.Database.PaymentTable;
import com.example.practice.Model.IToast;
import com.example.practice.Model.Payment;
import com.example.practice.R;
import com.example.practice.View.PaymentActivity;
import com.example.practice.View.PaymentListAdapter;
import com.multisoft.drivers.fiscalcore.IFiscalCore;


import java.util.ArrayList;
import java.util.List;

public class PaymentPresenter implements IToast {

    private PaymentActivity view;
    private List<Payment> payments;
    private DBHelper dbHelper;
    private static final String LANG_DEFAULT = "Ru-ru";
    private static final String ENVIRONMENT = "";
    private static final String TAG = "PaymentActivity";
    private static final int RECTYPE_UNFISCAL = 9;
    private static final int PAY_TYPE_CASH = 0;

    private FiscalCoreServiceConnection _connection;
    ExceptionCallback _callback = new ExceptionCallback();
    SQLiteDatabase database = dbHelper.getWritableDatabase();
    ContentValues contentValues = new ContentValues();

    public PaymentPresenter(PaymentActivity view) {
        this.view = view;
        payments=new ArrayList<>();
        dbHelper = new DBHelper(view);
    }


    public void initialize() {
        _connection = new FiscalCoreServiceConnection(view);
        _connection.Initialize(LANG_DEFAULT, ENVIRONMENT, this);
        try {
            IFiscalCore core = getCore();
            core.OpenRec(RECTYPE_UNFISCAL, _callback);
            _callback.Complete();
        } catch (Exception e) {
            Toast.makeText(view.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void add() {

    }

    public void pay() {

        try {
            IFiscalCore core = getCore();
            for (int i = 0; i < payments.size(); i++) {
                core.PrintRecItem(payments.get(i).getCount() + "", payments.get(i).getPay() + "",
                        payments.get(i).getName(), payments.get(i).getName(), _callback);
                _callback.Complete();
            }
            core.PrintRecTotal(_callback);
            _callback.Complete();
            String total = core.GetRecTotal(_callback);
            _callback.Complete();
            core.PrintRecItemPay(PAY_TYPE_CASH, total, view.getString(R.string.cash), _callback);
            _callback.Complete();
            core.CloseRec(_callback);
            _callback.Complete();
        } catch (Exception e) {
            Toast.makeText(view.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void payBack() {

    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void open(){
        try
        {
            IFiscalCore core = getCore();
            core.OpenRec(RECTYPE_UNFISCAL, _callback);
            _callback.Complete();
        }
        catch (Exception e)
        {
            Toast.makeText(view.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void Toast(int messageId, boolean isLong)
    {
        try
        {
            String message = view.getString(messageId);
            Toast.makeText(view, message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
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
            throw new Exception(view.getString(R.string.service_not_connected));
        return core;
    }
}
