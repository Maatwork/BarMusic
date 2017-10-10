package com.seapip.thomas.barmusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Happy Hour Bar");

        ListView listView = (ListView) findViewById(R.id.list);
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song("Lucy in the Sky with Diamonds", "Eleanor Rigby", 0));
        songs.add(new Song("Drops of Jupiter", "Some artist", 12));
        songs.add(new Song("Thriller", "Michael Jackson", 7));
        songs.add(new Song("Knights of Cydonia", "<3 Anime", 5));
        songs.add(new Song("Hymn for the weekend", "Coldplay", 2));
        listView.setAdapter(new Adapter(this, songs));

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.suggest);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchView.class));
            }
        });
    }
}
