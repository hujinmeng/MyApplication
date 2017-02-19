package com.hjm.simple;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hjm.bottomtabbar.BottomTabBar;
import com.hjm.simple.fragment.FourFragment;
import com.hjm.simple.fragment.OneFragment;
import com.hjm.simple.fragment.ThreeFragment;
import com.hjm.simple.fragment.TwoFragment;

public class MainActivity extends AppCompatActivity {

    private BottomTabBar mBottomTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomTabBar = (BottomTabBar) findViewById(R.id.bottom_tab_bar);
        mBottomTabBar.init(getSupportFragmentManager())
                .setImgSize(90, 90)
                .setFontSize(12)
                .setTabPadding(4, 6, 10)
                .setChangeColor(Color.GREEN, Color.RED)
                .addTabItem("第一项", R.mipmap.ic_launcher, OneFragment.class)
                .addTabItem("第二项", R.mipmap.ic_launcher, TwoFragment.class)
                .addTabItem("第三项", R.mipmap.ic_launcher, ThreeFragment.class)
                .addTabItem("第四项", R.mipmap.ic_launcher, FourFragment.class)
                .setTabBarBackgroundColor(Color.WHITE)
                .isShowDivider(false);
    }
}
