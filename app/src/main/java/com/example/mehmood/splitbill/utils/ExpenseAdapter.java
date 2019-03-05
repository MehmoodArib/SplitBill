package com.example.mehmood.splitbill.utils;

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

public class ExpenseAdapter extends ListAdapter<Expense, ExpenseAdapter.ExpenseHolder> {
    public static final DiffUtil.ItemCallback<Expense> Diff_CALLBACK = new DiffUtil.ItemCallback<Expense>() {
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
    private OnItemClickListner listner;

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
        this.listner = listner;
    }

    public interface OnItemClickListner {
        void onItemClick(Expense expense);
    }

    class ExpenseHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewAmount;

        public ExpenseHolder(@NonNull View itemView) {
            super(itemView);
            textViewAmount = itemView.findViewById(R.id.expense_amount);
            textViewTitle = itemView.findViewById(R.id.expense_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listner != null && position != RecyclerView.NO_POSITION) {
                        listner.onItemClick(getItem(position));
                    }
                }
            });
        }
    }
}
//package com.example.mehmood.splitbill.utils;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.mehmood.splitbill.R;
//import com.example.mehmood.splitbill.data.Expense;
//
//import java.util.List;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {
//    private List<Expense> mExpense;
//    private onItemClickListener mListener;
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public ExpenseAdapter(List<Expense> expenses) {
//        mExpense = expenses;
//    }
//
//    public void setOnItemClickListener(onItemClickListener listener) {
//        mListener = listener;
//
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public ExpenseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // create a new view
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.expense, parent, false);
//
//        MyViewHolder vh = new MyViewHolder(view, mListener);
//        return vh;
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        Expense currentExpense = mExpense.get(position);
//        holder.mExpenseName.setText(currentExpense.getExpenseName());
//        holder.mExpenseAmount.setText(currentExpense.getExpenseAmount());
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return mExpense.size();
//    }
//
//    public interface onItemClickListener {
//        void onItemClick(int position);
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        public TextView mExpenseName;
//        public TextView mExpenseAmount;
//
//        public MyViewHolder(View itemView, final onItemClickListener listener) {
//            super(itemView);
//
//            mExpenseName = itemView.findViewById(R.id.expense_name);
//            mExpenseAmount = itemView.findViewById(R.id.expense_amount);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(position);
//                        }
//                    }
//
//                }
//            });
//        }
//    }
//}
