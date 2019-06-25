package com.example.practice.Presenter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.Model.Payment;
import com.example.practice.R;

import java.util.ArrayList;
import java.util.List;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.PaymentHolder> {

    List<Payment> payments = new ArrayList<>();

    @Override
    public PaymentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_list, parent, false);
        return new PaymentHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentHolder holder, int position) {
        holder.bind(payments.get(position));
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public void setData(List<Payment> payments) {
        payments.clear();
        payments.addAll(payments);
        notifyDataSetChanged();
        Log.d("qweee", "size  = " + getItemCount());
    }

    static class PaymentHolder extends RecyclerView.ViewHolder {

        TextView text;

        public PaymentHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }

        void bind(Payment payment) {
            text.setText(String.format("id: %s, name: %s, pay: %s", payment.getId(), payment.getName(), payment.getPay()));
        }
    }

}
