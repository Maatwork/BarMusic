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
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.seapip.thomas.barmusic.Items.Item;
import com.seapip.thomas.barmusic.Items.SongItem;
import com.seapip.thomas.barmusic.webapi.BarService;
import com.seapip.thomas.barmusic.webapi.BarServiceManager;
import com.seapip.thomas.barmusic.webapi.MusicService;
import com.seapip.thomas.barmusic.webapi.MusicServiceManager;
import com.seapip.thomas.barmusic.webapi.objects.Bar;
import com.seapip.thomas.barmusic.webapi.objects.Song;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    final private static String BAR_ID = "testuuid";
    private MusicServiceManager musicServiceManager = new MusicServiceManager();
    private BarServiceManager barServiceManager = new BarServiceManager();
    private ListView listView;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
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

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.initiateScan();
    }

    private void updateQueue() {
        musicServiceManager.getService(new Callback<MusicService>() {
            @Override
            public void onSuccess(MusicService service) {
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
        musicServiceManager.getService(new Callback<MusicService>() {
            @Override
            public void onSuccess(MusicService service) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            Log.e("BAR", "We've got a result!");
            Log.e("BAR", result.getContents());
            Matcher matcher = Pattern.compile("https?:\\/\\/music\\.maatwerk\\.works\\/((\\w|\\d)+-(\\w|\\d)+-(\\w|\\d)+-(\\w|\\d)+-(\\w|\\d)+)").matcher(result.getContents());
            if (matcher.matches()) {
                Log.e("BAR", "We've got a match!");
                final String barUuid = matcher.group(1);
                Log.e("BAR", barUuid);
                barServiceManager.getService(new Callback<BarService>() {
                    @Override
                    public void onSuccess(BarService barService) {
                        barService.bar(barUuid).enqueue(new retrofit2.Callback<Bar>() {
                            @Override
                            public void onResponse(Call<Bar> call, Response<Bar> response) {
                                if (response.isSuccessful()) {
                                    String name = response.body().name;
                                    Log.e("BAR", name);
                                    actionBar.setTitle(name);
                                } else {
                                    Log.e("BAR", response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<Bar> call, Throwable t) {
                                Log.e("BAR", "FUCK me");
                            }
                        });
                    }
                });
            } else {
                Toast.makeText(this, "Invalid QR code", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
