package com.lymilestone.postcodemrd.ui.adapter.girls;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by CodeManLY on 2017/7/22 0022.
 */

public class GirlsVPAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    List<String> titleName;
    public GirlsVPAdapter(FragmentManager fm, List<Fragment> fragmentList,List<String> titleName) {
        super(fm);
        this.fragmentList=fragmentList;
        this.titleName=titleName;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleName.get(position);
    }
}
