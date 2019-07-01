package com.example.practice.Presenter;

import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import com.example.practice.Database.DBHelper;
import com.example.practice.Database.PaymentTable;
import com.example.practice.Model.Payment;
import com.example.practice.R;
import com.example.practice.View.PaymentListAdapter;

public class PaymentListPresenter {

    private PaymentListAdapter.PaymentListViewHolder holder;
    private int position;
    private DBHelper dbHelper;

    public PaymentListPresenter(PaymentListAdapter.PaymentListViewHolder holder, int position) {
        this.holder = holder;
        this.position = position;
        dbHelper = new DBHelper(holder.itemView.getContext());
    }

    public void plus() {
        Cursor c = dbHelper.getReadableDatabase().query(PaymentTable.TABLE, null, null, null, null, null, null);
        Payment payment = new Payment();
        int idColIndex = c.getColumnIndex(PaymentTable.COLUMN.ID);
        int nameColIndex = c.getColumnIndex(PaymentTable.COLUMN.NAME);
        int priceColIndex = c.getColumnIndex(PaymentTable.COLUMN.PAY);
        int countColIndex = c.getColumnIndex(PaymentTable.COLUMN.COUNT);
        int articleColIndex = c.getColumnIndex(PaymentTable.COLUMN.ARTICLE);
        if (c.moveToPosition(position)) {
            payment = new Payment(c.getInt(idColIndex), c.getString(nameColIndex), c.getString(priceColIndex),
                    c.getString(countColIndex), c.getString(articleColIndex));
        }
        c.close();
        View v = holder.itemView;
        int count = Integer.parseInt(payment.getCount());
        if (count < 1000000) {
            payment.setCount((count + 1) + "");
            ((TextView) v.findViewById(R.id.editTextCount)).setText(payment.getCount());
            ContentValues cv = new ContentValues(4);
            cv.put(PaymentTable.COLUMN.NAME, payment.getName());
            cv.put(PaymentTable.COLUMN.PAY, payment.getPay());
            cv.put(PaymentTable.COLUMN.COUNT, payment.getCount());
            cv.put(PaymentTable.COLUMN.ARTICLE, payment.getArticle());
            dbHelper.getWritableDatabase().update(PaymentTable.TABLE, cv, PaymentTable.COLUMN.ID + "=" + payment.getId(), null);
        }
    }

    public void minus() {
        Cursor c = dbHelper.getReadableDatabase().query(PaymentTable.TABLE, null, null, null, null, null, null);
        Payment payment = new Payment();
        int idColIndex = c.getColumnIndex(PaymentTable.COLUMN.ID);
        int nameColIndex = c.getColumnIndex(PaymentTable.COLUMN.NAME);
        int payColIndex = c.getColumnIndex(PaymentTable.COLUMN.PAY);
        int countColIndex = c.getColumnIndex(PaymentTable.COLUMN.COUNT);
        int articleColIndex = c.getColumnIndex(PaymentTable.COLUMN.ARTICLE);
        if (c.moveToPosition(position)) {
            payment = new Payment(c.getInt(idColIndex), c.getString(nameColIndex), c.getString(payColIndex),
                    c.getString(countColIndex), c.getString(articleColIndex));
        }
        c.close();
        View v = holder.itemView;
        int count = Integer.parseInt(payment.getCount());
        if (count > 1) {
            payment.setCount((count - 1) + "");
            ((TextView) v.findViewById(R.id.editTextCount)).setText(payment.getCount());
            ContentValues cv = new ContentValues(4);
            cv.put(PaymentTable.COLUMN.NAME, payment.getName());
            cv.put(PaymentTable.COLUMN.PAY, payment.getPay());
            cv.put(PaymentTable.COLUMN.COUNT, payment.getCount());
            cv.put(PaymentTable.COLUMN.ARTICLE, payment.getArticle());
            dbHelper.getWritableDatabase().update(PaymentTable.TABLE, cv, PaymentTable.COLUMN.ID + "=" + payment.getId(), null);
        }
    }
}
