package com.example.pankaj.assignment.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wellslock on 02-06-2015.
 */
public class PreferenceManager {

    public final String MY_PREFS_NAME = "myPreference";
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    public PreferenceManager(Context context) {
        this.context = context;
        this.editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        this.prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);

    }

    public void putPreferenceBoolValues(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String putPreferenceValues(String key, String value) {
        editor.putString(key, value);
        editor.commit();
        return key;
    }


    public void putPreferenceIntValues(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public boolean getPreferenceBoolValues(String key) {
        return prefs.getBoolean(key, true);
    }

    public int getPreferenceIntValues(String key) {
        return prefs.getInt(key, 0);
    }

    public String getPreferenceValues(String key) {
        return prefs.getString(key, "");
    }

    public void clearSharedPreferance() {
        prefs.edit().clear().commit();
    }

    public void putPreferencDoubleValues(String key, double value) {
        editor.putLong(key, Double.doubleToRawLongBits(value));
        editor.commit();
    }

    public double getPreferencDoubleValues(String key, double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }


}
