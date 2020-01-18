package org.aoli.weibo.delegates.main.hot;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import org.aoli.weibo.R;
import org.aoli.weibo.delegates.BaseDelegate;
import org.aoli.weibo.ui.UILoader;


public class HotDelegate extends BaseDelegate {
    private UILoader mUILaoder;

    @Override
    public Object setLayout() {
        mUILaoder = new UILoader(getContext()) {
            @Override
            protected Object setSuccessLayout() {
                return R.layout.delegate_hot;
            }
        };
        return mUILaoder;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mUILaoder.updateStatus(UILoader.UIStatus.ERROR);
    }
}
