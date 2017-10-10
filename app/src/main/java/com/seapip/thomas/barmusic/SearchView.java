package com.seapip.thomas.barmusic;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.seapip.thomas.barmusic.spotify.Manager;
import com.seapip.thomas.barmusic.spotify.Service;
import com.seapip.thomas.barmusic.spotify.objects.Search;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Response;

public class SearchView extends AppCompatActivity {

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Suggest music");

        // Get the intent, verify the action and get the query
        //handleIntent(getIntent());

        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                new String[]{"rockstar", "New Rules", "Too Good At Goodbyes", "Havana",
                        "Dusk Till Dawn - Radio Edit", "Mi Gente", "Silence",
                        "Look What You Made Me Do"}));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchView.class)));
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
        Suggest.search(this, query, new Callback<Collection<Song>>() {
            @Override
            public void onSuccess(Collection<Song> songs) {
                listView.setAdapter(new Adapter(SearchView.this, new ArrayList<>(songs)));
            }
        });
    }
}
