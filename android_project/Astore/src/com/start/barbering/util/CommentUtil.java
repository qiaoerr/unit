package com.start.barbering.util;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;

import com.start.astore.R;

public class CommentUtil {

	/*获取prefix开头的资源图片*/
	public static ArrayList<Integer> getPrefixImages(Context context,
			String prefix) {
		ArrayList<Integer> imgResourceIds = new ArrayList<Integer>();
		Field[] fields = R.drawable.class.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().startsWith(prefix)) {
				int imgResourceId = context.getResources().getIdentifier(
						field.getName(), "drawable", context.getPackageName());
				imgResourceIds.add(imgResourceId);
			}
		}
		return imgResourceIds;
	}
}
