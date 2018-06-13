package com.watheq.watheq.api;

import com.watheq.watheq.model.BaseModel;
import com.watheq.watheq.model.CompleteProfileBody;
import com.watheq.watheq.model.LawyerListResponse;
import com.watheq.watheq.model.LoginBody;
import com.watheq.watheq.model.LoginModelResponse;
import com.watheq.watheq.model.MainCategoriesResponse;
import com.watheq.watheq.model.NotificationModel;
import com.watheq.watheq.model.OrderDetailsModel;
import com.watheq.watheq.model.OrderLawyerBody;
import com.watheq.watheq.model.OrderLawyerResponse;
import com.watheq.watheq.model.OrdersResponseModel;
import com.watheq.watheq.model.RateModel;
import com.watheq.watheq.model.RegisterDeviceBody;
import com.watheq.watheq.model.ReportProblemModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mahmoud.diab on 11/29/2017.
 */

public interface ClientServices {
    String baseUrl = "http://wathiq.sa/";//http://159.89.41.54/watheq/public/api/

    @POST("api/client/login")
    Call<LoginModelResponse> loginUser(@Body LoginBody loginBody);

    @POST("api/auth/client/completeProfile")
    Call<LoginModelResponse> completeProfile(@Header("Authorization") String auth, @Body CompleteProfileBody completeProfileBody);

    @GET("api/auth/category/list")
    Call<MainCategoriesResponse> getCategories(@Header("Authorization") String auth);

    @POST("api/auth/order")
    Call<OrderLawyerResponse> orderLiveResponse(@Header("Authorization") String auth, @Body OrderLawyerBody orderLawyerBody);

    @GET("api/auth/client/order/listNewOrders")
    Call<OrdersResponseModel> getNewOrders(@Header("Authorization") String auth, @Query("page") int page,
                                           @Query("limit") int limit);

    @GET("api/auth/client/order/listClosedOrders")
    Call<OrdersResponseModel> getClosedOrders(@Header("Authorization") String auth, @Query("page") int page,
                                              @Query("limit") int limit);

    @GET("api/auth/client/order/listPendingOrders")
    Call<OrdersResponseModel> getOpenedOrders(@Header("Authorization") String auth, @Query("page") int page,
                                              @Query("limit") int limit);

    @GET("api/auth/order/laywersList")
    Call<LawyerListResponse> getLawerList(@Header("Authorization") String auth, @Query("orderId") int orderId
            , @Query("page") int page, @Query("limit") int limit);

    @GET("api/auth/client/order/selectLaywer")
    Call<OrderLawyerResponse> orderLawyer(@Header("Authorization") String auth
            , @Query("orderId") int orderId, @Query("lawyerId") int lawyerId);


    @GET("api/auth/orderDetails")
    Call<OrderDetailsModel> getOrderDetails(@Header("Authorization") String auth, @Query("orderId") int orderId);

    @POST("api/auth/client/registerDeviceToken")
    Call<BaseModel> registerDeviceToken(@Header("Authorization") String auth, @Body RegisterDeviceBody registerDeviceBody);

    @GET("api/auth/notification/list")
    Call<NotificationModel> getNotificationList(@Header("Authorization") String auth);

    @POST("api/auth/contactus/create")
    Call<BaseModel> reportProblem(@Header("Authorization") String auth, @Body ReportProblemModel reportProblemModel);

    @POST("api/auth/client/order/rate")
    Call<BaseModel> rateOrder(@Header("Authorization") String auth, @Body RateModel rateModel);

    @GET("api/auth/client/order/moveToSupport")
    Call<BaseModel> moveToSupport(@Header("Authorization") String auth, @Query("orderId") int orderId);

    @GET("api/auth/client/order/remove")
    Call<OrderLawyerResponse> cancelOrder(@Header("Authorization") String auth, @Query("orderId") int orderId);
}
