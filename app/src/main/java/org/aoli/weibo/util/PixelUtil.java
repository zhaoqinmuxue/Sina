package org.aoli.weibo.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import org.aoli.weibo.application.Aoli;

public class PixelUtil {
    public static int toPixel(int dp){
        final float scale = Aoli.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int getStatusBarHeight() {
        final Resources resources = Aoli.getApplicationContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        return resources.getDimensionPixelSize(resourceId);
    }

    public static int getScreenWidth(){
        final Resources resources = Aoli.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(){
        final Resources resources = Aoli.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
