package com.seapip.thomas.barmusic.spotify;

import com.seapip.thomas.barmusic.spotify.objects.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET("/v1/search")
    Call<Search> search(@Query("q") String query, @Query("type") String type);
}
