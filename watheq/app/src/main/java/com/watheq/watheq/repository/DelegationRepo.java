package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.delegation.DelegationViewModel;
import com.watheq.watheq.model.MainCategoriesResponse;

/**
 * Created by mahmoud.diab on 12/2/2017.
 */

public interface DelegationRepo {
    LiveData<MainCategoriesResponse> getCategoriesResponse(String auth, BaseHandlingErrors errors);
}
