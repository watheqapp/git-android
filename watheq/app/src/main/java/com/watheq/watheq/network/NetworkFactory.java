package com.watheq.watheq.network;

import com.watheq.watheq.utils.Errors;

/**
 * Created by mahmoud.diab on 12/9/2017.
 */

public class NetworkFactory {
    public static Errors getErrors(int code) {
        if (code == 401) return Errors.UNAUTHORIZE;
        if (code == 404) return Errors.NO_INTERNET;
        if(code == 500) return Errors.SERVER_ERROR;
        return null;
    }
}
