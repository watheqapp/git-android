package com.watheq.watheq.authentication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.watheq.watheq.model.CompleteProfileBody;
import com.watheq.watheq.model.LoginModelResponse;
import com.watheq.watheq.repository.CompleteProfileRepo;
import com.watheq.watheq.repository.CompleteProfileRepoImpl;

/**
 * Created by mahmoud.diab on 12/1/2017.
 */

public class CompleteProfileViewModel extends ViewModel {
    private MediatorLiveData<LoginModelResponse> completeProfileLvDate;
    private CompleteProfileRepo completeProfileRepo;

    public CompleteProfileViewModel() {
        completeProfileRepo = new CompleteProfileRepoImpl();
        completeProfileLvDate = new MediatorLiveData<>();
    }

    LiveData<LoginModelResponse> completeProfile(String auth, CompleteProfileBody completeProfileBody) {
        completeProfileLvDate.addSource(completeProfileRepo.completeProfile(auth, completeProfileBody), new Observer<LoginModelResponse>() {
            @Override
            public void onChanged(@Nullable LoginModelResponse completeProfileResponse) {
                completeProfileLvDate.setValue(completeProfileResponse);
            }
        });
        return completeProfileLvDate;
    }
}
