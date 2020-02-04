package org.aoli.weibo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.aoli.weibo.application.Aoli;
import org.aoli.weibo.delegates.BaseDelegate;
import org.aoli.weibo.ui.WindowInsetsFrameLayout;


public abstract class BaseActivity extends AppCompatActivity {
    public abstract BaseDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(Aoli.getTheme());
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
        Window window = getWindow();
        if(Aoli.getTheme() == R.style.WhiteTheme) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else if (Aoli.getTheme() == R.style.BlackTheme){
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    private void initContainer(@Nullable Bundle savedInstanceState){
        final WindowInsetsFrameLayout container = new WindowInsetsFrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        //onCreate需要先设置layout，所以以上一定要做
        //setContentView在onStart之前，所以不会恢复
        //但是如果savedInstanceState != null则会在onStart中自动恢复fragment
        //所以此时不用重复添加
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.delegate_container,setRootDelegate())
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
