package com.example.mehmood.splitbill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mehmood.splitbill.data.Expense;
import com.google.android.material.textfield.TextInputEditText;

import androidx.fragment.app.DialogFragment;

public class AddExpenseDialog extends DialogFragment {
    private TextInputEditText mTextInputEditTextExpenseName;
    private TextInputEditText mTextInputEditTextExpenseAmount;
    private Button mAddExpenseButton;
    private String expenseName;
    private String expenseAmount;
    private String eventId;
    private Expense expense = new Expense();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("ADD EXPENSE");
        eventId = getArguments().getString("eventId");
        View view = inflater.inflate(R.layout.add_expense_dialog, container,
                false);
        mAddExpenseButton = view.findViewById(R.id.buttonAddExpense);
        mTextInputEditTextExpenseAmount = view.findViewById(R.id.textInputEditTextExpenseAmount);
        mTextInputEditTextExpenseName = view.findViewById(R.id.textInputEditTextExpenseName);
        mAddExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateExpenseName()) return;
                if (!validateExpenseAmount()) return;
                expense.setExpenseName(expenseName);
                expense.setExpenseAmount(expenseAmount);
                expense.setExpenseEventId(eventId);
                MainActivity.myDataBase.myDao().addExpense(expense);
               // ExpenseAdapter.notifyDataSetChanged();
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    private boolean validateExpenseName() {
        expenseName = mTextInputEditTextExpenseName.getText().toString().trim();
        if (expenseName.isEmpty()) {
            mTextInputEditTextExpenseName.setError("Field Can't be empty");
            return false;
        }
        if (expenseName.length() > 20) {
            mTextInputEditTextExpenseName.setError("Maximum 20 characters Allowed");
            return false;
        } else {
            mTextInputEditTextExpenseName.setError(null);
            return true;
        }
    }

    private boolean validateExpenseAmount() {
        expenseAmount = mTextInputEditTextExpenseAmount.getText().toString().trim();
        if (expenseAmount.length() > 10) {
            mTextInputEditTextExpenseAmount.setError("Maximum 10 digit's Allowed");
            return false;
        } else {
            mTextInputEditTextExpenseAmount.setError(null);
            return true;
        }
    }

}

