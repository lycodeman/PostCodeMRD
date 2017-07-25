package com.lymilestone.postcodemrd.ui.adapter.joke;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.httplibrary.utils.manager.AppUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by CodeManLY on 2017/7/21 0021.
 */

public class JokeVpAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> titleList;

    public JokeVpAdapter(FragmentManager fm,List<Fragment> fragmentList,List<String> titleList) {
        super(fm);
        this.fragmentList=fragmentList;
        this.titleList=titleList;
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
        return titleList.get(position);
    }
}
