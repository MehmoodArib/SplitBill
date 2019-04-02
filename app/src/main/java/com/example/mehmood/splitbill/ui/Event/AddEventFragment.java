package com.example.mehmood.splitbill.ui.Event;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bigkoo.pickerview.MyOptionsPickerView;
import com.example.mehmood.splitbill.MainActivity;
import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Contact;
import com.example.mehmood.splitbill.data.Event;
import com.example.mehmood.splitbill.data.EventViewModel;
import com.example.mehmood.splitbill.ui.EditParticipantFragment;
import com.example.mehmood.splitbill.utils.Utilities.FragmentUtility;
import com.example.mehmood.splitbill.utils.Utilities.SharedPreferencesUtility;
import com.example.mehmood.splitbill.utils.Utilities.Utility;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.LimitColumn;
import com.wafflecopter.multicontactpicker.MultiContactPicker;
import com.wafflecopter.multicontactpicker.RxContacts.PhoneNumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

/**
 * Fragment Launched by ChooseAddOptionDialog
 * Used to create a new event
 * New Event should have Name optional Description participants added from contacts
 * currency etc.
 */

public class AddEventFragment extends Fragment implements MainActivity.ContactInfo {

    private static final int CONTACT_PICKER_REQUEST = 0;
    private static final String[] CURRENCIES = {"Rs", "$", "Eu"};
    private MyOptionsPickerView singlePicker;
    private TextInputLayout mEventNameTextInputLayout;
    private TextInputLayout mEventDescTextInputLayout;
    private Button mAddEventButton;
    private Button mChooseCurrencyButton;
    private String mEventCurrency;
    private String mEventName;
    private String mEventDesc;
    private Event mEvent = new Event();
    private HashSet<Contact> participants = new HashSet<>();
    private EventViewModel mEventViewModel;
    private Button mContactPickerButton;
    private ChipGroup mChipGroup;
    private Activity iActivity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        //Set Id's of the view
        setFindById(view);
        iActivity = getActivity();
        //ViewModel with owner MainActivity
        mEventViewModel = ViewModelProviders.of(getActivity()).get(EventViewModel.class);
        mEvent.setCurrency(Utility.defaultCurrency);//Default Currency
        setMyContactsInParticipants();
        mContactPickerButtonListener();
        mAddEventButtonListener(getActivity());
        currencyPicker();
        mChooseCurrencyButtonListener();
        return view;
    }

    private void setMyContactsInParticipants() {
        Contact myContact = new Contact(SharedPreferencesUtility.getInstance(getContext()).getString(SharedPreferencesUtility.Key.name),
                SharedPreferencesUtility.getInstance(getContext()).getString(SharedPreferencesUtility.Key.phone));
        participants.add(myContact);
        chipMaker(myContact);
    }
    private void mChooseCurrencyButtonListener() {
        mChooseCurrencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singlePicker.show();
            }
        });
    }

    private void mAddEventButtonListener(final FragmentActivity iActivity) {
        mAddEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEventName()) return;
                if (!validateEventDesc()) return;
                setEvent();
                mEventViewModel.addEvent(mEvent); //Finally Adding Event to the dataBase
                //pop-ing up this fragment to go back to EventList Fragment.
                Fragment fragment = iActivity.getSupportFragmentManager().findFragmentByTag(AddEventFragment.class.getSimpleName());
                if (fragment != null)
                    iActivity.getSupportFragmentManager().popBackStack();

            }
        });
    }

    private void mContactPickerButtonListener() {
        mContactPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MultiContactPicker.Builder(getActivity()) //Activity/fragment context
                        //.theme(R.style.MyCustomPickerTheme) //Optional - default: MultiContactPicker.Azure
                        .hideScrollbar(false) //Optional - default: false
                        .showTrack(true) //Optional - default: true
                        .searchIconColor(Color.WHITE) //Option - default: White
                        .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE) //Optional - default: CHOICE_MODE_MULTIPLE
                        .handleColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary)) //Optional - default: Azure Blue
                        .bubbleColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary)) //Optional - default: Azure Blue
                        .bubbleTextColor(Color.WHITE) //Optional - default: White
                        .setTitleText("Select Participants") //Optional - default: Select Contacts
                        //.setSelectedContacts("10", "5" / myList) //Optional - will pre-select contacts of your choice. String... or List<ContactResult>
                        .setLoadingType(MultiContactPicker.LOAD_ASYNC) //Optional - default LOAD_ASYNC (wait till all loaded vs stream results)
                        .limitToColumn(LimitColumn.NONE) //Optional - default NONE (Include phone + email, limiting to one can improve loading time)
                        .setActivityAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                android.R.anim.fade_in,
                                android.R.anim.fade_out) //Optional - default: No animation overrides
                        .showPickerForResult(CONTACT_PICKER_REQUEST);
            }
        });

    }
    private boolean validateEventName() {
        mEventName = mEventNameTextInputLayout.getEditText().getText().toString().trim();
        if (mEventName.isEmpty()) {
            mEventNameTextInputLayout.setError(Utility.errorNameValidation);
            return false;
        }
        if (mEventName.length() > 20) {
            mEventDescTextInputLayout.setError(Utility.errorNameValidation2);
                return false;
            } else {
            mEventNameTextInputLayout.setError(null);
                return true;
            }
        }

    private boolean validateEventDesc() {
        mEventDesc = mEventDescTextInputLayout.getEditText().getText().toString().trim();

        if (mEventDesc.length() > 50) {
            mEventDescTextInputLayout.setError(Utility.errorDescValidation);
                return false;
            } else {
            mEventDescTextInputLayout.setError(null);
                return true;
            }
        }

    private void setEvent() {
        mEvent.setEventName(mEventName);
        mEvent.setEventDesc(mEventDesc);
        mEvent.setCurrency(mEventCurrency);
        mEvent.setParticipantsList(participants);
        mEvent.setTotalAmount(0.0);
    }

    @Override
    public void setContacts(List<ContactResult> contacts) {
        int i = 0;
        while (i < contacts.size()) {
            boolean flag = false;
            List<PhoneNumber> phoneNumbers = contacts.get(i).getPhoneNumbers();
            String phone = phoneNumbers.get(0).getNumber();
            String name = contacts.get(i).getDisplayName();
            Contact contact = new Contact(name, phone);
            flag = participants.add(contact);
            if (flag) {
                chipMaker(contact);
            }
            i++;
        }
    }
    private void setFindById(View view) {
        mEventDescTextInputLayout = view.findViewById(R.id.textInputLayoutNewEventDescription);
        mEventNameTextInputLayout = view.findViewById(R.id.textInputLayoutNewEventName);
        mAddEventButton = view.findViewById(R.id.buttonAdd);
        mContactPickerButton = view.findViewById(R.id.addParticipantsButton);
        mChooseCurrencyButton = view.findViewById(R.id.chooseCurrencyButton);
        mChipGroup = view.findViewById(R.id.chip_group);
    }

    private void currencyPicker() {
        singlePicker = new MyOptionsPickerView(getActivity());
        final ArrayList<String> items = new ArrayList<>(Arrays.asList(CURRENCIES));
        singlePicker.setPicker(items);
        singlePicker.setTitle("Choose Currency");
        singlePicker.setCyclic(false);
        singlePicker.setSelectOptions(0);
        singlePicker.setOnoptionsSelectListener(new MyOptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //  singleTVOptions.setText("Single Picker " + items.get(options1));
                mEventCurrency = items.get(options1);
                mChooseCurrencyButton.setText(items.get(options1));
                Toast.makeText(getActivity(), "" + items.get(options1), Toast.LENGTH_SHORT).show();
                //vMasker.setVisibility(View.GONE);
            }
        });

    }

    private void chipMaker(Contact contact) {
        Chip chip = new Chip(iActivity);
        chip.setText(contact.getName());
        chip.setClickable(true);
        chip.setCloseIconVisible(true);
        chip.setCheckable(false);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chip chip = (Chip) v;
                String name = (String) chip.getText();
                for (Contact participant : participants) {
                    if (Objects.equals(name, participant.getName())) {
                        if (participants.size() > 1) {
                            participants.remove(participant);
                            mChipGroup.removeView(chip);
                            break;
                        } else {
                            Toast.makeText(getActivity(), "At least One Participant Required", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditParticipantFragment editParticipantFragment= new EditParticipantFragment();
                Bundle data = new Bundle();
               // data.putInt(Utility.eventId, eventId);
                FragmentUtility.inflateFragment(editParticipantFragment, getActivity().getSupportFragmentManager(),
                        R.id.fragmentContainer2, true, false, data);
            }
        });
        mChipGroup.addView(chip);
        mChipGroup.setVisibility(View.VISIBLE);
    }
}


