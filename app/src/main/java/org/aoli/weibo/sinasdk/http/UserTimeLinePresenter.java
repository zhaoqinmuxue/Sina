package org.aoli.weibo.sinasdk.http;


import com.alibaba.fastjson.JSON;

import org.aoli.weibo.application.Aoli;
import org.aoli.weibo.application.ConfigType;
import org.aoli.weibo.sinasdk.action.Actions;
import org.aoli.weibo.sinasdk.bean.ErrorMsg;
import org.aoli.weibo.sinasdk.bean.StatusContents;
import org.aoli.weibo.sinasdk.interfaces.ITimeLinePresenter;
import org.aoli.weibo.sinasdk.interfaces.ITimeLineViewCallback;
import org.aoli.weibo.util.net.HttpUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//由于官方限制，此接口只能获取本人微博
public class UserTimeLinePresenter implements ITimeLinePresenter {
    private enum  Type{
        LOAD,REFRESH,LOADMORE
    }

    private final long uid;

    private final int count = 20;

    private int page = 1;

    private WeakReference<ITimeLineViewCallback> callback;

    public UserTimeLinePresenter(long uid){
        this.uid = uid;
    }

    @Override
    public void getStatusContents() {
        page = 1;
        getStatusContents(Type.LOAD);
    }

    @Override
    public void pull2Refresh() {
        page = 1;
        getStatusContents(Type.REFRESH);
    }

    @Override
    public void loadMore() {
        page++;
        getStatusContents(Type.LOADMORE);
    }

    private void getStatusContents(Type type){
        HttpUtil.doGet(Aoli.getConfiguration(ConfigType.BASE_URL) + Actions.statusesHomeTimeLine + "?access_token=" + Aoli.getToken()
                        + "&&uid=" + uid + "&&count=" + count + "&&page=" + page,
                new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                        if (callback != null) {
                            Aoli.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.get().onFailure();
                                }
                            });
                        }
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.code() == HttpURLConnection.HTTP_OK) {
                            final String result = response.body().string();
                            ErrorMsg errorMsg = JSON.parseObject(result, ErrorMsg.class);
                            if (errorMsg != null && errorMsg.getError_code() != null) {
                                Aoli.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (callback != null) {
                                            callback.get().onError(errorMsg);
                                        }
                                    }
                                });
                            } else {
                                StatusContents statusContents = JSON.parseObject(result, StatusContents.class);
                                Aoli.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        switch (type) {
                                            case LOAD:
                                                if (callback != null) {
                                                    callback.get().onLoaded(statusContents.getStatuses());
                                                }
                                                break;
                                            case REFRESH:
                                                if (callback != null) {
                                                    callback.get().onRefresh(statusContents.getStatuses());
                                                }
                                                break;
                                            case LOADMORE:
                                                if (callback != null) {
                                                    callback.get().onLoadMore(statusContents.getStatuses());
                                                }
                                        }
                                    }
                                });
                            }
                        }else {
                            Aoli.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.get().onFailure();
                                }
                            });
                        }
                    }
                });

    }

    @Override
    public void registerViewCallback(ITimeLineViewCallback callback) {
        this.callback = new WeakReference<>(callback);
    }

    @Override
    public void unRegisterViewCallback(ITimeLineViewCallback callback) {
        this.callback = null;
    }
}
