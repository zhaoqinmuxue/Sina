package org.aoli.weibo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.Nullable;

import org.aoli.weibo.R;
import org.aoli.weibo.application.Aoli;

public abstract class UILoader extends FrameLayout {
    public enum UIStatus {
        LOADING,SUCCESS,ERROR,NONE
    }

    public UIStatus mCurrentStatus = UIStatus.NONE;

    private View mLoadingView;
    private View mSuccessView;
    private View mErrorView;

    private OnRetryClickListener mOnRetryClickListener;

    public UILoader(@Nullable Context context){
        this(context,null);
    }

    public UILoader(@Nullable Context context, @Nullable AttributeSet attrs){
        this(context,attrs,0);
    }

    public UILoader(@Nullable Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr){
        super(context,attrs,defStyleAttr);
        init();
    }

    private void init(){
        switchUIByCurrentStatus();
    }

    public void updateStatus(UIStatus status){
        mCurrentStatus = status;
        Aoli.getHandler().post(new Runnable() {
            @Override
            public void run() {
                switchUIByCurrentStatus();
            }
        });
    }

    public void switchUIByCurrentStatus(){
        if (mLoadingView == null){
            mLoadingView = getLoadingView();
            addView(mLoadingView);
        }
        if (mCurrentStatus == UIStatus.LOADING){
            mLoadingView.setVisibility(VISIBLE);
        }else{
            mLoadingView.setVisibility(GONE);
        }
        if (mSuccessView == null){
            mSuccessView = getSuccessView();
            addView(mSuccessView);
        }
        mSuccessView.setVisibility(mCurrentStatus == UIStatus.SUCCESS ? VISIBLE : GONE);
        if (mErrorView == null){
            mErrorView = getErrorView();
            addView(mErrorView);
        }
        mErrorView.setVisibility(mCurrentStatus == UIStatus.ERROR ? VISIBLE : GONE);
    }

    private View getLoadingView(){
        return LayoutInflater.from(getContext()).inflate(R.layout.uiloader_loading,this,false);
    }

    private View getSuccessView(){
        View view;
        Object object = setSuccessLayout();
        if (object instanceof Integer) {
            view = LayoutInflater.from(getContext()).inflate((int) object, this, false);
        } else if (object instanceof View) {
            view = (View) object;
        } else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }
        return view;
    }

    private View getErrorView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.uiloader_error,this,false);
        view.findViewById(R.id.bt_refresh).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRetryClickListener != null){
                    mOnRetryClickListener.onRetryClick();
                }
            }
        });
        return view;
    }

    protected abstract Object setSuccessLayout();

    public void setOnRetryClickListener(OnRetryClickListener onRetryClickListener){
        mOnRetryClickListener = onRetryClickListener;
    }

    public interface OnRetryClickListener{
        void onRetryClick();
    }
}
