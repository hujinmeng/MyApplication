package com.hjm.simple;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        mBottomBar = (BottomTabBar) findViewById(R.id.bottom_bar);

        mBottomBar.init(getSupportFragmentManager())
                .setImgSize(90, 90)
                .setFontSize(12)
                .setTabPadding(4, 6, 10)
                .setChangeColor(Color.GREEN, Color.RED)
                .addTabItem("第一项", R.mipmap.erweima, R.mipmap.ic_launcher, OneFragment.class)
                .addTabItem("第二项", R.mipmap.erweima, R.mipmap.ic_launcher, TwoFragment.class)
                .addTabItem("第三项", R.mipmap.ic_launcher, ThreeFragment.class)
                .addTabItem("第四项", R.mipmap.ic_launcher, FourFragment.class)
                .setTabBarBackgroundResource(R.mipmap.ic_launcher)
                .isShowDivider(false)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name) {
                        Log.i("TGA", "位置：" + position + "      选项卡：" + name);
                    }
                });
    }

}
