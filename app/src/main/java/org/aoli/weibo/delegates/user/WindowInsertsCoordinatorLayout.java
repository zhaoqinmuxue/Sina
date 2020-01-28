package org.aoli.weibo.delegates.user;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class WindowInsertsCoordinatorLayout extends CoordinatorLayout {
    public WindowInsertsCoordinatorLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                int childCount = getChildCount();
                for (int index = 0; index < childCount; index++)
                    getChildAt(index).dispatchApplyWindowInsets(insets);
                return insets;
            }
        });
    }
}
