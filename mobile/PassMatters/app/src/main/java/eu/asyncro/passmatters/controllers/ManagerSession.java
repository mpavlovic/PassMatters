package eu.asyncro.passmatters.controllers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class used to management shared preference
 */
public class ManagerSession {

    private static final String PREFERENCES_ID = "default_shared_preferences";
    private static final String TOKEN_ID = "eu.asyncro.token";

    /**
     * Method used to save token
     * @param context
     * @param token session token
     */
    public static void saveToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(TOKEN_ID, token);
        editor.commit();
    }

    /**
     * Method used to get token from preferences
     * @param context
     * @return
     */
    public static String getToken(Context context) {
        return context.getSharedPreferences(PREFERENCES_ID, Context.MODE_PRIVATE).getString(TOKEN_ID, null);
    }
}
