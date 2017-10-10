package com.seapip.thomas.barmusic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Song> implements View.OnClickListener {
    private ArrayList<Song> mSongs;
    private Context mContext;

    public Adapter(Context context, ArrayList<Song> songs) {
        super(context, R.layout.song_item, songs);
        mSongs = songs;
        mContext = context;
    }

    @Override
    public void onClick(View v) {
        //Nothing for now
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Song song = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.song_item, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.artist = (TextView) convertView.findViewById(R.id.artist);
            viewHolder.votes = (Button) convertView.findViewById(R.id.votes);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(song.getTitle());
        viewHolder.artist.setText(song.getArtist());
        viewHolder.votes.setVisibility(song.getVotes() > 0 ? View.VISIBLE : View.GONE);
        viewHolder.votes.setText(String.valueOf(song.getVotes()));

        return convertView;
    }

    public static class ViewHolder {
        TextView title;
        TextView artist;
        Button votes;
    }
}
