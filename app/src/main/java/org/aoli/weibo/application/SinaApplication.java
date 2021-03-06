package org.aoli.weibo.application;

import android.app.Application;
import android.os.Handler;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;

import org.aoli.weibo.util.PreferencesUtil;

public class SinaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WbSdk.install(this,new AuthInfo(this,Configurator.APP_KEY_SINA,Configurator.REDIRECT_URL,Configurator.SCOPE));
        Aoli.init(this)
                .withHandler(new Handler())
                .withTheme(PreferencesUtil.getTheme())
                .withToken(PreferencesUtil.getToken())
                .withBaseUrl("https://api.weibo.com/2/");
    }
}
