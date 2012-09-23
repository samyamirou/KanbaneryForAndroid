package com.kanbandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final String PREF_KEY = "com.kanbandroid";
    public static final String API_KEY = "api_key";

    public static void putSharedPreference(Object value, Context context, String prefKey, String editorKey) {

        SharedPreferences settings = context.getSharedPreferences(prefKey, 0);
        SharedPreferences.Editor editor = settings.edit();

        if (value != null) {
            if (value instanceof Boolean) {
                editor.putBoolean(editorKey, (Boolean) value);
            } else if (value instanceof Integer) {
                editor.putInt(editorKey, (Integer) value);
            } else if (value instanceof Long) {
                editor.putLong(editorKey, (Long) value);
            } else if (value instanceof Float) {
                editor.putFloat(editorKey, (Float) value);
            } else if (value instanceof String) {
                editor.putString(editorKey, (String) value);
            }
            editor.commit();
        }
    }
}
