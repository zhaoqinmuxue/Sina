package org.aoli.weibo.ui.imagesdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import org.aoli.weibo.R;
import org.aoli.weibo.sinasdk.bean.PicUrl;

import java.util.ArrayList;
import java.util.List;

public class BrowseImagesDialog extends Dialog {
    private View mRootView;
    private Context mContext;
    private ViewPager mViewPager;
    private TextView mIndexText;
    private List<PicUrl> mUrls;
    private List<String> mTitles;

    public BrowseImagesDialog(@Nullable Context context, List<PicUrl> urls,int position){
        super(context, R.style.BlackDialog);
        mContext = context;
        mUrls = urls;
        initView();
        initData(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mRootView);
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        LayoutParams lp = getWindow().getAttributes();
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView(){
        mRootView = View.inflate(mContext,R.layout.dialog_images_brower,null);
        mViewPager = mRootView.findViewById(R.id.vp_images);
        mIndexText = mRootView.findViewById(R.id.tv_index);
    }

    private void initData(int position){
        mTitles = new ArrayList<>();
        for (int i = 0;i < mUrls.size();i++){
            mTitles.add(i+1 + " / " + mUrls.size());
        }
        mIndexText.setText(mTitles.get(position));
        mViewPager.setAdapter(new BrowseImagesAdapter(mContext,mUrls,this));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mIndexText.setText(mTitles.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
