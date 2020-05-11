package com.glodon.tool;

import android.content.Context;

/**
 * Created by zhangtianjie
 *
 * @email zhangtj-a@glodon.com
 * @date 2019/5/11 8:24 PM
 **/
public class DensityUtils {
    /**
     * 四舍五入
     */
    private static final float DOT_FIVE = 0.5f;

    private DensityUtils() {
    }


    /**
     * dip转换成px
     *
     * @param context Context
     * @param dip     dip Value
     * @return 换算后的px值
     */
    public static int dip2px(Context context, float dip) {
        float density = getDensity(context);
        return (int) (dip * density + DensityUtils.DOT_FIVE);
    }


    /**
     * 得到显示密度
     *
     * @param context Context
     * @return 密度
     */
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }


}
