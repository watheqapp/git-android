package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;

import com.watheq.watheq.model.LoginBody;
import com.watheq.watheq.model.LoginModelResponse;

/**
 * Created by mahmoud.diab on 11/29/2017.
 */

public interface LoginRepo {
    LiveData<LoginModelResponse> loginUser(LoginBody loginBody);
}
