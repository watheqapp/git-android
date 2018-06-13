package com.watheq.watheq.orderDetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.BaseModel;
import com.watheq.watheq.model.OrderDetailsModel;
import com.watheq.watheq.model.RateModel;
import com.watheq.watheq.repository.OrderDetailsRepo;
import com.watheq.watheq.repository.OrderDetailsRepoImpl;

/**
 * Created by mahmoud.diab on 1/20/2018.
 */

public class OrderDetailsViewModel extends ViewModel {

    private MediatorLiveData<OrderDetailsModel> orderDetails;
    private MediatorLiveData<BaseModel> baseModel;
    private OrderDetailsRepo orderDetailsRepo;

    public OrderDetailsViewModel() {
        orderDetails = new MediatorLiveData<>();
        baseModel = new MediatorLiveData<>();
        orderDetailsRepo = new OrderDetailsRepoImpl();
    }

    public LiveData<OrderDetailsModel> getOrderDetails(String auth, int orderId, BaseHandlingErrors error) {
        orderDetails.addSource(orderDetailsRepo.getOrderDetails(auth, orderId, error), new Observer<OrderDetailsModel>() {
            @Override
            public void onChanged(@Nullable OrderDetailsModel mainCategoriesResponse) {
                orderDetails.setValue(mainCategoriesResponse);
            }
        });
        return orderDetails;
    }

    public LiveData<BaseModel> rateOrder(String auth, RateModel rateModel, BaseHandlingErrors error) {
        baseModel.addSource(orderDetailsRepo.rateOrder(auth, rateModel, error), new Observer<BaseModel>() {
            @Override
            public void onChanged(@Nullable BaseModel mainCategoriesResponse) {
                baseModel.setValue(mainCategoriesResponse);
            }
        });
        return baseModel;
    }
}
