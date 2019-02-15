package com.example.mehmood.splitbill.ui;


import android.content.Intent;
import android.os.Bundle;

import com.example.mehmood.splitbill.MainActivity;
import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mehmood.splitbill.utils.MyAdapter;

import java.util.List;

public class EventListFragment extends Fragment {

    List<Event> events = MainActivity.myDataBase.myDao().getEvents();

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
        mAdapter = new MyAdapter(events);
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
        Event selectedEvent =events.get(position);
        Intent intent = new Intent(getActivity(), DetailedEventActivity.class);
        intent.putExtra("EventId", selectedEvent.getEventId());
        startActivity(intent);
    }
}
/*Delete Event from local database
*
*Event event = new Event();
* event.setId();
* MainActivity.Mydatabase.mydao.deleteEvent(event);
*
*
*
* */
