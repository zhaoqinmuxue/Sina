package org.aoli.weibo.delegates.main.index;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.aoli.weibo.R;
import org.aoli.weibo.application.Aoli;
import org.aoli.weibo.application.ConfigType;
import org.aoli.weibo.delegates.main.BaseLazyDelegate;
import org.aoli.weibo.ui.UILoader;
import org.aoli.weibo.util.net.HttpUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class IndexDelegate extends BaseLazyDelegate {
    @BindView(R.id.index_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv)
    TextView mTextView;

    @Override
    protected Object setSuccessLayout() {
        return R.layout.delegate_index;
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
        HttpUtil.doGet(Aoli.getConfiguration(ConfigType.BASE_URL) + "statuses/home_timeline.json" + "?access_token=" + Aoli.getToken(),
                new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String result = response.body().string();
                        Aoli.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                updateStatus(UILoader.UIStatus.SUCCESS);
                                mTextView.setText(result);
                            }
                        });
                    }
                });
    }
}
