package com.seapip.thomas.barmusic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.seapip.thomas.barmusic.Items.Item;
import com.seapip.thomas.barmusic.Items.SongItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    final private static String BAR_ID = "testuuid";
    private ServiceManager serviceManager = new ServiceManager();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Happy Hour Bar");

        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new Adapter(this, new ArrayList<Item>()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) listView.getItemAtPosition(position);
                if (item instanceof SongItem) {
                    vote(((SongItem) item).getId());
                }
            }
        });


        final Handler queueHandler = new Handler();
        new Runnable() {
            @Override
            public void run() {
                updateQueue();
                queueHandler.postDelayed(this, 5000);
            }
        }.run();

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.suggest);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });
    }

    private void updateQueue() {
        serviceManager.getService(new Callback<Service>() {
            @Override
            public void onSuccess(Service service) {
                service.queue(BAR_ID).enqueue(new retrofit2.Callback<Song[]>() {
                    @Override
                    public void onResponse(Call<Song[]> call, Response<Song[]> response) {
                        if (response.isSuccessful()) {
                            ArrayList<Item> items = new ArrayList<>();
                            for (Song song : response.body()) {
                                items.add(new SongItem(song.id, song.title, song.artist, song.votes));
                            }
                            ((Adapter) listView.getAdapter()).updateAt(0, items);
                        }
                    }

                    @Override
                    public void onFailure(Call<Song[]> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void vote(final int songId) {
        Log.e("BAR", String.valueOf(songId));
        serviceManager.getService(new Callback<Service>() {
            @Override
            public void onSuccess(Service service) {
                service.vote(songId, "TEMP_DEVICE_ID").enqueue(new retrofit2.Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        //Successfully voted
                        updateQueue();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    }
}
