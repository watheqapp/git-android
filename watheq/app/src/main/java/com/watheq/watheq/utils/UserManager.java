package com.watheq.watheq.utils;

import android.support.annotation.Nullable;

import com.watheq.watheq.cache.UserCache;
import com.watheq.watheq.model.LoginModelResponse;

/**
 * Created by mahmoud.diab on 11/25/2017.
 */

public class UserManager {
    private static UserManager instance;
    private final UserCache userCache;
    private LoginModelResponse currentUser;


    // region  common
    private UserManager() {
        userCache = new UserCache();
    }

    @Nullable
    public LoginModelResponse getUser() {
        if (currentUser == null) {
            currentUser = getUserFromCache();
        }
        return currentUser;
    }

    public LoginModelResponse.Response getUserData() {
        return getUser().getResponse();
    }

    public void logout() {
        // reset user data

        try {
            userCache.deleteUser();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        currentUser = null;


    }

    public String getUserToken() {
        return getUser().getResponse().getToken();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                instance = new UserManager();
            }
        }
        return instance;
    }

    private LoginModelResponse getUserFromCache() {
        try {
            return userCache.getUser();
        } catch (Throwable e) {
            Logger.d(e.getMessage());
            return null;
        }
    }

    public void addUser(LoginModelResponse user) {
        if (user != null) {
            try {
                userCache.save(user);
            } catch (Throwable e) {
                Logger.d(e.getMessage());
            }

            currentUser = user;
        }
    }

}
