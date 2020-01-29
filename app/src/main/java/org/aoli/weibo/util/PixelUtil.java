package org.aoli.weibo.util;

import android.content.res.Resources;
import android.util.TypedValue;

import org.aoli.weibo.application.Aoli;

public class PixelUtil {
    public static int toPixel(int dp){
        final float scale = Aoli.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int getStatusBarHeight() {
        Resources resources = Aoli.getApplicationContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
