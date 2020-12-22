package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class WokerNotificationActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    TabItem tabItem1, tabItem2;
    private ViewPager viewPager;
    private WorkerResponseRequestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woker_notification);

        tabLayout = findViewById(R.id.WorkerTabLayout);
        viewPager = findViewById(R.id.wvPager);
        tabItem1 = findViewById(R.id.Worker_Response_Request);
        tabItem2 = findViewById(R.id.Worker_View_Request);
        adapter = new WorkerResponseRequestAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 0 || tab.getPosition() == 1)
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}