package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.NotificationModel;
import com.watheq.watheq.network.ApiFactory;
import com.watheq.watheq.network.NetworkFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmoud.diab on 1/22/2018.
 */

public class NotificationListRepoImpl implements NotificationRepo {
    @Override
    public LiveData<NotificationModel> getNotificationList(String auth, final BaseHandlingErrors baseHandlingErrors) {
        final MutableLiveData<NotificationModel> liveData = new MutableLiveData<>();
        ApiFactory.createInstance().getNotificationList(auth).enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(@NonNull Call<NotificationModel> call, @NonNull Response<NotificationModel> response) {
                if (response.body() != null)
                    liveData.setValue(response.body());
                else {
                    if (baseHandlingErrors != null) {
                        baseHandlingErrors.onResponseFail(NetworkFactory.getErrors(response.code()));
                    } else
                        throw new RuntimeException("you have to intiListener before calling the api");
                }
            }

            @Override
            public void onFailure(@NonNull Call<NotificationModel> call, @NonNull Throwable t) {
                baseHandlingErrors.onResponseFail(NetworkFactory.getErrors(404));
            }
        });
        return liveData;
    }
}
