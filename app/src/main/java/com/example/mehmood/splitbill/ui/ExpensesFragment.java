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

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Event;
import com.example.mehmood.splitbill.data.EventViewModel;
import com.example.mehmood.splitbill.data.Expense;
import com.example.mehmood.splitbill.utils.ExpenseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;

public class ExpensesFragment extends Fragment {
    private TextView mTotalExpense;
    private FloatingActionButton mFloatingActionButton;
    private EventViewModel eventViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final Integer eventId = getArguments().getInt("eventId");
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        mFloatingActionButton = view.findViewById(R.id.add_new_expense_button);
        mTotalExpense = view.findViewById(R.id.event_Total_Expense);

        RecyclerView recyclerView = view.findViewById(R.id.expense_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final ExpenseAdapter expenseAdapter = new ExpenseAdapter();
        recyclerView.setAdapter(expenseAdapter);
        eventViewModel = ViewModelProviders.of(getActivity()).get(EventViewModel.class);
        eventViewModel.getExpenseOfEvent(eventId);
        eventViewModel.getEvent(eventId);
        eventViewModel.getEvent().observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                mTotalExpense.setText(String.valueOf(event.getTotalAmount()));
            }
        });

        eventViewModel.getExpenseListOfEvent().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                expenseAdapter.submitList(expenses);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                expenseAdapter.getExpenseAt(viewHolder.getAdapterPosition());
                eventViewModel.deleteExpense(expenseAdapter.getExpenseAt(viewHolder.getAdapterPosition()));
                eventViewModel.getExpenseOfEvent(eventId);
            }
        }).attachToRecyclerView(recyclerView);

        expenseAdapter.setOnItemClickListner(new ExpenseAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(Expense expense) {


            }
        });
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager ft = (getActivity()).getSupportFragmentManager();
                AddExpenseDialog fragment = new AddExpenseDialog();
                Bundle bundle = new Bundle();
                bundle.putInt("eventId", eventId);
                fragment.setArguments(bundle);
                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialogFragmentStyle);
                fragment.show(ft, null);
            }
        });
        return view;
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
