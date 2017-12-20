package com.seapip.thomas.barmusic.webapi;

import com.seapip.thomas.barmusic.webapi.objects.Song;
import com.seapip.thomas.barmusic.webapi.objects.Vote;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MusicService {
    @GET("songs")
    Call<Song[]> library(@Query("userId") String barId);

    @GET("songs/{query}")
    Call<Song[]> search(@Path("query") String query, @Query("userId") String barId);

    @GET("votes")
    Call<Song[]> queue(@Query("userId") String barId);

    @POST("votes")
    Call<Void> vote(@Body Vote vote);
}
