package com.tixa.industry.activity;

import java.util.ArrayList;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.config.Constants;
import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.model.Advert;
import com.tixa.industry.model.Block;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.Modular;
import com.tixa.industry.model.ModularConfig;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.model.ZoomAd;
import com.tixa.industry.model.ZoomButton;
import com.tixa.industry.model.ZoomLogo;
import com.tixa.industry.parser.HomeParser;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.CommonUtil;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.util.ZoomUtil;
import com.tixa.industry.widget.BannerView;
import com.tixa.industry.widget.TopBar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class SimplyHomeActivity extends Fragment {
	private Context context;
	private IndustryApplication application;
	private IndexData indexData;
	private SparseArray<ModularConfig> map;
	private ArrayList<Modular> modularList;
	private TopBar topbar;
	private ArrayList<Advert> adList;
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
	private PageConfig config;
	private Block block;
	private RelativeLayout panel;
	private LayoutParams params;
	private ImageView icon;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		view = inflater.inflate(R.layout.common_scollview_relative_layout, null);
		
		initConifg(); // 加载一些配置文件及系统数据
		initView();
		return view;
	}

	private void initConifg() {
		context = getActivity();
		application = IndustryApplication.getInstance();
		map = application.getModularMap();
		indexData = application.getMainData();
		modularList = indexData.getModularList(); //栏目
		adList = indexData.getAdList(); //广告
		
		loader = new AsyncImageLoader(context);
		inflater = LayoutInflater.from(context);
		HomeParser p = new HomeParser(context);
		config = p.parser();
		block = config.getBlock();		
	
	}
	
	private void initView() {
		ZoomUtil util = new ZoomUtil(context);
		panel = (RelativeLayout) view.findViewById(R.id.container);
		ArrayList<com.tixa.industry.model.Button> buttons = block.getButtons();
		
		ArrayList<ZoomButton> zooms = new ArrayList<ZoomButton>();
		
		L.e("getSecmenuShow = "+block.getSecmenuShow());
		L.e("getBanner = "+block.getBanner());
		L.e("buttons = "+buttons);
		
		
		//当配置文件中的按钮数量多于数据源的数量时，只显示数据源的button
		int size = buttons.size() > modularList.size() ? modularList.size() : buttons.size();
		
		for (int i = 0; i < size; i++) {
			com.tixa.industry.model.Button button_model = buttons.get(i);
			ZoomButton preButton = null;
			com.tixa.industry.model.Button pButton = null;
			if (zooms.size() > 0) {
				preButton = zooms.get(i - 1);
			}
			if (i > 0) {
				pButton = buttons.get(i - 1);
			}
			
			ZoomButton zbtn = util.formatButton(button_model, preButton,
					pButton);
			Button btn = new Button(context);
			btn.setId(i + 1);
			
			// 当按钮里边的字距离按钮的 上方以及左边 为0 的时候 默认文字是居中的
			btn.setGravity(zbtn.getTextGravity());
			
			if (zbtn.getTextShowType() == ZoomButton.TEXT_IN_BUTTON) { // 字在按钮内
				btn.setPadding(zbtn.getTextOffsetLeft(),
						zbtn.getTextOffsetTop(), zbtn.getTextOffsetRight(),
						zbtn.getTextOffsetBottom());

				// System.out.println("字体左边偏移量为 "+zbtn.getTextOffsetLeft());

				TextPaint tp = btn.getPaint();
				tp.setFakeBoldText(true);
				int rgbs[] = zbtn.getTextRGBColor();
				btn.setTextColor(Color.rgb(rgbs[0], rgbs[1], rgbs[2]));

				String text = modularList.get(i).getModularName();
				if (zbtn.getTextLines() > 1) {
					if (text.length() > 2) {
						text = text.substring(0, 2) + "\n" + text.substring(2);
					}
				}

				btn.setText(text);
				btn.setTextSize(16);

			} else { // 字在按钮外
				TextView text = new TextView(context);
				params = new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT); // 按钮
				params.setMargins(zbtn.getTextMarginLeft(),
						zbtn.getTextMarginTop(), 0, 0);
				TextPaint tp = text.getPaint();
				tp.setFakeBoldText(true);
				int rgbs[] = zbtn.getTextRGBColor();
				text.setTextColor(Color.rgb(rgbs[0], rgbs[1], rgbs[2]));
				String texts = modularList.get(i).getModularName();
				if (zbtn.getTextLines() > 1) {
					if (text.length() > 2) {
						texts = texts.substring(0, 2) + "\n"
								+ texts.substring(2);
					}
				}
				text.setText(texts);
				text.setTextSize(16);
				panel.addView(text, params);
			}
			params = new LayoutParams(zbtn.getButtonWidth(),
					zbtn.getButtonHeight()); // 按钮
			params.setMargins(zbtn.getOffsetX(), zbtn.getOffsetY(), 0, 0);
			btn.setMaxLines(zbtn.getTextLines());
			btn.setOnClickListener(new MyClickListener(i));
			btn.setBackgroundResource(R.drawable.btn1 + i);
			panel.addView(btn, params);
			zooms.add(zbtn);
		}
		
		// icon
		icon = new ImageView(context);
		String imgpath = indexData.getAppIcon();
		if(StrUtil.isNotEmpty(imgpath)) {
			FileUtil.setImage(icon, InterfaceURL.IMGIP + imgpath, loader,
					R.drawable.logo);
		}
		
		//logo
		ZoomLogo logo = util.formatLogo(block);
		params = new LayoutParams(logo.getLogoWidth(), logo.getLogoHight());
		params.setMargins(logo.getLogoMarginLeft(), logo.getLogoMarginTop(), 0,
				0);
		panel.addView(icon, params);
		
		//banner
		ZoomAd ad = util.formatAd(block);
		banner = new BannerView(context, adList, pageStyle, ad.getLogoWidth(), ad.getLogoHight());
		banner.show();
		params = new LayoutParams(ad.getLogoWidth(), ad.getLogoHight());
		params.setMargins(ad.getLogoMarginLeft(), ad.getLogoMarginTop(), 0, 0);		
		panel.addView(banner, params);
				
	}
	
	class MyClickListener implements OnClickListener {
		private int position;

		public MyClickListener(int arg) {
			this.position = arg;
		}

		@Override
		public void onClick(View v) {
			try{
				Modular modular = modularList.get(position);
				if(modular != null) {
					ButtonClick(modular);
				}
			}catch (Exception e) {
				L.e("首页解析错误 "+e.toString());
			}
			
		}
		
	}
	
	//栏目的点击事件
	public void ButtonClick(Modular modular) {
		ModularConfig config = map.get(modular.getType());
		final long showType = modular.getShowType();			
		String className = CommonUtil.formatClassName(application, config, showType);
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
			L.e("首页ButtonClick错误 "+e.toString());
		}	
	}
	
	
	
}
