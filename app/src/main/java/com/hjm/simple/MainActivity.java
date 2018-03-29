package com.hjm.simple;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.hjm.bottomtabbar.BottomTabBar;
import com.hjm.simple.fragment.FourFragment;
import com.hjm.simple.fragment.OneFragment;
import com.hjm.simple.fragment.ThreeFragment;
import com.hjm.simple.fragment.TwoFragment;

public class MainActivity extends AppCompatActivity {

    private BottomTabBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_main);
        mBottomBar = findViewById(R.id.bottom_bar);

        mBottomBar.init(getSupportFragmentManager(), 720, 1280)
//                .setImgSize(70, 70)
//                .setFontSize(14)
//                .setTabPadding(5, 0, 5)
//                .setChangeColor(Color.parseColor("#FF00F0"),Color.parseColor("#CCCCCC"))
                .addTabItem("第一项", R.mipmap.home_selected, R.mipmap.home, OneFragment.class)
                .addTabItem("第二项", R.mipmap.list, TwoFragment.class)
                .addTabItem("第三项", R.mipmap.user, ThreeFragment.class)
//                .isShowDivider(true)
//                .setDividerColor(Color.parseColor("#FF0000"))
//                .setTabBarBackgroundColor(Color.parseColor("#00FF0000"))
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name, View view) {
                        if (position == 1)
                            mBottomBar.setSpot(1, false);
                    }
                })
                .setSpot(1, true)
                .setSpot(2, true);
    }

}
