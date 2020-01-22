package org.aoli.weibo.sinasdk.interfaces;

import org.aoli.weibo.sinasdk.bean.ErrorMsg;
import org.aoli.weibo.sinasdk.bean.StatusContent;

import java.util.List;

public interface IHomeTimeLineHandleCallback {
    void handleLoaded(List<StatusContent> statuses);

    void handleRefresh(List<StatusContent> statuses);

    void handleLoadMore(List<StatusContent> statuses);

    void handleError(ErrorMsg errorMsg);

    void handleFailure();
}
