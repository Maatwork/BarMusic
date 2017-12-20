package com.seapip.thomas.barmusic;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.seapip.thomas.barmusic.Items.Item;
import com.seapip.thomas.barmusic.Items.SongItem;
import com.seapip.thomas.barmusic.webapi.MusicService;
import com.seapip.thomas.barmusic.webapi.MusicServiceManager;
import com.seapip.thomas.barmusic.webapi.objects.Song;
import com.seapip.thomas.barmusic.webapi.objects.Vote;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private String barId = "testuuid";
    private ListView listView;
    private MusicServiceManager musicServiceManager = new MusicServiceManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        barId = getIntent().getStringExtra("barId");


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Suggest music");

        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) listView.getItemAtPosition(position);
                if (item instanceof SongItem) {
                    vote(((SongItem) item).getId());
                }
            }
        });
        search("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        // Get the SearchActivity and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchActivity.class)));
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });
        searchView.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                search("");
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            search(intent.getStringExtra(SearchManager.QUERY));
        }
    }

    private void search(final String query) {
        musicServiceManager.getService(new Callback<MusicService>() {
            @Override
            public void onSuccess(MusicService service) {
                Call<Song[]> call = query.length() > 0 ? service.search(query, barId) : service.library(barId);
                call.enqueue(new retrofit2.Callback<Song[]>() {
                    @Override
                    public void onResponse(Call<Song[]> call, Response<Song[]> response) {
                        if (response.isSuccessful()) {
                            ArrayList<Item> items = new ArrayList<>();
                            for (Song song : response.body()) {
                                items.add(new SongItem(song.id, song.name, song.artist, song.count));
                            }
                            listView.setAdapter(new Adapter(SearchActivity.this, items));
                        }
                    }

                    @Override
                    public void onFailure(Call<Song[]> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void vote(final String songId) {
        musicServiceManager.getService(new Callback<MusicService>() {
            @Override
            public void onSuccess(MusicService service) {
                service.vote(new Vote(songId)).enqueue(new retrofit2.Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        //Successfully voted
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
        finish();
    }
}
