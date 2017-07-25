package com.lymilestone.postcodemrd.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;


import com.lymilestone.httplibrary.rx.bus.BusFactory;
import com.lymilestone.httplibrary.rx.bus.EventSubscribe;
import com.lymilestone.httplibrary.rx.bus.inner.EventThread;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.httplibrary.utils.log.LogUtils;
import com.lymilestone.httplibrary.utils.manager.AppManager;
import com.lymilestone.httplibrary.utils.manager.AppUtils;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.base.activity.CommonActivity;
import com.lymilestone.postcodemrd.base.mvp.MBaseActivity;
import com.lymilestone.postcodemrd.modle.event.DrawerEvent;
import com.lymilestone.postcodemrd.ui.fragment.girls.GilsFragment;
import com.lymilestone.postcodemrd.ui.fragment.joke.JokeFragment;
import com.lymilestone.postcodemrd.ui.fragment.news.NewsFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import butterknife.BindView;

public class MainActivity extends CommonActivity {

    @BindView(R.id.header_toolbar)
    Toolbar headerToolbar;
    @BindView(R.id.main_content)
    FrameLayout mainContent;
    @BindView(R.id.main_nav)
    NavigationView mainNav;
    @BindView(R.id.main_drawer)
    DrawerLayout mainDrawer;
    private FragmentTransaction fragmentTransaction;
    private JokeFragment jokeFragment;
    private GilsFragment gilsFragment;
    private NewsFragment newsFragment;
    private Fragment showFragment;
    private Fragment hideFragment;
    private String fragmentTag;
    private List<Fragment> fragments;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initBefore() {
        super.initBefore();
        getActivityComponent().inject(this);
        BusFactory.getBus().register(this);
    }

    @Override
    public void initView() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mainDrawer, headerToolbar, R.string.open, R.string.close);
        drawerToggle.syncState();
        mainDrawer.addDrawerListener(drawerToggle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        setToolBar(headerToolbar, "主页").setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainDrawer.openDrawer(Gravity.START);
            }
        });
    }

    @Override
    public void fetchData() {
        super.fetchData();

        fragments = new ArrayList<>();
        jokeFragment = new JokeFragment();
        gilsFragment = new GilsFragment();
        newsFragment = new NewsFragment();
        fragments.add(newsFragment);
        fragments.add(jokeFragment);
        fragments.add(gilsFragment);
        addFragment(fragments);
        showFragment = jokeFragment;
        fragmentTag = jokeFragment.getClass().getSimpleName();
        hideFragment = showFragment;
        fragmentTransaction.show(showFragment).commit();
    }

    @EventSubscribe(thread = EventThread.MAIN_THREAD)
    public void drawerState(DrawerEvent drawerEvent){
        if (!drawerEvent.isOpen()){
            mainDrawer.closeDrawer(Gravity.START);
        }else {
            mainDrawer.openDrawer(Gravity.START);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusFactory.getBus().unregister(this);
    }

    @Override
    public void initListener() {
        super.initListener();
        mainNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_joke:
                        showFragment = jokeFragment;
                        showToolBar();
                        fragmentTag = jokeFragment.getClass().getSimpleName();
                        break;
                    case R.id.menu_other:
                        showFragment = gilsFragment;
                        showToolBar();
                        fragmentTag = gilsFragment.getClass().getSimpleName();
                        break;
                    case R.id.menu_news:
                        showFragment = newsFragment;
                        hideToolBar();
                        fragmentTag = newsFragment.getClass().getSimpleName();
                        break;
                    default:
                        break;
                }
                LUtils.e(fragmentTag);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (!hideFragment.equals(showFragment)) {
                    fragmentTransaction.hide(hideFragment);
                    fragmentTransaction.show(showFragment);
                    fragmentTransaction.commit();
                }
                mainDrawer.closeDrawers();//关闭抽屉
                hideFragment = showFragment;//显隐切换
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mainDrawer.isDrawerOpen(Gravity.START)){
            mainDrawer.closeDrawer(Gravity.START);
        }
        if (AppUtils.exitTwice()) {
            AppManager.exitApp();
        } else {
            toast("再按一次退出程序");
        }
    }

    public void addFragment(List<Fragment> fragments) {
        for (int i = 0; i < fragments.size(); i++) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = fragments.get(i);
            fragmentTransaction.add(R.id.main_content, fragment, fragment.getClass().getSimpleName())
                    .hide(fragment)//必须hide，否则在此项目中，无视懒加载
                    .commit();
        }
    }


}
