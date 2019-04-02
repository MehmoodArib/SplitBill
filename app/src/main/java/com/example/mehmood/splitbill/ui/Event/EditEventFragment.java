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
import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Contact;
import com.example.mehmood.splitbill.data.DetailedActivityViewModel;
import com.example.mehmood.splitbill.data.Event;
import com.example.mehmood.splitbill.ui.EditParticipantFragment;
import com.example.mehmood.splitbill.utils.Utilities.FragmentUtility;
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
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class EditEventFragment extends androidx.fragment.app.Fragment implements DetailedEventActivity.ContactInfo, EditParticipantFragment.UpdateContact {

    private static final int CONTACT_PICKER_REQUEST = 0;
    private static final String[] CURRENCIES = {"Rs", "$", "Eu"};
    MyOptionsPickerView singlePicker;

    private TextInputLayout mEventNameTextInputLayout;
    private TextInputLayout mEventDescTextInputLayout;
    private Button mSaveEventButton;
    private Button mChooseCurrencyButton;
    private String mEventCurrency;
    private String mEventName;
    private String mEventDesc;
    private Event mEvent = new Event();
    private HashSet<Contact> participants = new HashSet<>();
    private DetailedActivityViewModel detailedActivityViewModel;
    private Button mContactPickerButton;
    private ChipGroup mChipGroup;
    private Integer eventId;
    private Activity iActivity;
    private Contact olderParticipantValue;
    private Chip olderParticipantChip;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_event, container, false);
        iActivity = getActivity();
        mEventDescTextInputLayout = view.findViewById(R.id.textInputLayoutEditEventDescription);
        mEventNameTextInputLayout = view.findViewById(R.id.textInputLayoutEditEventName);
        mSaveEventButton = view.findViewById(R.id.buttonSave);
        mContactPickerButton = view.findViewById(R.id.addMoreParticipantsButton);
        mChooseCurrencyButton = view.findViewById(R.id.editChooseCurrencyButton);
        mChipGroup = view.findViewById(R.id.edit_chip_group);
        detailedActivityViewModel = ViewModelProviders.of(getActivity()).get(DetailedActivityViewModel.class);
        detailedActivityViewModel.getEvent().observe(getActivity(), new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                mEventDescTextInputLayout.getEditText().setText(event.getEventDesc());
                mEventNameTextInputLayout.getEditText().setText(event.getEventName());
                mChooseCurrencyButton.setText(event.getCurrency());
                participants = event.getParticipantsList();
                eventId = event.getEventId();
                mSetContactsInChipGroup(participants);
            }
        });
        mContactPickerButtonListener();
        mSaveEventButtonListener(getActivity());
        currencyPicker();
        mChooseCurrencyButtonListener();
        return view;
    }

    private void mSetContactsInChipGroup(HashSet<Contact> participants) {
        for (Contact participant : participants) {
            chipMaker(participant);
        }
    }

    private void mChooseCurrencyButtonListener() {
        mChooseCurrencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singlePicker.show();
            }
        });
    }

    private void mSaveEventButtonListener(final FragmentActivity iActivity) {
        mSaveEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEventName()) return;
                if (!validateEventDesc()) return;
                setEvent();
                detailedActivityViewModel.updateEvent(mEvent); //Finally Updating Event to the dataBase
                //pop-ing up this fragment to go back to EventList Fragment.
                androidx.fragment.app.Fragment fragment = iActivity.getSupportFragmentManager().findFragmentByTag(EditEventFragment.class.getSimpleName());
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
            mEventNameTextInputLayout.setError("Field Can't be empty");
            return false;
        }
        if (mEventName.length() > 20) {
            mEventDescTextInputLayout.setError("Maximum 20 characters Allowed");
            return false;
        } else {
            mEventNameTextInputLayout.setError(null);
            return true;
        }
    }

    private boolean validateEventDesc() {
        mEventDesc = mEventDescTextInputLayout.getEditText().getText().toString().trim();
        //TODO: do not use hardcode values for any rules
        if (mEventDesc.length() > 50) {
            mEventDescTextInputLayout.setError("Maximum 50 characters Allowed");
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
        mEvent.setEventId(eventId);
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
                Chip chip = (Chip) v;
                String name = (String) chip.getText();
                for (Contact participant : participants) {
                    if (Objects.equals(name, participant.getName())) {
                        EditParticipantFragment editParticipantFragment = new EditParticipantFragment(EditEventFragment.this);
                        Bundle data = new Bundle();
                        data.putParcelable("contact", participant);
                        olderParticipantValue = participant;
                        olderParticipantChip = chip;
                        FragmentUtility.inflateFragment(editParticipantFragment, getActivity().getSupportFragmentManager(),
                                R.id.fragmentContainer2, true, true, data);
                        break;
                    }
                }
            }
        });
        mChipGroup.addView(chip);
        mChipGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUpdate(Contact contact) {
        participants.add(contact);
        chipMaker(contact);
        mChipGroup.removeView(olderParticipantChip);
        participants.remove(olderParticipantValue);
    }
}


