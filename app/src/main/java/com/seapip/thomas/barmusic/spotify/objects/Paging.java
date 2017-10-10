package com.seapip.thomas.barmusic.spotify.objects;

import java.util.Arrays;

public class Paging<I> {
    public String href;
    public I[] items;
    public int limit;
    public String next;
    public int offset;
    public String previous;
    public int total;

    @Override
    public String toString() {
        return "Paging{" +
                "href='" + href + '\'' +
                ", items=" + Arrays.toString(items) +
                ", limit=" + limit +
                ", next='" + next + '\'' +
                ", offset=" + offset +
                ", previous='" + previous + '\'' +
                ", total=" + total +
                '}';
    }
}
