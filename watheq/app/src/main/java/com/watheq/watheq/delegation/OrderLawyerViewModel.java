package com.watheq.watheq.delegation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.OrderLawyerBody;
import com.watheq.watheq.model.OrderLawyerResponse;
import com.watheq.watheq.repository.OrderLawyerRepo;
import com.watheq.watheq.repository.OrderLawyerRepoImpl;

/**
 * Created by mahmoud.diab on 12/18/2017.
 */

public class OrderLawyerViewModel extends ViewModel {
    private MediatorLiveData<OrderLawyerResponse> deMediatorLiveData;
    private OrderLawyerRepo orderLawyerRepo;

    public OrderLawyerViewModel() {
        orderLawyerRepo = new OrderLawyerRepoImpl();
        deMediatorLiveData = new MediatorLiveData<>();
    }

    public LiveData<OrderLawyerResponse> orderLawyer(String auth, OrderLawyerBody orderLawyerBody, BaseHandlingErrors error) {

            deMediatorLiveData.addSource(orderLawyerRepo.orderLiveResponse(auth,orderLawyerBody, error), new Observer<OrderLawyerResponse>() {
                @Override
                public void onChanged(@Nullable OrderLawyerResponse orderLawyerResponse) {
                    deMediatorLiveData.setValue(orderLawyerResponse);
                }
            });
        return deMediatorLiveData;
    }
}
