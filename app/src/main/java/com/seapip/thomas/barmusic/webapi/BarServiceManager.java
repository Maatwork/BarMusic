package com.seapip.thomas.barmusic.webapi;

import com.seapip.thomas.barmusic.Callback;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BarServiceManager {
    private BarService mService;

    public void getService(final Callback<BarService> callback) {
        if (mService == null) {
            mService = new Retrofit.Builder()
                    .baseUrl("http://maatwerk.works/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BarService.class);
        }
        callback.onSuccess(mService);
    }
}
