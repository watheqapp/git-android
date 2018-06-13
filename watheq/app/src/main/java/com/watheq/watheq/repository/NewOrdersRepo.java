package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.OrdersResponseModel;

/**
 * Created by mahmoud.diab on 12/20/2017.
 */

public interface NewOrdersRepo {
    LiveData<OrdersResponseModel> getNewOrders(String auth, int page, int limit, BaseHandlingErrors errors);
    LiveData<OrdersResponseModel> getClosedOrders(String auth, int page, int limit, BaseHandlingErrors errors);
    LiveData<OrdersResponseModel> getOpenedOrders(String auth, int page, int limit, BaseHandlingErrors errors);
}
