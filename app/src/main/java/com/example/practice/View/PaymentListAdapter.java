package com.example.practice.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.Model.Payment;
import com.example.practice.Presenter.PaymentListPresenter;
import com.example.practice.Presenter.PaymentPresenter;
import com.example.practice.R;

import java.util.List;

    public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.PaymentListViewHolder> {

        public List<Payment> paymentList;
        private PaymentPresenter presenter;

        public static class PaymentListViewHolder extends RecyclerView.ViewHolder {
            private View view;

            public PaymentListViewHolder(View v) {
                super(v);
                view = v;
            }
        }

        public PaymentListAdapter(List<Payment> paymentList) {
            this.paymentList = paymentList;
        }

        @Override
        public PaymentListViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_payment_list, parent, false);
            PaymentListViewHolder holder = new PaymentListViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(PaymentListViewHolder holder, int position) {
            final Payment payment = paymentList.get(position);
            final PaymentListPresenter presenter=new PaymentListPresenter(holder, position);
            ((TextView)holder.view.findViewById(R.id.editTextName)).setText(payment.getName());
            ((TextView)holder.view.findViewById(R.id.editTextPrice)).setText(payment.getPay()+"");
            ((TextView)holder.view.findViewById(R.id.editTextCount)).setText("1");
            (holder.view.findViewById(R.id.editTextCount)).setEnabled(false);
            holder.view.findViewById(R.id.buttonPlus).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    presenter.plus();
                }
            });
            holder.view.findViewById(R.id.buttonMinus).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    presenter.minus();
                }
            });
        }

        @Override
        public int getItemCount() {
            return paymentList.size();
        }
    }
