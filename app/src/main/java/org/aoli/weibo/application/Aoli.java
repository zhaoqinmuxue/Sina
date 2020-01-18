package org.aoli.weibo.application;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

public class Aoli {
    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT,context.getApplicationContext());
        return Configurator.getInstance();
    }

    private static HashMap<ConfigType,Object> getConfigurations(){
        return Configurator.getInstance().getConfigs();
    }

    public static boolean isLoginIn(){
        return getConfigurations().containsKey(ConfigType.TOKEN);
    }

    public static Handler getHandler(){
        return getConfiguration(ConfigType.HANDLER);
    }

    public static String getToken(){
        return getConfiguration(ConfigType.TOKEN);
    }

    public static void writeToken(String token){
        getConfigurations().put(ConfigType.TOKEN,token);
    }

    public static void deleteToken(){
        getConfigurations().remove(ConfigType.TOKEN);
    }

    public static Context getApplicationContext(){
        return getConfiguration(ConfigType.APPLICATION_CONTEXT);
    }


    public static <T> T getConfiguration(ConfigType type){
        return (T)getConfigurations().get(type);
    }
}
