package com.example.mehmood.splitbill.ui.Event;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Event;
import com.example.mehmood.splitbill.data.EventViewModel;
import com.example.mehmood.splitbill.ui.ChooseAddOptionDialogFragment;
import com.example.mehmood.splitbill.utils.Adapters.EventAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;
/**Fragment Launched by MainActivity.
 * This Fragment List All the Events User is participant in.
 * and an Add event button*/
public class EventListFragment extends Fragment {
    private final EventAdapter mEventAdapter = new EventAdapter();
    private FloatingActionButton mAddEventFloatingActionButton;
    private EventViewModel mEventViewModel;
    private LifecycleOwner myActivity;
    private RecyclerView mEventListRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        setHasOptionsMenu(true);
        myActivity = getActivity();

        mAddEventFloatingActionButton = view.findViewById(R.id.add_button);
        mAddEventFloatingActionButtonListener();

        mEventListRecyclerView = view.findViewById(R.id.my_recycler_view);
        mEventListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mEventListRecyclerView.setHasFixedSize(true);
        mEventListRecyclerView.setAdapter(mEventAdapter);

        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        mEventViewModel.getAllEvents().observe(myActivity, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                mEventAdapter.submitList(events);
            }
        });
        itemTouchHelper();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.event_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAll:
                EventViewModel eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
                eventViewModel.deleteAllEvent();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void mAddEventFloatingActionButtonListener() {
        mAddEventFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = (getActivity()).getSupportFragmentManager().beginTransaction();
                ChooseAddOptionDialogFragment dialog = new ChooseAddOptionDialogFragment();
                dialog.show(ft, "TAG");
            }
        });
    }

    private void eventAdapterListener() {
        mEventAdapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event event) {
                Intent intent = new Intent(getActivity(), DetailedEventActivity.class);
                intent.putExtra("EventId", event.getEventId());
                startActivity(intent);

            }
        });
    }

    private void itemTouchHelper() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                mEventAdapter.getEventAt(viewHolder.getAdapterPosition());
                mEventViewModel.deleteEvent(mEventAdapter.getEventAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(mEventListRecyclerView);
        eventAdapterListener();
    }
}
