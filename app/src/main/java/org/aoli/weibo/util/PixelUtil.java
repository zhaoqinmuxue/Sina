package org.aoli.weibo.util;

import org.aoli.weibo.application.Aoli;

public class PixelUtil {
    public static int toPixel(int dp){
        final float scale = Aoli.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
