package com.example.mehmood.splitbill.utils.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Expense;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
/**Adapter for creating the ExpenseList used In ExpenseFragment*/

public class ExpenseAdapter extends ListAdapter<Expense, ExpenseAdapter.ExpenseHolder> {
    private static final DiffUtil.ItemCallback<Expense> Diff_CALLBACK = new DiffUtil.ItemCallback<Expense>() {
        @Override
        public boolean areItemsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
            return oldItem.getExpenseId() == newItem.getExpenseId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
            return oldItem.getExpenseAmount().equals(newItem.getExpenseAmount())
                    && oldItem.getExpenseName().equals(newItem.getExpenseName());
        }
    };
    private OnItemClickListner listener;

    public ExpenseAdapter() {
        super(Diff_CALLBACK);
    }

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense, parent, false);
        return new ExpenseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
        Expense currentExpense = getItem(position);
        holder.textViewTitle.setText(currentExpense.getExpenseName());
        holder.textViewAmount.setText(String.valueOf(currentExpense.getExpenseAmount()));

    }


    public Expense getExpenseAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListner(OnItemClickListner listner) {
        this.listener = listner;
    }

    public interface OnItemClickListner {
        void onItemClick(Expense expense);
    }

    class ExpenseHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewAmount;

        private ExpenseHolder(@NonNull View itemView) {
            super(itemView);
            textViewAmount = itemView.findViewById(R.id.expense_amount);
            textViewTitle = itemView.findViewById(R.id.expense_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }
}
