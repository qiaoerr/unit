package com.start.jdzchina.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PopupOverlay;

/**
 * @author administrator
 * @version 创建时间：2013-7-21 下午03:59:50
 * 
 */
public class MyRouteMapView extends MapView {
	private PopupOverlay pop = null;// 弹出泡泡图层，浏览节点时使用

	public PopupOverlay getPop() {
		return pop;
	}

	public void setPop(PopupOverlay pop) {
		this.pop = pop;
	}

	public MyRouteMapView(Context arg0) {
		super(arg0);
	}

	public MyRouteMapView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean b = super.onTouchEvent(event);
		// System.out.println("super.onTouchEvent(event):" + b + "==="
		// + event.getAction());
		if (!b) {
			// 消隐泡泡
			if (pop != null && event.getAction() == MotionEvent.ACTION_UP)
				pop.hidePop();
		}
		return true;
	}
}
