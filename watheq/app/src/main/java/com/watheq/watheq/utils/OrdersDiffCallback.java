package com.watheq.watheq.utils;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.watheq.watheq.model.Data;

import java.util.List;

public class OrdersDiffCallback extends DiffUtil.Callback {

    private List<Data> mOldOrdersList;
    private List<Data> mNewOrdersList;

    public OrdersDiffCallback(List<Data> oldEmployeeList, List<Data> newEmployeeList) {
        this.mOldOrdersList = oldEmployeeList;
        this.mNewOrdersList = newEmployeeList;
    }

    public void setNewOrdersList(List<Data> newEmployeeList){
        this.mNewOrdersList = newEmployeeList;
    }

    public void setOldOrdersList(List<Data> oldEmployeeList){
        this.mOldOrdersList = oldEmployeeList;
    }

    @Override
    public int getOldListSize() {
        return mOldOrdersList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewOrdersList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldOrdersList.get(oldItemPosition).getId() == mNewOrdersList.get(
                newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Data oldOrders = mOldOrdersList.get(oldItemPosition);
        final Data newOrders = mNewOrdersList.get(newItemPosition);

        return oldOrders.getId() == newOrders.getId();
    }
}
