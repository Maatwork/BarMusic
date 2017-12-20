package com.seapip.thomas.barmusic.Items;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.seapip.thomas.barmusic.R;

public class SongItem implements Item {
    private String id;
    private String title;
    private String artist;
    private int votes;
    private Runnable runnable;

    public SongItem(String id, String title, String artist, int votes) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.votes = votes;
    }

    public String getId() {
        return id;
    }

    @NonNull
    @Override
    public View getView(@NonNull Context context, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.song_item, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.artist = (TextView) convertView.findViewById(R.id.artist);
            viewHolder.votes = (Button) convertView.findViewById(R.id.votes);
            convertView.setTag(viewHolder);
            /*
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    votes++;
                    updateView(viewHolder);
                }
            });*/
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        updateView(viewHolder);

        return convertView;
    }

    @Override
    public void setOnClick(Runnable runnable) {
        this.runnable = runnable;
    }

    private void updateView(ViewHolder viewHolder) {
        viewHolder.title.setText(title);
        viewHolder.artist.setText(artist);
        viewHolder.votes.setVisibility(votes > 0 ? View.VISIBLE : View.GONE);
        viewHolder.votes.setText(String.valueOf(votes));
    }

    private static class ViewHolder {
        TextView title;
        TextView artist;
        Button votes;
    }
}
