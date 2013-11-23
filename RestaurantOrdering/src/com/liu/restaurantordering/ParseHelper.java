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
		// ��restaurant�в�������

		// ParseObject restaurant = new ParseObject("Restaurant");
		// restaurant.put("restaurantId", 1);
		// restaurant.put("name", "���Ϸ���");
		// restaurant.put("address", "������6��");
		// restaurant
		// .put("info",
		// "������   ���    �������ƺ�֮ˮ���������������������ء�  �����������������׷���������˿ĺ��ѩ��  ���������뾡����Īʹ���׿ն��¡�  �����Ҳı����ã�ǧ��ɢ����������  ������ţ��Ϊ�֣�����һ�����ٱ���  ᯷��ӣ��������������ƣ���Īͣ      �����һ�������Ϊ�Ҳ������  �ӹ��������󣬵�Ը��������      ����ʥ�ͽԼ�į��Ω��������������  ������ʱ��ƽ�֣�����ʮǧ���ʡ�  ���˺�Ϊ����Ǯ,�����ȡ�Ծ��á�  �廨��ǧ���ã��������������ƣ����ͬ����ų������   ���    �������ƺ�֮ˮ���������������������ء�  �����������������׷���������˿ĺ��ѩ��  ���������뾡����Īʹ���׿ն��¡�  �����Ҳı����ã�ǧ��ɢ����������  ������ţ��Ϊ�֣�����һ�����ٱ���  ᯷��ӣ��������������ƣ���Īͣ      �����һ�������Ϊ�Ҳ������  �ӹ��������󣬵�Ը��������      ����ʥ�ͽԼ�į��Ω��������������  ������ʱ��ƽ�֣�����ʮǧ���ʡ�  ���˺�Ϊ����Ǯ,�����ȡ�Ծ��á�  �廨��ǧ���ã��������������ƣ����ͬ����ų�");
		// restaurant.put("phone", "18810365196");
		// restaurant.put("scanNum", 999);
		// restaurant.put("imagePaths", R.drawable.two);
		// restaurant.put("likeNum", 50);
		// restaurant.saveInBackground();
		// System.out.println("hello");

		// ��restaurant�в�ѯ����

		ParseQuery query = new ParseQuery("Restaurant");
		query.whereEqualTo("name", "���Ϸ���");
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

		// ��food���в�������

		// ParseObject foods = new ParseObject("Foodstest");
		// foods.put("restaurantId", 2);
		// foods.put("name", "������ѩ");
		// foods.put("price", 1900);
		// foods.put("info", "��������Ÿ��ƹ�");
		// foods.put("onsale", true);
		// foods.put("scanNum", 999);
		// foods.put("imagePaths", R.drawable.one);
		// foods.put("likeNum", 50);
		// foods.saveInBackground();
		// System.out.println("hellof");

		// ��foods���в�ѯ����

		// ParseQuery query = new ParseQuery("Foods");
		// query.whereEqualTo("name", "������ѩ");
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

		// ��foods���и�������

		// ParseQuery query2 = new ParseQuery("Foods");
		// query2.whereEqualTo("name", "������ѩ");
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
