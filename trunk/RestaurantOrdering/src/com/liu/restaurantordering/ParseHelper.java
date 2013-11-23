package com.liu.restaurantordering;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ParseHelper extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testone);
		Parse.initialize(this, "Kkh2kigwgxrrzaeNpizAU8tGPYXpCAkdup3Aa2aC",
				"BwGMGDTLNoe3M0VVNfgiohCBsrtyBmF6x14O6ygC");
		// 向restaurant中插入数据

		// ParseObject restaurant = new ParseObject("Restaurant");
		// restaurant.put("restaurantId", 1);
		// restaurant.put("name", "江南饭店");
		// restaurant.put("address", "王府井6号");
		// restaurant
		// .put("info",
		// "将进酒   李白    君不见黄河之水天上来，奔流到海不复回。  君不见高堂明镜悲白发，朝如青丝暮成雪。  人生得意须尽欢，莫使金樽空对月。  天生我材必有用，千金散尽还复来。  烹羊宰牛且为乐，会须一饮三百杯。  岑夫子，丹丘生，将进酒，杯莫停      与君歌一曲，请君为我侧耳听。  钟鼓馔玉何足贵，但愿长醉不复醒      古来圣贤皆寂寞，惟有饮者留其名。  陈王昔时宴平乐，斗酒十千恣欢谑。  主人何为言少钱,径须沽取对君酌。  五花马，千金裘，呼儿将出换美酒，与尔同销万古愁。将进酒   李白    君不见黄河之水天上来，奔流到海不复回。  君不见高堂明镜悲白发，朝如青丝暮成雪。  人生得意须尽欢，莫使金樽空对月。  天生我材必有用，千金散尽还复来。  烹羊宰牛且为乐，会须一饮三百杯。  岑夫子，丹丘生，将进酒，杯莫停      与君歌一曲，请君为我侧耳听。  钟鼓馔玉何足贵，但愿长醉不复醒      古来圣贤皆寂寞，惟有饮者留其名。  陈王昔时宴平乐，斗酒十千恣欢谑。  主人何为言少钱,径须沽取对君酌。  五花马，千金裘，呼儿将出换美酒，与尔同销万古愁");
		// restaurant.put("phone", "18810365196");
		// restaurant.put("scanNum", 999);
		// restaurant.put("imagePaths", R.drawable.two);
		// restaurant.put("likeNum", 50);
		// restaurant.saveInBackground();
		// System.out.println("hello");

		// 在restaurant中查询数据

		ParseQuery query = new ParseQuery("Restaurant");
		query.whereEqualTo("name", "江南饭店");
		query.findInBackground(new FindCallback() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				ParseObject res = objects.get(0);
				int id = res.getInt("restaurantId");
				ParseQuery query = new ParseQuery("Foods");
				query.whereEqualTo("restaurantId", id);
				query.findInBackground(new FindCallback() {

					@Override
					public void done(List<ParseObject> objects, ParseException e) {
						Iterator<ParseObject> inIterator = objects.iterator();
						while (inIterator.hasNext()) {
							ParseObject food = inIterator.next();
							System.out.println(food.getString("name"));
						}
					}
				});
			}
		});

		// 向food表中插入数据

		// ParseObject foods = new ParseObject("Foodstest");
		// foods.put("restaurantId", 2);
		// foods.put("name", "青龙卧雪");
		// foods.put("price", 1900);
		// foods.put("info", "白糖上面放根黄瓜");
		// foods.put("onsale", true);
		// foods.put("scanNum", 999);
		// foods.put("imagePaths", R.drawable.one);
		// foods.put("likeNum", 50);
		// foods.saveInBackground();
		// System.out.println("hellof");

		// 在foods表中查询数据

		// ParseQuery query = new ParseQuery("Foods");
		// query.whereEqualTo("name", "青龙卧雪");
		// query.findInBackground(new FindCallback() {
		// @Override
		// public void done(List<ParseObject> objects, ParseException e) {
		// ParseObject food = objects.get(0);
		// int restaurantId = food.getInt("restaurantId");
		// ParseQuery query = new ParseQuery("Restaurant");
		// query.whereEqualTo("restaurantId", restaurantId);
		// query.findInBackground(new FindCallback() {
		//
		// @Override
		// public void done(List<ParseObject> objects, ParseException e) {
		// ParseObject restaurant = objects.get(0);
		// System.out.println(restaurant.getString("name"));
		// }
		// });
		// }
		// });

		// 在foods表中更新数据

		// ParseQuery query2 = new ParseQuery("Foods");
		// query2.whereEqualTo("name", "青龙卧雪");
		// query2.findInBackground(new FindCallback() {
		//
		// @Override
		// public void done(List<ParseObject> objects, ParseException e) {
		// ParseObject food = objects.get(0);
		// food.increment("scanNum", -2);
		// food.put("price", 2100);
		// food.saveInBackground();
		// System.out.println("update");
		// }
		// });
	}
}
