package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.BaseModel;
import com.watheq.watheq.model.OrderLawyerResponse;

/**
 * Created by mahmoud.diab on 2/27/2018.
 */

public interface MoveToSupportRepo {
    LiveData<BaseModel> moveToSupport(String auth, int orderId);
    LiveData<OrderLawyerResponse> cancelOrder(String auth, int orderId);
}
