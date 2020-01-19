package org.aoli.weibo.delegates.main.message;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.aoli.weibo.R;
import org.aoli.weibo.delegates.main.BaseLazyDelegate;
import org.aoli.weibo.ui.UILoader;

public class MessageDelegate extends BaseLazyDelegate {

    @Override
    protected Object setSuccessLayout() {
        return R.layout.delegate_message;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        updateStatus(UILoader.UIStatus.SUCCESS);
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    public void onRefresh() {

    }
}
