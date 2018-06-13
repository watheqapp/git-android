package com.watheq.watheq.lawerList;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.watheq.watheq.model.LawyerListResponse;
import com.watheq.watheq.model.OrderLawyerResponse;
import com.watheq.watheq.repository.LaywerListRepo;
import com.watheq.watheq.repository.LaywerListRepoImpl;

/**
 * Created by mahmoud.diab on 1/9/2018.
 */

public class LawyerViewModel extends ViewModel {
    private MediatorLiveData<LawyerListResponse> deMediatorLiveData;
    private MediatorLiveData<OrderLawyerResponse> responseMediatorLiveData;
    private LaywerListRepo laywerListRepo;

    public LawyerViewModel() {
        laywerListRepo = new LaywerListRepoImpl();
        deMediatorLiveData = new MediatorLiveData<>();
    }

    LiveData<LawyerListResponse> getLawyerList(String auth, int orderId, int page, int limit) {

        deMediatorLiveData.addSource(laywerListRepo.getLawyerList(auth, orderId, page, limit), new Observer<LawyerListResponse>() {
            @Override
            public void onChanged(@Nullable LawyerListResponse orderLawyerResponse) {
                deMediatorLiveData.setValue(orderLawyerResponse);
            }
        });
        return deMediatorLiveData;
    }

    LiveData<OrderLawyerResponse> orderLawyer(String auth, final int orderId, int lawyerId) {
        responseMediatorLiveData = new MediatorLiveData<>();
        responseMediatorLiveData.addSource(laywerListRepo.getLawyer(auth, orderId, lawyerId), new Observer<OrderLawyerResponse>() {
            @Override
            public void onChanged(@Nullable OrderLawyerResponse orderLawyerResponse) {

                responseMediatorLiveData.setValue(orderLawyerResponse);
            }
        });
        return responseMediatorLiveData;
    }

}
