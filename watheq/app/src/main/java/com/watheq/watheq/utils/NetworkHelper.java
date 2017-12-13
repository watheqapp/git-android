package com.watheq.watheq.utils;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmoud.diab on 12/4/2017.
 */

public class NetworkHelper {

    public static <T> Callback getCallBacks(final MutableLiveData<T> mutableLiveData) {
        return new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.body() != null)
                    mutableLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
//                completeProfileData.setValue(new LoginModelResponse(t));
            }
        };
    }
}
