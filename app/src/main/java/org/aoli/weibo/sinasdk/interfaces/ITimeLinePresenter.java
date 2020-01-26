package org.aoli.weibo.sinasdk.interfaces;

//获取微博内容列表
public interface ITimeLinePresenter {
    void getStatusContents();

    void pull2Refresh();

    void loadMore();

    void registerViewCallback(ITimeLineViewCallback callback);

    void unRegisterViewCallback(ITimeLineViewCallback callback);
}
