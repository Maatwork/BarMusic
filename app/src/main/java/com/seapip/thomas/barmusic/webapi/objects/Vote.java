package com.seapip.thomas.barmusic.webapi.objects;

import com.seapip.thomas.barmusic.Util;

public class Vote {
    final String songId;
    final String deviceId;

    public Vote(String songId) {
        this.songId = songId;
        String deviceId = "DEBUG";
        try {
            deviceId = Util.uniqueDeviceId();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            //
        } finally {
            this.deviceId = deviceId;
        }
    }
}
