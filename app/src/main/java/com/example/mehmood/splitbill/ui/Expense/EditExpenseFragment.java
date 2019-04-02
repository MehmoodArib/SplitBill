package com.example.mehmood.splitbill.ui.Expense;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Contact;
import com.example.mehmood.splitbill.data.DetailedActivityViewModel;
import com.example.mehmood.splitbill.data.Event;
import com.example.mehmood.splitbill.data.Expense;
import com.example.mehmood.splitbill.utils.Utilities.Utility;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashSet;
import java.util.Objects;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class EditExpenseFragment extends Fragment {
    Expense expense;
    private DetailedActivityViewModel detailedActivityViewModel;
    private TextInputEditText mExpenseNameTextInputEditText;
    private TextInputEditText mExpenseAmountTextInputEditText;
    private Button mUpdateExpenseButton;
    private Switch mPaidByMeSwitch;
    private Switch mPaidForAllSwitch;
    private String expenseName;
    private Double expenseAmount;
    private Expense mExpense = new Expense();
    private HashSet<Contact> participants;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_edit_expense, container, false);
        setHasOptionsMenu(false);
        mExpenseAmountTextInputEditText = view.findViewById(R.id.textInputEditTextExpenseAmount);
        mExpenseNameTextInputEditText = view.findViewById(R.id.textInputEditTextExpenseName);
        mUpdateExpenseButton = view.findViewById(R.id.buttonAddExpense);
        expense = getArguments().getParcelable(Utility.expense);
        mExpenseAmountTextInputEditText.setText(String.valueOf(expense.getExpenseAmount()));
        mExpenseNameTextInputEditText.setText(expense.getExpenseName());
        detailedActivityViewModel = ViewModelProviders.of(getActivity()).get(DetailedActivityViewModel.class);
        detailedActivityViewModel.getEvent().observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                participants = event.getParticipantsList();

                final LinearLayout mPaidByLinearLayout;
                final LinearLayout mPAidForLinearLayout;
                mPaidByLinearLayout = view.findViewById(R.id.paidByLinearLayout);
                mPAidForLinearLayout = view.findViewById(R.id.paidForLinearLayout);
                mPaidByMeSwitch = view.findViewById(R.id.paidByMeSwitchButton);
                mPaidForAllSwitch = view.findViewById(R.id.paidForAllSwitchButton);
                mPaidByLinearLayout.setVisibility(View.GONE);
                mPAidForLinearLayout.setVisibility(View.GONE);

                for (Contact participant : participants) {
                    View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.participant, null);
                    TextView textView = view2.findViewById(R.id.participantTextView);
                    textView.setText(participant.getName());
                    mPAidForLinearLayout.addView(view2);
                }
                for (Contact participant : participants) {
                    View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.participant, null);
                    TextView textView = view1.findViewById(R.id.participantTextView);
                    textView.setText(participant.getName());
                    mPaidByLinearLayout.addView(view1);
                }


                mPaidForAllSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (mPaidForAllSwitch.isChecked()) {
                            mPAidForLinearLayout.setVisibility(View.GONE);
                        }
                        if (!mPaidForAllSwitch.isChecked()) {
                            mPAidForLinearLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
                mPaidByMeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (mPaidByMeSwitch.isChecked()) {
                            mPaidByLinearLayout.setVisibility(View.GONE);
                        }
                        if (!mPaidByMeSwitch.isChecked()) {
                            mPaidByLinearLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

        mUpdateExpenseButtonListener();
        return view;
    }


    private void mUpdateExpenseButtonListener() {
        mUpdateExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateExpenseName()) return;
                if (!validateExpenseAmount()) return;
                mExpense.setExpenseName(expenseName);
                mExpense.setExpenseAmount(expenseAmount);
                mExpense.setExpenseEventId(expense.getExpenseEventId());
                mExpense.setExpenseId(expense.getExpenseId());
                detailedActivityViewModel.updateExpense(mExpense);
                //detailedActivityViewModel.getExpenseOfEvent(eventId);
                //pop-ing up this fragment to go back to ExpenseFragment.
                androidx.fragment.app.Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(EditExpenseFragment.class.getSimpleName());
                if (fragment != null)
                    getActivity().getSupportFragmentManager().popBackStack();
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