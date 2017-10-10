package com.seapip.thomas.barmusic.spotify;

import android.content.Context;

import com.seapip.thomas.barmusic.Callback;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Manager {
    final static private String ACCESS_TOKEN = "BQBQpZ2ZCudgdn89ilyZ7-klueP_-AKXAMq_BXoCaAE7p0fCCSrf2W_kMds3ngSWU7grQNNQimUuDIwmOuO3AF7_zuzluyai2msVd2frFMm9m-XlyTOFPJZm627DxWWE9VK6c6qS-Oy1Cg29cmRcAyXgjXRsy6k0";
    private Retrofit.Builder mBuilder;

    public void getService(Context context, final Callback<Service> callback) {
        if (mBuilder == null) {
            mBuilder = new Retrofit.Builder()
                    .baseUrl("https://api.spotify.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create());
        }
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                return chain.proceed(request.newBuilder()
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .method(request.method(), request.body())
                        .build());
            }
        });
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);
        mBuilder = mBuilder.client(httpClient.build());
        Service service = mBuilder.build().create(Service.class);
        callback.onSuccess(service);
    }
}
