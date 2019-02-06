package com.example.mehmood.splitbill;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

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
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String userName = sharedPref.getString(getString(R.string.name), "");
        String userEmail = sharedPref.getString(getString(R.string.email), "");
        String profileUrl = sharedPref.getString(getString(R.string.profileUrl),"");
        mTextViewName.setText(userName);
        mTextViewEmail.setText(userEmail);
        ImageLoadTask imageLoadTask = new ImageLoadTask(profileUrl,mImageView);
        imageLoadTask.execute();
        return view;
    }

}
