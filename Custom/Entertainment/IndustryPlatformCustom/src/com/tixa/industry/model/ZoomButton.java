package com.tixa.industry.model;

import java.util.Arrays;

/**
 * 拉伸按钮
 *
 * @author shengy
 *
 */
public class ZoomButton {
	
	public static final int TEXT_IN_BUTTON = 0; //文字在按钮内
	public static final int TEXT_OUT_OF_BUTTON = 1; //文字在按钮外
	
	private int offsetX; //距离左边框距离 像素px
	private int offsetY; //距离上边框距离 像素px
	private int buttonWidth; //按钮的宽
	private int buttonHeight; //按钮的高 px
	
	private int textOffsetTop; // 按钮中文字向上偏移量
	private int textOffsetLeft; // 按钮中文字向下偏移量
	private int textOffsetRight; //按钮文字向右偏移量
	private int textOffsetBottom; //按钮文字想下偏移
	
	private int textMarginTop; //按钮外 文字距离顶部距离
	private int textMarginLeft; //按钮外 文字距离左边距离
	
	private int textShowType; //按钮文字显示类型 0 按钮内 1 按钮外
	
	private int textLines ; //文字的行数
	private int[] textRGBColor ; //文字颜色
	private int textGravity;  //居中方式
	
	
	public int[] getTextRGBColor() {
		return textRGBColor;
	}
	public void setTextRGBColor(int[] textRGBColor) {
		this.textRGBColor = textRGBColor;
	}
	public int getOffsetX() {
		return offsetX;
	}
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}
	public int getOffsetY() {
		return offsetY;
	}
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}
	public int getButtonWidth() {
		return buttonWidth;
	}
	public void setButtonWidth(int buttonWidth) {
		this.buttonWidth = buttonWidth;
	}
	public int getButtonHeight() {
		return buttonHeight;
	}
	public void setButtonHeight(int buttonHeight) {
		this.buttonHeight = buttonHeight;
	}
	public int getTextOffsetTop() {
		return textOffsetTop;
	}
	public void setTextOffsetTop(int textOffsetTop) {
		this.textOffsetTop = textOffsetTop;
	}
	public int getTextOffsetLeft() {
		return textOffsetLeft;
	}
	public void setTextOffsetLeft(int textOffsetLeft) {
		this.textOffsetLeft = textOffsetLeft;
	}
	public int getTextOffsetRight() {
		return textOffsetRight;
	}
	public void setTextOffsetRight(int textOffsetRight) {
		this.textOffsetRight = textOffsetRight;
	}
	public int getTextOffsetBottom() {
		return textOffsetBottom;
	}
	public void setTextOffsetBottom(int textOffsetBottom) {
		this.textOffsetBottom = textOffsetBottom;
	}
	public int getTextLines() {
		return textLines;
	}
	public void setTextLines(int textLines) {
		this.textLines = textLines;
	}
	
	public int getTextGravity() {
		return textGravity;
	}
	public void setTextGravity(int textGravity) {
		this.textGravity = textGravity;
	}

	
	public int getTextMarginTop() {
		return textMarginTop;
	}
	public void setTextMarginTop(int textMarginTop) {
		this.textMarginTop = textMarginTop;
	}
	public int getTextMarginLeft() {
		return textMarginLeft;
	}
	public void setTextMarginLeft(int textMarginLeft) {
		this.textMarginLeft = textMarginLeft;
	}
	public int getTextShowType() {
		return textShowType;
	}
	public void setTextShowType(int textShowType) {
		this.textShowType = textShowType;
	}
	@Override
	public String toString() {
		return "ZoomButton [offsetX=" + offsetX + ", offsetY=" + offsetY
				+ ", buttonWidth=" + buttonWidth + ", buttonHeight="
				+ buttonHeight + ", textOffsetTop=" + textOffsetTop
				+ ", textOffsetLeft=" + textOffsetLeft + ", textOffsetRight="
				+ textOffsetRight + ", textOffsetBottom=" + textOffsetBottom
				+ ", textLines=" + textLines + ", textRGBColor="
				+ Arrays.toString(textRGBColor) + ", textGravity="
				+ textGravity  + "]";
	}

}
