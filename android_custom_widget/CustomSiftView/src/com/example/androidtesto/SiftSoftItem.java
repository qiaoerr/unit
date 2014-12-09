package com.example.androidtesto;

import java.io.Serializable;

public class SiftSoftItem implements Serializable {
	private static final long serialVersionUID = 1L;
	public String itemName;
	public String itemId;
	public int checkStatus;// 1为选中

	public SiftSoftItem() {
	}

	public SiftSoftItem(String name) {
		itemName = name;
	}

	public SiftSoftItem(String name, String id) {
		itemName = name;
		itemId = id;
	}

}
