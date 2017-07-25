package com.lymilestone.postcodemrd.ui.adapter.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lymilestone.postcodemrd.modle.response.news.NewsChannelTable;
import com.lymilestone.postcodemrd.modle.response.news.NewsSummary;

import java.util.List;

/**
 * Created by CodeManLY on 2017/7/24 0024.
 */

public class NewsFragmentVPAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList;
    List<NewsChannelTable> summaryList;

    public NewsFragmentVPAdapter(FragmentManager fm, List<Fragment> fragmentList, List<NewsChannelTable> summaryList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.summaryList = summaryList;
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
        return summaryList.get(position).getNewsChannelName();
    }
}
