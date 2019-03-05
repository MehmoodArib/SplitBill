package com.example.mehmood.splitbill.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mehmood.splitbill.App;
import com.example.mehmood.splitbill.MainActivity;
import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.utils.SharedPreferencesUtility;
import com.google.android.material.textfield.TextInputEditText;

import androidx.fragment.app.Fragment;

public class PhoneFragment extends Fragment {
    private TextInputEditText mTextInputEditText;
    private Button mButton;
    String phone;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        mButton = view.findViewById(R.id.verifyButton);
        mTextInputEditText = view.findViewById(R.id.textInputEditTextPhoneNumber);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validatePhone()){
                    SharedPreferencesUtility.getInstance(App.getContext()).put(SharedPreferencesUtility.Key.phone,phone);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        return view;
    }
    private boolean validatePhone () {
       phone = mTextInputEditText.getText().toString().trim();
        if (phone.length()!=10) {
            mTextInputEditText.setError("In-Valid Number");
            return false;
        } else {
            mTextInputEditText.setError(null);
            return true;
        }
    }
}
