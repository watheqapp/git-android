package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.watheq.watheq.model.LoginBody;
import com.watheq.watheq.model.LoginModelResponse;
import com.watheq.watheq.network.ApiFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmoud.diab on 11/29/2017.
 */

public class LoginRepoImpl implements LoginRepo {

    public LiveData<LoginModelResponse> loginUser(final LoginBody loginBody) {
        final MutableLiveData<LoginModelResponse> loginModel = new MutableLiveData<>();
        ApiFactory.createInstance().loginUser(loginBody).enqueue(new Callback<LoginModelResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginModelResponse> call, @NonNull Response<LoginModelResponse> response) {
                loginModel.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginModelResponse> call, Throwable t) {
                loginModel.setValue(new LoginModelResponse(t));
            }
        });
        return loginModel;
    }
}
