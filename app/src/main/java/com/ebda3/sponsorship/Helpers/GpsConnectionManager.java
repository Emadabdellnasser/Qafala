package com.ebda3.sponsorship.Helpers;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class GpsConnectionManager {

    @SuppressWarnings("deprecation")
    public void turnGPSOn(Context context) {
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        context.sendBroadcast(intent);

        String provider = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) { // if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings",
                    "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            context.sendBroadcast(poke);

        }
    }


    public void turnGPSOff(Context context) {
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", false);
        context.sendBroadcast(intent);
        // String provider =
        // Settings.Secure.getString(this.getContentResolver(),
        // Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        // if (provider.contains("gps")) { // if gps is enabled
        // final Intent poke = new Intent();
        // poke.setClassName("com.android.settings",
        // "com.android.settings.widget.SettingsAppWidgetProvider");
        // poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
        // poke.setData(Uri.parse("3"));
        // this.sendBroadcast(poke);
        // }
    }
}