package com.example.myapplication;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FirstAidContentAdapter extends FragmentStatePagerAdapter {

    int tabno;
    public FirstAidContentAdapter(FragmentManager fm, int t)
    {
        super(fm);
        tabno=t;
    }
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0: FirstAidTips firstAidTips= new FirstAidTips();
                return firstAidTips;
            case 1: FirstAidOffline firstAidOffline= new FirstAidOffline();
                return firstAidOffline;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabno;
    }

}
