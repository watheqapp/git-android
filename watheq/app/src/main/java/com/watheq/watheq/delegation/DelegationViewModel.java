package com.watheq.watheq.delegation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.MainCategoriesResponse;
import com.watheq.watheq.repository.DelegationRepo;
import com.watheq.watheq.repository.DelegationRepoImpl;

/**
 * Created by mahmoud.diab on 12/2/2017.
 */

public class DelegationViewModel extends ViewModel {
    private MediatorLiveData<MainCategoriesResponse> deMediatorLiveData;
    private DelegationRepo delegationRepo;

    public DelegationViewModel() {
        delegationRepo = new DelegationRepoImpl();
        deMediatorLiveData = new MediatorLiveData<>();
    }

    public LiveData<MainCategoriesResponse> getCategories(String auth,BaseHandlingErrors error) {
        if (deMediatorLiveData.getValue() != null)
            deMediatorLiveData.postValue(deMediatorLiveData.getValue());
        else
            deMediatorLiveData.addSource(delegationRepo.getCategoriesResponse(auth, error), new Observer<MainCategoriesResponse>() {
                @Override
                public void onChanged(@Nullable MainCategoriesResponse mainCategoriesResponse) {
                    deMediatorLiveData.setValue(mainCategoriesResponse);
                }
            });
        return deMediatorLiveData;
    }
}
