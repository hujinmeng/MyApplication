package com.hjm.bottomtabbar.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by hjm on 2016/9/4.
 * <p/>
 * 切记：ImageView一定要设为可点击的
 */
public class TintUtil {

    //===================设置ImageView的Tint==========================

    //第一种：API15之后可以直接在xml文件中设置tint，兼容4.0.3 ~ 6.0

    //第二种：传入ColorId

    /**
     * @param context
     * @param drawable 图片转换成的Drawable
     * @param colorId  颜色资源的id
     * @return
     */
    public static Drawable setTint(Context context, Drawable drawable, int colorId) {
        //return getTintDrawable(drawable, context.getResources().getColor(colorId));
        return getTintDrawable(drawable, ContextCompat.getColor(context, colorId));
    }

    //第三种：传入Color或者“#FFFFFF”

    /**
     * @param drawable 同上
     * @param color    颜色，eg:Color.paserColor("#FF0000") || Color.Black || Color.rgb(0,0,0)....
     * @return
     */
    public static Drawable setTint(Drawable drawable, @ColorInt int color) {
        return getStateDrawable(drawable, ColorStateList.valueOf(color));
    }

    /**
     * @param drawable 同上
     * @param colorStr eg:#FF0000
     * @return
     */
    public static Drawable setTint(Drawable drawable, String colorStr) {
        return getStateDrawable(drawable, ColorStateList.valueOf(Color.parseColor(colorStr)));
    }

    //===================设置ImageView的Selector.Tint==========================

    //第一种：推荐使用，兼容6.0及其以下版本，传入ColorId、Color或者“#FFFFFF”

    /**
     * @param context
     * @param drawable      同上
     * @param colorId       样式同上，这是未选择的
     * @param colorSelectId 样式同上，这是选择的
     * @return
     */
    public static Drawable setStateListTintColorId(Context context, Drawable drawable, int colorId, int colorSelectId) {
        return setStateListTintColor(drawable, ContextCompat.getColor(context, colorSelectId), ContextCompat.getColor(context, colorId));
    }

    /**
     * @param drawable    同上
     * @param color       样式同上，这是未选择的
     * @param colorSelect 样式同上，这是选择的
     * @return
     */
    public static Drawable setStateListTintColor(Drawable drawable, @ColorInt int color, @ColorInt int colorSelect) {
        int[] colors = new int[]{colorSelect, colorSelect, color};
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{};
        StateListDrawable stateListDrawable = getStateListDrawable(drawable, states);
        ColorStateList colorList = new ColorStateList(states, colors);
        return getStateDrawable(stateListDrawable, colorList);
    }

    /**
     * @param drawable       同上
     * @param colorStr       样式同上，这是未选择的
     * @param colorSelectStr 样式同上，这是选择的
     * @return
     */
    public static Drawable setStateListTintColorStr(Drawable drawable, String colorStr, String colorSelectStr) {
        return setStateListTintColor(drawable, Color.parseColor(colorSelectStr), Color.parseColor(colorStr));
    }

    //第二种：只兼容6.0以下的版本，只做介绍，不推荐使用，传入ColorId、Color或者“#FFFFFF”

    /**
     * @param context
     * @param drawable
     * @param colorId       样式同上，这是未选择的
     * @param colorSelectId 样式同上，这是选择的
     * @return
     */
    public static Drawable setStateListTint_low_6_0_ColorId(Context context, Drawable drawable, int colorId, int colorSelectId) {
        int[] colors = new int[]{ContextCompat.getColor(context, colorSelectId), ContextCompat.getColor(context, colorId)};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return getStateDrawable(drawable, colorList);
    }

    /**
     * @param drawable
     * @param color       样式同上，这是未选择的
     * @param colorSelect 样式同上，这是选择的
     * @return
     */
    public static Drawable setStateListTint_low_6_0_Color(Drawable drawable, int color, int colorSelect) {
        int[] colors = new int[]{colorSelect, color};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return getStateDrawable(drawable, colorList);
    }

    /**
     * @param drawable
     * @param colorStr       样式同上，这是未选择的
     * @param colorSelectStr 样式同上，这是选择的
     * @return
     */
    public static Drawable setStateListTint_low_6_0_ColorStr(Drawable drawable, String colorStr, String colorSelectStr) {
        int[] colors = new int[]{Color.parseColor(colorSelectStr), Color.parseColor(colorStr)};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return getStateDrawable(drawable, colorList);
    }

    //第三种：只兼容6.0以下的版本，只做介绍，不推荐使用，传入一个selector的颜色选择器(value文件夹下新建一个color文件夹，在color文件夹下创建这个selector)
    //    <selector xmlns:android="http://schemas.android.com/apk/res/android">
    //        <item android:color="#FF4081" android:state_pressed="true" />
    //        <item android:color="#3F51B5" />
    //    </selector>

    /**
     * @param context
     * @param drawable
     * @param id       eg:R.color.abc,文件位置在value下的color中
     * @return
     */
    public static Drawable setStateListTint_low_6_0(Context context, Drawable drawable, int id) {
        return getStateDrawable(drawable, context.getResources().getColorStateList(id));
    }

    //===================内部处理方法，不对外开放==================================

    public static Drawable getTintDrawable(Drawable drawable, int color) {
        Drawable.ConstantState state = drawable.getConstantState();
        Drawable wrappenDrawable = DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
        wrappenDrawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        DrawableCompat.setTint(wrappenDrawable, color);
        return wrappenDrawable;
    }

    public static Drawable getStateDrawable(Drawable drawable, ColorStateList colorStateList) {
        Drawable.ConstantState state = drawable.getConstantState();
        Drawable wrappenDrawable = DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
        DrawableCompat.setTintList(wrappenDrawable, colorStateList);
        return wrappenDrawable;
    }

    @NonNull
    public static StateListDrawable getStateListDrawable(Drawable drawable, int[][] states) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        for (int[] state : states) {
            stateListDrawable.addState(state, drawable);
        }
        return stateListDrawable;
    }
}
