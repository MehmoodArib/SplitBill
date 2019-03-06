package com.example.mehmood.splitbill.ui.Profile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.ui.Event.AddEventFragment;
import com.example.mehmood.splitbill.ui.Event.JoinEventFragment;
import com.example.mehmood.splitbill.utils.Utilities.FragmentUtility;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ChoosePhoneNumberDialogFragment extends AppCompatDialogFragment {
    private String selection;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] option = getActivity().getResources().getStringArray(R.array.Option);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("OPTION");
        builder.setSingleChoiceItems(R.array.Option, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selection = option[which];
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selection == option[0]) {
                    AddEventFragment addEventFragment = new AddEventFragment();
                    FragmentUtility.inflateFragment(addEventFragment, getActivity().getSupportFragmentManager(), R.id.fragmentContainer, true, true, null);
                }
                if (selection == option[1]) {
                    JoinEventFragment joinEventFragment = new JoinEventFragment();
                    FragmentUtility.inflateFragment(joinEventFragment, getActivity().getSupportFragmentManager(), R.id.fragmentContainer, true, true, null);
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }


}
