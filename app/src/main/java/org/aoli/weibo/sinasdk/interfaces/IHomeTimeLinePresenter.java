package org.aoli.weibo.sinasdk.interfaces;

//获取微博内容列表
public interface IHomeTimeLinePresenter {
    void getStatusContents();

    void pull2Refresh();

    void loadMore();

    void registerViewCallback(IHomeTimeLineViewCallback callback);

    void unRegisterViewCallback(IHomeTimeLineViewCallback callback);
}
