package com.example.mehmood.splitbill.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mehmood.splitbill.AddExpenseDialog;
import com.example.mehmood.splitbill.MainActivity;
import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Event;
import com.example.mehmood.splitbill.data.Expense;
import com.example.mehmood.splitbill.utils.ExpenseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExpensesFragment extends Fragment {
    List<Expense> expenses;
    private RecyclerView mRecyclerView;
    private ExpenseAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mTotalExpense;
    private FloatingActionButton mFloatingActionButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final String eventId = getArguments().getString("eventId");
        Event event = MainActivity.myDataBase.myDao().getEvent(eventId);
        expenses = MainActivity.myDataBase.myDao().getExpenses(eventId);
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        mFloatingActionButton = view.findViewById(R.id.add_new_expense_button);
        mTotalExpense = view.findViewById(R.id.event_Total_Expense);
        mTotalExpense.setText(event.getTotalAmount());
        mRecyclerView = view.findViewById(R.id.expense_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ExpenseAdapter(expenses);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ExpenseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                clickedItem(position);
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager ft = (getActivity()).getSupportFragmentManager();
                AddExpenseDialog fragment = new AddExpenseDialog();
                Bundle bundle = new Bundle();
                bundle.putString("eventId", eventId);
                fragment.setArguments(bundle);
                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialogFragmentStyle);
                fragment.show(ft, null);
            }
        });
        return view;
    }

    public void clickedItem(int position) {
        /*  Here we will open the detailed fragment of the expense   */
        Expense selectedExpense = expenses.get(position);
//        Intent intent = new Intent(getActivity(), DetailedEventActivity.class);
//        intent.putExtra("EventId", selectedEvent.getEventId());
//        startActivity(intent);
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
            Toast.makeText(getActivity(), "Clicked on" + item.getTitle(), Toast.LENGTH_SHORT).show();
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
}
