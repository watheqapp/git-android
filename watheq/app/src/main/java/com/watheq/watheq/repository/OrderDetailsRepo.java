package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.BaseModel;
import com.watheq.watheq.model.OrderDetailsModel;
import com.watheq.watheq.model.RateModel;

/**
 * Created by mahmoud.diab on 1/19/2018.
 */

public interface OrderDetailsRepo {
    LiveData<OrderDetailsModel> getOrderDetails(String auth, int orderId, BaseHandlingErrors errors);

    LiveData<BaseModel> rateOrder(String auth, RateModel rateModel, BaseHandlingErrors errors);
}
