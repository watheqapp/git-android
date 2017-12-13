package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;

import com.watheq.watheq.model.CompleteProfileBody;
import com.watheq.watheq.model.LoginModelResponse;

/**
 * Created by mahmoud.diab on 12/1/2017.
 */

public interface CompleteProfileRepo {
    LiveData<LoginModelResponse> completeProfile(String auth, CompleteProfileBody completeProfileBody);
}
