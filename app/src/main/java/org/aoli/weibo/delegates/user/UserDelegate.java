package org.aoli.weibo.delegates.user;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;

import org.aoli.weibo.R;
import org.aoli.weibo.delegates.BaseBackDelegate;
import org.aoli.weibo.delegates.adapter.WeiBoAdapter;
import org.aoli.weibo.sinasdk.ErrorMsgUtil;
import org.aoli.weibo.sinasdk.bean.ErrorMsg;
import org.aoli.weibo.sinasdk.bean.StatusContent;
import org.aoli.weibo.sinasdk.bean.WeiBoUser;
import org.aoli.weibo.sinasdk.http.HomeTimeLinePresenter;
import org.aoli.weibo.sinasdk.http.UserTimeLinePresenter;
import org.aoli.weibo.sinasdk.interfaces.ITimeLinePresenter;
import org.aoli.weibo.sinasdk.interfaces.ITimeLineViewCallback;
import org.aoli.weibo.ui.UILoader;
import org.aoli.weibo.util.PixelUtil;

import java.util.List;

import butterknife.BindView;

public class UserDelegate extends BaseBackDelegate implements ITimeLineViewCallback {
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.fra_head)
    FrameLayout mFrameLayout;
    @BindView(R.id.tool_bar)
    FrameLayout mToolbar;
    @BindView(R.id.layout)
    CoordinatorLayout mCoordinatorLayout;

    private UILoader mUILoader;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.content_view)
    FrameLayout mContentView;
    @BindView(R.id.tb_name)
    Toolbar mTb_name;
    @BindView(R.id.img_head)
    ImageView mImg_head;
    @BindView(R.id.img_background)
    ImageView mImg_background;
    @BindView(R.id.tv_name)
    TextView mTv_name;
    @BindView(R.id.tv_describe)
    TextView mTv_describe;
    @BindView(R.id.tv_guanzhu)
    TextView mTv_guanzhu;
    @BindView(R.id.tv_fans)
    TextView mTv_fans;

    private ITimeLinePresenter mUserTimeLinePresenter;

    private WeiBoAdapter mWeiBoAdapter;

    private int size = PixelUtil.toPixel(66);
    private int toolBarSize = PixelUtil.toPixel(56);
    private int marginTop = PixelUtil.toPixel(115);
    private int marginStart = PixelUtil.toPixel(10);
    private int targetSize = PixelUtil.toPixel(38);
    private int actionBarSize = PixelUtil.toPixel(56);

    private int TranY = (toolBarSize-targetSize)/2 - marginTop + PixelUtil.getStatusBarHeight();
    private int TranX = PixelUtil.toPixel(10) - marginStart;
    private float ScaleXY = ((float)targetSize)/size;

    @Override
    protected Object setLayout() {
        return R.layout.delegate_user;
    }

    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mAppBarLayout.addOnOffsetChangedListener(listener);
        initRecyclerView();
        initData();
        getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    private void initData(){
        WeiBoUser user = (WeiBoUser) getArguments().getSerializable("user");
        mTb_name.setTitle(user.getScreen_name());
        mTv_name.setText(user.getScreen_name());
        mTv_describe.setText(user.getDescription());
        mTv_guanzhu.setText(String.valueOf(user.getFriends_count()));
        mTv_fans.setText(String.valueOf(user.getFollowers_count()));
        Glide.with(this).load(user.getCover_image_phone()).into(mImg_background);
        Glide.with(this).load(user.getAvatar_hd()).into(mImg_head);
        mUserTimeLinePresenter = new UserTimeLinePresenter(user.getId());
        mUserTimeLinePresenter.registerViewCallback(this);
        mUILoader.updateStatus(UILoader.UIStatus.LOADING);
        mUserTimeLinePresenter.getStatusContents();
    }

    private void initRecyclerView(){
        mUILoader = new UILoader(getContext()) {
            @Override
            protected Object setSuccessLayout() {
                return R.layout.recyclerview;
            }
        };
        mRecyclerView = mUILoader.findViewById(R.id.rv);
        mContentView.addView(mUILoader, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mWeiBoAdapter = new WeiBoAdapter(getContext());
        mRecyclerView.setAdapter(mWeiBoAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mSwipeRefreshLayout = mUILoader.findViewById(R.id.sr);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setProgressViewOffset(true, PixelUtil.toPixel(-20),PixelUtil.toPixel(40));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mUserTimeLinePresenter.pull2Refresh();
            }
        });
        mUserTimeLinePresenter = HomeTimeLinePresenter.getInstance();
        mUserTimeLinePresenter.registerViewCallback(this);
    }

    @Override
    public void onDestroyView() {
        mAppBarLayout.removeOnOffsetChangedListener(listener);
        mUserTimeLinePresenter.unRegisterViewCallback(this);
        getActivity().getWindow().setStatusBarColor(Color.WHITE);
        super.onDestroyView();
    }

    private AppBarLayout.OnOffsetChangedListener listener = new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            View view = getActivity().findViewById(R.id.delegate_container);
            float factor = ((float) Math.abs(verticalOffset))/(appBarLayout.getTotalScrollRange() - actionBarSize);
            if (factor >= 1.0f){
                mFrameLayout.setElevation(mToolbar.getElevation());
                mToolbar.setVisibility(View.VISIBLE);
                factor = 1.0f;
            }else {
                mFrameLayout.setElevation(PixelUtil.toPixel(0));
                mToolbar.setVisibility(View.GONE);
            }
            float offset = (1.0f-ScaleXY)*factor*size/2;
            mFrameLayout.setTranslationX(factor * TranX - offset);
            mFrameLayout.setTranslationY(factor * TranY - offset);
            mFrameLayout.setScaleX(1.0f-(1.0f-ScaleXY)*factor);
            mFrameLayout.setScaleY(1.0f-(1.0f-ScaleXY)*factor);
            mAppBarLayout.setAlpha(1.0f-factor/2);
        }
    };

    @Override
    public void onLoaded(List<StatusContent> statuses) {
        mUILoader.updateStatus(UILoader.UIStatus.SUCCESS);
        mWeiBoAdapter.setData(statuses);
    }

    @Override
    public void onRefresh(List<StatusContent> statuses) {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMore(List<StatusContent> statuses) {

    }

    @Override
    public void onError(ErrorMsg errorMsg) {
        Toast.makeText(getContext(), ErrorMsgUtil.getMsg(errorMsg.getError_code()),Toast.LENGTH_LONG).show();
        mUILoader.updateStatus(UILoader.UIStatus.ERROR);
    }

    @Override
    public void onFailure() {
        mUILoader.updateStatus(UILoader.UIStatus.ERROR);
    }
}
