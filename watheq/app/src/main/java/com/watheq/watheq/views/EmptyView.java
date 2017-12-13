package com.watheq.watheq.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.watheq.watheq.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mahmoud.diab on 12/5/2017.
 */

public class EmptyView extends LinearLayout {

    @BindView(R.id.image_empty)
    ImageView emptyImage;
    @BindView(R.id.text_empty)
    TextView emptyText;

    public EmptyView(Context context) {
        super(context);
        init();
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.empty_view, this);
        ButterKnife.bind(this);
    }

    public void setErrorImage(int imageRes) {
        emptyImage.setImageResource(imageRes);
    }

    public void setErrorText(int msgRes) {
        emptyText.setText(msgRes);
    }
}
