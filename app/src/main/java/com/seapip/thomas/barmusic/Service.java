package com.seapip.thomas.barmusic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @GET("/{bar_uuid}")
    Call<Song[]> library(@Path("bar_uuid") String barId);

    @GET("/{bar_uuid}/{query}")
    Call<Song[]> search(@Path("bar_uuid") String barId, @Path("query") String query);

    @GET("/queue/{bar_uuid}")
    Call<Song[]> queue(@Path("bar_uuid") String barId);

    @POST("/vote/{song_id}")
    Call<Void> vote(@Path("song_id") int songId, @Query("device_uuid") String deviceId);
}
