package com.seapip.thomas.barmusic.webapi;

import com.seapip.thomas.barmusic.Callback;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MusicServiceManager {
    private MusicService mService;

    public void getService(final Callback<MusicService> callback) {
        if (mService == null) {
            mService = new Retrofit.Builder()
                    .baseUrl("http://music.maatwerk.works/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MusicService.class);
        }
        callback.onSuccess(mService);
    }
}
