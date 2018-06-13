package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.BaseModel;
import com.watheq.watheq.model.OrderLawyerResponse;
import com.watheq.watheq.network.ApiFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmoud.diab on 2/27/2018.
 */

public class MoveToSupportRepoImpl implements MoveToSupportRepo {
    @Override
    public LiveData<BaseModel> moveToSupport(String auth, int orderId) {
        final MutableLiveData<BaseModel> registerModel = new MutableLiveData<>();
        ApiFactory.createInstance().moveToSupport(auth, orderId).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                registerModel.setValue(response.body());
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {

            }
        });
        return registerModel;
    }

    @Override
    public LiveData<OrderLawyerResponse> cancelOrder(String auth, int orderId) {
        final MutableLiveData<OrderLawyerResponse> registerModel = new MutableLiveData<>();
        ApiFactory.createInstance().cancelOrder(auth, orderId).enqueue(new Callback<OrderLawyerResponse>() {
            @Override
            public void onResponse(Call<OrderLawyerResponse> call, Response<OrderLawyerResponse> response) {
                if (response.isSuccessful())
                    registerModel.setValue(response.body());
            }

            @Override
            public void onFailure(Call<OrderLawyerResponse> call, Throwable t) {

            }
        });
        return registerModel;
    }
}
