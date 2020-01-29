package org.aoli.weibo.delegates.main.index;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import org.aoli.weibo.R;
import org.aoli.weibo.application.Aoli;
import org.aoli.weibo.delegates.adapter.WeiBoAdapter;
import org.aoli.weibo.delegates.main.BaseLazyDelegate;
import org.aoli.weibo.sinasdk.ErrorMsgUtil;
import org.aoli.weibo.sinasdk.bean.ErrorMsg;
import org.aoli.weibo.sinasdk.bean.StatusContent;
import org.aoli.weibo.sinasdk.http.HomeTimeLinePresenter;
import org.aoli.weibo.sinasdk.interfaces.ITimeLinePresenter;
import org.aoli.weibo.sinasdk.interfaces.ITimeLineViewCallback;
import org.aoli.weibo.ui.UILoader;
import org.aoli.weibo.util.PixelUtil;
import java.util.List;

import butterknife.BindView;

public class IndexDelegate extends BaseLazyDelegate implements ITimeLineViewCallback {
    @BindView(R.id.index_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.index_sr)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ITimeLinePresenter mHomeTimeLinePresenter;

    private WeiBoAdapter mWeiBoAdapter;

    @Override
    protected Object setSuccessLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        updateStatus(UILoader.UIStatus.ERROR);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mWeiBoAdapter = new WeiBoAdapter(getContext());
        mRecyclerView.setAdapter(mWeiBoAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setProgressViewOffset(true, PixelUtil.toPixel(-20),PixelUtil.toPixel(40));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHomeTimeLinePresenter.pull2Refresh();
            }
        });
        mHomeTimeLinePresenter = HomeTimeLinePresenter.getInstance();
        mHomeTimeLinePresenter.registerViewCallback(this);
    }

    @Override
    protected void onLazyLoad() {
        if (Aoli.isLoginIn()){
            updateStatus(UILoader.UIStatus.LOADING);
            mHomeTimeLinePresenter.getStatusContents();
        }else {
            updateStatus(UILoader.UIStatus.ERROR);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomeTimeLinePresenter.unRegisterViewCallback(this);
        mHomeTimeLinePresenter = null;
    }

    @Override
    public void onLoaded(List<StatusContent> statuses) {
        updateStatus(UILoader.UIStatus.SUCCESS);
        mWeiBoAdapter.setData(statuses);
    }

    @Override
    public void onRefresh(List<StatusContent> statuses) {
        mSwipeRefreshLayout.setRefreshing(false);
        mWeiBoAdapter.setData(statuses);
    }

    @Override
    public void onLoadMore(List<StatusContent> statuses) {
        mWeiBoAdapter.addData(statuses);
    }

    @Override
    public void onError(ErrorMsg errorMsg) {
        Toast.makeText(getContext(),ErrorMsgUtil.getMsg(errorMsg.getError_code()),Toast.LENGTH_LONG).show();
        updateStatus(UILoader.UIStatus.ERROR);
    }

    @Override
    public void onFailure() {
        updateStatus(UILoader.UIStatus.ERROR);
    }
}
