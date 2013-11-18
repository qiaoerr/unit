package com.tixa.industry.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.RouteAdapter;
import com.tixa.industry.model.Data;
import com.tixa.industry.modelCustom.ProvideAndNeed;
import com.tixa.industry.widgetCustom.LoadView;
import com.tixa.industry.widgetCustom.TopBar;
import com.tixa.industry.widgetCustom.TopBar.TopBarListener;

/**
 * @author administrator
 * @version
 * 
 */
public class RouteListActivity extends Activity implements OnItemClickListener {
	private Context context;
	private MKSearch mkSearch;
	private TopBar topbar;
	private LoadView loadView;
	private BDLocation bdLocation;
	private MKPlanNode start;
	private MKPlanNode end;
	private ProvideAndNeed goodsDetail;
	private ListView transit_list;
	private ListView driver_list;
	private ListView walk_list;
	private int num = 0;
	public static MKWalkingRouteResult mkWalkingRouteResult;
	public static MKTransitRouteResult mkTransitRouteResult;
	public static MKDrivingRouteResult mkDrivingRouteResult;
	public final static String TRANSIT = "transit";
	public final static String DRIVER = "driver";
	public final static String WALK = "walk";

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Data.NONETWORK:
				loadView.noNetWork(new OnClickListener() {

					@Override
					public void onClick(View v) {
						loadView.loading();
						// 公交路线
						mkSearch.transitSearch(bdLocation.getCity(), start, end);
					}
				});
				break;
			case Data.NODATA:
				loadView.showNodataView();
				break;
			case Data.FULLDATA:
				loadView.close();
				if (mkTransitRouteResult != null) {
					RouteAdapter adapter = new RouteAdapter(context,
							mkTransitRouteResult);
					transit_list.setAdapter(adapter);
				}
				if (mkDrivingRouteResult != null) {
					RouteAdapter adapter = new RouteAdapter(context,
							mkDrivingRouteResult);
					driver_list.setAdapter(adapter);
				}
				if (mkWalkingRouteResult != null) {
					RouteAdapter adapter = new RouteAdapter(context,
							mkWalkingRouteResult);
					walk_list.setAdapter(adapter);
				}
				break;
			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_route_list);
		context = this;
		initData();
		initView();
		// System.out.println("this: " + this);

	}

	private void initData() {
		goodsDetail = (ProvideAndNeed) getIntent().getSerializableExtra(
				"goodsDetail");
		bdLocation = IndustryApplication.getInstance().getmBdLocation();
		start = new MKPlanNode();
		start.pt = new GeoPoint((int) (bdLocation.getLatitude() * 1e6),
				(int) (bdLocation.getLongitude() * 1e6));
		end = new MKPlanNode();
		end.pt = new GeoPoint((int) (goodsDetail.getLat() * 1e6),
				(int) (goodsDetail.getLng() * 1e6));
		mkSearch = new MKSearch();
		mkSearch.init(IndustryApplication.getInstance().getmBMapManager(),
				new MKSearchListener() {

					// 公交
					@Override
					public void onGetTransitRouteResult(
							MKTransitRouteResult arg0, int arg1) {
						Log.d("out", "onGetTransitRouteResult距离： "
								+ arg0.getPlan(0).getDistance());
						mkSearch.drivingSearch("", start, "", end);
						if (arg1 != 0) {
							num++;
						} else {
							mkTransitRouteResult = arg0;
						}
					}

					// 自驾
					@Override
					public void onGetDrivingRouteResult(
							MKDrivingRouteResult arg0, int arg1) {
						Log.d("out", "onGetDrivingRouteResult距离： "
								+ arg0.getPlan(0).getDistance());
						mkSearch.walkingSearch("", start, "", end);
						if (arg1 != 0) {
							num++;
						} else {
							mkDrivingRouteResult = arg0;
						}
					}

					// 步行
					@Override
					public void onGetWalkingRouteResult(
							MKWalkingRouteResult arg0, int arg1) {
						Log.d("out", "onGetWalkingRouteResult距离： "
								+ arg0.getPlan(0).getDistance());

						if (arg1 != 0) {
							if (num == 2) {
								handler.sendEmptyMessage(Data.NODATA);
							}
						} else {
							mkWalkingRouteResult = arg0;
							handler.sendEmptyMessage(Data.FULLDATA);
						}

					}

					@Override
					public void onGetSuggestionResult(MKSuggestionResult arg0,
							int arg1) {
					}

					@Override
					public void onGetPoiResult(MKPoiResult arg0, int arg1,
							int arg2) {
					}

					@Override
					public void onGetPoiDetailSearchResult(int arg0, int arg1) {
					}

					@Override
					public void onGetBusDetailResult(MKBusLineResult arg0,
							int arg1) {
					}

					@Override
					public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
					}
				});
	}

	private void initView() {
		transit_list = (ListView) findViewById(R.id.transit);
		driver_list = (ListView) findViewById(R.id.driver);
		walk_list = (ListView) findViewById(R.id.walk);
		transit_list.setOnItemClickListener(this);
		driver_list.setOnItemClickListener(this);
		walk_list.setOnItemClickListener(this);
		topbar = (TopBar) findViewById(R.id.topbar);
		topbar.setShowConfig("线路列表", R.drawable.top_back, 0);
		topbar.setTopBarListener(new TopBarListener() {

			@Override
			public void btnRightOnClick() {

			}

			@Override
			public void btnLeftOnClick() {
				finish();

			}
		});
		loadView = (LoadView) findViewById(R.id.loadview);
		loadView.loading();
		// 公交路线
		Log.d("out", "city: " + bdLocation.getCity());
		mkSearch.transitSearch(bdLocation.getCity(), start, end);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(context, RouteDetailActivity.class);
		intent.putExtra("goodsDetail", goodsDetail);
		intent.putExtra("position", position);
		if (parent.getId() == R.id.transit) {
			intent.putExtra("type", RouteListActivity.TRANSIT);
		}
		if (parent.getId() == R.id.driver) {
			intent.putExtra("type", RouteListActivity.DRIVER);
		}
		if (parent.getId() == R.id.walk) {
			intent.putExtra("type", RouteListActivity.WALK);
		}
		startActivity(intent);

	}
}
