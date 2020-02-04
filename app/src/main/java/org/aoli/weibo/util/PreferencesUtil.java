package org.aoli.weibo.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.aoli.weibo.R;
import org.aoli.weibo.application.Aoli;
import org.aoli.weibo.application.ConfigType;

public class PreferencesUtil {
    private static final SharedPreferences PREFERENCES =
            PreferenceManager.getDefaultSharedPreferences(Aoli.getApplicationContext());

    private static SharedPreferences getAppPreference() {
        return PREFERENCES;
    }

    public static String getToken(){
        return getAppPreference().getString(ConfigType.TOKEN.name(),null);
    }

    public static void deleteToken(){
        Aoli.deleteToken();
        getAppPreference().edit().remove(ConfigType.TOKEN.name()).apply();
    }

    public static void writeToken(String token){
        Aoli.writeToken(token);
        getAppPreference().edit().putString(ConfigType.TOKEN.name(),token).apply();
    }

    public static int getTheme(){
        return getAppPreference().getInt(ConfigType.THEME.name(),R.style.WhiteTheme);
    }

    public static void setTheme(int theme){
        Aoli.setTheme(theme);
        getAppPreference().edit().putInt(ConfigType.THEME.name(),theme).apply();
    }
}
