package com.watheq.watheq.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


/**
 * Created by mahmoud.diab on 11/23/2017.
 */

public class AnimationHelper {

    private AnimationHelper(){

    }

    public static void animateFadeIn(Context context, final View view) {
        Animation animFadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        view.setAnimation(animFadeIn);
        view.setVisibility(View.VISIBLE);

    }

}
