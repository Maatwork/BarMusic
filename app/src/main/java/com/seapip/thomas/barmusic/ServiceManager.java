package com.seapip.thomas.barmusic;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceManager {
    private Service mService;

    public void getService(final Callback<Service> callback) {
        if (mService == null) {
            mService = new Retrofit.Builder()
                    .baseUrl("https://barzo.seapip.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Service.class);
        }
        callback.onSuccess(mService);
    }
}
