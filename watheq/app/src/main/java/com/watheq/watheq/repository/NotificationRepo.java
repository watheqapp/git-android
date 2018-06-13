package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.NotificationModel;

import java.util.List;

/**
 * Created by mahmoud.diab on 1/22/2018.
 */

public interface NotificationRepo {

    LiveData<NotificationModel> getNotificationList(String auth, BaseHandlingErrors baseHandlingErrors);
}
