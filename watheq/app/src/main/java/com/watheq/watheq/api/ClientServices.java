package com.watheq.watheq.api;

import com.watheq.watheq.model.CompleteProfileBody;
import com.watheq.watheq.model.LoginBody;
import com.watheq.watheq.model.LoginModelResponse;
import com.watheq.watheq.model.MainCategoriesResponse;
import com.watheq.watheq.model.OrderLawyerBody;
import com.watheq.watheq.model.OrderLawyerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by mahmoud.diab on 11/29/2017.
 */

public interface ClientServices {
    String baseUrl = "http://138.197.41.25/watheq/public/";

    @POST("api/client/login")
    Call<LoginModelResponse> loginUser(@Body LoginBody loginBody);

    @POST("api/auth/client/completeProfile")
    Call<LoginModelResponse> completeProfile(@Header("Authorization") String auth, @Body CompleteProfileBody completeProfileBody);

    @GET("api/auth/category/list")
    Call<MainCategoriesResponse> getCategories(@Header("Authorization") String auth);

    @POST("api/auth/order")
    Call<OrderLawyerResponse> orderLiveResponse(@Header("Authorization") String auth, @Body OrderLawyerBody orderLawyerBody);
}
