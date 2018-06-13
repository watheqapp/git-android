package com.watheq.watheq.delegation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.watheq.watheq.model.BaseModel;
import com.watheq.watheq.model.OrderLawyerResponse;
import com.watheq.watheq.model.RegisterDeviceBody;
import com.watheq.watheq.repository.MoveToSupportRepo;
import com.watheq.watheq.repository.MoveToSupportRepoImpl;

/**
 * Created by mahmoud.diab on 2/27/2018.
 */

public class MoveToSupportViewModel extends ViewModel {
    private MoveToSupportRepo moveToSupportRepo;
    private MediatorLiveData<BaseModel> baseModelMediatorLiveData;
    private MediatorLiveData<OrderLawyerResponse> mediatorLiveData;

    public MoveToSupportViewModel() {
        moveToSupportRepo = new MoveToSupportRepoImpl();
        baseModelMediatorLiveData = new MediatorLiveData<>();
        mediatorLiveData = new MediatorLiveData<>();
    }

    LiveData<BaseModel> registerDeviceToken(String auth, final int orderId) {
        baseModelMediatorLiveData.addSource(moveToSupportRepo.moveToSupport(auth, orderId), new Observer<BaseModel>() {
            @Override
            public void onChanged(@Nullable BaseModel baseModel) {
                baseModelMediatorLiveData.setValue(baseModel);
            }
        });
        return baseModelMediatorLiveData;
    }


    LiveData<OrderLawyerResponse> CancelOrder(String auth, final int orderId) {
        mediatorLiveData.addSource(moveToSupportRepo.cancelOrder(auth, orderId), new Observer<OrderLawyerResponse>() {
            @Override
            public void onChanged(@Nullable OrderLawyerResponse baseModel) {
                mediatorLiveData.setValue(baseModel);
            }
        });
        return mediatorLiveData;
    }

}
