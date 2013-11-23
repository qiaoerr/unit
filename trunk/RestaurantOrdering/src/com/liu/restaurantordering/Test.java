package com.liu.restaurantordering;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Test extends Activity {
	LinearLayout linearLayout = null;
	Bundle bundle = null;
	private ExpandableListAdapter adapter = null;
	private ExpandableListView expandableListView = null;
	private int j = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		adapter = getAdapter();
		expandableListView = (ExpandableListView) findViewById(R.id.list);
		expandableListView.setAdapter(adapter);
	}

	private ExpandableListAdapter getAdapter() {
		adapter = new BaseExpandableListAdapter() {
			// ��������ͼ��ͼƬ
			int[] logos = new int[] { R.drawable.wei, R.drawable.shu,
					R.drawable.wu };
			// ��������ͼ����ʾ����
			// private String[] generalsTypes = new String[] { "κ", "��", "��" };
			// ����ͼ��ʾ����
			// private String[][] generals = new String[][] { { "�ĺ" },
			// { "��", "�ŷ�" }, { "����", "½ѷ", "�缧" } };
			// ����ͼͼƬ
			public int[][][] generallogos = new int[][][] {
					{ { R.drawable.xiahoudun } },
					{ { R.drawable.machao }, { R.drawable.luxun } },
					{ { R.drawable.xiahoudun }, { R.drawable.luxun },
							{ R.drawable.xiahoudun }, { R.drawable.luxun },
							{ R.drawable.xiahoudun } } };

			// �Լ�����һ�����������Ϣ�ķ���
			TextView getTextView() {
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 64);
				TextView textView = new TextView(Test.this);
				textView.setLayoutParams(lp);
				textView.setGravity(Gravity.CENTER_VERTICAL);
				textView.setPadding(36, 0, 0, 0);
				textView.setTextSize(20);
				textView.setTextColor(Color.BLACK);
				return textView;
			}

			// ��дExpandableListAdapter�еĸ�������
			@Override
			public int getGroupCount() {
				return logos.length;
			}

			@Override
			public Object getGroup(int groupPosition) {
				return logos[groupPosition];
			}

			@Override
			public long getGroupId(int groupPosition) {
				return groupPosition;
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				// return generallogos[groupPosition].length;
				return 1;
			}

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				return generallogos[groupPosition][childPosition];
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				return childPosition;
			}

			@Override
			public boolean hasStableIds() {
				return true;
			}

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				LinearLayout ll = new LinearLayout(Test.this);
				ll.setOrientation(0);
				ImageView logo = new ImageView(Test.this);
				logo.setImageResource(logos[groupPosition]);
				logo.setPadding(50, 0, 0, 0);
				ll.addView(logo);
				// TextView textView = getTextView();
				// textView.setText(getGroup(groupPosition).toString());
				// ll.addView(textView);

				return ll;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				RelativeLayout rl = new RelativeLayout(Test.this);
				int[][] inf = generallogos[groupPosition];

				for (int i = 0; i < inf.length; i++) {
					
					if (i == 0) {
						j = i;
						RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
								100, 100);
						lp.setMargins(45, 20, 0, 0);
						ImageView generallogo = new ImageView(Test.this);
						generallogo.setScaleType(ScaleType.FIT_XY);
						generallogo.setId(i + 1);
						generallogo.setImageResource(inf[childPosition][0]);
						generallogo.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Toast.makeText(getApplicationContext(),
										("number: " + j), Toast.LENGTH_SHORT)
										.show();
							}
						});
						rl.addView(generallogo, lp);
					} else if (i == 1) {
						j = i;
						RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
								100, 100);
						lp1.setMargins(0, 20, 45, 0);
						lp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
						ImageView generallogo1 = new ImageView(Test.this);
						generallogo1.setScaleType(ScaleType.FIT_XY);
						generallogo1.setId(i + 1);
						generallogo1
								.setImageResource(inf[childPosition + i][0]);
						generallogo1.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Toast.makeText(getApplicationContext(),
										("number: " + j), Toast.LENGTH_SHORT)
										.show();
							}
						});
						rl.addView(generallogo1, lp1);
					} else if (i % 2 == 0 && i > 1) {
						j = i;
						RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
								100, 100);
						lp2.setMargins(45, 20, 0, 0);
						lp2.addRule(RelativeLayout.BELOW, i - 1);
						ImageView generallogo2 = new ImageView(Test.this);
						generallogo2.setScaleType(ScaleType.FIT_XY);
						generallogo2.setId(i + 1);
						generallogo2
								.setImageResource(inf[childPosition + i][0]);
						generallogo2.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Toast.makeText(getApplicationContext(),
										("number: " + j), Toast.LENGTH_SHORT)
										.show();
							}
						});
						rl.addView(generallogo2, lp2);
					} else if ((i % 2) == 1 && i > 1) {
						j = i;
						RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(
								100, 100);
						lp3.setMargins(0, 20, 45, 0);
						lp3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
						lp3.addRule(RelativeLayout.BELOW, i - 1);
						ImageView generallogo2 = new ImageView(Test.this);
						generallogo2.setScaleType(ScaleType.FIT_XY);
						generallogo2.setId(i + 1);
						generallogo2
								.setImageResource(inf[childPosition + i][0]);
						generallogo2.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Toast.makeText(getApplicationContext(),
										("number: " + j), Toast.LENGTH_SHORT)
										.show();
							}
						});
						rl.addView(generallogo2, lp3);
					}
				}

				return rl;
			}

			@Override
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				return true;
			}
		};
		return adapter;
	}
}
