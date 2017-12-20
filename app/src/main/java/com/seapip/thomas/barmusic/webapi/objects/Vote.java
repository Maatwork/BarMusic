package com.seapip.thomas.barmusic.webapi.objects;

public class Vote {
    final String songId;
    final String deviceId;

    public Vote(String songId, String deviceId) {
        this.songId = songId;
        this.deviceId = deviceId;
    }
}
