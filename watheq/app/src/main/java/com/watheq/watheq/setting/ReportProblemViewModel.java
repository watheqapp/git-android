package com.watheq.watheq.setting;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.BaseModel;
import com.watheq.watheq.model.ReportProblemModel;
import com.watheq.watheq.repository.ReportProblemRepo;
import com.watheq.watheq.repository.ReportProblemRepoImpl;

/**
 * Created by mahmoud.diab on 2/12/2018.
 */

public class ReportProblemViewModel extends ViewModel {
    private ReportProblemRepo problemRepo;
    private MediatorLiveData mediatorLiveData;

    public ReportProblemViewModel() {
        mediatorLiveData = new MediatorLiveData<>();
        problemRepo = new ReportProblemRepoImpl();
    }

    LiveData<BaseModel> reportProblem(String auth, ReportProblemModel reportProblemModel, BaseHandlingErrors error) {

        mediatorLiveData.addSource(problemRepo.reportProblem(auth, reportProblemModel, error), new Observer<BaseModel>() {
            @Override
            public void onChanged(@Nullable BaseModel mainCategoriesResponse) {
                mediatorLiveData.setValue(mainCategoriesResponse);
            }
        });
        return mediatorLiveData;
    }
}
