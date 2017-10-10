package com.seapip.thomas.barmusic;

public class Song {
    private String title;
    private String artist;
    private int votes;

    public Song(String title, String artist, int votes) {
        this.title = title;
        this.artist = artist;
        this.votes = votes;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
