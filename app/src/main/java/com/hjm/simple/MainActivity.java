package com.hjm.simple;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

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
                .setCurrentTab(2)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name, View view) {
                        Log.i("TGA", "位置：" + position + "      选项卡：" + name);
                        if (position == 2) {
                            showTree(view);
                        }
                    }
                });
    }

    private void showTree(View view) {
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(R.layout.test_pop, null, false);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        PopupWindow window = new PopupWindow(popupWindow_view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);
        // 在底部显示
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);


        popupWindow_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomBar.setCurrentTab(0);
            }
        });
    }


}
