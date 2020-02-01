package org.aoli.weibo.ui.imagesdialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;

import org.aoli.weibo.sinasdk.bean.PicUrl;

import java.util.List;

public class BrowseImagesAdapter extends PagerAdapter {
    private Context mContext;
    private List<PicUrl> mUrls;
    private View[] mCacheViews;

    private Dialog mParent;

    public BrowseImagesAdapter(Context context, List<PicUrl> urls, Dialog dialog){
        mContext = context;
        mUrls = urls;
        mCacheViews = new View[mUrls.size()];
        mParent = dialog;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mCacheViews[position];
        if (view == null){
            PhotoView photoView = new PhotoView(mContext);
            photoView.enable();
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mParent.dismiss();
                }
            });
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setLayoutParams(layoutParams);
            Glide.with(mContext).load(mUrls.get(position).getOrigin_pic()).into(photoView);
            mCacheViews[position] = photoView;
            view = photoView;
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
