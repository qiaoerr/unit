package com.tixa.industry.model;

import java.io.Serializable;

public class Button implements Serializable {

	private static final long serialVersionUID = 4923860929921614740L;
	private String frame;// Button�����λ�ü���С {{x, y}, {width, height}}
	private String edge;// ������Button����е�ƫ���� {top, left, bottom, right}
	private int numberOfLines; // ����
	private String textColor; // ���ֵ���ɫ

	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}

	public String getEdge() {
		return edge;
	}

	public void setEdge(String edge) {
		this.edge = edge;
	}

	public int getNumberOfLines() {
		return numberOfLines;
	}

	public void setNumberOfLines(int numberOfLines) {
		this.numberOfLines = numberOfLines;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	@Override
	public String toString() {
		return "Button [frame=" + frame + ", edge=" + edge + ", numberOfLines="
				+ numberOfLines + ", textColor=" + textColor + "]";
	}
	
	
	
}
