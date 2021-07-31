package com.nodz.galleryimages.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {
    private static final String PREF_KEY = "QUERY";
    private Context context;

    public MyPreferences(Context context) {
        // TODO Auto-generated constructor stub
         this.context = context;
    }

    public  void saveData(Context context,  String key, String value) {
        //Log.d("value", value);
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREF_KEY, 0);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.apply();

    }

    public String getData(Context context,  String key) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREF_KEY, 0);

        return sharedpreferences.getString(key, "false");
    }


}
