package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.OrderLawyerBody;
import com.watheq.watheq.model.OrderLawyerResponse;

/**
 * Created by mahmoud.diab on 12/18/2017.
 */

public interface OrderLawyerRepo {
    LiveData<OrderLawyerResponse> orderLiveResponse(String auth, OrderLawyerBody orderLawyerBody,BaseHandlingErrors errors);
}
