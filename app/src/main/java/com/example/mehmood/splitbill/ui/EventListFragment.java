package com.example.mehmood.splitbill.ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Event;
import com.example.mehmood.splitbill.data.EventViewModel;
import com.example.mehmood.splitbill.utils.EventAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;

public class EventListFragment extends Fragment {

    private EventViewModel eventViewModel;
    private FloatingActionButton mFloatingActionButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        setHasOptionsMenu(true);
        mFloatingActionButton = view.findViewById(R.id.add_button);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = (getActivity()).getSupportFragmentManager().beginTransaction();
                ChooseAddOptionDialogFragment dialog = new ChooseAddOptionDialogFragment();
                dialog.show(ft, "TAG");
            }
        });
        Log.d("ELF", "OnCreateView");
        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final EventAdapter eventAdapter = new EventAdapter();
        recyclerView.setAdapter(eventAdapter);
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        eventViewModel.getAllEvents().observe(getActivity(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                Log.d("ELF", "Onchanged");
                eventAdapter.submitList(events);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                eventAdapter.getEventAt(viewHolder.getAdapterPosition());
                eventViewModel.deleteEvent(eventAdapter.getEventAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        eventAdapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event event) {
                Intent intent = new Intent(getActivity(), DetailedEventActivity.class);
                intent.putExtra("EventId", event.getEventId());
                startActivity(intent);

            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.event_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAll:
                EventViewModel eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
                eventViewModel.deleteAllEvent();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
