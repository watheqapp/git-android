package com.watheq.watheq.base;

import com.watheq.watheq.utils.Errors;

/**
 * Created by mahmoud.diab on 12/4/2017.
 */

public interface BaseHandlingErrors {
    void onResponseFail(Errors msg);
}
