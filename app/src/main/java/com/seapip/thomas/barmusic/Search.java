package com.seapip.thomas.barmusic;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class Search extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        Log.e("BAR", "Uhm?");
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String query = intent.getStringExtra(SearchManager.QUERY);
            Log.e("BAR", query);
            setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{query, "Item 2", "Item 3"}));
        }
    }
}
