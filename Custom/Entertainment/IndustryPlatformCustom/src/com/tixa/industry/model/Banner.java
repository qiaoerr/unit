package com.tixa.industry.model;

import java.io.Serializable;

public class Banner implements Serializable{

	private static final long serialVersionUID = -256683490306500300L;
	private String frame;
	private String frameIndside;
	
	public String getFrame() {
		return frame;
	}
	public void setFrame(String frame) {
		this.frame = frame;
	}
	public String getFrameIndside() {
		return frameIndside;
	}
	public void setFrameIndside(String frameIndside) {
		this.frameIndside = frameIndside;
	}	
}
