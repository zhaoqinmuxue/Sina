package org.aoli.weibo.util;


import android.content.res.TypedArray;

import androidx.annotation.ColorInt;

import org.aoli.weibo.R;
import org.aoli.weibo.application.Aoli;

public class ColorUtil {
    public static @ColorInt int getAccentBackColor(){
        int[] attrs = new int[] {R.attr.accentBack};
        TypedArray typedArray = Aoli.getApplicationContext().obtainStyledAttributes(Aoli.getTheme(),attrs);
        int color = typedArray.getColor(0, 0xfffafafa);
        typedArray.recycle();
        return color;
    }
}
