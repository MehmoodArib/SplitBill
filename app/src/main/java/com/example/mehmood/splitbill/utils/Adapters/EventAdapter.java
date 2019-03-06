package com.example.mehmood.splitbill.utils.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Event;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**Adapter for creating the EventList used In EventList Fragment*/
public class EventAdapter extends ListAdapter<Event, EventAdapter.EventHolder> {
    private static final DiffUtil.ItemCallback<Event> Diff_CALLBACK = new DiffUtil.ItemCallback<Event>() {
        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getEventId().equals(newItem.getEventId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getEventName().equals(newItem.getEventName())
                    && oldItem.getEventDesc().equals(newItem.getEventDesc());
        }
    };
    private OnItemClickListener listener;

    public EventAdapter() {
        super(Diff_CALLBACK);
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event, parent, false);
        return new EventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        Event currentEvent = getItem(position);
        holder.textViewTitle.setText(currentEvent.getEventName());
        holder.textViewDesc.setText(currentEvent.getEventDesc());

    }


    public Event getEventAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    class EventHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDesc;

        private EventHolder(@NonNull View itemView) {
            super(itemView);
            textViewDesc = itemView.findViewById(R.id.event_desc);
            textViewTitle = itemView.findViewById(R.id.event_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }
}
