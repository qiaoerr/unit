package com.tixa.industry.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.GridAdapter;
import com.tixa.industry.adapter.HomeGridAdapter;
import com.tixa.industry.adapter.HomeListViewAdapter;
import com.tixa.industry.adapter.PicListAdapter;
import com.tixa.industry.config.ConfigData;
import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.model.Advert;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.Modular;
import com.tixa.industry.model.ModularConfig;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.CommonUtil;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.ListViewUtil;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.BannerView;
import com.tixa.industry.widget.MyNoScrollGridView;
import com.tixa.industry.widget.NoScrollGridView;
import com.tixa.industry.widget.TopBar;

/**
 * 首页
 * 
 */
public class HomeActivity extends Fragment implements OnItemClickListener {

	private Context context;
	private IndustryApplication application;
	private IndexData indexData;
	private SparseArray<ModularConfig> map;
	private ArrayList<Modular> modularList;
	private TopBar topbar;
	private NoScrollGridView gridView;
	private GridAdapter gridapter;
	private ArrayList<Advert> adList;
	private PageConfig config;
	private int pageStyle;
	private int pageStatus;
	private View view;
	private TopBarUtil util;
	private LinearLayout container;
	private BannerView banner;
	private int naviStyle;
	private int naviType;
	private int tableType;
	private String templateId;
	private LayoutInflater inflater;
	private AsyncImageLoader loader;
	private ArrayList<Modular> leftModulars = null;
	private ArrayList<Modular> rightModulars = null;
	private int screem_width;

	private int num = 4;// 每个list显示4个item

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		templateId = getResources().getString(R.string.templateid);
		// if (templateId.equals("1005") || templateId.equals("1008")
		// || templateId.equals("1010")) {
		if (templateId.equals("1008") || templateId.equals("1010")) {
			view = inflater.inflate(R.layout.common_scollview_layout_no, null);
		} else {
			view = inflater.inflate(R.layout.common_scollview_layout, null);
		}
		screem_width = getResources().getDisplayMetrics().widthPixels;
		initConifg(); // 加载一些配置文件及系统数据
		initView(); // 显示布局
		return view;
	}

	private void initConifg() {
		context = getActivity();
		loader = new AsyncImageLoader(context);
		inflater = LayoutInflater.from(context);
		PageConfigParser p = new PageConfigParser(context,
				"layout/HomeLayout.xml");
		config = p.parser();
		application = IndustryApplication.getInstance();
		map = application.getModularMap();
		indexData = application.getMainData();
		modularList = indexData.getModularList();
		adList = indexData.getAdList();
		pageStyle = config.getAd().getType(); // 展示样式
		pageStatus = config.getAd().getShow(); // 展示状态
		naviStyle = config.getNavi().getBackItem();
		naviType = config.getNavi().getType();
		tableType = config.getBlock().getTableType(); // 展示样式 1 列表 2九宫格
	}

	private void initView() {
		container = (LinearLayout) view.findViewById(R.id.container);
		// show topbar
		topbar = (TopBar) view.findViewById(R.id.topbar);
		util = new TopBarUtil(true, naviStyle, topbar, getResources()
				.getString(R.string.app_name), getFragmentManager(),
				(Activity) context, application.getTemplateId(), false,
				naviType);
		util.showConfig();
		View homeView = null;
		if (templateId.equals("1002") || templateId.equals("1004")
				|| templateId.equals("1008") || templateId.equals("1009")
				|| templateId.equals("1010") || templateId.equals("1012")
				|| templateId.equals("1019")) {
			if (templateId.equals("1002")) {
				homeView = inflater.inflate(R.layout.homelayout1002, null);
			} else if (templateId.equals("1004")) {
				homeView = inflater.inflate(R.layout.homelayout1004, null);
			} else if (templateId.equals("1008")) {
				homeView = inflater.inflate(R.layout.homelayout1008, null);
			} else if (templateId.equals("1009")) {
				homeView = inflater.inflate(R.layout.homelayout1009, null);
			} else if (templateId.equals("1010")) {
				homeView = inflater.inflate(R.layout.homelayout1010, null);
			} else if (templateId.equals("1012")) {
				homeView = inflater.inflate(R.layout.homelayout1012, null);
			} else if (templateId.equals("1019")) {
				homeView = inflater.inflate(R.layout.homelayout1019, null);
			}
			ListView left = (ListView) homeView.findViewById(R.id.left_list);
			ListView right = (ListView) homeView.findViewById(R.id.right_list);
			leftModulars = new ArrayList<Modular>();
			rightModulars = new ArrayList<Modular>();

			// if (modularList.size() >= num + 1) {
			// for (int i = 0; i < num; i++) {
			// leftModulars.add(modularList.get(i));
			// try {
			// rightModulars.add(modularList.get(i + num));
			// } catch (Exception e) {
			// continue;
			// }
			// }
			// }
			for (int i = 0; i < modularList.size(); i++) {
				if (i % 2 == 0) {
					leftModulars.add(modularList.get(i));
				} else {
					rightModulars.add(modularList.get(i));
				}
			}
			HomeListViewAdapter leftadapter = new HomeListViewAdapter(context,
					leftModulars);
			HomeListViewAdapter rightadapter = new HomeListViewAdapter(context,
					rightModulars);
			left.setAdapter(leftadapter);
			right.setAdapter(rightadapter);
			ListViewUtil.setListViewHeightBasedOnChildren(right);
			ListViewUtil.setListViewHeightBasedOnChildren(left);
			left.setOnItemClickListener(this);
			right.setOnItemClickListener(this);

			if (templateId.equals("1008")) {
				container.addView(homeView, 0);
				banner = new BannerView(context, adList, pageStyle, -1);
				container.addView(banner, 1); // 添加广告位
				banner.show();
			} else if (templateId.equals("1010")) {
				container.addView(homeView, 0);
				ListView advListView = (ListView) homeView
						.findViewById(R.id.my_list);
				advListView.setAdapter(new PicListAdapter(context, adList));
			} else {
				banner = new BannerView(context, adList, pageStyle);
				container.addView(banner, 0); // 添加广告位
				banner.show();
				container.addView(homeView, 1);
			}
		}
		// else if (templateId.equals("1003")) {
		// homeView = inflater.inflate(R.layout.homelayout1003, null);
		// ImageView logo = (ImageView) homeView.findViewById(R.id.logo);
		// logo.setBackgroundResource(R.drawable.logo1);
		// LinearLayout left_layout = (LinearLayout) homeView
		// .findViewById(R.id.linearLayout_left);
		// LinearLayout right_layout = (LinearLayout) homeView
		// .findViewById(R.id.linearLayout_right);
		// left_layout.setOnClickListener(new MyClickListener(0));
		// right_layout.setOnClickListener(new MyClickListener(1));
		// TextView menu_left_text = (TextView) homeView
		// .findViewById(R.id.menu_left_text);
		// TextView menu_right_text = (TextView) homeView
		// .findViewById(R.id.menu_right_text);
		// menu_left_text.setText(modularList.get(0).getModularName());
		// menu_right_text.setText(modularList.get(1).getModularName());
		// ListView listView = (ListView) homeView.findViewById(R.id.mylist);
		// ArrayList<Modular> tempModulars = new ArrayList<Modular>();
		// for (int i = 2; i < modularList.size(); i++) {
		// tempModulars.add(modularList.get(i));
		// }
		// HomeListViewAdapter adapter = new HomeListViewAdapter(context,
		// tempModulars);
		// listView.setAdapter(adapter);
		// ListViewUtil.setListViewHeightBasedOnChildren(listView);
		// listView.setOnItemClickListener(this);
		// container.addView(homeView, 0);
		// }
		else if (templateId.equals("1005") || templateId.equals("1006")
				|| templateId.equals("1011") || templateId.equals("1016")) {
			if (templateId.equals("1005")) {
				homeView = inflater.inflate(R.layout.homelayout1005, null);
			} else if (templateId.equals("1006")) {
				homeView = inflater.inflate(R.layout.homelayout1006, null);
				ImageView logo = (ImageView) homeView.findViewById(R.id.logo);
				// 获取logo
				FileUtil.setImage(logo,
						InterfaceURL.IMGIP + indexData.getAppIcon(), loader,
						R.drawable.logo1);
			} else if (templateId.equals("1011")) {
				homeView = inflater.inflate(R.layout.homelayout1011, null);
			} else if (templateId.equals("1016")) {
				homeView = inflater.inflate(R.layout.homelayout1016, null);
				ImageView logo = (ImageView) homeView.findViewById(R.id.logo);
				FileUtil.setImage(logo,
						InterfaceURL.IMGIP + indexData.getAppIcon(), loader,
						R.drawable.logo1);
			}
			MyNoScrollGridView gridView = (MyNoScrollGridView) homeView
					.findViewById(R.id.myGridView);
			HomeGridAdapter gridapter = new HomeGridAdapter(context,
					modularList);
			gridView.setAdapter(gridapter);
			gridView.setOnItemClickListener(this);
			if (templateId.equals("1005")) {
				container.addView(homeView, 0);
				banner = new BannerView(context, adList, pageStyle,
						screem_width);
				container.addView(banner, 1);
				banner.show();
			} else if (templateId.equals("1011")) {
				banner = new BannerView(context, adList, pageStyle);
				container.addView(banner, 0);
				banner.show();
				container.addView(homeView, 1);
			} else {
				container.addView(homeView, 0);
			}

		} else if (templateId.equals("1001") || templateId.equals("1003")
				|| templateId.equals("1014") || templateId.equals("1018")) {
			if (templateId.equals("1001")) {
				homeView = inflater.inflate(R.layout.homelayout1001, null);
			} else if (templateId.equals("1003")) {
				homeView = inflater.inflate(R.layout.homelayout1003, null);
			} else if (templateId.equals("1014")) {
				homeView = inflater.inflate(R.layout.homelayout1014, null);
			} else if (templateId.equals("1018")) {
				homeView = inflater.inflate(R.layout.homelayout1018, null);
			}
			banner = new BannerView(context, adList, pageStyle);
			container.addView(banner, 0);
			banner.show();
			ListView listView = (ListView) homeView.findViewById(R.id.mylist);
			HomeListViewAdapter adapter = new HomeListViewAdapter(context,
					modularList);
			listView.setAdapter(adapter);
			ListViewUtil.setListViewHeightBasedOnChildren(listView);
			listView.setOnItemClickListener(this);
			container.addView(homeView, 1);
		} else if (templateId.equals("1015")) {
			homeView = inflater.inflate(R.layout.homelayout1015, null);
			RelativeLayout logo_relative = (RelativeLayout) homeView
					.findViewById(R.id.logo_group);
			ImageView logo = (ImageView) homeView.findViewById(R.id.logo);
			FileUtil.setImage(logo,
					InterfaceURL.IMGIP + indexData.getAppIcon(), loader,
					R.drawable.logo1);
			RelativeLayout viewGroup_up = (RelativeLayout) homeView
					.findViewById(R.id.up_relative);
			for (int i = 0; i < viewGroup_up.getChildCount(); i++) {
				TextView modularText = (TextView) viewGroup_up.getChildAt(i);
				modularText.setText(modularList.get(i).getModularName());
				modularText.setOnClickListener(new MyClickListener(i));
			}
			RelativeLayout viewGroup_down = (RelativeLayout) homeView
					.findViewById(R.id.down_relative);
			for (int i = 0; i < viewGroup_down.getChildCount(); i++) {
				TextView modularText = (TextView) viewGroup_down.getChildAt(i);
				modularText.setText(modularList.get(i + 3).getModularName());
				modularText.setOnClickListener(new MyClickListener(i + 3));
			}
			ViewGroup homeViewGroup = (ViewGroup) homeView;
			homeViewGroup.removeAllViews();
			container.addView(logo_relative, 0);
			banner = new BannerView(context, adList, pageStyle);
			container.addView(banner, 1);
			banner.show();
			container.addView(viewGroup_up, 2);
			container.addView(viewGroup_down, 3);
		} else if (templateId.equals("1017")) {
			homeView = inflater.inflate(R.layout.homelayout1017, null);
			RelativeLayout logo_re = (RelativeLayout) homeView
					.findViewById(R.id.logo_re);
			ImageView logo = (ImageView) homeView.findViewById(R.id.logo);
			FileUtil.setImage(logo,
					InterfaceURL.IMGIP + indexData.getAppIcon(), loader,
					R.drawable.logo1);
			RelativeLayout groupView = (RelativeLayout) homeView
					.findViewById(R.id.relative_group);
			for (int i = 0, j = 0; i < groupView.getChildCount(); i++) {
				LinearLayout linearLayout_out = (LinearLayout) groupView
						.getChildAt(i);
				LinearLayout linearLayout = (LinearLayout) linearLayout_out
						.getChildAt(0);
				Button btn1 = (Button) linearLayout.getChildAt(0);
				Button btn2 = (Button) linearLayout.getChildAt(1);
				btn1.setText(modularList.get(j).getModularName());
				btn2.setText(modularList.get(j + 1).getModularName());
				btn1.setOnClickListener(new MyClickListener(j));
				btn2.setOnClickListener(new MyClickListener(j + 1));
				j += 2;
			}
			LinearLayout up_Linear = (LinearLayout) homeView
					.findViewById(R.id.up_Linear_out);
			LinearLayout middle_Linear = (LinearLayout) homeView
					.findViewById(R.id.middle_Linear_out);
			LinearLayout bottom_Linear = (LinearLayout) homeView
					.findViewById(R.id.bottom_Linear_out);
			ViewGroup homeViewGroup = (ViewGroup) homeView;
			homeViewGroup.removeAllViews();
			groupView.removeAllViews();
			container.addView(logo_re, 0);
			container.addView(up_Linear, 1);
			container.addView(middle_Linear, 2);
			int screenWidth = CommonUtil.getWidthPx(context);
			float scale = context.getResources().getDisplayMetrics().density;
			int width = (int) (screenWidth - 60 * scale);
			banner = new BannerView(context, adList, pageStyle, width, 0);
			LinearLayout.LayoutParams params = new LayoutParams(-2, -2);
			params.gravity = Gravity.CENTER_HORIZONTAL;
			container.addView(banner, 3, params);
			banner.show();
			container.addView(bottom_Linear, 4);
		} else if (templateId.equals("1007") || templateId.equals("1013")) {
			if (templateId.equals("1007")) {
				homeView = inflater.inflate(R.layout.homelayout1007, null);
				// 获取logo
				ImageView logo = (ImageView) homeView.findViewById(R.id.logo);
				FileUtil.setImage(logo,
						InterfaceURL.IMGIP + indexData.getAppIcon(), loader,
						R.drawable.logo1);
			} else if (templateId.equals("1013")) {
				homeView = inflater.inflate(R.layout.homelayout1013, null);
				ImageView logo = (ImageView) homeView.findViewById(R.id.logo);
				FileUtil.setImage(logo,
						InterfaceURL.IMGIP + indexData.getAppIcon(), loader,
						R.drawable.logo1);
			}
			RelativeLayout viewGroup = (RelativeLayout) homeView
					.findViewById(R.id.myViewGroup);
			for (int i = 0, j = 0; i < viewGroup.getChildCount(); i++) {
				TextView modularText = null;
				try {
					modularText = (TextView) viewGroup.getChildAt(i);
				} catch (Exception e) {
					continue;
				}

				modularText.setText(modularList.get(j).getModularName());

				modularText.setOnClickListener(new MyClickListener(j++));
			}
			if (templateId.equals("1013")) {
				banner = new BannerView(context, adList, pageStyle);
				container.addView(banner, 0);
				banner.show();
				container.addView(homeView, 1);
			} else {
				container.addView(homeView, 0);
			}
			// container.addView(homeView, 0);
		} else if (templateId.equals("1020")) {
			homeView = inflater.inflate(R.layout.homelayout1020, null);
			topbar.setVisibility(View.GONE);
			ViewGroup viewGroup = (ViewGroup) homeView
					.findViewById(R.id.my_view_group);
			for (int i = 0, j = 0; i < viewGroup.getChildCount(); i++) {
				LinearLayout linearLayout_out = (LinearLayout) viewGroup
						.getChildAt(i);
				LinearLayout linearLayout = (LinearLayout) linearLayout_out
						.getChildAt(0);
				TextView textView1 = (TextView) linearLayout.getChildAt(0);
				TextView textView2 = (TextView) linearLayout.getChildAt(2);
				textView1.setText(modularList.get(j).getModularName());
				textView2.setText(modularList.get(j + 1).getModularName());
				textView1.setOnClickListener(new MyClickListener(j));
				textView2.setOnClickListener(new MyClickListener(j + 1));
				j += 2;
			}
			LinearLayout myLinearOne_out = (LinearLayout) homeView
					.findViewById(R.id.myLinearOne_out);
			LinearLayout myLinearTwo_out = (LinearLayout) homeView
					.findViewById(R.id.myLinearTwo_out);
			LinearLayout myLinearThree_out = (LinearLayout) homeView
					.findViewById(R.id.myLinearThree_out);
			LinearLayout myLinearFour_out = (LinearLayout) homeView
					.findViewById(R.id.myLinearFour_out);
			RelativeLayout image_one = (RelativeLayout) homeView
					.findViewById(R.id.horizontalLine_one);
			RelativeLayout image_two = (RelativeLayout) homeView
					.findViewById(R.id.horizontalLine_two);
			RelativeLayout image_three = (RelativeLayout) homeView
					.findViewById(R.id.horizontalLine_three);
			RelativeLayout myTitle_relative = (RelativeLayout) homeView
					.findViewById(R.id.myTitle_relative);
			TextView myTitleView = (TextView) homeView
					.findViewById(R.id.myTitle);
			myTitleView.setText(getResources().getString(R.string.app_name));
			myTitle_relative.setVisibility(View.VISIBLE);
			ViewGroup homeViewGroup = (ViewGroup) homeView;
			viewGroup.removeAllViews();
			homeViewGroup.removeAllViews();

			// image.setBackgroundResource(R.drawable.item3);
			banner = new BannerView(context, adList, pageStyle);
			container.addView(banner, 0);
			banner.show();
			container.addView(myTitle_relative, 1);
			container.addView(myLinearOne_out, 2);
			container.addView(image_three, 3);
			container.addView(myLinearTwo_out, 4);
			container.addView(image_one, 5);
			container.addView(myLinearThree_out, 6);
			container.addView(image_two, 7);
			container.addView(myLinearFour_out, 8);

		} else {
			ScrollView scrollView = (ScrollView) view
					.findViewById(R.id.myScrollView);
			scrollView.setBackgroundColor(Color.WHITE);
			// show banner
			if (pageStatus == ConfigData.SHOW_AD) { // 展示广告
				banner = new BannerView(context, adList, pageStyle);
				container.addView(banner, 0);
				banner.show();
			}
			// show modularList
			switch (tableType) {
			case ConfigData.TABLE_LIST_VIEW: // 列表格式

				break;
			case ConfigData.TABLE_GRID_VIEW: // 九宫格格式
				gridView = new NoScrollGridView(context);
				gridapter = new GridAdapter(context, modularList);
				gridView.setAdapter(gridapter);
				gridView.setOnItemClickListener(this);
				container.addView(gridView, 1);
				break;
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ArrayList<Modular> tempModulars = modularList;
		if (templateId.equals("1002") || templateId.equals("1004")
				|| templateId.equals("1008") || templateId.equals("1009")
				|| templateId.equals("1010") || templateId.equals("1012")
				|| templateId.equals("1019")) {
			if (parent.getId() == R.id.right_list) {
				tempModulars = rightModulars;
			} else {
				tempModulars = leftModulars;
			}
		}
		Modular modular = tempModulars.get(position);
		buttonClick(modular);
	}

	private void buttonClick(Modular modular) {
		ModularConfig config = map.get(modular.getType());
		final long showType = modular.getShowType();
		String className = CommonUtil.formatClassName(application, config,
				showType);
		try {
			Fragment fragment = (Fragment) Class.forName(className)
					.newInstance();
			Bundle args = CommonUtil.packagModuleBundle(modular);
			fragment.setArguments(args);
			FragmentTransaction fragmentTransaction = getFragmentManager()
					.beginTransaction();
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.replace(R.id.fragment, fragment);
			fragmentTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class MyClickListener implements OnClickListener {
		private final int index;

		public MyClickListener(int index) {
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			Modular modular = modularList.get(index);
			buttonClick(modular);
		}

	}
}
