package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.AndroidException;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.delegation.DelegationViewModel;
import com.watheq.watheq.model.LoginModelResponse;
import com.watheq.watheq.model.MainCategoriesResponse;
import com.watheq.watheq.network.ApiFactory;
import com.watheq.watheq.network.NetworkFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmoud.diab on 12/2/2017.
 */

public class DelegationRepoImpl implements DelegationRepo {
    @Override
    public LiveData<MainCategoriesResponse> getCategoriesResponse(String auth, final BaseHandlingErrors baseHandlingErrors) {
        final MutableLiveData<MainCategoriesResponse> mutableLiveData = new MutableLiveData<>();
        ApiFactory.createInstance().getCategories(auth).enqueue(new Callback<MainCategoriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MainCategoriesResponse> call, @NonNull Response<MainCategoriesResponse> response) {
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
            public void onFailure(@NonNull Call<MainCategoriesResponse> call, @NonNull Throwable t) {
//                if (baseHandlingErrors != null)
                baseHandlingErrors.onResponseFail(NetworkFactory.getErrors(404));
//                else
//                    throw new RuntimeException("you have to intiListener before calling the api");
            }
        });
        return mutableLiveData;
    }
}
