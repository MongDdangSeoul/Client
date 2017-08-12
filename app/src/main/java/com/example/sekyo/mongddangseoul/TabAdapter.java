package com.example.sekyo.mongddangseoul;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sekyo on 2017-08-12.
 */

public class TabAdapter extends FragmentPagerAdapter {
    int tabCount;
    NewsFeedFragment newsFeedFragment;
    RecommendFragment recommendFragment;
    MyPageFragment myPageFragment;

    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                newsFeedFragment = new NewsFeedFragment();
                return newsFeedFragment;
            case 1:
                recommendFragment = new RecommendFragment();
                return recommendFragment;
            case 2:
                myPageFragment = new MyPageFragment();
                return myPageFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return tabCount;
    }
}
