package com.watheq.watheq.network;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.watheq.watheq.BuildConfig;
import com.watheq.watheq.api.ClientServices;
import com.watheq.watheq.utils.PrefsManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

/**
 * Created by mahmoud.diab on 11/29/2017.
 */

public class ApiFactory {

    private static final String API_KEY = "U2F0PDe4bSEQzBMi2SET8xaMNSB8amET";
    private static ClientServices clientServices;

    private ApiFactory() {

    }

    public static ClientServices createInstance() {
        if (clientServices != null) {
            return clientServices;
        } else {
            clientServices = new Retrofit.Builder().baseUrl(ClientServices.baseUrl).client(newClient())
                    .addConverterFactory(GsonConverterFactory.create(newGson()))
                    .build().create(ClientServices.class);
            return clientServices;
        }

    }

    private static Gson newGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }


    private static OkHttpClient newClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(new HeadersInterceptor(API_KEY));

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(BODY));
        }
        builder.addNetworkInterceptor(new LanguageInterceptor());
        return builder.build();

    }

    private static final class LanguageInterceptor implements Interceptor {

        private static final String KEY = "X-Api-Language";

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request.Builder ongoing = chain.request().newBuilder();
            ongoing.addHeader(KEY, PrefsManager.getInstance().getLang());
            return chain.proceed(ongoing.build());
        }
    }


    private static final class HeadersInterceptor implements Interceptor {
        private final String apiKey;

        HeadersInterceptor(String apiKey) {
            this.apiKey = apiKey;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            // add default header
            builder.addHeader("X_Api_Key", apiKey);
            builder.addHeader("Accept", "application/json");

            return chain.proceed(builder.build());

        }
    }
}
