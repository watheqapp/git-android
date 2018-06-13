package com.watheq.watheq.thread;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.watheq.watheq.Constants;
import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseChatActivity;
import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.beans.Message;
import com.watheq.watheq.beans.User;
import com.watheq.watheq.model.BaseModel;
import com.watheq.watheq.model.Lawer;
import com.watheq.watheq.model.LoginModelResponse;
import com.watheq.watheq.model.OrderDetailsModel;
import com.watheq.watheq.model.RateModel;
import com.watheq.watheq.orderDetails.OrderDetailsViewModel;
import com.watheq.watheq.utils.App;
import com.watheq.watheq.utils.Errors;
import com.watheq.watheq.utils.UserManager;
import com.watheq.watheq.views.EmptyStateRecyclerView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static android.view.View.VISIBLE;

public class ThreadActivity extends BaseChatActivity implements TextWatcher, BaseHandlingErrors {

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.activity_thread_messages_recycler)
    EmptyStateRecyclerView messagesRecycler;
    @BindView(R.id.activity_thread_send_fab)
    TextView sendFab;
    @BindView(R.id.activity_thread_input_edit_text)
    EditText inputEditText;
    @BindView(R.id.activity_thread_empty_view)
    TextView emptyView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    //    @BindView(R.id.finish_order)
//    TextView finishOrder;
    @BindView(R.id.rating_bar)
    MaterialRatingBar ratingBar;
    @BindView(R.id.rate_group)
    View rateGroup;
    @BindView(R.id.chat_icons)
    View chatIcons;
    private MessagesAdapter adapter;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String userUid;
    private Lawer lawer;
    private boolean emptyInput;

    private User user;
    //    private FirebaseUser owner;
    private LoginModelResponse.Response owner;
    private FirebaseRecyclerOptions<Message> options;
    private String orderId;
    protected App mMyApp;
    private boolean isClosedOrder;
    private OrderDetailsViewModel orderDetailsViewModel;
    private boolean fromList;
    private String phone;
    private double lat, lng, myLat, myLong;

    private final Observer<BaseModel> rateOrder = new Observer<BaseModel>() {
        @Override
        public void onChanged(@Nullable BaseModel baseModel) {

            if (baseModel.getCode() == 200) {
                showSnackBarNotification(getString(R.string.your_request_submited));
            }
        }
    };

    private final Observer<OrderDetailsModel> getOrderDetails = new Observer<OrderDetailsModel>() {
        @Override
        public void onChanged(@Nullable OrderDetailsModel orderLawyerResponse) {
            if (orderLawyerResponse.getData() != null) {

                phone = String.valueOf(orderLawyerResponse.getData().getLawyer().getPhone());
                lat = orderLawyerResponse.getData().getClient().getLat();
                lng = orderLawyerResponse.getData().getClient().getLong();
                myLat = orderLawyerResponse.getData().getClientLat();
                myLong = orderLawyerResponse.getData().getClientLong();
                if (!isClosedOrder)
                    emptyView.setText(String.format("%s %s", getString(R.string.chat_msg),
                            orderLawyerResponse.getData().getLawyer().getName()));
            } else {
                chatIcons.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        ButterKnife.bind(this);
        mMyApp = (App) this.getApplicationContext();
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        chatIcons.setVisibility(VISIBLE);
        orderId = getIntent().getStringExtra("orderId");
        fromList = getIntent().getBooleanExtra("fromList", false);
        toolbarTitle.setText(getString(R.string.request_num) + " " + orderId);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        orderDetailsViewModel = ViewModelProviders.of(this).get(OrderDetailsViewModel.class);
        isClosedOrder = getIntent().getBooleanExtra("isClosed", false);

        if (isClosedOrder) {
            chatIcons.setVisibility(View.GONE);
            inputEditText.setEnabled(false);
            sendFab.setBackground(ContextCompat.getDrawable(this, R.drawable.send_disable));
            emptyView.setText(getString(R.string.closed_orders));
            rateGroup.setVisibility(VISIBLE);
            inputEditText.setHint("");
        }
//        lawer = getIntent().getParcelableExtra(Constants.USER_ID_EXTRA);
//        userUid = String.valueOf(lawer.getId());
//        toolbarTitle.setText(lawer.getName());

        ratingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                RateModel rateModel = new RateModel();
                rateModel.setOrderId(orderId);
                rateModel.setRate(String.valueOf(rating));
                orderDetailsViewModel.rateOrder(UserManager.getInstance().getUserToken(), rateModel
                        , ThreadActivity.this).observe(ThreadActivity.this, rateOrder);
            }
        });

        if (getIntent().getParcelableExtra(Constants.USER_ID_EXTRA) != null) {
            lawer = getIntent().getParcelableExtra(Constants.USER_ID_EXTRA);
            userUid = String.valueOf(lawer.getId());
            if (!isClosedOrder)
                emptyView.setText(String.format("%s %s", getString(R.string.chat_msg), lawer.getName()));
        } else {
            userUid = getIntent().getExtras().getString("senderId");
        }

        orderDetailsViewModel.getOrderDetails(UserManager.getInstance().getUserToken()
                , Integer.parseInt(orderId), this)
                .observe(this, getOrderDetails);

        sendFab.requestFocus();

        loadUserDetails();
        initializeAuthListener();
        initializeInteractionListeners();
    }

    @OnClick(R.id.call)
    void onCallClicked() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    @OnClick(R.id.location)
    void onLocationClicked() {
        String
                uri = "http://maps.google.com/maps?f=d&hl=en" + "&daddr=" +
                myLat + "," +
                myLong;

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));

        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    private void initializeInteractionListeners() {
        inputEditText.addTextChangedListener(this);
    }

    private void addUserToDatabase(Lawer firebaseUser) {
        User user = new User(
                firebaseUser.getName(),
                firebaseUser.getPhone(),
                String.valueOf(firebaseUser.getId()), "android"
        );

        mDatabase.child("users")
                .child(user.getUid()).setValue(user);

        String instanceId = FirebaseInstanceId.getInstance().getToken();
        if (instanceId != null) {
            mDatabase.child("users")
                    .child(String.valueOf(firebaseUser.getId()))
                    .child("instanceId")
                    .setValue(instanceId);
        }
    }

    private void loadUserDetails() {
        DatabaseReference userReference = mDatabase
                .child("users")
                .child(userUid);

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                initializeMessagesRecycler();
                displayUserDetails();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ThreadActivity.this, R.string.error_loading_user, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    private void initializeAuthListener() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                owner = UserManager.getInstance().getUserData();
                if (owner != null) {
                    initializeMessagesRecycler();

                    Log.d("@@@@", "thread:signed_in:" + owner.getId());
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void initializeMessagesRecycler() {
        if (user == null || owner == null) {
            Log.d("@@@@", "initializeMessagesRecycler: User:" + user + " Owner:" + owner);
            return;
        }
        Query messagesQuery = mDatabase
                .child("messages")
                .child(String.valueOf(owner.getId()))
                .child(String.valueOf(owner.getId()) + userUid + orderId)
                .orderByChild("negatedTimestamp");
        options = new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(messagesQuery, Message.class)
                .build();
        adapter = new MessagesAdapter(options, this, String.valueOf(owner.getId()), messagesQuery);
        messagesRecycler.setAdapter(adapter);
        messagesRecycler.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        adapter.startListening();
        messagesRecycler.setEmptyView(emptyView);
        messagesRecycler.getAdapter().registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                messagesRecycler.smoothScrollToPosition(0);
            }
        });
    }

    @OnClick(R.id.activity_thread_send_fab)
    public void onClick() {
        if (user == null || owner == null) {
            Log.d("@@@@", "onSendClick: User:" + user + " Owner:" + owner);
            return;
        }
        if (TextUtils.isEmpty(inputEditText.getText().toString()))
            return;
        long timestamp = new Date().getTime() / 1000;
        long dayTimestamp = getDayTimestamp(timestamp);
        String body = inputEditText.getText().toString().trim();
        String ownerUid = String.valueOf(owner.getId());
        String userUid = user.getUid();
        Message message =
                new Message(timestamp, body, ownerUid, userUid, -timestamp, dayTimestamp, owner.getName(), orderId);
        mDatabase
                .child("notifications")
                .child("messages")
                .push()
                .setValue(message);
        mDatabase
                .child("messages")
                .child(userUid)
                .child(ownerUid + userUid + orderId)
                .push()
                .setValue(message);
        if (!userUid.equals(ownerUid)) {
            mDatabase
                    .child("messages")
                    .child(ownerUid)
                    .child(ownerUid + userUid + orderId)
                    .push()
                    .setValue(message);
        }
        inputEditText.setText("");
    }

    @Override
    protected void displayLoadingState() {
        //was considering a progress bar but firebase offline database makes it unnecessary

        //TransitionManager.beginDelayedTransition(editorParent);
//        progress.setVisibility(isLoading ? VISIBLE : INVISIBLE);
        //displayInputState();
    }

    private void displayInputState() {
        //inputEditText.setEnabled(!isLoading);
        sendFab.setEnabled(!emptyInput && !isLoading);
        //sendFab.setImageResource(isLoading ? R.color.colorTransparent : R.drawable.ic_send);
    }

    private long getDayTimestamp(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTimeInMillis();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void displayUserDetails() {
        //todo[improvement]: maybe display the picture in the toolbar.. WhatsApp style
//        toolbar.setTitle(user.getDisplayName());
        //toolbar.setSubtitle(user.getEmail());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        emptyInput = s.toString().trim().isEmpty();
        displayInputState();
    }

    protected void onResume() {
        super.onResume();
        mMyApp.setCurrentActivity(this);
    }

    protected void onPause() {
        mMyApp.setCurrentActivity(new Activity());
        super.onPause();
    }

    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void clearReferences() {
        Activity currActivity = mMyApp.getCurrentActivity();
        if (this.equals(currActivity))
            mMyApp.setCurrentActivity(null);
    }

    @Override
    public void onResponseFail(Errors msg) {

    }

    @Override
    public void onBackPressed() {
        if (fromList) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        }
    }
}
