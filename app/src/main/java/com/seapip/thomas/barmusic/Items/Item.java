package com.seapip.thomas.barmusic.Items;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

public interface Item {
    @NonNull
    View getView(@NonNull Context context, @Nullable View convertView, @NonNull ViewGroup parent);

    void setOnClick(Runnable runnable);
}
