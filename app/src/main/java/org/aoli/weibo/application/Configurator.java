package org.aoli.weibo.application;

import android.os.Handler;

import java.util.HashMap;

public class Configurator {
    private final HashMap<ConfigType,Object> CONFIGS = new HashMap<>();
    public static final String APP_KEY_SINA = "469501326";
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    public static final String SCOPE = "all";

    private Configurator(){}

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    final HashMap getConfigs(){
        return CONFIGS;
    }

    private static final class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    public Configurator withToken(String token){
        if (token != null)
            CONFIGS.put(ConfigType.TOKEN,token);
        return this;
    }

    public Configurator withHandler(Handler handler){
        CONFIGS.put(ConfigType.HANDLER,handler);
        return this;
    }

    public Configurator withBaseUrl(String baseUrl){
        CONFIGS.put(ConfigType.BASE_URL,baseUrl);
        return this;
    }
}
