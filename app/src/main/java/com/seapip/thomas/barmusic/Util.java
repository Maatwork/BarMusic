package com.seapip.thomas.barmusic;

import android.os.Build;

import java.util.UUID;

public class Util {
    private Util() {
    }

    /**
     * https://stackoverflow.com/questions/2785485/is-there-a-unique-android-device-id
     */
    public static String uniqueDeviceId() throws NoSuchFieldException, IllegalAccessException {
        String device = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) +
                (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) +
                (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);
        String serial = android.os.Build.class.getField("SERIAL").get(null).toString();
        return new UUID(device.hashCode(), serial.hashCode()).toString();
    }
}
