package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.LoginModelResponse;
import com.watheq.watheq.model.OrderLawyerBody;
import com.watheq.watheq.model.OrderLawyerResponse;
import com.watheq.watheq.network.ApiFactory;
import com.watheq.watheq.network.NetworkFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmoud.diab on 12/18/2017.
 */

public class OrderLawyerRepoImpl implements OrderLawyerRepo {
    @Override
    public LiveData<OrderLawyerResponse> orderLiveResponse(String auth, OrderLawyerBody orderLawyerBody, final BaseHandlingErrors errors) {
        final MutableLiveData<OrderLawyerResponse> orderLawyerLiveData = new MutableLiveData<>();
        ApiFactory.createInstance().orderLiveResponse(auth, orderLawyerBody).enqueue(new Callback<OrderLawyerResponse>() {
            @Override
            public void onResponse(Call<OrderLawyerResponse> call, Response<OrderLawyerResponse> response) {
                if (response.body() != null && response.code() == 200)
                    orderLawyerLiveData.setValue(response.body());
                else {
                    if (errors != null) {
                        errors.onResponseFail(NetworkFactory.getErrors(response.code()));
                    } else
                        throw new RuntimeException("you have to intiListener before calling the api");
                }
            }

            @Override
            public void onFailure(Call<OrderLawyerResponse> call, Throwable t) {
                errors.onResponseFail(NetworkFactory.getErrors(404));
            }
        });
        return orderLawyerLiveData;
    }
}
