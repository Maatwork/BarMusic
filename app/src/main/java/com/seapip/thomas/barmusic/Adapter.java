package com.seapip.thomas.barmusic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.seapip.thomas.barmusic.Items.Item;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Item> {
    private ArrayList<Item> mItems;
    private Context mContext;

    public Adapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.song_item, items);
        mItems = items;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getItem(position).getView(mContext, convertView, parent);
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    public void updateAt(int index, ArrayList<Item> items) {
        //mItems.removeAll(mItems.subList(index, items.size() > mItems.size() ? mItems.size() : items.size()));
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    private static class TitleViewHolder {
        TextView title;
    }
}
