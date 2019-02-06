package com.example.mehmood.splitbill;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mehmood.splitbill.Utills.SplitBillUtility;

public class EventAddOption extends Fragment {
    private Button mAddNewEventButton;
    private Button mJoinEventButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_add_option, container, false);

         mAddNewEventButton = view.findViewById(R.id.addNewEventButton);
         mJoinEventButton = view.findViewById(R.id.joinEventButton);
         mAddNewEventButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AddEvent addEvent = new AddEvent();
                 SplitBillUtility.inflateFragment(addEvent,getActivity().getSupportFragmentManager(), R.id.fragementContainer, true, true, null);

             }
         });
         mJoinEventButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //SplitBillUtility.inflateFragment(eventListFragment, getSupportFragmentManager(), R.id.fragementContainer, false, false, null);

             }
         });
        return view;
    }
}
