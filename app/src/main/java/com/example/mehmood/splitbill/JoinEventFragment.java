package com.example.mehmood.splitbill;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class JoinEventFragment extends Fragment {
    TextInputLayout mTextInputLayoutEventId;
    private Button mButtonJoin;
    String eventId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_join_event, container, false);
        mTextInputLayoutEventId = view.findViewById(R.id.textInputLayoutEventId);
        mButtonJoin = view.findViewById(R.id.buttonJoin);

        mButtonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEventId()) return;
                Toast.makeText(getActivity(), eventId+"Invalid Event Id", Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }

    private boolean validateEventId() {
         eventId = mTextInputLayoutEventId.getEditText().getText().toString().trim();
        if (eventId.isEmpty()) {
            mTextInputLayoutEventId.setError("Field Can't be empty");
            return false;
        }
        else {
            mTextInputLayoutEventId.setError(null);
            return true;
        }
    }
}