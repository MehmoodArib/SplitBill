package com.example.mehmood.splitbill.ui;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.utils.SharedPreferencesUtility;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ProfileFragment extends Fragment {
private TextView mTextViewName;
private TextView mTextViewEmail;
private ImageView mImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        mImageView = view.findViewById(R.id.profile_image);
        mTextViewEmail= view.findViewById(R.id.profile_email);
        mTextViewName= view.findViewById(R.id.profile_name);
        String userName = SharedPreferencesUtility.getInstance(getContext()).getString(SharedPreferencesUtility.Key.name);
        String userEmail =SharedPreferencesUtility.getInstance(getContext()).getString(SharedPreferencesUtility.Key.email);
        String profileUrl = SharedPreferencesUtility.getInstance(getContext()).getString(SharedPreferencesUtility.Key.profileUrl);
        mTextViewName.setText(userName);
        mTextViewEmail.setText(userEmail);
        Picasso.get().load(profileUrl).resize(600, 600).transform(new CropCircleTransformation()).into(mImageView);

        return view;
    }

}
