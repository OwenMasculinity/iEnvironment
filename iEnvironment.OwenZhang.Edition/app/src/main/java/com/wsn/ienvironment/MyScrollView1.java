package com.wsn.ienvironment;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
/*
*@author张凌霄
*设置OnScrollChangedListener接口监听，获取ScrollView当前位置
 */
public class MyScrollView1 extends ScrollView {
    public MyScrollView1(Context context) {
        super(context);
    }

    public MyScrollView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);  
    }  

    public interface OnScrollChangedListener {  
        void onScrollChanged(ScrollView who, int x, int y, int oldx, int oldy);
    }  
  
    private OnScrollChangedListener mOnScrollChangedListener;  
  
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {  
        super.onScrollChanged(x, y, oldx, oldy);  
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, x, y, oldx, oldy);  
        }  
    }  
  
    /**
     * @param listener
     */
    public void setOnScrollChangedListener(OnScrollChangedListener listener) {  
        mOnScrollChangedListener = listener;  
    }  
}
