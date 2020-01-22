package org.aoli.weibo.sinasdk.interfaces;

import org.aoli.weibo.sinasdk.bean.ErrorMsg;
import org.aoli.weibo.sinasdk.bean.StatusContent;

import java.util.List;

public interface IHomeTimeLineViewCallback {
    void onLoaded(List<StatusContent> statuses);

    void onRefresh(List<StatusContent> statuses);

    void onLoadMore(List<StatusContent> statuses);

    void onError(ErrorMsg errorMsg);

    void onFailure();
}
