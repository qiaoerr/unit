package com.tixa.industry.util;


import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Block;
import com.tixa.industry.model.Button;
import com.tixa.industry.model.ZoomAd;
import com.tixa.industry.model.ZoomButton;
import com.tixa.industry.model.ZoomLogo;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;


public class ZoomUtil {	
	private int standardX; //标准比例下的宽度
	private int standardY; //标准比例下的高度
	private double zoomX; //横向拉伸比例
	private double zoomY; //纵向拉伸比例
	private double surplusX; //横向多余出像素
	private double surplusY; //纵向多余出像素
	private double originalZoomX; //原始高度的拉伸比
	private double originalZoomY; //原始高度的拉伸比
	
	public ZoomUtil(Context context) {
	
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int width = dm.widthPixels; //屏幕宽度
		int height = dm.heightPixels; //屏幕高度
		
		height = height - CommonUtil.dip2px(context, 75); 
		
		double currentAspectRatio =  (double)width / height; //当前屏幕的比例
		double templateAspectRatio = Constants.WIDTH / Constants.HEIGHT; //模版屏幕的比例
			
		if(currentAspectRatio > templateAspectRatio) { //按模版等比例 拉伸 宽度过宽有剩余			
			surplusX = (width - height * templateAspectRatio)/2 ; //横向偏移量
			surplusY = 0;
			standardX = (int)(height * templateAspectRatio);
			standardY = height;
			
		}else if(currentAspectRatio == templateAspectRatio) {
			surplusX = 0;
			surplusY = 0;
			standardX = width;
			standardY = height;
			
		} else { //按模版等比例 拉伸 高度过长有剩余
			surplusX = 0;
			surplusY = (height - width/templateAspectRatio)/2 ; //纵向偏移量
			standardX = width;
			standardY = (int)(width/templateAspectRatio);
		}
		
		zoomX = standardX / Constants.WIDTH ;  
		zoomY = standardY / Constants.HEIGHT ;
		originalZoomY = height / Constants.HEIGHT;
	}	
	
	/**
	 * 格式化广告
	 * @param block
	 * @return
	 */
	public ZoomAd formatAd(Block block) {
		String adStr = block.getBanner().getFrame().trim();
		double[] adPara = parserAndConvert(adStr);
		double originalOffsetY = adPara[1]*zoomY;
		int logoMarginLeft = (int)(adPara[0] * zoomX + surplusX) ; //x方向偏移量
		int logoMarginTop = (int) (adPara[1] * zoomY + surplusY) ; //y方向偏移量
		int logoWidth = (int) (adPara[2] * zoomX); //广告宽度
		int logoHight = (int) (adPara[3] * zoomY); //广告高度
		
		logoMarginTop = (int)(adPara[1] * originalZoomY); //顶部位置不加偏移量 适配固定背景图
		
		if(originalOffsetY == 0) {
			logoMarginTop = 0;
		}
		
		if(logoMarginLeft < 0) {
			logoMarginLeft = 0;
		}
		
		if(logoMarginTop < 0) {
			logoMarginTop = 0;
		}		
		ZoomAd ad = new ZoomAd();
		ad.setLogoHight(logoHight);
		ad.setLogoMarginLeft(logoMarginLeft);
		ad.setLogoMarginTop(logoMarginTop);
		ad.setLogoWidth(logoWidth);		
		return ad;		
	}
	
	
	/**
	 * 格式化logo
	 * @return
	 */
	public ZoomLogo formatLogo(Block block) {
		String iconStr = block.getIcon().getFrame().trim();
		double[] iconPara = parserAndConvert(iconStr);
		double originalOffsetY = iconPara[1]*zoomY; 		
		int logoMarginLeft = (int)(iconPara[0] * zoomX + surplusX) ; //x方向偏移量
		int logoMarginTop = (int) (iconPara[1] * zoomY + surplusY) ; //y方向偏移量
		int logoWidth = (int) (iconPara[2] * zoomX); //logo宽度
		int logoHight = (int) (iconPara[3] * zoomY); //logo高度
		
		logoMarginTop = (int)(iconPara[1] * originalZoomY);
		
		if(originalOffsetY == 0) {
			logoMarginTop = 0;
		}
		
		if(logoMarginLeft < 0) {
			logoMarginLeft = 0;
		}
		
		if(logoMarginTop < 0) {
			logoMarginTop = 0;
		}
		
		ZoomLogo logo = new ZoomLogo();
		logo.setLogoHight(logoHight);
		logo.setLogoMarginLeft(logoMarginLeft);
		logo.setLogoMarginTop(logoMarginTop);
		logo.setLogoWidth(logoWidth);

		return logo;			
	}
	
	
	/**
	 * 格式化button的距离 
	 * @param button 本次的button
	 * @param preButton 上一个button 解决button不能无间隔显示问题
	 * @return 
	 */
	public ZoomButton formatButton(Button button , ZoomButton preButton ,Button pButton) {
		ZoomButton zoom = new ZoomButton();
		String btnStr = button.getFrame().trim();
		double[] btnPara = parserAndConvert(btnStr);
		
		double originalOffsetX = btnPara[0]; //原始配置文件中的偏移量
		double originalOffsetY = btnPara[1]; 
		double originalbuttonWidth = btnPara[2];	
		double originalbuttonHeight = btnPara[3];
		
		int offsetX = (int)(originalOffsetX * zoomX + surplusX); //距离左边框距离 像素px
		//int offsetY = (int)(originalOffsetY * zoomY + surplusY); //距离上边框距离 像素px
		int buttonWidth = (int)(originalbuttonWidth * zoomX) ; //按钮宽度
		int buttonHeight = (int)(originalbuttonHeight * zoomY) ;//按钮的高 px		
		int oButtonHeight = (int)(originalbuttonHeight * originalZoomY) ;//原始按钮的高 px		
		int offsetY = (int)(originalOffsetY * originalZoomY);		

		if((int)originalOffsetY != 0) { //当配置文件中配置按钮在最顶端时，也显示在最顶端
			offsetY = offsetY + (oButtonHeight - buttonHeight);
		}
		
		//当上下button据top的距离差。小于等于button的高度时，去掉button间的间隔
		if(preButton != null) {
			int proOriginalOffsetY = preButton.getOffsetY();	
			if(pButton !=null) {
				String pBtnStr = pButton.getFrame().trim();
				double[] pBtnPara = parserAndConvert(pBtnStr);
				double pOriginalOffsetY = pBtnPara[1];				
				//当两个button的高度差大于10像素时才处理
				//小于10像素默认为同一行的button
				if((int)(originalOffsetY - pOriginalOffsetY)>= 10 && 
						(int)(originalOffsetY - pOriginalOffsetY) <= originalbuttonHeight) {
					offsetY = proOriginalOffsetY + buttonHeight;
				}else if((int)(originalOffsetY - pOriginalOffsetY) == 0) {
					offsetY = proOriginalOffsetY;
				}
			}
		}
		
		if(offsetX < 0) {
			offsetX = 0;
		}
		if(offsetY < 0) {
			offsetY = 0;
		}

		
		int textLines = button.getNumberOfLines(); //文字的行数
		String rgb_str = button.getTextColor();
		int[] textRGBColor = convertToRGB(rgb_str); //文字RGB颜色
		String btnStr_edge = button.getEdge().trim();
		double[] btnPara_edge = parserAndConvert(btnStr_edge);
		
		int originalTextOffsetLeft = (int)(btnPara_edge[1]);
		int textOffsetTop = (int)(btnPara_edge[0]*zoomY) ;  // 按钮中文字向上偏移量
		int textOffsetLeft = (int)(btnPara_edge[1]*zoomX) ; // 按钮中文字向左偏移量
		int textOffsetBottom =(int) (btnPara_edge[2]* zoomY);// 按钮中文字向下偏移量
		int textOffsetRight = (int) (btnPara_edge[3] * zoomX); //按钮文字向右偏移量
		
		int textShowType = ZoomButton.TEXT_IN_BUTTON; //文字显示样式
	
		if(surplusX >0) { //左右宽度过宽时，也要平铺整个屏幕
			if((int)originalbuttonWidth / Constants.WIDTH == 0.5) { //宽度为整个屏幕的一半时
				buttonWidth = (int)(buttonWidth+surplusX);
	
				if((int)textOffsetLeft != 0) {
					textOffsetLeft = textOffsetLeft + (int)surplusX/2 ; //字不居中时，加上偏移量，使字体居中							
				}
				
				if((int)originalOffsetX == 0) {
					offsetX = 0;
				}else{
					offsetX = (int)(originalOffsetX * zoomX) + (int)surplusX;
				}
				
			}else if((int)originalbuttonWidth == Constants.WIDTH) { //宽度为铺满整个屏幕时
				buttonWidth = (int)(buttonWidth+surplusX*2);
				if((int)textOffsetLeft != 0) {
					textOffsetLeft = textOffsetLeft + (int)surplusX ; //字不居中时，加上偏移量，使字体居中					
				}
				offsetX = 0;
			}		
		}	
		
		//System.out.println("textOffsetLeft is"+textOffsetLeft);
		
		
		if(textOffsetTop > buttonHeight) { //文字在图标下
			textShowType = ZoomButton.TEXT_OUT_OF_BUTTON;
			int textMarginTop = offsetY + textOffsetTop;
			int textMarginLeft = offsetX + textOffsetLeft;
			zoom.setTextMarginLeft(textMarginLeft);
			zoom.setTextMarginTop(textMarginTop);
		}
		
		if(textOffsetLeft > buttonWidth) { //文字在图标的右侧
			textShowType = ZoomButton.TEXT_OUT_OF_BUTTON;		
			int textMarginLeft = offsetX + textOffsetLeft; //距离左边的距离
			if(originalOffsetX + originalbuttonWidth >= originalTextOffsetLeft) {
				textMarginLeft = textOffsetLeft;
			}
			
			
			int textMarginTop = offsetY + textOffsetTop;
			zoom.setTextMarginLeft(textMarginLeft);
			zoom.setTextMarginTop(textMarginTop);
		}
		
		
		
		int gravity = Gravity.LEFT|Gravity.TOP ; 
		
		if(textOffsetTop == 0 && textOffsetLeft==0) { //全部居中
			gravity = Gravity.CENTER ;
			//System.out.println("gravity is 全部居中");
		}else if(textOffsetTop == 0 && textOffsetLeft!=0) { //上下居中，左右不居中
			gravity = Gravity.CENTER_VERTICAL ;
			//System.out.println("gravity is 垂直居中");
		}else if(textOffsetTop!=0 && textOffsetLeft==0) { //左右居中，上下不居中
			gravity = Gravity.CENTER_HORIZONTAL ;
			//System.out.println("gravity is 水平居中");
		}
		
		zoom.setTextShowType(textShowType);
		zoom.setTextGravity(gravity);
		zoom.setButtonHeight(buttonHeight);
		zoom.setButtonWidth(buttonWidth);
		zoom.setOffsetX(offsetX);
		zoom.setOffsetY(offsetY);
		zoom.setTextLines(textLines);
		zoom.setTextOffsetBottom(textOffsetBottom);
		zoom.setTextOffsetLeft(textOffsetLeft);
		zoom.setTextOffsetRight(textOffsetRight);
		zoom.setTextOffsetTop(textOffsetTop);
		zoom.setTextRGBColor(textRGBColor);
		System.out.println("offsetY1 ="+offsetY);
		return zoom;
	}
	
	// 将字符串转换后传回
	private double[] parserAndConvert(String str) {
		String[] temp = str.replace("{", "").replace("}", "").split(",");
		double[] coordinates = new double[temp.length];
		for (int i = 0; i < temp.length; i++) {
			coordinates[i] = Double.parseDouble(temp[i]);
		}
		return coordinates;
	}
	
	// 将十六进制转换为rgb
	private int[] convertToRGB(String str) {
		String temp = str.replace("#", "");
		int len = temp.length();
		int[] rgb = new int[len / 2];
		for (int i = 0, j = 0; i < len; j++) {
			rgb[j] = Integer.parseInt(temp.substring(i, i + 2), 16);
			i += 2;
		}
		return rgb;
	}
	
}
