package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.BaseModel;
import com.watheq.watheq.model.ReportProblemModel;

/**
 * Created by mahmoud.diab on 2/12/2018.
 */

public interface ReportProblemRepo {
    LiveData<BaseModel> reportProblem(String auth, ReportProblemModel reportProblemModel, BaseHandlingErrors baseHandlingErrors);
}
