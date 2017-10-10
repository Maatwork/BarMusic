package com.seapip.thomas.barmusic;

import android.content.Context;

import com.seapip.thomas.barmusic.spotify.Manager;
import com.seapip.thomas.barmusic.spotify.Service;
import com.seapip.thomas.barmusic.spotify.objects.Search;
import com.seapip.thomas.barmusic.spotify.objects.Track;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Response;

public class Suggest {
    private Suggest() {
    }

    public static void search(final Context context, final String query, final Callback<Collection<Song>> callback) {
        new Manager().getService(context, new Callback<Service>() {
            @Override
            public void onSuccess(Service service) {
                Call<Search> call = service.search(query, "track");
                call.enqueue(new retrofit2.Callback<Search>() {
                    @Override
                    public void onResponse(Call<Search> call, Response<Search> response) {
                        if (response.isSuccessful()) {
                            Search search = response.body();
                            if (search != null) {
                                ArrayList<Song> songs = new ArrayList<Song>(search.tracks.items.length);
                                for (Track track : search.tracks.items) {
                                    songs.add(new Song(track.name, track.artists[0].name, (int) Math.floor(Math.random() * 5d)));
                                }
                                callback.onSuccess(songs);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Search> call, Throwable t) {
                        callback.onError();
                    }
                });
            }
        });
    }
}
