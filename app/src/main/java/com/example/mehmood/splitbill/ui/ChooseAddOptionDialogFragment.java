package com.example.mehmood.splitbill.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.ui.Event.AddEventFragment;
import com.example.mehmood.splitbill.ui.Event.JoinEventFragment;
import com.example.mehmood.splitbill.utils.Utilities.FragmentUtility;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

/**Dialog that comes on pressing Add button of EventList Fragment to Add Event
 * that Gives an option to Add new Event or join Already created Event.
 * if user choose AddOption this Dialog will launch AddEventFragment
 * if user choose Join Event dialog will Launch JoinEventFragment*/
public class ChooseAddOptionDialogFragment extends AppCompatDialogFragment {
    private String selection;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] option = Objects.requireNonNull(getActivity()).getResources().getStringArray(R.array.Option);
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
                if (Objects.equals(selection, option[0])) {
                    //launching Add event Fragment
                    AddEventFragment addEventFragment = new AddEventFragment();
                    FragmentUtility.inflateFragment(addEventFragment, Objects.requireNonNull(getActivity()).getSupportFragmentManager(), R.id.fragmentContainer, true, true, null);
                }
                if (Objects.equals(selection, option[1])) {
                    //launching Join Event Fragment
                    JoinEventFragment joinEventFragment = new JoinEventFragment();
                    FragmentUtility.inflateFragment(joinEventFragment, Objects.requireNonNull(getActivity()).getSupportFragmentManager(), R.id.fragmentContainer, true, true, null);
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }
}
