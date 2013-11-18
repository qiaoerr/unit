package com.tixa.industry.reg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.model.CountryCode;
import com.tixa.industry.util.StrUtil;

public class CountryCodeAdapter extends BaseAdapter implements Filterable,
		SectionIndexer {
	private ArrayList<CountryCode> myData;
	private LayoutInflater mInflater;
	private ArrayList<CountryCode> allData;
	private HashMap<Character, Integer> spMap;
	private String searchKey = "";

	public ArrayList<CountryCode> getMyData() {
		return myData;
	}

	public void setMyData(ArrayList<CountryCode> myData) {
		this.myData = myData;
	}

	public ArrayList<CountryCode> getAllData() {
		return allData;
	}

	public void setAllData(ArrayList<CountryCode> allData) {
		this.allData = allData;
	}

	public CountryCodeAdapter(Context context, ArrayList<CountryCode> _myData) {
		this.myData = _myData;
		allData = myData;
		this.mInflater = LayoutInflater.from(context);
		spMap = new HashMap<Character, Integer>();
		initSPMap();
	}

	class ViewHolder {
		TextView textView1;
		TextView textView2;
		LinearLayout section;
		TextView setionText;
	}

	@Override
	public int getCount() {
		return myData.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		convertView = mInflater.inflate(R.layout.layout_item_countrycode, null);
		holder = new ViewHolder();
		holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
		holder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
		holder.section = (LinearLayout) convertView.findViewById(R.id.section);
		holder.setionText = (TextView) convertView
				.findViewById(R.id.setctionText);
		char fa;
		try {
			fa = myData.get(position).getCountryName_pinyin();
			if (fa > 'Z' || fa < 'A') {
				fa = '#';
			}
		} catch (Exception e) {
			fa = '#';
		}
		if (spMap.get(fa) != null && spMap.get(fa) == position
				&& StrUtil.isEmpty(searchKey)) {
			holder.setionText.setText(fa + "");
			holder.section.setVisibility(View.VISIBLE);
		} else {
			holder.section.setVisibility(View.GONE);
		}
		String name = myData.get(position).getCountryName();
		String code = myData.get(position).getCountryCode();

		if (!searchKey.equals("")
				&& name.toUpperCase().indexOf(searchKey) >= 0
				&& name.toUpperCase().indexOf(searchKey) + searchKey.length() <= name
						.length()) {
			SpannableString s = new SpannableString(name);
			s.setSpan(new ForegroundColorSpan(Color.RED), name.toUpperCase()
					.indexOf(searchKey), name.toUpperCase().indexOf(searchKey)
					+ searchKey.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			holder.textView1.setText(s);
		} else {
			holder.textView1.setText(name);
		}
		if (!searchKey.equals("")
				&& code.toUpperCase().indexOf(searchKey) >= 0
				&& code.toUpperCase().indexOf(searchKey) + searchKey.length() <= code
						.length()) {
			SpannableString s = new SpannableString(code);
			s.setSpan(new ForegroundColorSpan(Color.RED), code.toUpperCase()
					.indexOf(searchKey), code.toUpperCase().indexOf(searchKey)
					+ searchKey.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			holder.textView2.setText(s);
		} else {
			holder.textView2.setText(code);
		}
		return convertView;
	}

	@Override
	public Filter getFilter() {

		Filter filter = new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence s) {
				searchKey = s.toString().toUpperCase();
				FilterResults results = new FilterResults();
				ArrayList<CountryCode> result = new ArrayList<CountryCode>();
				if (searchKey.equals("")) {
					results.values = allData;
					results.count = allData.size();
					return results;
				}
				if (allData != null && allData.size() > 0) {

					for (int i = 0; i < allData.size(); i++) {
						if (allData.get(i).getCountryName().toUpperCase()
								.contains(searchKey)) {
							result.add(allData.get(i));
						} else if (allData.get(i).getCountryCode()
								.toUpperCase().contains(searchKey)) {
							result.add(0, allData.get(i));
						}

					}
					results.values = result;
					results.count = result.size();
				}
				return results;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				myData = (ArrayList<CountryCode>) results.values;
				if (myData == null) {
					myData = new ArrayList<CountryCode>();

				}
				if (myData != null && myData.size() > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}

			}

			@Override
			public CharSequence convertResultToString(Object resultValue) {
				return super.convertResultToString(resultValue);
			}

		};
		return filter;
	}

	public void initSPMap() {
		if (myData == null || myData.size() == 0)
			return;
		spMap.clear();
		for (int i = 0; i < myData.size(); i++) {
			if (myData.get(i) == null)
				continue;
			char fa = myData.get(i).getCountryName_pinyin();
			if (fa > 'Z' || fa < 'A') {
				fa = '#';
			}
			if (!spMap.containsKey(fa)) {
				spMap.put(fa, i);
			}
			if (spMap.size() == 27)
				break;
		}
	}

	static char[] l = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z', '#' };

	@Override
	public int getPositionForSection(int section) {
		// initSPMap();
		// if (spMap.containsKey((char) section)) {
		// return spMap.get((char) section);
		// }
		// return -1;
		if (section >= 0 && section < l.length) {
			if (spMap.containsKey((char) l[section])) {
				return spMap.get((char) l[section]);
			}
		}
		return -1;
	}

	@Override
	public int getSectionForPosition(int position) {
		if (position < 0 || position >= getCount()) {
			return -1;
		}
		// 注意这个方法的返回值，它就是index<0时，返回-index-2的原因
		// 解释Arrays.binarySearch，如果搜索结果在数组中，刚返回它在数组中的索引，如果不在，刚返回第一个比它大的索引的负数-1
		// 如果没弄明白，请自己想查看api
		int index = Arrays.binarySearch(getSectionValueArray(), position);
		return index >= 0 ? index : -index - 2; // 当index小于0时，返回-index-2，

	}

	private int[] getSectionValueArray() {
		if (spMap != null && spMap.size() > 0) {
			ArrayList<Integer> list = new ArrayList<Integer>(spMap.values());
			Collections.sort(list);
			int[] arr = new int[list.size()];
			for (int i = 0; i < list.size(); i++) {
				arr[i] = list.get(i);
			}
			return arr;
		}
		return null;
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}
