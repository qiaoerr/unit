package com.liu.restaurantordering;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailFragment extends android.support.v4.app.Fragment {
	private ExpandableListAdapter adapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new BaseExpandableListAdapter() {
			// 设置组视图的图片
			int[] logos = new int[] { R.drawable.wei, R.drawable.shu,
					R.drawable.wu };
			// 设置组视图的显示文字
			private String[] generalsTypes = new String[] { "魏", "蜀", "吴" };
			// 子视图显示文字
			private String[][] generals = new String[][] { { "夏侯惇", "甄姬" },
					{ "马超", "张飞" }, { "吕蒙", "陆逊" }

			};
			// 子视图图片
			public int[][] generallogos = new int[][] {
					{ R.drawable.xiahoudun, R.drawable.luxun },
					{ R.drawable.machao, R.drawable.luxun },
					{ R.drawable.lvmeng, R.drawable.luxun } };

			// 自己定义一个获得文字信息的方法
			TextView getTextView() {
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 64);
				TextView textView = new TextView(getActivity());
				textView.setLayoutParams(lp);
				textView.setGravity(Gravity.CENTER_VERTICAL);
				textView.setPadding(36, 0, 0, 0);
				textView.setTextSize(20);
				textView.setTextColor(Color.BLACK);
				return textView;
			}

			// 重写ExpandableListAdapter中的各个方法
			@Override
			public int getGroupCount() {
				// TODO Auto-generated method stub
				return generalsTypes.length;
			}

			@Override
			public Object getGroup(int groupPosition) {
				// TODO Auto-generated method stub
				return generalsTypes[groupPosition];
			}

			@Override
			public long getGroupId(int groupPosition) {
				// TODO Auto-generated method stub
				return groupPosition;
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				// TODO Auto-generated method stub
				return generals[groupPosition].length;
			}

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return generals[groupPosition][childPosition];
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return childPosition;
			}

			@Override
			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				LinearLayout ll = new LinearLayout(getActivity());
				ll.setOrientation(0);
				ImageView logo = new ImageView(getActivity());
				logo.setImageResource(logos[groupPosition]);
				logo.setPadding(50, 0, 0, 0);
				ll.addView(logo);
				TextView textView = getTextView();
				textView.setText(getGroup(groupPosition).toString());
				ll.addView(textView);

				return ll;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				LinearLayout ll = new LinearLayout(getActivity());
				ll.setOrientation(0);
				ImageView generallogo = new ImageView(getActivity());
				generallogo
						.setImageResource(generallogos[groupPosition][childPosition]);
				ll.addView(generallogo);
				TextView textView = getTextView();
				textView.setText(getChild(groupPosition, childPosition)
						.toString());
				ll.addView(textView);
				return ll;
			}

			@Override
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				// TODO Auto-generated method stub
				return true;
			}
		};
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.detail, null, false);
		ExpandableListView exlistview = (ExpandableListView) view
				.findViewById(R.id.list);
		exlistview.setAdapter(adapter);
		exlistview.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				Toast.makeText(
						getActivity(),
						"你点击了" + adapter.getChild(groupPosition, childPosition),
						Toast.LENGTH_SHORT).show();

				return false;
			}
		});
		return view;
	}

}
