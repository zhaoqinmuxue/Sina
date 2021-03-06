package org.aoli.weibo.sinasdk.http;

import com.alibaba.fastjson.JSON;

import org.aoli.weibo.application.Aoli;
import org.aoli.weibo.application.ConfigType;
import org.aoli.weibo.sinasdk.action.Actions;
import org.aoli.weibo.sinasdk.bean.ErrorMsg;
import org.aoli.weibo.sinasdk.bean.StatusContent;
import org.aoli.weibo.sinasdk.bean.StatusContents;
import org.aoli.weibo.sinasdk.interfaces.IHomeTimeLineHandleCallback;
import org.aoli.weibo.sinasdk.interfaces.ITimeLinePresenter;
import org.aoli.weibo.sinasdk.interfaces.ITimeLineViewCallback;
import org.aoli.weibo.util.net.HttpUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeTimeLinePresenter implements ITimeLinePresenter, IHomeTimeLineHandleCallback {
    private enum  Type{
        LOAD,REFRESH,LOADMORE
    }

    private final int count = 20;

    private int page = 1;

    private List<ITimeLineViewCallback> callbacks = new ArrayList<>();

    private HomeTimeLinePresenter(){}

    private static final class Holder{
        private static final HomeTimeLinePresenter INSTANCE = new HomeTimeLinePresenter();
    }

    public static HomeTimeLinePresenter getInstance(){
        return Holder.INSTANCE;
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
                + "&&count=" + count + "&&page=" + page,
                new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                        Aoli.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                handleFailure();
                            }
                        });
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
                                        handleError(errorMsg);
                                    }
                                });
                            } else {
                                StatusContents statusContents = JSON.parseObject(result, StatusContents.class);
                                Aoli.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        switch (type) {
                                            case LOAD:
                                                handleLoaded(statusContents.getStatuses());
                                                break;
                                            case REFRESH:
                                                handleRefresh(statusContents.getStatuses());
                                                break;
                                            case LOADMORE:
                                                handleLoadMore(statusContents.getStatuses());
                                        }
                                    }
                                });
                            }
                        }else {
                            Aoli.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    handleFailure();
                                }
                            });
                        }
                    }
                });
    }

    @Override
    public void registerViewCallback(ITimeLineViewCallback callback) {
        if (!callbacks.contains(callback)){
            callbacks.add(callback);
        }
    }

    @Override
    public void unRegisterViewCallback(ITimeLineViewCallback callback) {
        callbacks.remove(callback);
    }

    @Override
    public void handleLoaded(List<StatusContent> statuses) {
        for (ITimeLineViewCallback callback : callbacks){
            callback.onLoaded(statuses);
        }
    }

    @Override
    public void handleRefresh(List<StatusContent> statuses) {
        for (ITimeLineViewCallback callback : callbacks){
            callback.onRefresh(statuses);
        }
    }

    @Override
    public void handleLoadMore(List<StatusContent> statuses) {
        for (ITimeLineViewCallback callback : callbacks){
            callback.onLoadMore(statuses);
        }
    }

    @Override
    public void handleError(ErrorMsg errorMsg) {
        for (ITimeLineViewCallback callback : callbacks){
            callback.onError(errorMsg);
        }
    }

    @Override
    public void handleFailure() {
        for (ITimeLineViewCallback callback : callbacks){
            callback.onFailure();
        }
    }
}
