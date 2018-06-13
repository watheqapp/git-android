package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.OrdersResponseModel;
import com.watheq.watheq.network.ApiFactory;
import com.watheq.watheq.network.NetworkFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmoud.diab on 12/20/2017.
 */

public class NewOrdersRepoImpl implements NewOrdersRepo {
    @Override
    public LiveData<OrdersResponseModel> getNewOrders(String auth, int page, int limit, final BaseHandlingErrors baseHandlingErrors) {
        final MutableLiveData<OrdersResponseModel> mutableLiveData = new MutableLiveData<>();
        ApiFactory.createInstance().getNewOrders(auth, page, limit).enqueue(new Callback<OrdersResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<OrdersResponseModel> call, @NonNull Response<OrdersResponseModel> response) {
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
            public void onFailure(@NonNull Call<OrdersResponseModel> call, @NonNull Throwable t) {
                baseHandlingErrors.onResponseFail(NetworkFactory.getErrors(404));
            }
        });
        return mutableLiveData;
    }

    @Override
    public LiveData<OrdersResponseModel> getClosedOrders(String auth, int page, int limit, final BaseHandlingErrors baseHandlingErrors) {
        final MutableLiveData<OrdersResponseModel> mutableLiveData = new MutableLiveData<>();
        ApiFactory.createInstance().getClosedOrders(auth, page, limit).enqueue(new Callback<OrdersResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<OrdersResponseModel> call, @NonNull Response<OrdersResponseModel> response) {
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
            public void onFailure(@NonNull Call<OrdersResponseModel> call, @NonNull Throwable t) {
                baseHandlingErrors.onResponseFail(NetworkFactory.getErrors(404));
            }
        });
        return mutableLiveData;
    }

    @Override
    public LiveData<OrdersResponseModel> getOpenedOrders(String auth, int page, int limit, final BaseHandlingErrors baseHandlingErrors) {
        final MutableLiveData<OrdersResponseModel> mutableLiveData = new MutableLiveData<>();
        ApiFactory.createInstance().getOpenedOrders(auth, page, limit).enqueue(new Callback<OrdersResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<OrdersResponseModel> call, @NonNull Response<OrdersResponseModel> response) {
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
            public void onFailure(@NonNull Call<OrdersResponseModel> call, @NonNull Throwable t) {
                baseHandlingErrors.onResponseFail(NetworkFactory.getErrors(404));
            }
        });
        return mutableLiveData;
    }
}
