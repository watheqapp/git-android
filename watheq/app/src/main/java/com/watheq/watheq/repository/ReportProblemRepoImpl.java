package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.BaseModel;
import com.watheq.watheq.model.ReportProblemModel;
import com.watheq.watheq.network.ApiFactory;
import com.watheq.watheq.network.NetworkFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmoud.diab on 2/12/2018.
 */

public class ReportProblemRepoImpl implements ReportProblemRepo {
    @Override
    public LiveData<BaseModel> reportProblem(String auth, ReportProblemModel reportProblemModel, final BaseHandlingErrors baseHandlingErrors) {
        final MutableLiveData<BaseModel> baseModel = new MutableLiveData<>();
        ApiFactory.createInstance().reportProblem(auth, reportProblemModel).enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.body() != null && response.body().getCode() == 200)
                    baseModel.setValue(response.body());
                else {
                    if (baseHandlingErrors != null) {
                        baseHandlingErrors.onResponseFail(NetworkFactory.getErrors(response.code()));
                    } else
                        throw new RuntimeException("you have to intiListener before calling the api");
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                baseHandlingErrors.onResponseFail(NetworkFactory.getErrors(404));
            }
        });
        return baseModel;
    }
}
