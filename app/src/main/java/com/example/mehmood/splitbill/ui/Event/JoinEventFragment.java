package com.example.mehmood.splitbill.ui.Event;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mehmood.splitbill.R;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Fragment Launched by ChooseAddOptionDialog on choosing join Event where we can enter the
 * event id and can join the event.
 */
public class JoinEventFragment extends Fragment {
    private TextInputLayout mEventIdTextInputLayout;
    private Button mJoinButton;
    private String eventId;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_join_event, container, false);
        mEventIdTextInputLayout = view.findViewById(R.id.textInputLayoutEventId);
        mJoinButton = view.findViewById(R.id.buttonJoin);

        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEventId()) return;
                Toast.makeText(getActivity(), eventId+"Invalid Event Id", Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }

    private boolean validateEventId() {
        eventId = mEventIdTextInputLayout.getEditText().getText().toString().trim();
        if (eventId.isEmpty()) {
            mEventIdTextInputLayout.setError("Field Can't be empty");
            return false;
        } else {
            mEventIdTextInputLayout.setError(null);
            return true;
        }
    }
}