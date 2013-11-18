package com.tixa.industry.model;

import com.tixa.industry.config.Constants;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 拉伸比例
 * @author shengy
 *
 */
public class Zoom {
	private double zoomX; //横向拉伸比例
	private double zoomY; //纵向拉伸比例
	private double surplusX; //横向多余出像素
	private double surplusY; //纵向多余出像素
	private int surplus; // 1 X 2 Y
	
	public Zoom(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int width = dm.widthPixels; //屏幕宽度
		int height = dm.heightPixels; //屏幕高度
		double currentAspectRatio =  width/height; //当前屏幕的比例
		double templateAspectRatio = Constants.WIDTH / Constants.HEIGHT; //模版屏幕的比例
		
		if(currentAspectRatio > templateAspectRatio) { //按模版等比例 拉伸 宽度过宽有剩余			
			surplusX = (width - width * templateAspectRatio)/2 ; //横向偏移量
			surplusY = 0;			
		}else if(currentAspectRatio == templateAspectRatio) {
			surplusX = 0;
			surplusY = 0;
		} else { //按模版等比例 拉伸 高度过长有剩余
			surplusX = 0;
			surplusY = (height - width/templateAspectRatio)/2 ; //纵向偏移量	
		}
		
	} 
	
}
