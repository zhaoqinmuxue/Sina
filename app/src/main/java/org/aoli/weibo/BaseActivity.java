package org.aoli.weibo;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.aoli.weibo.delegates.BaseDelegate;



public abstract class BaseActivity extends AppCompatActivity {
    public abstract BaseDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(@Nullable Bundle savedInstanceState){
        final FrameLayout container = new FrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        //onCreate需要先设置layout，所以以上一定要做
        //setContentView在onStart之前，所以不会恢复
        //但是如果savedInstanceState != null则会在onStart中自动恢复fragment
        //所以此时不用重复添加
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.delegate_container,setRootDelegate())
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
