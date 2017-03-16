package com.ytrain.mutrain.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyListview extends ListView{
	/**
	 * 自定义listview，解决和scroolview冲突问题。
	 * @param context
	 */
	  
    
    public MyListview(Context context) {  
        super(context);  
    }  
  
    public MyListview(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public MyListview(Context context, AttributeSet attrs,  
            int defStyle) {  
        super(context, attrs, defStyle);  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
                MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
    }  

}
