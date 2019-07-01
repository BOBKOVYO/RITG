package com.example.practice.Presenter;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
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
    private DBHelper dbHelper;

    private static final String LANG_DEFAULT = "Ru-ru";
    private static final String ENVIRONMENT = "";
    private static final String TAG = "PaymentActivity";
    private static final int RECTYPE = 1;
    private static final int PAY_TYPE_CASH = 0;

    private FiscalCoreServiceConnection _connection;
    ExceptionCallback _callback = new ExceptionCallback();

    public PaymentPresenter(PaymentActivity view) {
        this.view = view;
        dbHelper = new DBHelper(view);
    }

    public void initialize() {
        _connection = new FiscalCoreServiceConnection(view);
        _connection.Initialize(LANG_DEFAULT, ENVIRONMENT, this);
    }

    public void add() {
        try {
            ContentValues cv = new ContentValues(4);
            cv.put(PaymentTable.COLUMN.NAME, "1");
            cv.put(PaymentTable.COLUMN.PAY, "100");
            cv.put(PaymentTable.COLUMN.COUNT, "1");
            cv.put(PaymentTable.COLUMN.ARTICLE, "");
            dbHelper.getWritableDatabase().insert(PaymentTable.TABLE, null, cv);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        Cursor c = dbHelper.getReadableDatabase().query(PaymentTable.TABLE, null, null, null, null, null, null);
        List<Payment> paymentList = new ArrayList<>();
        int idColIndex = c.getColumnIndex(PaymentTable.COLUMN.ID);
        int nameColIndex = c.getColumnIndex(PaymentTable.COLUMN.NAME);
        int payColIndex = c.getColumnIndex(PaymentTable.COLUMN.PAY);
        int countColIndex = c.getColumnIndex(PaymentTable.COLUMN.COUNT);
        int articleColIndex = c.getColumnIndex(PaymentTable.COLUMN.ARTICLE);
        while (c.moveToNext()) {
            paymentList.add(new Payment(c.getInt(idColIndex), c.getString(nameColIndex), c.getString(payColIndex),
                    c.getString(countColIndex), c.getString(articleColIndex)));
        }
        c.close();
        ((RecyclerView) view.findViewById(R.id.recyclerView)).setAdapter(new PaymentListAdapter(paymentList));
    }

    public void pay() {
        try {
            IFiscalCore core = getCore();
            core.SetTaxationUsing(1,_callback);
            _callback.Complete();
            core.OpenRec(RECTYPE, _callback);
            _callback.Complete();
        } catch (Exception e) {
            Toast.makeText(view.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Cursor c = dbHelper.getReadableDatabase().query(PaymentTable.TABLE, null, null, null, null, null, null);
        List<Payment> paymentList = new ArrayList<>();
        int idColIndex = c.getColumnIndex(PaymentTable.COLUMN.ID);
        int nameColIndex = c.getColumnIndex(PaymentTable.COLUMN.NAME);
        int payColIndex = c.getColumnIndex(PaymentTable.COLUMN.PAY);
        int countColIndex = c.getColumnIndex(PaymentTable.COLUMN.COUNT);
        int articleColIndex = c.getColumnIndex(PaymentTable.COLUMN.ARTICLE);
        while (c.moveToNext()) {
            paymentList.add(new Payment(c.getInt(idColIndex), c.getString(nameColIndex), c.getString(payColIndex),
                    c.getString(countColIndex), c.getString(articleColIndex)));
        }
        c.close();
        try {
            IFiscalCore core = getCore();
            for (int i = 0; i < paymentList.size(); i++) {
                core.PrintRecItem(paymentList.get(i).getCount(), paymentList.get(i).getPay(),
                        paymentList.get(i).getName(), paymentList.get(i).getArticle(), _callback);
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
        dbHelper.getWritableDatabase().delete(PaymentTable.TABLE,null,null);
        view.finish();
    }

    public List<Payment> getPaymentList() {
        Cursor c = dbHelper.getReadableDatabase().query(PaymentTable.TABLE, null, null, null, null, null, null);
        List<Payment> paymentList = new ArrayList<>();
        int idColIndex = c.getColumnIndex(PaymentTable.COLUMN.ID);
        int nameColIndex = c.getColumnIndex(PaymentTable.COLUMN.NAME);
        int payColIndex = c.getColumnIndex(PaymentTable.COLUMN.PAY);
        int countColIndex = c.getColumnIndex(PaymentTable.COLUMN.COUNT);
        int articleColIndex = c.getColumnIndex(PaymentTable.COLUMN.ARTICLE);
        while (c.moveToNext()) {
            paymentList.add(new Payment(c.getInt(idColIndex), c.getString(nameColIndex), c.getString(payColIndex),
                    c.getString(countColIndex), c.getString(articleColIndex)));
        }
        c.close();
        return paymentList;
    }

    public void cancel(){
        dbHelper.getWritableDatabase().delete(PaymentTable.TABLE,null,null);
        try
        {
            IFiscalCore core = getCore();
            core.RecVoid(_callback);
            _callback.Complete();
        }
        catch (Exception e)
        {
            Toast.makeText(view.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        view.finish();
    }

    public void payBack() {

    }

    public void destroy() {
        dbHelper.close();
    }

    @Override
    public void Toast(int messageId, boolean isLong) {
        try {
            String message = view.getString(messageId);
            Toast.makeText(view, message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
        } catch (Resources.NotFoundException e) {
            String report = e.getMessage() + "\n";
            report += e.getStackTrace();
            Log.e(TAG, report);
        }
    }

    private IFiscalCore getCore() throws Exception {
        IFiscalCore core = _connection.GetCore();
        if (core == null)
            throw new Exception(view.getString(R.string.service_not_connected));
        return core;
    }
}
