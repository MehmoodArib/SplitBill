package com.example.mehmood.splitbill.ui.Expense;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.EventViewModel;
import com.example.mehmood.splitbill.data.Expense;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

/**
 * This Dialog is used to get the new Expense details from user
 * Launch by the AddExpenseFloatingButton in Expense Fragment
 */

public class AddExpenseDialog extends DialogFragment {
    private TextInputEditText mExpenseNameTextInputEditText;
    private TextInputEditText mExpenseAmountTextInputEditText;
    private Button mAddExpenseButton;
    private EventViewModel mEventViewModel;
    private String expenseName;
    private Double expenseAmount;
    private Expense mExpense = new Expense();
    private Integer eventId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(getDialog()).setTitle("ADD EXPENSE");
        mEventViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(EventViewModel.class);
        assert getArguments() != null;
        eventId = getArguments().getInt("eventId", -1);
        View view = inflater.inflate(R.layout.add_expense_dialog, container,
                false);
        mExpenseAmountTextInputEditText = view.findViewById(R.id.textInputEditTextExpenseAmount);
        mExpenseNameTextInputEditText = view.findViewById(R.id.textInputEditTextExpenseName);
        mAddExpenseButton = view.findViewById(R.id.buttonAddExpense);
        mAddExpenseButtonListener();
        return view;
    }

    private void mAddExpenseButtonListener() {
        mAddExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateExpenseName()) return;
                if (!validateExpenseAmount()) return;
                mExpense.setExpenseName(expenseName);
                mExpense.setExpenseAmount(expenseAmount);
                mExpense.setExpenseEventId(eventId);

                mEventViewModel.addExpense(mExpense);
                mEventViewModel.getExpenseOfEvent(eventId);
                dismiss();
            }
        });
    }

    private boolean validateExpenseAmount() {
        expenseAmount = Double.valueOf(Objects.requireNonNull(mExpenseAmountTextInputEditText.getText()).toString().trim());
        if (expenseAmount <= 0) {
            mExpenseAmountTextInputEditText.setError("Expense Should be greater then 0");
            return false;
        } else {
            mExpenseAmountTextInputEditText.setError(null);
            return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        Objects.requireNonNull(getDialog().getWindow()).setAttributes(params);
    }

    private boolean validateExpenseName() {
        expenseName = mExpenseNameTextInputEditText.getText().toString().trim();
        if (expenseName.isEmpty()) {
            mExpenseNameTextInputEditText.setError("Field Can't be empty");
            return false;
        }
        if (expenseName.length() > 20) {
            mExpenseNameTextInputEditText.setError("Maximum 20 characters Allowed");
            return false;
        } else {
            mExpenseNameTextInputEditText.setError(null);
            return true;
        }
    }
}



