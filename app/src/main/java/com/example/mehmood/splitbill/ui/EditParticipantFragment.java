package com.example.mehmood.splitbill.ui;


import android.app.Activity;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Contact;
import com.example.mehmood.splitbill.ui.Event.EditEventFragment;
import com.example.mehmood.splitbill.utils.Utilities.Utility;
import com.google.android.material.textfield.TextInputLayout;

public class EditParticipantFragment extends androidx.fragment.app.Fragment {
    private TextInputLayout mTextInputLayoutParticipantName;
    private TextInputLayout mTextInputLayoutParticipantPhone;
    private Button mButtonUpdateContact;
    private String mParticipantName;
    private String mParticipantNumber;
    private Activity iActivity;
    private Contact contact;

    private UpdateContact onUpdate;

    public EditParticipantFragment() {
    }

    public EditParticipantFragment(UpdateContact editEventFragment) {
        onUpdate = editEventFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contact = getArguments().getParcelable("contact");
        View view = inflater.inflate(R.layout.fragment_edit_participant, container, false);
        mButtonUpdateContact = view.findViewById(R.id.buttonUpdateParticipant);
        mTextInputLayoutParticipantName = view.findViewById(R.id.textInputParticipantName);
        mTextInputLayoutParticipantPhone = view.findViewById(R.id.textInputParticipantPhone);
        mTextInputLayoutParticipantName.getEditText().setText(contact.getName());
        mTextInputLayoutParticipantPhone.getEditText().setText(contact.getNumber());
        iActivity = getActivity();
        mButtonUpdateContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateParticipantName()) return;
                if (!validateParticipantNumber()) return;
                String updatedName = mTextInputLayoutParticipantName.getEditText().getText().toString();
                String updatedPhone = mTextInputLayoutParticipantPhone.getEditText().getText().toString();
                Contact contact = new Contact(updatedName,updatedPhone);
                onUpdate.onUpdate(contact);

                androidx.fragment.app.Fragment fragment = getActivity().getSupportFragmentManager()
                                  .findFragmentByTag(EditParticipantFragment.class.getSimpleName());
                if (fragment != null)
                    getActivity().getSupportFragmentManager().popBackStack();

            }
        });
        return view;
    }

    private boolean validateParticipantName() {
        mParticipantName = mTextInputLayoutParticipantName.getEditText().getText().toString().trim();
        if (mParticipantName.isEmpty()) {
            mTextInputLayoutParticipantName.setError(Utility.errorNameValidation);
            return false;
        } else {
            mTextInputLayoutParticipantName.setError(null);
            return true;
        }
    }

    private boolean validateParticipantNumber() {
        mParticipantNumber = mTextInputLayoutParticipantPhone.getEditText().getText().toString().trim();
        if (mParticipantNumber.length() > 10 || mParticipantNumber.length() < 10) {
            mTextInputLayoutParticipantPhone.setError(Utility.errorNumberValidation);
            return false;
        } else {
            mTextInputLayoutParticipantPhone.setError(null);
            return true;
        }
    }

    public interface UpdateContact{
        void onUpdate(Contact contact);
    }
}
