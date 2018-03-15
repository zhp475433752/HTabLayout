package com.hpzhang.htablayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hpzhang on 2018/3/14.
 * 自动换行添加tab标签
 */

public class MyTabLayout extends ViewGroup {
    /**
     * int[0] 子控件left
     * int[1] 子控件top
     * int[2] 子控件right
     * int[3] 子控件bottom
     *
     * 存储每一个子控件坐标的集合
     */
    private List<int[]> childArr;

    public MyTabLayout(Context context) {
        super(context);
        init();
    }

    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        childArr = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 测量每个子控件的大小和模式
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // 子控件的个数
        int childCount = getChildCount();
        int left = 0;           // 当前的左边距
        int top = 0;            // 当前的上边距
        int totalHeight = 0;    // wrap_content时控件总高度
        int totalWidth = 0;     // wrap_content时控件总宽度

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) child.getLayoutParams();
            // 子控件的高度（基本都是一样高的，且多次使用，因此写到这里。下同）
            int childHeight = layoutParams.topMargin + child.getMeasuredHeight() + layoutParams.bottomMargin;
            // 子控件的宽度
            int childWidth = layoutParams.leftMargin + child.getMeasuredWidth() + layoutParams.rightMargin;
            // 该容器的总宽度
            int measuredWidth = getMeasuredWidth();

            // 该行所有子控件所占的宽度 大于 该容器的总宽度，则换行
            if (left + childWidth > measuredWidth) {
                left = 0;
                top += childHeight;
            }

            int[] arr = new int[4];
            // left
            arr[0] = left + layoutParams.leftMargin;
            // top
            arr[1] = top + layoutParams.topMargin;
            // right
            arr[2] = left + childWidth - layoutParams.rightMargin;// 因为childWidth里面有一个右边距,所以需要减掉，也可以写为 left + layoutParams.leftMargin + child.getMeasuredWidth()。下同
            // bottom
            arr[3] = top + childHeight - layoutParams.bottomMargin;

            childArr.add(arr);

            left += childWidth;
            // 计算最大宽度
            if (left > totalWidth) {
                totalWidth = left;
            }
            totalHeight = top + childHeight;

            int height = 0;
            int width = 0;
            if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {// 精确高度
                height = MeasureSpec.getSize(heightMeasureSpec);
            } else {
                height = totalHeight;
            }
            if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {// 精确宽度
                width = MeasureSpec.getSize(widthMeasureSpec);
            } else {
                width = totalWidth;// 也可以直接使用该容器的宽度 measuredWidth
            }

            setMeasuredDimension(width, height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 获取所有的子控件个数
        int childCount = getChildCount();
        for (int k = 0; k < childCount; k++) {
            int[] pArr = childArr.get(k);
            View child = getChildAt(k);
            child.layout(pArr[0], pArr[1], pArr[2], pArr[3]);
        }
    }
}
