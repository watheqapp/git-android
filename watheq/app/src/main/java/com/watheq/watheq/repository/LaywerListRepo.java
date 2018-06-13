package com.watheq.watheq.repository;

import android.arch.lifecycle.LiveData;

import com.watheq.watheq.model.LawyerListResponse;
import com.watheq.watheq.model.OrderLawyerResponse;

/**
 * Created by mahmoud.diab on 1/9/2018.
 */

public interface LaywerListRepo {
    LiveData<LawyerListResponse> getLawyerList(String auth, int orderId, int page, int limit);

    LiveData<OrderLawyerResponse> getLawyer(String auth, int orderId, int lawyerId);
}
