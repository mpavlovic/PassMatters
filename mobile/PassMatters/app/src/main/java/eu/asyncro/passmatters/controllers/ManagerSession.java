package eu.asyncro.passmatters.controllers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ahuskano on 11/14/2014.
 */
public class ManagerSession {

    private static final String PREFERENCES_ID = "default_shared_preferences";
    private static final String TOKEN_ID = "eu.asyncro.token";

    public static void saveToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(TOKEN_ID, token);
        editor.commit();
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences(PREFERENCES_ID, Context.MODE_PRIVATE).getString(TOKEN_ID, null);
    }
}
