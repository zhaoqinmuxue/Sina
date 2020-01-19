package org.aoli.weibo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import org.aoli.weibo.util.PreferencesUtil;

public class LoginActivity extends AppCompatActivity {

    private SsoHandler mSsoHandler = null;

    public static String DIRECTLOGIN = "DIRECTLOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSsoHandler = new SsoHandler(this);
        if (getIntent().getBooleanExtra(DIRECTLOGIN,false)) {
            getIntent().removeExtra(DIRECTLOGIN);
            mSsoHandler.authorizeWeb(new LoginActivity.SelfWbAuthListener());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null){
            mSsoHandler.authorizeCallBack(requestCode,resultCode,data);
        }
    }

    private class SelfWbAuthListener implements WbAuthListener {
        @Override
        public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
            PreferencesUtil.writeToken(oauth2AccessToken.getToken());
            setResult(1);
        }

        @Override
        public void cancel() {
            Toast.makeText(LoginActivity.this,"取消登录",Toast.LENGTH_SHORT).show();
            setResult(2);
        }

        @Override
        public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
            Toast.makeText(LoginActivity.this,wbConnectErrorMessage.getErrorMessage(),Toast.LENGTH_SHORT).show();
            setResult(3);
        }
    }
}
