package com.watheq.watheq.thread;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.transition.TransitionManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.watheq.watheq.R;
import com.watheq.watheq.beans.Message;


import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/*
 * Created by Mahmoud on 3/13/2017.
 */

class MessagesAdapter extends FirebaseRecyclerAdapter<Message, MessagesAdapter.MessageViewHolder> {

    private static final int VIEW_TYPE_SENT = 0;
    private static final int VIEW_TYPE_SENT_WITH_DATE = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;
    private static final int VIEW_TYPE_RECEIVED_WITH_DATE = 3;

    private final String ownerUid;
    private final Context context;
    private ArrayList<Integer> selectedPositions;
    private int selectedPos = -1;

    MessagesAdapter(FirebaseRecyclerOptions<Message> options, Context context, String ownerUid, Query ref) {
        super(options);
        this.context = context;
        this.ownerUid = ownerUid;
        selectedPositions = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        Message message = getItem(position);
        if (message.getFrom().equals(ownerUid)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case VIEW_TYPE_SENT:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_sent, parent, false);
                break;
            case VIEW_TYPE_SENT_WITH_DATE:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_sent, parent, false);
                break;
            case VIEW_TYPE_RECEIVED:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_received, parent, false);
                break;
            case VIEW_TYPE_RECEIVED_WITH_DATE:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_received, parent, false);
                break;
            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_sent, parent, false);
        }
        return new MessageViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull Message model) {
        holder.setMessage(model);
    }

    class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.item_message_body_text_view)
        TextView itemMessageBodyTextView;
        @BindView(R.id.item_message_parent)
        LinearLayout itemMessageParent;
        @BindView(R.id.item_message_date_text_view)
        TextView itemMessageDateTextView;

        MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemMessageBodyTextView.setOnClickListener(this);
            itemMessageBodyTextView.setOnLongClickListener(this);
        }

        void setMessage(Message message) {
            int viewType = MessagesAdapter.this.getItemViewType(getLayoutPosition());
            itemMessageBodyTextView.setText(message.getBody());
            boolean shouldHideDate = viewType == VIEW_TYPE_SENT || viewType == VIEW_TYPE_RECEIVED;
            itemMessageDateTextView.setVisibility(shouldHideDate ? GONE : VISIBLE);
            if (!shouldHideDate) {
//                itemMessageDateTextView.setText(getDatePretty(message.getTimestamp(), true));
            }

        }

        @Override
        public void onClick(View v) {
            //if (selectedPositions.contains(getLayoutPosition())) {
            //    selectedPositions.remove(Integer.valueOf(getLayoutPosition()));
            //    setDateVisibility(GONE);
            //} else {
            //    selectedPositions.add(getLayoutPosition());
            //    setDateVisibility(VISIBLE);
            //}
        }

//        private void setDateVisibility(int visibility) {
//            TransitionManager.beginDelayedTransition(itemMessageParent);
//            itemMessageDateTextView.setVisibility(visibility);
//        }

        @Override
        public boolean onLongClick(View v) {
            Message message = getItem(getLayoutPosition());
            if (message != null) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(
                        context.getString(R.string.clipboard_title_copied_message),
                        message.getBody());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, R.string.message_message_copied, Toast.LENGTH_SHORT).show();
                selectedPos = getAdapterPosition();
                notifyDataSetChanged();
            }
            return true;
        }
    }

    private String getDatePretty(long timestamp, boolean showTimeOfDay) {
        DateTime yesterdayDT = new DateTime(DateTime.now().getMillis() - 1000 * 60 * 60 * 24);
        yesterdayDT = yesterdayDT.withTime(0, 0, 0, 0);
        Interval today = new Interval(DateTime.now().withTimeAtStartOfDay(), Days.ONE);
        Interval yesterday = new Interval(yesterdayDT, Days.ONE);
        DateTimeFormatter timeFormatter = DateTimeFormat.shortTime();
        DateTimeFormatter dateFormatter = DateTimeFormat.shortDate();
        if (today.contains(timestamp)) {
            if (showTimeOfDay) {
                return timeFormatter.print(timestamp);
            } else {
                return context.getString(R.string.today);
            }
        } else if (yesterday.contains(timestamp)) {
            return context.getString(R.string.yesterday);
        } else {
            return dateFormatter.print(timestamp);
        }
    }
}
