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

        mBottomBar.init(getSupportFragmentManager(), 750, 1334)
//                .setImgSize(50, 50)
//                .setFontSize(28)
//                .setTabPadding(10, 6, 4)
//                .setChangeColor(Color.parseColor("#2784E7"),Color.parseColor("#282828"))
                .addTabItem("首页", R.mipmap.ic_common_tab_index_select, R.mipmap.ic_common_tab_index_unselect, OneFragment.class)
                .addTabItem("热点", R.mipmap.ic_common_tab_hot_select, R.mipmap.ic_common_tab_hot_unselect, TwoFragment.class)
                .addTabItem("发布", R.mipmap.ic_common_tab_publish_select, R.mipmap.ic_common_tab_publish_unselect, ThreeFragment.class)
                .addTabItem("我的", R.mipmap.ic_common_tab_user_select, R.mipmap.ic_common_tab_user_unselect, FourFragment.class)
//                .isShowDivider(true)
//                .setDividerColor(Color.parseColor("#373737"))
//                .setTabBarBackgroundColor(Color.parseColor("#FFFFFF"))
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

    public void setShowTabBar(boolean isShow){
        if (isShow){
            mBottomBar.getTabBar().setVisibility(View.VISIBLE);
        }else {
            mBottomBar.getTabBar().setVisibility(View.GONE);
        }
    }

}
