package com.sp.psi.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Koushik on 5/26/2018.
 */
public class AppVariables {
    private static final String APP_SETTINGS_PREFERENCES = "ApplicationSettingPreferences";
    public static final int ORIENTATION_PORT = 0;
    public static final int ORIENTATION_LAND = 1;

    private static final String KEY_ORIENTATION = "Orientation";


    public static SharedPreferences getSharedPref(Context ctx) {
        return ctx.getSharedPreferences(APP_SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static int getOrientationSetting(Context ctx) {
        return getSharedPref(ctx).getInt(KEY_ORIENTATION, ORIENTATION_PORT);
    }
}
