package org.aoli.weibo.delegates.main.hot;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import org.aoli.weibo.R;
import org.aoli.weibo.delegates.main.BaseLazyDelegate;
import org.aoli.weibo.ui.UILoader;


public class HotDelegate extends BaseLazyDelegate {

    @Override
    protected Object setSuccessLayout() {
        return R.layout.delegate_hot;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        updateStatus(UILoader.UIStatus.ERROR);
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    public void onRefresh() {

    }
}
