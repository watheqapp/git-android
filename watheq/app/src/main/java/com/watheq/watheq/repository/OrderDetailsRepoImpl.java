package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.BaseModel;
import com.watheq.watheq.model.OrderDetailsModel;
import com.watheq.watheq.model.RateModel;
import com.watheq.watheq.network.ApiFactory;
import com.watheq.watheq.network.NetworkFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmoud.diab on 1/19/2018.
 */

public class OrderDetailsRepoImpl implements OrderDetailsRepo {

    @Override
    public LiveData<OrderDetailsModel> getOrderDetails(String auth, int orderId, final BaseHandlingErrors baseHandlingErrors) {
        final MutableLiveData<OrderDetailsModel> mutableLiveData = new MutableLiveData<>();
        ApiFactory.createInstance().getOrderDetails(auth, orderId).enqueue(new Callback<OrderDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<OrderDetailsModel> call, @NonNull Response<OrderDetailsModel> response) {
                if (response.body() != null && response.code() == 200)
                    mutableLiveData.setValue(response.body());
                else {
                    if (baseHandlingErrors != null) {
                        baseHandlingErrors.onResponseFail(NetworkFactory.getErrors(response.code()));
                    } else
                        throw new RuntimeException("you have to intiListener before calling the api");
                }
            }

            @Override
            public void onFailure(@NonNull Call<OrderDetailsModel> call, @NonNull Throwable t) {
                baseHandlingErrors.onResponseFail(NetworkFactory.getErrors(404));
            }
        });
        return mutableLiveData;
    }

    @Override
    public LiveData<BaseModel> rateOrder(String auth, RateModel rateModel, final BaseHandlingErrors baseHandlingErrors) {
        final MutableLiveData<BaseModel> mutableLiveData = new MutableLiveData<>();
        ApiFactory.createInstance().rateOrder(auth, rateModel).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(@NonNull Call<BaseModel> call, @NonNull Response<BaseModel> response) {
                if (response.body() != null && response.code() == 200)
                    mutableLiveData.setValue(response.body());
                else {
                    if (baseHandlingErrors != null) {
                        baseHandlingErrors.onResponseFail(NetworkFactory.getErrors(response.code()));
                    } else
                        throw new RuntimeException("you have to intiListener before calling the api");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseModel> call, @NonNull Throwable t) {
                baseHandlingErrors.onResponseFail(NetworkFactory.getErrors(404));
            }
        });
        return mutableLiveData;
    }
}
