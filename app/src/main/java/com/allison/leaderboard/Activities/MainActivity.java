package com.allison.leaderboard.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.allison.leaderboard.CustomAdapters.ViewPagerAdapter;
import com.allison.leaderboard.R;
import com.allison.leaderboard.Fragments.HourFragment;
import com.allison.leaderboard.Fragments.SkillFragment;
import com.google.android.material.tabs.TabLayout;



public class MainActivity extends AppCompatActivity {

    private ViewPager mPager;
    private Toolbar mToolbar;
    private Button mButton;
    private ViewPagerAdapter mViewPagerAdapter;
    private SkillFragment skillFragment;
    private HourFragment hourFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mButton= findViewById(R.id.button_submit);
        mButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SubmitActivity.class));
            }
        });

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);


        skillFragment = new SkillFragment();
        hourFragment = new HourFragment();
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setSelectedTabIndicatorColor(getColor(R.color.colorBG));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        viewPagerAdapter.addFragment(hourFragment , "Learning Leaders");
        viewPagerAdapter.addFragment(skillFragment, "Skill IQ Leaders");

        viewPager.setAdapter(viewPagerAdapter);


    }
}