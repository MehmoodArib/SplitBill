package com.example.mehmood.splitbill.ui.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mehmood.splitbill.App;
import com.example.mehmood.splitbill.MainActivity;
import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.utils.Utilities.SharedPreferencesUtility;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**This Fragment is Launched by Login Activity
 * used to get the users phone number
 * after successful completion it again Launch the MainActivity.*/

public class PhoneFragment extends Fragment {
    private TextInputEditText mPhoneTextInputEditText;
    private Button mVerifyPhoneButton;
    private String phone;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        mVerifyPhoneButton = view.findViewById(R.id.verifyButton);
        mPhoneTextInputEditText = view.findViewById(R.id.textInputEditTextPhoneNumber);
        mButtonListener();
        return view;
    }

    private void mButtonListener() {
        mVerifyPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatePhone()) {
                    //storing phone number in shared preference.
                    SharedPreferencesUtility.getInstance(App.getContext()).put(SharedPreferencesUtility.Key.phone, phone);
                    //Launching MAinActivity.
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    Objects.requireNonNull(getActivity()).finish();
                }
            }
        });
    }

    //verify phone Number
    private boolean validatePhone () {
       phone = mPhoneTextInputEditText.getText().toString().trim();
        if (phone.length() != 10) {
            mPhoneTextInputEditText.setError("In-Valid Number");
            return false;
        } else {
            mPhoneTextInputEditText.setError(null);
            return true;
        }
    }
}
