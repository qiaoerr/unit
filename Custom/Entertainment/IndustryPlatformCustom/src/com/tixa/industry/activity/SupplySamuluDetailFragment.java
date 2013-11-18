package com.tixa.industry.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.model.Goods;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.TopBar;

/**
 * 抓取的产品类型
 * @author shengy
 *
 */
public class SupplySamuluDetailFragment extends Fragment {
	private FragmentActivity context;
	private View view;
	private ImageView imageDetail;
	private TextView companyName;
	private TextView price;
	private TextView companyposition; //商品详细
	private IndustryApplication application;
	private String appID;
	private PageConfig config;
	private TopBar topbar;
	private TopBarUtil util;
	private Goods good;
	private AsyncImageLoader loader;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		view = inflater.inflate(R.layout.produce_detail_no_bottombar, null);
		initView();
		getPageConfig();
		initData();
		return view;
	}

	private void initData() {
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();		
		util = new TopBarUtil(false, naviStyle, topbar, "商品展示" , getFragmentManager() , context,
				application.getTemplateId() , true, navi);
		util.showConfig();
		
		good = (Goods) getArguments().getSerializable("goods");
		loader = new AsyncImageLoader(context);
		FileUtil.setImage(imageDetail, good.getGoodsImg(), loader, R.drawable.default_ad);
		
		companyName.setText(good.getGoodsName());
		
		price.setText(good.getGoodsPrice()+"");
	
		companyposition.setText(good.getGoodsDescStr());
	}

	private void initView() {
		topbar = (TopBar) view.findViewById(R.id.topbar);
		imageDetail = (ImageView) view.findViewById(R.id.imageDetail);
		companyName = (TextView) view.findViewById(R.id.companyName);
		price = (TextView) view.findViewById(R.id.price);		
		companyposition = (TextView) view.findViewById(R.id.companyposition_);
	}
	
	private void getPageConfig() {
		PageConfigParser p = new PageConfigParser(context,
				"layout/SupplyLayout.xml");
		config = p.parser();
	}
	
	
	
}
