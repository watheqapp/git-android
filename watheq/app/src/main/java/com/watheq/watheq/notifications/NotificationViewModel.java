package com.watheq.watheq.notifications;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.NotificationModel;
import com.watheq.watheq.repository.NotificationListRepoImpl;
import com.watheq.watheq.repository.NotificationRepo;

import java.util.List;

/**
 * Created by mahmoud.diab on 1/22/2018.
 */

public class NotificationViewModel extends ViewModel {
    private MediatorLiveData<List<NotificationModel.NotificationData>> deMediatorLiveData;
    private NotificationRepo notificationRepo;

    public NotificationViewModel() {
        notificationRepo = new NotificationListRepoImpl();
        deMediatorLiveData = new MediatorLiveData<>();
    }

    LiveData<List<NotificationModel.NotificationData>> getNotificationList(String auth, BaseHandlingErrors baseHandlingErrors) {

        deMediatorLiveData.addSource(notificationRepo.getNotificationList(auth, baseHandlingErrors), new Observer<NotificationModel>() {
            @Override
            public void onChanged(@Nullable NotificationModel orderLawyerResponse) {
                deMediatorLiveData.setValue(orderLawyerResponse.getData());
            }
        });
        return deMediatorLiveData;
    }
}
