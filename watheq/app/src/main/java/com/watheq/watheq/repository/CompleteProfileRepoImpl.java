package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.watheq.watheq.model.CompleteProfileBody;
import com.watheq.watheq.model.LoginModelResponse;
import com.watheq.watheq.network.ApiFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmoud.diab on 12/1/2017.
 */

public class CompleteProfileRepoImpl implements CompleteProfileRepo {
    @Override
    public LiveData<LoginModelResponse> completeProfile(String auth, CompleteProfileBody completeProfileBody) {
        final MutableLiveData<LoginModelResponse> completeProfileData = new MutableLiveData<>();
        ApiFactory.createInstance().completeProfile(auth, completeProfileBody).enqueue(new Callback<LoginModelResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginModelResponse> call, @NonNull Response<LoginModelResponse> response) {
                if (response.body() != null)
                    completeProfileData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LoginModelResponse> call, @NonNull Throwable t) {
                completeProfileData.setValue(new LoginModelResponse(t));
            }
        });
        return completeProfileData;
    }
}
