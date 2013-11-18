package com.tixa.industry.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.tixa.industry.R;
import com.tixa.industry.adapter.SimpleListPicAdapter;
import com.tixa.industry.modelCustom.ProvideAndNeed;
import com.tixa.industry.widgetCustom.TopBar;
import com.tixa.industry.widgetCustom.TopBar.TopBarListener;

/**
 * @author administrator
 * @version
 * 
 */
public class RouteDetailActivity extends Activity implements
		OnItemClickListener {
	private Context context;
	private TextView start;
	private TextView end;
	private ListView list;
	private ProvideAndNeed goodsDetail;
	private int position;
	private String type;
	private TopBar topbar;
	private TransitOverlay transitOverlay;
	private RouteOverlay routeOverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_route_detail);
		context = this;
		initData();
		initView();
	}

	private void initData() {
		goodsDetail = (ProvideAndNeed) getIntent().getSerializableExtra(
				"goodsDetail");
		position = getIntent().getIntExtra("position", 0);
		type = getIntent().getStringExtra("type");

	}

	private void initView() {
		topbar = (TopBar) findViewById(R.id.topbar);
		topbar.setShowConfig("线路" + (position + 1), R.drawable.top_back, 0);
		topbar.setTopBarListener(new TopBarListener() {

			@Override
			public void btnRightOnClick() {

			}

			@Override
			public void btnLeftOnClick() {
				finish();

			}
		});
		start = (TextView) findViewById(R.id.start);
		end = (TextView) findViewById(R.id.end);
		list = (ListView) findViewById(R.id.detail);
		start.setText("我的位置");
		end.setText(goodsDetail.getGoodsName());
		//

		if (type.equals(RouteListActivity.TRANSIT)) {
			transitOverlay = new TransitOverlay(this, null);
			transitOverlay.setData(RouteListActivity.mkTransitRouteResult
					.getPlan(position));
			SimpleListPicAdapter<TransitOverlay> adapter = new SimpleListPicAdapter<TransitOverlay>(
					context, transitOverlay, RouteListActivity.TRANSIT);
			list.setAdapter(adapter);

		} else {
			routeOverlay = new RouteOverlay(this, null);
			SimpleListPicAdapter<RouteOverlay> adapter = null;
			if (type.equals(RouteListActivity.DRIVER)) {
				routeOverlay.setData(RouteListActivity.mkDrivingRouteResult
						.getPlan(0).getRoute(position));
				adapter = new SimpleListPicAdapter<RouteOverlay>(context,
						routeOverlay, RouteListActivity.DRIVER);
			} else if (type.equals(RouteListActivity.WALK)) {
				routeOverlay.setData(RouteListActivity.mkWalkingRouteResult
						.getPlan(0).getRoute(position));
				adapter = new SimpleListPicAdapter<RouteOverlay>(context,
						routeOverlay, RouteListActivity.WALK);
			}

			list.setAdapter(adapter);
		}
		list.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int arg0, long id) {
		Intent intent = new Intent(context, RouteMapActivity.class);
		intent.putExtra("position", position);
		intent.putExtra("type", type);
		startActivity(intent);

	}
}
