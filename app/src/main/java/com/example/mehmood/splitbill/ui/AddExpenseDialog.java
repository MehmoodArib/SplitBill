package com.example.mehmood.splitbill.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.EventViewModel;
import com.example.mehmood.splitbill.data.Expense;
import com.google.android.material.textfield.TextInputEditText;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class AddExpenseDialog extends DialogFragment {
    private TextInputEditText mTextInputEditTextExpenseName;
    private TextInputEditText mTextInputEditTextExpenseAmount;
    private Button mAddExpenseButton;
    private String expenseName;
    private Double expenseAmount;
    private Expense expense = new Expense();
    private Integer eventId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("ADD EXPENSE");
        final EventViewModel eventViewModel = ViewModelProviders.of(getActivity()).get(EventViewModel.class);
        eventId = getArguments().getInt("eventId", -1);
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

                eventViewModel.addExpense(expense);
                eventViewModel.getExpenseOfEvent(eventId);
                dismiss();
            }
        });

        return view;
    }

    private boolean validateExpenseAmount() {
        expenseAmount = Double.valueOf(mTextInputEditTextExpenseAmount.getText().toString().trim());
        if (expenseAmount <= 0) {
            mTextInputEditTextExpenseAmount.setError("Expense Should be greater then 0");
            return false;
        } else {
            mTextInputEditTextExpenseAmount.setError(null);
            return true;
        }
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
    }



