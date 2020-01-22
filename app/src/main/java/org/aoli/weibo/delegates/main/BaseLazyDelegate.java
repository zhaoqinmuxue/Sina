package org.aoli.weibo.delegates.main;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import org.aoli.weibo.LoginActivity;
import org.aoli.weibo.application.Aoli;
import org.aoli.weibo.delegates.BaseDelegate;
import org.aoli.weibo.ui.UILoader;

public abstract class BaseLazyDelegate extends BaseDelegate {

    private UILoader mUILoader;

    private Boolean isLazyLoad = true;

    @Override
    protected final Object setLayout() {
        mUILoader = new UILoader(getContext()) {
            @Override
            protected Object setSuccessLayout() {
                return BaseLazyDelegate.this.setSuccessLayout();
            }
        };
        mUILoader.setOnRetryClickListener(new UILoader.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                if (Aoli.isLoginIn()){
                    updateStatus(UILoader.UIStatus.LOADING);
                    onLazyLoad();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("您的身份已失效，请重新登录")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    intent.putExtra(LoginActivity.DIRECTLOGIN,true);
                                    startActivityForResult(intent,1);
                                }
                            })
                            .setNegativeButton("取消",null)
                            .setCancelable(false)
                            .show();
                }
            }
        });
        return mUILoader;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1){
            onLazyLoad();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isLazyLoad){
            isLazyLoad = false;
            onLazyLoad();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLazyLoad = true;
    }

    protected void updateStatus(UILoader.UIStatus status){
        mUILoader.updateStatus(status);
    }

    abstract protected Object setSuccessLayout();

    protected abstract void onLazyLoad();
}
