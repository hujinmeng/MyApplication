package com.hjm.bottomtabbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.hjm.bottomtabbar.custom.CustomFragmentTabHost;
import com.hjm.bottomtabbar.util.TintUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjm on 2017/2/18/018.
 */
public class BottomTabBar extends LinearLayout {

    private Context context;
    private LinearLayout mLayout;
    //BottomTabBar整体布局，这里使用FragmentTabHost
    private CustomFragmentTabHost mTabHost;
    //分割线
    private View mDivider;

    //图片的宽高
    private float imgWidth = 0, imgHeight = 0;
    //文字尺寸
    private float fontSize = 14;
    //文字图片的间隔
    private float fontImgPadding;
    //上边距和下边距
    private float paddingTop, paddingBottom;
    //选中未选中的颜色
    private int selectColor = Color.parseColor("#F1453B"), unSelectColor = Color.parseColor("#626262");
    //分割线高度
    private float dividerHeight;
    //是否显示分割线
    private boolean isShowDivider = false;
    //分割线背景
    private int dividerBackgroundColor = Color.parseColor("#CCCCCC");
    //BottomTabBar的整体背景
    private int tabBarBackgroundColor = Color.parseColor("#FFFFFF");
    //tabId集合
    private List<String> tabIdList = new ArrayList<>();

    /**
     * Tab标签切换监听
     */
    public interface OnTabChangeListener {
        void onTabChange(int position, String name);
    }

    private OnTabChangeListener listener;

    public BottomTabBar(Context context) {
        super(context);
        this.context = context;
    }

    public BottomTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.BottomTabBar);
        if (attributes != null) {
            //图片宽度
            imgWidth = attributes.getDimension(R.styleable.BottomTabBar_tab_img_width, 0);
            //图片高度
            imgHeight = attributes.getDimension(R.styleable.BottomTabBar_tab_img_height, 0);
            //文字尺寸
            fontSize = attributes.getDimension(R.styleable.BottomTabBar_tab_font_size, 14);
            //上边距
            paddingTop = attributes.getDimension(R.styleable.BottomTabBar_tab_padding_top, dp2px(2));
            //图片文字间隔
            fontImgPadding = attributes.getDimension(R.styleable.BottomTabBar_tab_img_font_padding, dp2px(3));
            //下边距
            paddingBottom = attributes.getDimension(R.styleable.BottomTabBar_tab_padding_bottom, dp2px(5));
            //分割线高度
            dividerHeight = attributes.getDimension(R.styleable.BottomTabBar_tab_divider_height, dp2px(1));
            //是否显示分割线
            isShowDivider = attributes.getBoolean(R.styleable.BottomTabBar_tab_isshow_divider, false);
            //选中的颜色
            selectColor = attributes.getColor(R.styleable.BottomTabBar_tab_selected_color, Color.parseColor("#626262"));
            //未选中的颜色
            unSelectColor = attributes.getColor(R.styleable.BottomTabBar_tab_unselected_color, Color.parseColor("#F1453B"));
            //BottomTabBar的整体背景
            tabBarBackgroundColor = attributes.getColor(R.styleable.BottomTabBar_tab_bar_background, Color.parseColor("#FFFFFF"));
            //分割线背景
            dividerBackgroundColor = attributes.getColor(R.styleable.BottomTabBar_tab_divider_background, Color.parseColor("#CCCCCC"));

            attributes.recycle();
        }
    }

    /**
     * 初始化，主要是需要传入一个FragmentManager
     * <p>
     * 此方法必须在所有的方法之前调用
     *
     * @param manager
     * @return
     */
    public BottomTabBar init(FragmentManager manager) {
        mLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.bottom_tab_bar, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mLayout.setLayoutParams(layoutParams);
        addView(mLayout);

        mTabHost = (CustomFragmentTabHost) mLayout.findViewById(android.R.id.tabhost);
        mTabHost.setup(context, manager, R.id.realtabcontent);
        mTabHost.setBackgroundColor(tabBarBackgroundColor);
        mTabHost.getTabWidget().setDividerDrawable(null);
        tabIdList.clear();
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (listener != null) {
                    listener.onTabChange(tabIdList.indexOf(tabId), tabId);
                }
            }
        });

        mDivider = mLayout.findViewById(R.id.split);
        if (isShowDivider) {
            LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) dividerHeight);
            mDivider.setLayoutParams(dividerParams);
            mDivider.setBackgroundColor(dividerBackgroundColor);
            mDivider.setVisibility(VISIBLE);
        } else {
            mDivider.setVisibility(GONE);
        }

        return this;
    }

    public BottomTabBar setOnTabChangeListener(OnTabChangeListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        return this;
    }

    /**
     * 添加TabItem
     *
     * @param name          文字
     * @param imgId         图片id
     * @param fragmentClass fragment
     * @return
     */
    public BottomTabBar addTabItem(String name, int imgId, Class fragmentClass) {
        return addTabItem(name, ContextCompat.getDrawable(context, imgId), fragmentClass);
    }

    public BottomTabBar addTabItem(String name, int imgIdSelect, int imgIdUnSelect, Class fragmentClass) {
        return addTabItem(name, ContextCompat.getDrawable(context, imgIdSelect), ContextCompat.getDrawable(context, imgIdUnSelect), fragmentClass);
    }

    /**
     * 添加TabItem
     *
     * @param name          文字
     * @param drawable      图片
     * @param fragmentClass fragment
     * @return
     */
    public BottomTabBar addTabItem(final String name, Drawable drawable, Class fragmentClass) {
        tabIdList.add(TextUtils.isEmpty(name) ? fragmentClass.getName() : name);
        mTabHost.addTab(mTabHost.newTabSpec(TextUtils.isEmpty(name) ? fragmentClass.getName() : name)
                .setIndicator(getTabItemView(TextUtils.isEmpty(name) ? fragmentClass.getName() : name, drawable)), fragmentClass, null);
        return this;
    }

    public BottomTabBar addTabItem(final String name, Drawable drawableSelect, Drawable drawableUnSelect, Class fragmentClass) {
        tabIdList.add(TextUtils.isEmpty(name) ? fragmentClass.getName() : name);
        mTabHost.addTab(mTabHost.newTabSpec(TextUtils.isEmpty(name) ? fragmentClass.getName() : name)
                .setIndicator(getTabItemView(TextUtils.isEmpty(name) ? fragmentClass.getName() : name, drawableSelect, drawableUnSelect)), fragmentClass, null);
        return this;
    }

    private View getTabItemView(String name, Drawable drawable) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item, null);

        ImageView iv = (ImageView) view.findViewById(R.id.tab_item_iv);
        LayoutParams ivParams = new LayoutParams(imgWidth == 0 ? LayoutParams.WRAP_CONTENT : (int) imgWidth, imgHeight == 0 ? LayoutParams.WRAP_CONTENT : (int) imgHeight);
        ivParams.topMargin = (int) paddingTop;
        ivParams.gravity = Gravity.CENTER_HORIZONTAL;
        iv.setLayoutParams(ivParams);
        iv.setImageDrawable(TintUtil.setStateListTintColor(drawable, unSelectColor, selectColor));

        TextView tv = (TextView) view.findViewById(R.id.tab_item_tv);
        tv.setText(name);
        tv.setPadding(0, (int) fontImgPadding, 0, (int) paddingBottom);
        tv.setTextSize(fontSize);
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{};
        int[] colors = new int[]{selectColor, selectColor, unSelectColor};//colorSelect跟states[0]对应，color跟states[2]对应，以此类推
        ColorStateList csl = new ColorStateList(states, colors);
        tv.setTextColor(csl);

        return view;
    }

    private View getTabItemView(String name, Drawable drawableSelect, Drawable drawableUnSelect) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item, null);

        ImageView iv = (ImageView) view.findViewById(R.id.tab_item_iv);
        LayoutParams ivParams = new LayoutParams(imgWidth == 0 ? LayoutParams.WRAP_CONTENT : (int) imgWidth, imgHeight == 0 ? LayoutParams.WRAP_CONTENT : (int) imgHeight);
        ivParams.topMargin = (int) paddingTop;
        ivParams.gravity = Gravity.CENTER_HORIZONTAL;
        iv.setLayoutParams(ivParams);

        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed},
                drawableSelect);
        drawable.addState(new int[]{android.R.attr.state_selected},
                drawableSelect);
        drawable.addState(new int[]{},
                drawableUnSelect);
        iv.setImageDrawable(drawable);

        TextView tv = (TextView) view.findViewById(R.id.tab_item_tv);
        tv.setText(name);
        tv.setPadding(0, (int) fontImgPadding, 0, (int) paddingBottom);
        tv.setTextSize(fontSize);
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{};
        int[] colors = new int[]{selectColor, selectColor, unSelectColor};//colorSelect跟states[0]对应，color跟states[2]对应，以此类推
        ColorStateList csl = new ColorStateList(states, colors);
        tv.setTextColor(csl);

        return view;
    }

    //=========================参数设置START========================================

    /**
     * 设置图片的尺寸
     * <p>
     * 此方法必须在addTabItem()之前调用
     *
     * @param width  宽度 px
     * @param height 高度 px
     * @return
     */
    public BottomTabBar setImgSize(float width, float height) {
        this.imgWidth = width;
        this.imgHeight = height;
        return this;
    }

    /**
     * 设置文字的尺寸
     * <p>
     * 此方法必须在addTabItem()之前调用
     *
     * @param textSize 文字的尺寸 sp
     * @return
     */
    public BottomTabBar setFontSize(float textSize) {
        this.fontSize = textSize;
        return this;
    }

    /**
     * 设置Tab的padding值
     * <p>
     * 此方法必须在addTabItem()之前调用
     *
     * @param top    上边距 px
     * @param middle 文字图片的距离 px
     * @param bottom 下边距 px
     * @return
     */
    public BottomTabBar setTabPadding(float top, float middle, float bottom) {
        this.paddingTop = top;
        this.fontImgPadding = middle;
        this.paddingBottom = bottom;
        return this;
    }

    /**
     * 设置选中未选中的颜色
     * <p>
     * 此方法必须在addTabItem()之前调用
     *
     * @param selectColor   选中的颜色
     * @param unSelectColor 未选中的颜色
     * @return
     */
    public BottomTabBar setChangeColor(@ColorInt int selectColor, @ColorInt int unSelectColor) {
        this.selectColor = selectColor;
        this.unSelectColor = unSelectColor;
        return this;
    }

    /**
     * 设置BottomTabBar的整体背景
     *
     * @param color 背景颜色
     * @return
     */
    public BottomTabBar setTabBarBackgroundColor(@ColorInt int color) {
        this.tabBarBackgroundColor = color;
        mTabHost.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置BottomTabBar的整体背景
     *
     * @param resid 背景图片id
     * @return
     */
    public BottomTabBar setTabBarBackgroundResource(@DrawableRes int resid) {
        mTabHost.setBackgroundResource(resid);
        return this;
    }
//    /**
//     * 设置BottomTabBar的整体背景
//     * api 16开始才支持
//     *
//     * @param drawable 背景图片
//     * @return
//     */
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    public BottomTabBar setTabBarBackgroundResource(Drawable drawable){
//        mTabHost.setBackground(drawable);
//        return this;
//    }

    /**
     * 是否显示分割线
     *
     * @param isShowDivider
     * @return
     */
    public BottomTabBar isShowDivider(boolean isShowDivider) {
        this.isShowDivider = isShowDivider;
        if (isShowDivider) {
            LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) dividerHeight);
            mDivider.setLayoutParams(dividerParams);
            mDivider.setBackgroundColor(dividerBackgroundColor);
            mDivider.setVisibility(VISIBLE);
        } else {
            mDivider.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 设置分割线的高度
     *
     * @param height
     * @return
     */
    public BottomTabBar setDividerHeight(float height) {
        this.dividerHeight = height;
        if (isShowDivider) {
            LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) dividerHeight);
            mDivider.setLayoutParams(dividerParams);
        }
        return this;
    }

    /**
     * 设置分割线的颜色
     *
     * @param color
     * @return
     */
    public BottomTabBar setDividerColor(@ColorInt int color) {
        this.dividerBackgroundColor = color;
        if (isShowDivider) {
            mDivider.setBackgroundColor(dividerBackgroundColor);
        }
        return this;
    }

    //=========================参数设置END===========================================

    //=========================工具类START========================================

    /**
     * dp转px
     *
     * @param dpValue
     * @return
     */
    private int dp2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    //=========================工具类END===========================================
}
