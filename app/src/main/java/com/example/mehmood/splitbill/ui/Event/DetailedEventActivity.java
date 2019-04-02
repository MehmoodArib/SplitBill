package com.example.mehmood.splitbill.ui.Event;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.DetailedActivityViewModel;
import com.example.mehmood.splitbill.data.Event;
import com.example.mehmood.splitbill.data.MyViewModelFactory;
import com.example.mehmood.splitbill.utils.Utilities.FragmentUtility;
import com.example.mehmood.splitbill.utils.Utilities.Utility;
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static com.example.mehmood.splitbill.MainActivity.CONTACT_PICKER_REQUEST;

public class DetailedEventActivity extends AppCompatActivity {
    private TextView mTitle;
    private TextView mSubTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_event);
        Integer eventId = getIntent().getExtras().getInt(Utility.eventId);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mTitle = mToolbar.findViewById(R.id.toolbar_title);
        mSubTitle = mToolbar.findViewById(R.id.toolbar_sub_title);
        DetailedActivityViewModel detailedActivityViewModel = ViewModelProviders.of(this, new MyViewModelFactory(this.getApplication(), eventId)).get(DetailedActivityViewModel.class);
        detailedActivityViewModel.getEvent().observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                mTitle.setText(event.getEventName());
                mSubTitle.setText(Utility.getNameList(event.getParticipantsList()));
            }
        });
        this.setSupportActionBar(mToolbar);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        ExpenseBalanceFragment expenseBalanceFragment = new ExpenseBalanceFragment();
        Bundle data = new Bundle();
        data.putInt(Utility.eventId, eventId);
        FragmentUtility.inflateFragment
                (expenseBalanceFragment,
                        getSupportFragmentManager(),
                        R.id.fragmentContainer2, false, false, data);
    }

    /**
     * This code In Activity is for choosing contacts used in Add Event Fragment
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONTACT_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                List<ContactResult> results = MultiContactPicker.obtainResult(data);
                EditEventFragment editEventFragment = (EditEventFragment) getSupportFragmentManager().findFragmentByTag(EditEventFragment.class.getSimpleName());
                editEventFragment.setContacts(results);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "User closed the picker without selecting items", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface ContactInfo {
        void setContacts(List<ContactResult> contacts);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
