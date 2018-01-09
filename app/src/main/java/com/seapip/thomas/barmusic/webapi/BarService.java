package com.seapip.thomas.barmusic.webapi;

import com.seapip.thomas.barmusic.webapi.objects.Bar;
import com.seapip.thomas.barmusic.webapi.objects.Song;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BarService {
    @GET("/api/bars/{bar_uuid}")
    Call<Bar> bar(@Path("bar_uuid") String barId);
}
