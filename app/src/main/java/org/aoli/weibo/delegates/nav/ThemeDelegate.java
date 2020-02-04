package org.aoli.weibo.delegates.nav;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.aoli.weibo.R;
import org.aoli.weibo.application.Aoli;
import org.aoli.weibo.delegates.BaseDelegate;
import org.aoli.weibo.util.PreferencesUtil;

import butterknife.BindView;


public class ThemeDelegate extends BaseDelegate implements View.OnClickListener {
    @BindView(R.id.theme_white)
    LinearLayout White;
    @BindView(R.id.theme_black)
    LinearLayout Black;

    @Override
    protected Object setLayout() {
        return R.layout.delegate_theme;
    }

    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        White.setOnClickListener(this);
        Black.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.theme_white:
                if (Aoli.getTheme() != R.style.WhiteTheme) {
                    PreferencesUtil.setTheme(R.style.WhiteTheme);
                    getActivity().recreate();
                }
                break;
            case R.id.theme_black:
                if (Aoli.getTheme() != R.style.BlackTheme) {
                    PreferencesUtil.setTheme(R.style.BlackTheme);
                    getActivity().recreate();
                }
                break;
        }
    }
}
