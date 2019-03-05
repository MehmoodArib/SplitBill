package com.example.mehmood.splitbill.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Contact;
import com.example.mehmood.splitbill.data.Event;
import com.example.mehmood.splitbill.data.EventViewModel;
import com.example.mehmood.splitbill.utils.FragmentUtility;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class AddEventFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private Spinner spinner;
    private TextInputLayout mTextInputLayoutEventName;
    private TextInputLayout mTextInputLayoutEventDesc;
    private Button mAddButton;
    private String eventCurrency;
    private String eventName;
    private String eventDesc;
    private Event event = new Event();
    private static final String[] paths = {"Rs", "$", "Eu"};
    private ArrayList<Contact> participants = new ArrayList<>();
    private EventViewModel eventViewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);
        spinner = view.findViewById(R.id.spinner);

        mTextInputLayoutEventDesc = view.findViewById(R.id.textInputLayoutNewEventDescription);
        mTextInputLayoutEventName = view.findViewById(R.id.textInputLayoutNewEventName);
        mAddButton = view.findViewById(R.id.buttonAdd);
        eventViewModel = ViewModelProviders.of(getActivity()).get(EventViewModel.class);
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
                //TO-DO add contact picker
                //here we are hardcoding the participants list
                Contact contact1 = new Contact("Arib","991169753");
                Contact contact2 = new Contact("Amir","991169753");
                participants.add(contact1);
                participants.add(contact2);
                setEvent();
                eventViewModel.addEvent(event);

                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(AddEventFragment.class.getSimpleName());
                if (fragment != null)
                    getActivity().getSupportFragmentManager().popBackStack();

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
            eventCurrency = "INR";
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

    public void setEvent() {
        event.setEventName(eventName);
        event.setEventDesc(eventDesc);
        event.setCurrency(eventCurrency);
        event.setParticipantsList(participants);
        event.setTotalAmount(0.0);
    }
    }


