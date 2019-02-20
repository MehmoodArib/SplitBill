package com.example.mehmood.splitbill.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Expense;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {
    private List<Expense> mExpense;
    private onItemClickListener mListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExpenseAdapter(List<Expense> expenses) {
        mExpense = expenses;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExpenseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense, parent, false);

        MyViewHolder vh = new MyViewHolder(view, mListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Expense currentExpense = mExpense.get(position);
        holder.mExpenseName.setText(currentExpense.getExpenseName());
        holder.mExpenseAmount.setText(currentExpense.getExpenseAmount());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mExpense.size();
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mExpenseName;
        public TextView mExpenseAmount;

        public MyViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);

            mExpenseName = itemView.findViewById(R.id.expense_name);
            mExpenseAmount = itemView.findViewById(R.id.expense_amount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }
}
