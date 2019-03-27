package com.example.mehmood.splitbill.ui.Expense;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Event;
import com.example.mehmood.splitbill.data.EventViewModel;
import com.example.mehmood.splitbill.data.Expense;
import com.example.mehmood.splitbill.ui.Event.EditEventFragment;
import com.example.mehmood.splitbill.utils.Adapters.ExpenseAdapter;
import com.example.mehmood.splitbill.utils.Utilities.FragmentUtility;
import com.example.mehmood.splitbill.utils.Utilities.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;

/**
 * This Fragment is launched by Detailed EventActivity
 * It shows the list of the Expense of the Event
 * The Event is recognised by the eventId which we send in bundle.
 * before launching this fragment
 */

public class ExpenseFragment extends Fragment {
    private Integer eventId;   //EventId Which we get from bundle.
    private RecyclerView mExpenseListRecyclerView; //RecyclerView to show List of Expenses.
    private TextView mTotalExpenseTextView;         //Bottom Left TextView TO show Total Expense.
    private FloatingActionButton mAddExpenseFloatingActionButton; //Button to Add new Expense.
    private EventViewModel mEventViewModel;
    private ExpenseAdapter mExpenseAdapter;
    private TextView mTotalAmountCurrency;
    private TextView mMyAmountCurrency;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        Log.d("EF","OnCreate");
        assert getArguments() != null;
        eventId = getArguments().getInt(Utility.eventId);
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        mAddExpenseFloatingActionButton = view.findViewById(R.id.add_new_expense_button);
        mTotalExpenseTextView = view.findViewById(R.id.event_Total_Expense);
        mTotalAmountCurrency = view.findViewById(R.id.event_Currency);
        mMyAmountCurrency = view.findViewById(R.id.event_Currency2);

        mExpenseListRecyclerView = view.findViewById(R.id.expense_recycler_view);
        mExpenseListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mExpenseListRecyclerView.setHasFixedSize(true);
        mExpenseAdapter = new ExpenseAdapter();
        mExpenseListRecyclerView.setAdapter(mExpenseAdapter);

        mEventViewModel = ViewModelProviders.of(getActivity()).get(EventViewModel.class);
        mEventViewModel.getExpenseOfEvent(eventId);
        mEventViewModel.getEvent(eventId);
        mEventViewModel.getEvent().observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                mTotalExpenseTextView.setText(String.valueOf(event.getTotalAmount()));
                mMyAmountCurrency.setText(event.getCurrency());
                mTotalAmountCurrency.setText(event.getCurrency());
            }
        });
        mEventViewModel.getExpenseListOfEvent().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                mExpenseAdapter.submitList(expenses);
            }
        });

        itemTouchHelper();
        expenseAdapterItemClickListener();
        mFloatingActionButtonListener();
        return view;
    }
    private void expenseAdapterItemClickListener() {
        mExpenseAdapter.setOnItemClickListener(new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Expense expense) {


            }
        });
    }

    private void mFloatingActionButtonListener() {
        mAddExpenseFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt(Utility.eventId, eventId);

                AddExpenseFragment addExpenseFragment = new AddExpenseFragment();
                FragmentUtility.inflateFragment(addExpenseFragment, getActivity().getSupportFragmentManager(), R.id.fragmentContainer2, true, true, bundle);

            }
        });
    }

    //
    private void itemTouchHelper() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                mExpenseAdapter.getExpenseAt(viewHolder.getAdapterPosition());
                mEventViewModel.deleteExpense(mExpenseAdapter.getExpenseAt(viewHolder.getAdapterPosition()));
                mEventViewModel.getExpenseOfEvent(eventId);
            }
        }).attachToRecyclerView(mExpenseListRecyclerView);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.expense_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.expense_share) {
            Toast.makeText(getActivity(), "Clicked on" + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.expense_edit) {
            EditEventFragment editEventFragment = new EditEventFragment();
            Bundle data = new Bundle();
            data.putInt(Utility.eventId, eventId);
            FragmentUtility.inflateFragment(editEventFragment, getActivity().getSupportFragmentManager(),
                    R.id.fragmentContainer2, true, false, data);
            return true;

        } else if (item.getItemId() == R.id.expense_sort_by_title) {
            Toast.makeText(getActivity(), "Clicked on" + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.expense_sort_by_amount) {
            Toast.makeText(getActivity(), "Clicked on" + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.expense_sort_by_date) {
            Toast.makeText(getActivity(), "Clicked on" + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.expense_sort_by_payer) {
            Toast.makeText(getActivity(), "Clicked on" + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;

        } else
            return false;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("EF","On Resume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("EF","On Stop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("EF","On DestroyView");
    }
}