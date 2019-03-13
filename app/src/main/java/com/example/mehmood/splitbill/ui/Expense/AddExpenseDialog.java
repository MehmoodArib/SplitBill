package com.example.mehmood.splitbill.ui.Expense;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Contact;
import com.example.mehmood.splitbill.data.Event;
import com.example.mehmood.splitbill.data.EventViewModel;
import com.example.mehmood.splitbill.data.Expense;
import com.example.mehmood.splitbill.utils.Utilities.Utility;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
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
    private List<Contact> participants;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      //  super.onCreateView(inflater, container, savedInstanceState);

        getDialog().setTitle("ADD EXPENSE");
        final View view = inflater.inflate(R.layout.add_expense_dialog, container,
                false);

        mEventViewModel = ViewModelProviders.of(getActivity()).get(EventViewModel.class);
        assert getArguments() != null;
        eventId = getArguments().getInt(Utility.eventId, -1);
        mEventViewModel.getEvent(eventId);
        mEventViewModel.getEvent().observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                participants = event.getParticipantsList();

                LinearLayout mPaidByLinearLayout;
                LinearLayout mPAidForLinearLayout;
                mPaidByLinearLayout = view.findViewById(R.id.paidByLinearLayout);
                mPAidForLinearLayout = view.findViewById(R.id.paidForLinearLayout);
                for (int i = 0; i < participants.size() - 1; i++) {
                    View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.participant, null);
                    mPaidByLinearLayout.addView(view1);

                    View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.participant, null);
                    mPAidForLinearLayout.addView(view2);
                }
            }
        });
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



