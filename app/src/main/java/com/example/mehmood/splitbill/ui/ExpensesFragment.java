package com.example.mehmood.splitbill.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mehmood.splitbill.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ExpensesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
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
