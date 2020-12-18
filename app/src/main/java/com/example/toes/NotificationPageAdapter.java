package com.example.toes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NotificationPageAdapter extends FragmentPagerAdapter {
    int tabCount;

    public NotificationPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

        tabCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TabRequestResponce();
            case 1:
                return new TabViewRequests();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
