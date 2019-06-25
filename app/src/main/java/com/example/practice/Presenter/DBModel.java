package com.example.practice.Presenter;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.practice.Database.DbHelper;
import com.example.practice.Database.PaymentTable;
import com.example.practice.Model.Payment;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DBModel
{
    private final DbHelper dbHelper;

    public DBModel(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void loadPayments(LoadPaymentCallback callback) {
        LoadPaymentsTask loadPaymentsTask  = new LoadPaymentsTask(callback);
        loadPaymentsTask.execute();
    }

    public void addPayment(ContentValues contentValues, CompleteCallback callback) {
        AddPaymentTask addPaymentTask = new AddPaymentTask(callback);
        addPaymentTask.execute(contentValues);
    }


    interface LoadPaymentCallback {
        void onLoad(List<Payment> payments);
    }

    interface CompleteCallback {
        void onComplete();
    }

    class LoadPaymentsTask extends AsyncTask<Void, Void, List<Payment>> {

        private final LoadPaymentCallback callback;

        LoadPaymentsTask(LoadPaymentCallback callback) {
            this.callback = callback;
        }

        @Override
        protected List<Payment> doInBackground(Void... params) {
            List<Payment> payments = new LinkedList<>();
            Cursor cursor = dbHelper.getReadableDatabase().query(PaymentTable.TABLE, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                Payment payment = new Payment();
                payment.setId(cursor.getInt(cursor.getColumnIndex(PaymentTable.COLUMN.ID)));
                payment.setName(cursor.getString(cursor.getColumnIndex(PaymentTable.COLUMN.NAME)));
                payment.setPay(cursor.getString(cursor.getColumnIndex(PaymentTable.COLUMN.PAY)));
                payments.add(payment);
            }
            cursor.close();
            return payments;
        }

        @Override
        protected void onPostExecute(List<Payment> payments) {
            if (callback != null) {
                callback.onLoad(payments);
            }
        }
    }

    class AddPaymentTask extends AsyncTask<ContentValues, Void, Void> {

        private final CompleteCallback callback;

        AddPaymentTask(CompleteCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(ContentValues... params) {
            ContentValues cvPayment = params[0];
            dbHelper.getWritableDatabase().insert(PaymentTable.TABLE, null, cvPayment);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (callback != null) {
                callback.onComplete();
            }
        }
    }

    class ClearPaymentsTask extends AsyncTask<Void, Void, Void> {

        private final CompleteCallback callback;

        ClearPaymentsTask(CompleteCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            dbHelper.getWritableDatabase().delete(PaymentTable.TABLE, null, null);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (callback != null) {
                callback.onComplete();
            }
        }
    }
}
