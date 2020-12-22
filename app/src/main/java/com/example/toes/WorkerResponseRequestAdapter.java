package com.example.toes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class WorkerResponseRequestAdapter extends FragmentPagerAdapter {
    int workerTabCount;
    public WorkerResponseRequestAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        workerTabCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TabWorkerRequestResponse();
            case 1:
                return new TabWorkerViewRequest();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return workerTabCount;
    }
}
