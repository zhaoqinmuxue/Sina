package org.aoli.weibo.delegates.main.index;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.aoli.weibo.LoginActivity;
import org.aoli.weibo.R;
import org.aoli.weibo.application.Aoli;
import org.aoli.weibo.delegates.BaseDelegate;
import org.aoli.weibo.ui.UILoader;

import butterknife.BindView;

public class IndexDelegate extends BaseDelegate implements UILoader.OnRetryClickListener {
    @BindView(R.id.index_rv)
    RecyclerView mRecyclerView;

    private UILoader mUILoader;

    @Override
    public Object setLayout() {
        mUILoader = new UILoader(getContext()) {
            @Override
            protected Object setSuccessLayout() {
                return R.layout.delegate_index;
            }
        };
        return mUILoader;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mUILoader.setOnRetryClickListener(this);
        mUILoader.updateStatus(UILoader.UIStatus.ERROR);
    }

    @Override
    public void onRetryClick() {
        if (Aoli.isLoginIn()){

        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("您的身份已失效，请重新登录")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.putExtra(LoginActivity.DIRECTLOGIN,true);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消",null)
                    .setCancelable(false)
                    .show();
        }
    }
}
