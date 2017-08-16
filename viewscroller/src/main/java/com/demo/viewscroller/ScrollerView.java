package com.demo.viewscroller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by yangdi on 2017/8/16.
 * 利用Scroller实现类似ViewPager的滑动
 */

public class ScrollerView extends ViewGroup {

    private Scroller mScroller;
    // 设备屏幕的最小移动像素
    private int mTouchSlop;

    // 界面可滚动的左边界
    private int leftBorder;
    // 界面可滚动的右边界
    private int rightBorder;
    // 手指第一次按下去时的X坐标
    private float firstXDown;
    // 手指按下去后移动后的坐标
    private float moveX;

    private static String TAG = "ScrollerView";


    public ScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // Init
    private void init(Context context) {
        mScroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 对子View的进行测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 为ScrollerLayout中的每一个子控件测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 对容器中的子View进行布局安排
     *
     * @param changed
     * @param l       子View布局的左位置
     * @param t       子View布局的上位置
     * @param r       子View布局的右位置
     * @param b       子View布局的下位置
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                // 为ScrollerLayout中的控件在水平方向上进行布局
                // TODO childView.layout获取子View对象，当i为1时，0*childView.getWidth()值为零，第一个View布局的left坐标为0，
                // TODO 右坐标即为第一个View的宽度，即（0+1）* childView.getWidth()，一次类推，子View按水平方向在ViewGroup内
                // TODO 排列，top为0，bottom为子View的高度。
                // TODO: 2017/8/16 注： childView.getMeasuredWidth()和childView.getWidth()的区别：View.getWidth() ——> 在布局完成后获得的View的宽度，
                // TODO 在View未完成布局之前，该值为0；View.getMeasuredWidth() ——> 对View上的内容进行测量后得到的View内容占据的宽度、View需要的布局空间，前提是在这之前已调用measureview方法。
                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }
            // 初始化左右边界值
            leftBorder = getChildAt(0).getLeft() - 30;
            rightBorder = getChildAt(getChildCount() - 1).getRight() + 30;
        }
    }




    /**
     * View触摸事件拦截处理
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // TODO getRawX()和getX() ——> getRawX(),原始坐标，在ViewParent未添加任何View时的坐标值，也就是相对于屏幕原点的坐标值
                firstXDown = ev.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = ev.getRawX();
                float dis = Math.abs(moveX - firstXDown);
                // TODO 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                if (dis > mTouchSlop) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    /**
     * View触摸事件处理
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                firstXDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getRawX();
                Log.i(TAG, "onTouchEvent: getScrollX() : " + getScrollX());
                int scrollX = (int) (firstXDown - moveX);
                // TODO getScrollX() ——> View左上角点偏移的距离，也就是说，可以理解为左上角的坐标位置是在View上进行改变的，当view向左滑动时，getScrollX()为正值，反之为负值。
                // TODO getScrollX()指向所有子View的滑动偏移，不单单指向第一个子View，同样，一个ViewGroup有三个子View，那么getScrollX()发生偏移是三个子View的总和
                if (getScrollX() + scrollX < leftBorder) {
                    Log.i(TAG, "onTouchEvent: leftBorder() : " + leftBorder);
                    scrollTo(leftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrollX > rightBorder) {
                    // TODO 所有滑动坐标参考点（相对点）都是左上角位置
                    Log.i(TAG, "onTouchEvent: rightBorder : " + rightBorder);
                    Log.i(TAG, "onTouchEvent: getWidth() : " + getWidth());
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(scrollX, 0);
                firstXDown = moveX;
                break;
            case MotionEvent.ACTION_UP:
                // TODO 当手指离开屏幕时，根据当前的滚动值来判断滚动到哪个子View上面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();// 假设屏幕为1080x1920, 代码(getScrollX()+1080/2)/1080
                int dx = targetIndex * getWidth() - getScrollX();
                Log.i(TAG, "onTouchEvent: targetIndex : " + targetIndex);
                Log.i(TAG, "onTouchEvent: dx : " + dx);
                // TODO 调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                // TODO  invalidate()在UI线程中调用，非UI线程中刷新界面使用postinvalidate();
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * TODO computeScroll()方法，在其内部完成平滑滚动的逻辑
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
