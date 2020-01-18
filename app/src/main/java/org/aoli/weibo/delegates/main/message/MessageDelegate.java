package org.aoli.weibo.delegates.main.message;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.aoli.weibo.R;
import org.aoli.weibo.delegates.BaseDelegate;
import org.aoli.weibo.ui.UILoader;

public class MessageDelegate extends BaseDelegate {
    private UILoader mUILaoder;

    @Override
    public Object setLayout() {
        mUILaoder = new UILoader(getContext()) {
            @Override
            protected Object setSuccessLayout() {
                return R.layout.delegate_message;
            }
        };
        return mUILaoder;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mUILaoder.updateStatus(UILoader.UIStatus.SUCCESS);
    }
}
