package com.example.mehmood.splitbill;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mehmood.splitbill.Utills.MyAdapter;

public class EventListFragment extends Fragment {
    Event event1 = new Event("Shimla Trip", "Official");
    Event event2 = new Event("Mumbai Trip", "Meeting");
    Event event3 = new Event("Delhi Trip",null);
    Event event4 = new Event("London",null);
    Event event5 = new Event("Taj Mahal","Agra");

    Event[] myEvent = {event1, event2,event3,event4,event5};

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFloatingActionButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mFloatingActionButton = view.findViewById(R.id.add_button);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(myEvent);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                clickedItem(position);
            }
        });
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = (getActivity()).getSupportFragmentManager().beginTransaction();
                ChooseAddOptionDialogFragment dialog = new ChooseAddOptionDialogFragment();
                dialog.show(ft, "TAG");
            }
        });
        return view;
    }

    public void clickedItem(int position) {
        /*

           Here we will open the detailed fragment of the event
        *
        * */
        Log.e("Clicked", "Clicked");
    }
}

