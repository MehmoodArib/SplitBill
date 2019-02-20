package com.example.mehmood.splitbill.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.mehmood.splitbill.MainActivity;
import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Contact;
import com.example.mehmood.splitbill.data.Event;
import com.example.mehmood.splitbill.utils.FragmentUtility;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class AddEventFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private Spinner spinner;
    TextInputLayout mTextInputLayoutEventName;
    TextInputLayout mTextInputLayoutEventDesc;
    Button mAddButton;
    String eventCurrency;
    String eventName;
    String eventDesc;
    Event event = new Event();
    private static final String[] paths = {"Rs", "$", "Eu"};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);
        spinner = view.findViewById(R.id.spinner);

        mTextInputLayoutEventDesc = view.findViewById(R.id.textInputLayoutNewEventDescription);
        mTextInputLayoutEventName = view.findViewById(R.id.textInputLayoutNewEventName);
        mAddButton = view.findViewById(R.id.buttonAdd);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEventName()) return;
                if (!validateEventDesc()) return;
                event.setEventName(eventName);
                event.setEventDesc(eventDesc);
                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();
                event.setEventId(ts);
                Contact contact1 = new Contact("Arib","991169753");
                Contact contact2 = new Contact("Amir","991169753");
                ArrayList<Contact> participants = new ArrayList<>();
                participants.add(contact1);
                participants.add(contact2);
                event.setParticipantsList(participants);
                event.setTotalAmount("500");
                MainActivity.myDataBase.myDao().addEvent(event);

                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(AddEventFragment.class.getSimpleName());
                if (fragment != null)
                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                EventListFragment eventListFragment = new EventListFragment();
                FragmentUtility.inflateFragment(eventListFragment, getActivity().getSupportFragmentManager(), R.id.fragmentContainer, false, false, null);
            }
        });


        return view;
    }

        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

            switch (position) {
                case 0:
                    eventCurrency = "INR";
                    break;
                case 1:
                    eventCurrency = "Dollar";
                    break;
                case 2:
                    eventCurrency = "Euro";
                    break;

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }

    private boolean validateEventName() {
        eventName = mTextInputLayoutEventName.getEditText().getText().toString().trim();
        if (eventName.isEmpty()) {
            mTextInputLayoutEventName.setError("Field Can't be empty");
            return false;
        }
            if (eventName.length() > 20) {
                mTextInputLayoutEventDesc.setError("Maximum 20 characters Allowed");
                return false;
            } else {
                mTextInputLayoutEventName.setError(null);
                return true;
            }
        }

        private boolean validateEventDesc () {
            eventDesc = mTextInputLayoutEventDesc.getEditText().getText().toString().trim();
            if (eventDesc.length() > 50) {
                mTextInputLayoutEventDesc.setError("Maximum 50 characters Allowed");
                return false;
            } else {
                mTextInputLayoutEventDesc.setError(null);
                return true;
            }
        }

    }

