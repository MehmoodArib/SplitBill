package com.example.mehmood.splitbill.ui.Balance;


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

public class BalancesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_balances, container, false);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.balance_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.balance_send) {
            Toast.makeText(getActivity(), "Clicked on" + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        } else
            return false;
    }
}
