package com.example.mehmood.splitbill.ui;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mehmood.splitbill.App;
import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.utils.SharedPreferencesUtility;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import androidx.fragment.app.Fragment;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ProfileFragment extends Fragment {
private TextView mTextViewName;
private TextView mTextViewEmail;
private ImageView mImageView;
    private Button msaveUserInfoButton;

    private TextInputEditText mTextInputEditTextUserName;
    private TextInputEditText mTextInputEditTextUserPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mImageView = view.findViewById(R.id.profile_image);
        mTextViewEmail= view.findViewById(R.id.profile_email);
        mTextViewName= view.findViewById(R.id.profile_name);
        msaveUserInfoButton = view.findViewById(R.id.saveUserInfoButton);
        final String userName = SharedPreferencesUtility.getInstance(getContext()).getString(SharedPreferencesUtility.Key.name);
        String userEmail =SharedPreferencesUtility.getInstance(getContext()).getString(SharedPreferencesUtility.Key.email);
        String profileUrl = SharedPreferencesUtility.getInstance(getContext()).getString(SharedPreferencesUtility.Key.profileUrl);
        final String phone = SharedPreferencesUtility.getInstance(getContext()).getString(SharedPreferencesUtility.Key.phone);
        mTextViewName.setText(userName);
        mTextViewEmail.setText(userEmail);
        Picasso.get().load(profileUrl).resize(600, 600).transform(new CropCircleTransformation()).into(mImageView);

        mTextInputEditTextUserName = view.findViewById(R.id.textInputEditTextUserName);
        mTextInputEditTextUserPhone = view.findViewById(R.id.textInputEditTextUserPhone);
        mTextInputEditTextUserName.setText(userName);
        mTextInputEditTextUserPhone.setText(phone);
        mTextInputEditTextUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (validateUserName()&!mTextInputEditTextUserName.getText().toString().equals(userName)) ;
                {
                    msaveUserInfoButton.setVisibility(View.VISIBLE);
                }
            }
        });
        mTextInputEditTextUserPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (validatePhone()& !mTextInputEditTextUserPhone.getText().toString().equals(phone)) ;
                {
                    msaveUserInfoButton.setVisibility(View.VISIBLE);
                    msaveUserInfoButton.setEnabled(false);
                }
            }
        });

        msaveUserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtility.getInstance(App.getContext()).put(SharedPreferencesUtility.Key.name, mTextInputEditTextUserName.getText().toString());
                SharedPreferencesUtility.getInstance(App.getContext()).put(SharedPreferencesUtility.Key.phone, mTextInputEditTextUserPhone.getText().toString());
                Toast.makeText(getContext(),"Saved",Toast.LENGTH_SHORT).show();
                msaveUserInfoButton.setVisibility(View.GONE);
            }
        });
        return view;
    }
    private boolean validateUserName () {
        String userName = mTextInputEditTextUserName.getText().toString().trim();
        if (userName.length() > 10||userName.length()<1) {
            mTextInputEditTextUserName.setError("Minimum 1 & Maximum 10 characters Allowed");
            return false;
        } else {
            mTextInputEditTextUserName.setError(null);
            return true;
        }
    }
    private boolean validatePhone () {
        String phone = mTextInputEditTextUserPhone.getText().toString().trim();
        if (phone.length()!=10) {
            mTextInputEditTextUserPhone.setError("In-Valid Number");
            return false;
        } else {
            mTextInputEditTextUserPhone.setError(null);
            return true;
        }
    }
}
