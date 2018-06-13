package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;

import com.watheq.watheq.model.BaseModel;
import com.watheq.watheq.model.LoginBody;
import com.watheq.watheq.model.LoginModelResponse;
import com.watheq.watheq.model.RegisterDeviceBody;

/**
 * Created by mahmoud.diab on 11/29/2017.
 */

public interface LoginRepo {
    LiveData<LoginModelResponse> loginUser(LoginBody loginBody);
    LiveData<BaseModel> registerToken(String auth, RegisterDeviceBody registerDeviceBody);
}
