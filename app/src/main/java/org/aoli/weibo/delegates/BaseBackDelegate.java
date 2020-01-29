package org.aoli.weibo.delegates;

import android.view.KeyEvent;
import android.view.View;

public abstract class BaseBackDelegate extends BaseDelegate implements View.OnKeyListener{

    @Override
    public void onResume() {
        super.onResume();
        final View rootView = getView();
        if (rootView != null){
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    //拦截返回事件，退出当前delegate
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            getFragmentManager().beginTransaction().remove(this).commit();
            return true;
        }
        return false;
    }
}
