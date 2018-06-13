package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.watheq.watheq.model.LawyerListResponse;
import com.watheq.watheq.model.LoginModelResponse;
import com.watheq.watheq.model.OrderLawyerResponse;
import com.watheq.watheq.model.OrdersResponseModel;
import com.watheq.watheq.network.ApiFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmoud.diab on 1/9/2018.
 */

public class LaywerListRepoImpl implements LaywerListRepo {
    @Override
    public LiveData<LawyerListResponse> getLawyerList(String auth, int orderId, int page, int limit) {
        final MutableLiveData<LawyerListResponse> liveData = new MutableLiveData<>();
        ApiFactory.createInstance().getLawerList(auth, orderId, page, limit).enqueue(new Callback<LawyerListResponse>() {
            @Override
            public void onResponse(@NonNull Call<LawyerListResponse> call, @NonNull Response<LawyerListResponse> response) {
                if (response.body() != null)
                    liveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LawyerListResponse> call, @NonNull Throwable t) {
                liveData.setValue(new LawyerListResponse(t));
            }
        });
        return liveData;
    }

    @Override
    public LiveData<OrderLawyerResponse> getLawyer(String auth, int orderId, int lawyerId) {
        final MutableLiveData<OrderLawyerResponse> liveData = new MutableLiveData<>();
        ApiFactory.createInstance().orderLawyer(auth, orderId, lawyerId).enqueue(new Callback<OrderLawyerResponse>() {
            @Override
            public void onResponse(@NonNull Call<OrderLawyerResponse> call, @NonNull Response<OrderLawyerResponse> response) {
                if (response.body() != null)
                    liveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OrderLawyerResponse> call, @NonNull Throwable t) {

            }
        });
        return liveData;
    }
}
