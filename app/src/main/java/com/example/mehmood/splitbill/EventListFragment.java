package com.example.mehmood.splitbill;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mehmood.splitbill.Utills.SplitBillUtility;

public class EventListFragment extends Fragment {
    Event event1 = new Event("Shimla", "Official");
    Event event2 = new Event("Mumbai", "Meeting");
    Event[] myEvent = {event1, event2};

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton mImageButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mImageButton = view.findViewById(R.id.add_button);
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
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventAddOption eventAddOption = new EventAddOption();
                SplitBillUtility.inflateFragment(eventAddOption,
                        getActivity().getSupportFragmentManager(),
                        R.id.fragementContainer, false,
                        true, null);

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

