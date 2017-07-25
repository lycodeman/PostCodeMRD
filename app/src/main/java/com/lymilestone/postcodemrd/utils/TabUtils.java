package com.lymilestone.postcodemrd.utils;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.lymilestone.httplibrary.utils.manager.AppManager;

/**
 * Created by CodeManLY on 2017/7/24 0024.
 */

public class TabUtils {

    public static void dynamicSetTabLayoutMode(TabLayout tabLayout) {
        int tabWidth = calculateTabWidth(tabLayout);
        int screenWidth = getScreenWith();

        if (tabWidth <= screenWidth) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }
    private static int calculateTabWidth(TabLayout tabLayout) {
        int tabWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final View view = tabLayout.getChildAt(i);
            view.measure(0, 0); // 通知父view测量，以便于能够保证获取到宽高
            tabWidth += view.getMeasuredWidth();
        }
        return tabWidth;
    }
    public static int getScreenWith() {
        return AppManager.appContext().getResources().getDisplayMetrics().widthPixels;
    }
}
