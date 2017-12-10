package io.gresse.hugo.tp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Chris on 04/12/2017.
 */

public class UserStorage {

    public static final String USER_NAME = "userName";
    public static final String USER_EMAIL = "userEmail";

    public static void saveUserInfo(Context context, String name, String email) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(USER_NAME, name);
        editor.putString(USER_EMAIL, email);
        editor.apply();
    }

    public static String getUserName(Context context){
        SharedPreferences editor = PreferenceManager.getDefaultSharedPreferences(context);
        return editor.getString(USER_NAME,USER_NAME);
    }

    public static String getUserEmail(Context context){
        SharedPreferences editor = PreferenceManager.getDefaultSharedPreferences(context);
        return editor.getString(USER_EMAIL,USER_EMAIL);
    }

}
