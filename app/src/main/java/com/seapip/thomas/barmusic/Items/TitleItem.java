package com.seapip.thomas.barmusic.Items;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

public class TitleItem implements Item{
    private String title;

    public TitleItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @NonNull
    @Override
    public View getView(@NonNull Context context, @Nullable View convertView, @NonNull ViewGroup parent) {
        return null;
    }

    @Override
    public void setOnClick(Runnable runnable) {

    }
}
