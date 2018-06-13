package com.watheq.watheq.myOrder;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.OrderLawyerResponse;
import com.watheq.watheq.model.OrdersResponseModel;
import com.watheq.watheq.repository.NewOrdersRepo;
import com.watheq.watheq.repository.NewOrdersRepoImpl;

/**
 * Created by mahmoud.diab on 12/20/2017.
 */

public class OrdersViewModel extends ViewModel {
    private MediatorLiveData<OrdersResponseModel> newOrdersModel;
    private NewOrdersRepo delegationRepo;

    public OrdersViewModel() {
        delegationRepo = new NewOrdersRepoImpl();
        newOrdersModel = new MediatorLiveData<>();
    }


    LiveData<OrdersResponseModel> getNewOrders(String auth, int page, int limit, BaseHandlingErrors error) {
//        if (newOrdersModel.getValue() != null)
//            newOrdersModel.postValue(newOrdersModel.getValue());
//        else
            newOrdersModel.addSource(delegationRepo.getNewOrders(auth, page, limit, error), new Observer<OrdersResponseModel>() {
                @Override
                public void onChanged(@Nullable OrdersResponseModel mainCategoriesResponse) {
                    newOrdersModel.setValue(mainCategoriesResponse);
                }
            });
        return newOrdersModel;
    }

    public LiveData<OrdersResponseModel> getClosedOrders(String auth, int page, int limit, BaseHandlingErrors error) {
//        if (newOrdersModel.getValue() != null)
//            newOrdersModel.postValue(newOrdersModel.getValue());
//        else
            newOrdersModel.addSource(delegationRepo.getClosedOrders(auth, page, limit, error), new Observer<OrdersResponseModel>() {
                @Override
                public void onChanged(@Nullable OrdersResponseModel mainCategoriesResponse) {
                    newOrdersModel.setValue(mainCategoriesResponse);
                }
            });
        return newOrdersModel;
    }

    LiveData<OrdersResponseModel> getOpenedOrders(String auth, int page, int limit, BaseHandlingErrors error) {
//        if (newOrdersModel.getValue() != null)
//            newOrdersModel.postValue(newOrdersModel.getValue());
//        else
            newOrdersModel.addSource(delegationRepo.getOpenedOrders(auth, page, limit, error), new Observer<OrdersResponseModel>() {
                @Override
                public void onChanged(@Nullable OrdersResponseModel mainCategoriesResponse) {
                    newOrdersModel.setValue(mainCategoriesResponse);
                }
            });
        return newOrdersModel;
    }
}
